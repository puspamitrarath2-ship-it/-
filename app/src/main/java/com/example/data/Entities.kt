package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey val id: String,
    val name: String,
    val rollNo: String,
    val grade: String,
    val section: String,
    val parentId: String,
    val email: String,
    val avatarUrl: String,
    val remarks: String = ""
)

@Entity(tableName = "teachers")
data class TeacherEntity(
    @PrimaryKey val id: String,
    val name: String,
    val subject: String,
    val email: String,
    val phone: String,
    val avatarUrl: String
)

@Entity(tableName = "parents")
data class ParentEntity(
    @PrimaryKey val id: String,
    val name: String,
    val childId: String,
    val email: String,
    val phone: String
)

@Entity(tableName = "attendance")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: String,
    val date: String, // yyyy-MM-dd
    val status: String, // Present, Absent, Late
    val lateMinutes: Int = 0
)

@Entity(tableName = "homework")
data class HomeworkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val subject: String,
    val grade: String,
    val dueDate: String,
    val teacherId: String,
    val submissionText: String = "" // In a real app we'd have a separate submissions table, but a single workspace text simplifies client demo!
)

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val subject: String,
    val grade: String,
    val questionsJson: String // Serialized list of questions
)

@Entity(tableName = "exam_results")
data class ExamResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: String,
    val subject: String,
    val examName: String, // Term I, Term II, Finals
    val marksObtained: Double,
    val totalMarks: Double,
    val gradeChar: String,
    val term: String // e.g. "Term I"
)

@Entity(tableName = "fee_invoices")
data class FeeInvoiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: String,
    val title: String,
    val amount: Double,
    val dueDate: String,
    val status: String, // Pending, Paid
    val paymentDate: String = "",
    val receiptNo: String = ""
)

@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val type: String, // Notice, Circular, Holiday, Event, Emergency
    val date: String, // yyyy-MM-dd
    val priority: String = "Normal" // High, Normal
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val senderId: String,
    val senderName: String,
    val senderRole: String, // Teacher, Parent, Admin, Student
    val receiverId: String,
    val receiverName: String,
    val receiverRole: String,
    val messageText: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class Question(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
