package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "school_phulbani_db"
    )
    .fallbackToDestructiveMigration()
    .build()

    val repository = SchoolRepository(db)

    // --- Firebase Auth & Local Sandbox ---
    val authManager = AuthManager(application)
    
    private val _authUser = MutableStateFlow<AuthUser?>(null)
    val authUser: StateFlow<AuthUser?> = _authUser.asStateFlow()

    private val _authLoading = MutableStateFlow(false)
    val authLoading: StateFlow<Boolean> = _authLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    // --- Authentication & Role states ---
    // User Roles: "Admin", "Teacher", "Student", "Parent"
    val _currentRole = MutableStateFlow("Student")
    val currentRole: StateFlow<String> = _currentRole.asStateFlow()

    // Logged in IDs
    val currentStudentId = MutableStateFlow("s1")
    val currentTeacherId = MutableStateFlow("t1")
    val currentParentId = MutableStateFlow("p1")

    fun login(email: String, password: String) {
        _authLoading.value = true
        _authError.value = null
        authManager.login(email, password) { result ->
            _authLoading.value = false
            result.fold(
                onSuccess = { user ->
                    _authUser.value = user
                    _currentRole.value = user.role
                    authManager.saveActiveSession(user.email)
                    
                    // Assign active ID
                    when (user.role) {
                        "Student" -> currentStudentId.value = user.associatedId
                        "Teacher" -> currentTeacherId.value = user.associatedId
                        "Parent" -> {
                            currentParentId.value = user.associatedId
                            currentStudentId.value = "s1"
                        }
                    }
                },
                onFailure = { error ->
                    _authError.value = error.localizedMessage ?: "Login failed"
                }
            )
        }
    }

    fun register(email: String, password: String, name: String, role: String, associatedId: String) {
        _authLoading.value = true
        _authError.value = null
        authManager.register(email, password, name, role, associatedId) { result ->
            _authLoading.value = false
            result.fold(
                onSuccess = { user ->
                    _authUser.value = user
                    _currentRole.value = user.role
                    authManager.saveActiveSession(user.email)
                    
                    // Assign active ID
                    when (user.role) {
                        "Student" -> currentStudentId.value = user.associatedId
                        "Teacher" -> currentTeacherId.value = user.associatedId
                        "Parent" -> {
                            currentParentId.value = user.associatedId
                            currentStudentId.value = "s1"
                        }
                    }
                },
                onFailure = { error ->
                    _authError.value = error.localizedMessage ?: "Registration failed"
                }
            )
        }
    }

    fun logout() {
        authManager.logout()
        authManager.saveActiveSession(null)
        _authUser.value = null
        _authError.value = null
    }

    fun clearAuthError() {
        _authError.value = null
    }

    // --- Dynamic UI State ---
    val allStudents = repository.allStudents.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allTeachers = repository.allTeachers.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allParents = repository.allParents.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allAttendance = repository.allAttendance.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allHomework = repository.allHomework.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allQuizzes = repository.allQuizzes.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allExamResults = repository.allExamResults.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allInvoices = repository.allInvoices.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allNotices = repository.allNotices.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val allChatMessages = repository.allChatMessages.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // AI States
    private val _aiResponse = MutableStateFlow("")
    val aiResponse: StateFlow<String> = _aiResponse.asStateFlow()

    private val _aiResultLoading = MutableStateFlow(false)
    val aiResultLoading: StateFlow<Boolean> = _aiResultLoading.asStateFlow()

    // Interactive Quiz State
    private val _activeQuizAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap()) // QuestId -> SelectedOptIndex
    val activeQuizAnswers: StateFlow<Map<Int, Int>> = _activeQuizAnswers.asStateFlow()

    private val _quizScoreText = MutableStateFlow("")
    val quizScoreText: StateFlow<String> = _quizScoreText.asStateFlow()

    init {
        // Seed default records on launch
        viewModelScope.launch {
            repository.allStudents.first().let { list ->
                if (list.isEmpty()) {
                    repository.seedMockData()
                }
            }
            
            // Check if there is an active authenticated user session on launch
            val loggedInUser = authManager.getCurrentlyLoggedInUser()
            if (loggedInUser != null) {
                _authUser.value = loggedInUser
                _currentRole.value = loggedInUser.role
                // Align loaded logged-in ID
                when (loggedInUser.role) {
                    "Student" -> currentStudentId.value = loggedInUser.associatedId
                    "Teacher" -> currentTeacherId.value = loggedInUser.associatedId
                    "Parent" -> {
                        currentParentId.value = loggedInUser.associatedId
                        currentStudentId.value = "s1"
                    }
                }
            }
        }
    }

    // --- Actions ---

    fun changeRole(role: String) {
        _currentRole.value = role
        // Align default user selections
        when (role) {
            "Student" -> {
                currentStudentId.value = "s1"
                currentParentId.value = "p1"
            }
            "Teacher" -> {
                currentTeacherId.value = "t1"
            }
            "Parent" -> {
                currentStudentId.value = "s1"
                currentParentId.value = "p1"
            }
        }
    }

    // 1. One-Tap Attendance Recording
    fun markAttendance(studentId: String, status: String, lateMinutes: Int = 0) {
        viewModelScope.launch {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val todayStr = formatter.format(Date())
            val record = AttendanceEntity(
                studentId = studentId,
                date = todayStr,
                status = status,
                lateMinutes = lateMinutes
            )
            repository.insertAttendance(record)
        }
    }

    // 2. Publish Homework Assignment
    fun publishHomework(title: String, description: String, subject: String, grade: String, dueDate: String) {
        viewModelScope.launch {
            val hw = HomeworkEntity(
                title = title,
                description = description,
                subject = subject,
                grade = grade,
                dueDate = dueDate,
                teacherId = currentTeacherId.value
            )
            repository.insertHomework(hw)
        }
    }

    // 3. Student Submits Workspace Answer
    fun submitHomeworkWorkspace(homeworkId: Long, subject: String, title: String, answerText: String) {
        viewModelScope.launch {
            // Fetch homework details or update directly
            val allHws = allHomework.value
            val currentHw = allHws.find { it.id == homeworkId }
            if (currentHw != null) {
                val updatedHw = currentHw.copy(submissionText = answerText)
                repository.insertHomework(updatedHw)
            }
        }
    }

    // Delete notice (Admin capability)
    fun deleteNotice(noticeId: Long) {
        viewModelScope.launch {
            repository.deleteNoticeById(noticeId)
        }
    }

    // Delete homework (Teacher capability)
    fun deleteHomework(hwId: Long) {
        viewModelScope.launch {
            repository.deleteHomeworkById(hwId)
        }
    }

    // Add Notice (Admin capability)
    fun publishNotice(title: String, content: String, type: String, priority: String) {
        viewModelScope.launch {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateStr = formatter.format(Date())
            val notice = NoticeEntity(
                title = title,
                content = content,
                type = type,
                date = dateStr,
                priority = priority
            )
            repository.insertNotice(notice)
        }
    }

    // Submit Quiz Answer & Grade it
    fun selectQuizAnswer(questionId: Int, optionIndex: Int) {
        val currentAnswers = _activeQuizAnswers.value.toMutableMap()
        currentAnswers[questionId] = optionIndex
        _activeQuizAnswers.value = currentAnswers
    }

    fun submitAndGradeQuiz(questions: List<Question>, quizTitle: String, subject: String) {
        var correctCount = 0
        questions.forEach { question ->
            val selected = _activeQuizAnswers.value[question.id]
            if (selected == question.correctAnswerIndex) {
                correctCount++
            }
        }
        val pct = (correctCount.toDouble() / questions.size.toDouble()) * 100.0
        val gradeChar = when {
            pct >= 90 -> "A+"
            pct >= 80 -> "A"
            pct >= 70 -> "B"
            else -> "C"
        }
        
        _quizScoreText.value = "Score: $correctCount / ${questions.size} (${pct.toInt()}%) - Grade $gradeChar"
        
        // Save score to exam_results for s1
        viewModelScope.launch {
            val result = ExamResultEntity(
                studentId = currentStudentId.value,
                subject = subject,
                examName = quizTitle,
                marksObtained = correctCount.toDouble(),
                totalMarks = questions.size.toDouble(),
                gradeChar = gradeChar,
                term = "Online Quiz"
            )
            repository.insertExamResult(result)
        }
    }

    fun publishExamScore(studentId: String, subject: String, examName: String, marksObtained: Double, totalMarks: Double, gradeChar: String) {
        viewModelScope.launch {
            repository.insertExamResult(
                ExamResultEntity(
                    studentId = studentId,
                    subject = subject,
                    examName = examName,
                    marksObtained = marksObtained,
                    totalMarks = totalMarks,
                    gradeChar = gradeChar,
                    term = "Scholastic Assessment"
                )
            )
        }
    }

    fun resetQuiz() {
        _activeQuizAnswers.value = emptyMap()
        _quizScoreText.value = ""
    }

    // 4. Pay Fees Online (Generates a receipt securely & updates Room)
    fun payInvoice(invoiceId: Long) {
        viewModelScope.launch {
            val invoices = allInvoices.value
            val target = invoices.find { it.id == invoiceId }
            if (target != null) {
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val todayStr = formatter.format(Date())
                val receiptNumber = "PS-${100000 + Random().nextInt(899999)}"
                val updated = target.copy(
                    status = "Paid",
                    paymentDate = todayStr,
                    receiptNo = receiptNumber
                )
                repository.insertInvoice(updated)
            }
        }
    }

    // 5. Send Chat Message between teacher & parent
    fun sendConversationMessage(receiverId: String, receiverName: String, receiverRole: String, text: String) {
        viewModelScope.launch {
            val senderId = when (currentRole.value) {
                "Teacher" -> currentTeacherId.value
                "Student" -> currentStudentId.value
                "Parent" -> currentParentId.value
                else -> "admin"
            }
            // Simple Sender Name resolution
            val senderName = when (currentRole.value) {
                "Teacher" -> "Mr. Rajesh Mohanty"
                "Student" -> "Manoj Kumar Rath (Grade 10)"
                "Parent" -> "Mr. Alok Rath"
                else -> "Administrator"
            }
            val msg = ChatMessageEntity(
                senderId = senderId,
                senderName = senderName,
                senderRole = currentRole.value,
                receiverId = receiverId,
                receiverName = receiverName,
                receiverRole = receiverRole,
                messageText = text
            )
            repository.sendChatMessage(msg)
        }
    }

    // --- AI Integration Methods ---

    fun clearAiResponse() {
        _aiResponse.value = ""
    }

    // AI Homework Helper
    fun askAiHomeworkHelper(homeworkTitle: String, homeworkDesc: String) {
        _aiResultLoading.value = true
        _aiResponse.value = "AI is thinking, studying homework parameters..."
        viewModelScope.launch {
            val prompt = """
                Homework Title: $homeworkTitle
                Description: $homeworkDesc
                
                Please act as an inspiring, supportive AI homework guide. Give the student a helpful explanation of the concepts, relevant standard formulas, step-by-step logic, and a guided hint to solve it. DO NOT just write the final direct answer for cutting and pasting—encourage the student to solve it.
            """.trimIndent()
            val instruction = "You are the primary AI Tutor at Public School, Phulbani. Guide students with CBSE 10th standard pedagogy."
            val response = repository.askGemini(prompt, instruction)
            _aiResponse.value = response
            _aiResultLoading.value = false
        }
    }

    // AI Study Planner
    fun generateAiStudyPlan(grade: String) {
        _aiResultLoading.value = true
        _aiResponse.value = "AI is generating a customized academic Study Plan..."
        viewModelScope.launch {
            val prompt = """
                Generate a highly actionable weekly study timetable and planning guide for a CBSE student in $grade at Public School, Phulbani.
                Include:
                1. Dedicated subject hours (Math, Science, Social Studies, English).
                2. Active study slots (Feynman technique, revision loops).
                3. Weekly goal checklist.
                Make the plan inspiring, positive, and formatted with clean bullet points.
            """.trimIndent()
            val response = repository.askGemini(prompt, "You are an expert CBSE student academic advisor. Craft highly structural, practical studies plans.")
            _aiResponse.value = response
            _aiResultLoading.value = false
        }
    }

    // AI Doubt Solver
    fun askAiDoubtSolver(doubtText: String, subject: String) {
        _aiResultLoading.value = true
        _aiResponse.value = "AI is consulting CBSE curriculum files to solve your doubt..."
        viewModelScope.launch {
            val prompt = """
                Subject: $subject
                Doubt: "$doubtText"
                
                Please solve this specific academic doubt in detail. Give definitions, step-by-step equations, clean structural text explanations, and a practical example to make it extremely clear for a student to score full marks in CBSE exams.
            """.trimIndent()
            val instruction = "You are the resident CBSE master teacher at Public School, Phulbani. Solve academic doubts in a structured, inspiring format."
            val response = repository.askGemini(prompt, instruction)
            _aiResponse.value = response
            _aiResultLoading.value = false
        }
    }

    // AI Academic Performance Analysis and Learning Suggestions
    fun runAiPerformanceAnalysis() {
        _aiResultLoading.value = true
        _aiResponse.value = "AI is retrieving Grade 10 marksheets and daily attendance logs..."
        viewModelScope.launch {
            val studentId = currentStudentId.value
            // Build descriptions
            val scores = allExamResults.value.filter { it.studentId == studentId }
            val attRecords = allAttendance.value.filter { it.studentId == studentId }
            
            val totalAttDays = attRecords.size
            val presentDays = attRecords.count { it.status == "Present" }
            val lateDays = attRecords.count { it.status == "Late" }
            val attRate = if(totalAttDays > 0) ((presentDays + lateDays).toDouble() / totalAttDays * 100.0).toInt() else 100

            val scoreSummary = scores.joinToString("; ") { "${it.subject} (${it.examName}): ${it.marksObtained}/${it.totalMarks} [Grade: ${it.gradeChar}]" }
            
            val prompt = """
                Student Profile: Manoj Kumar Rath (Grade 10, Public School Phulbani)
                CBSE Examination Scores: $scoreSummary
                Smart Attendance Status: Total school days analyzed: $totalAttDays (Present: ${presentDays + lateDays}, Attendance Rate: $attRate%)
                
                Analyze the student's performance:
                1. Calculate the overall average and comment on the strength of the student.
                2. Evaluate how attendance ($attRate%) affects performance.
                3. Highlight specific subjects that need focus.
                4. Provide 3 customized strategic learning tips.
                Write this in a warm, encouraging, professional, and descriptive letter style for parents and students.
            """.trimIndent()
            val instruction = "You are the AI Academic Counselor at Public School, Phulbani. Deliver beautiful pointwise performance analysis reports."
            val response = repository.askGemini(prompt, instruction)
            _aiResponse.value = response
            _aiResultLoading.value = false
        }
    }

    // AI Report Card Generation for Teacher/Admin
    fun generateAiReportSummary(selectedStudentName: String) {
        _aiResultLoading.value = true
        _aiResponse.value = "AI is generating a holistic, professional CBSE Grade 10 Executive Report..."
        viewModelScope.launch {
            val prompt = """
                Selected Student: $selectedStudentName
                Generate a professional executive academic report card report card for this student of Public School, Phulbani (CBSE Affiliated).
                Include:
                - Overall scholastic rating (Scholastic & Co-scholastic areas).
                - Brief evaluation of cognitive skills (understanding of concepts, analytical prowess).
                - A formal teacher's recommendation remarks focusing on discipline, potential, and future progress.
                Wrap it in highly clean, professional school administration language.
            """.trimIndent()
            val response = repository.askGemini(prompt, "You are the Chief Academic Evaluator at Public School, Phulbani. Write high-quality formal academic progress summaries.")
            _aiResponse.value = response
            _aiResultLoading.value = false
        }
    }
}
