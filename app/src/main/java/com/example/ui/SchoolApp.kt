package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun VibrantAppHeader(
    currentRole: String,
    onRoleSwitcherClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary, // Vibrant Blue 700
        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 18.dp, top = 20.dp, bottom = 18.dp)
        ) {
            // Header Top Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Navigation Hamburger icon button linked to role switcher
                    IconButton(
                        onClick = onRoleSwitcherClick,
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color.White.copy(alpha = 0.15f), CircleShape)
                            .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)), CircleShape)
                            .testTag("nav_drawer_toggle")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open Navigation Drawer",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Curved logo holder
                    Surface(
                        modifier = Modifier.size(38.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White
                    ) {
                        Image(
                            painter = painterResource(id = com.example.R.drawable.school_logo_1780650329779),
                            contentDescription = "School Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().padding(2.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Public School",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Phulbani * Est. 1986",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 9.sp,
                            color = Color.White.copy(alpha = 0.82f),
                            letterSpacing = 0.8.sp
                        )
                    }
                }

                // Notification Indicator Button styled like a bell
                IconButton(
                    onClick = { /* Action placeholder */ },
                    modifier = Modifier
                        .size(34.dp)
                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                        .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Integrated User Card / Role Selector Combo
            Surface(
                color = Color.White.copy(alpha = 0.12f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Circular Avatar
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .border(BorderStroke(1.dp, Color.White), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (currentRole) {
                                "Student" -> "M"
                                "Parent" -> "P"
                                "Teacher" -> "T"
                                else -> "A"
                            },
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Welcome back,",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = when (currentRole) {
                                "Student" -> "Manoj Kumar Rath"
                                "Parent" -> "Puspamitra Rath"
                                "Teacher" -> "Mrs. Anita Sahu"
                                else -> "Admin Desk"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color.White
                        )
                    }

                    // Role pill button
                    Surface(
                        onClick = onRoleSwitcherClick,
                        color = Color.White.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.testTag("role_switcher_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(Color(0xFFF59E0B), CircleShape)
                            )
                            Text(
                                text = currentRole.uppercase(),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GlassmorphicDrawerContent(
    currentRole: String,
    onRoleSelected: (String) -> Unit,
    onCloseClick: () -> Unit,
    students: List<StudentEntity>,
    teachers: List<TeacherEntity>,
    notices: List<NoticeEntity>,
    invoices: List<FeeInvoiceEntity>,
    onLogoutClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(290.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xF2FFFFFF),
                        Color(0xE6F8FAFC)
                    )
                )
            )
            .border(
                BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                RoundedCornerShape(0.dp)
            )
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Drawer Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
                        ) {
                            Image(
                                painter = painterResource(id = com.example.R.drawable.school_logo_1780650329779),
                                contentDescription = "School Logo",
                                modifier = Modifier.fillMaxSize().padding(2.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Public School",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Phulbani CBSE-1530076",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                    IconButton(onClick = onCloseClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Sidebar",
                            tint = Color(0xFF64748B)
                        )
                    }
                }

                HorizontalDivider(color = Color(0xFFE2E8F0))

                // Title Section
                Text(
                    text = "ROLE-BASED ACCESS CONTROL",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF94A3B8),
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                // List of Roles
                val rolesList = listOf("Student", "Parent", "Teacher", "Administrator")
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    rolesList.forEach { role ->
                        val isSelected = currentRole == role
                        val (icon, tint, desc) = when (role) {
                            "Student" -> Triple(Icons.Default.School, MaterialTheme.colorScheme.primary, "View Academic Workspace & Homework")
                            "Parent" -> Triple(Icons.Default.People, Color(0xFFE11D48), "Monitor child logs, attendance & pay billing")
                            "Teacher" -> Triple(Icons.Default.Class, Color(0xFFD97706), "Publish homework grades & logs")
                            else -> Triple(Icons.Default.AdminPanelSettings, Color(0xFF16A34A), "Access administrative center counters")
                        }

                        Surface(
                            onClick = { onRoleSelected(role) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0x1F3B82F6) else Color.White.copy(alpha = 0.5f),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) Color(0xFF3B82F6) else Color(0xFFE2E8F0)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("select_role_$role")
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(
                                            if (isSelected) Color(0xFF3B82F6) else tint.copy(alpha = 0.1f),
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.White else tint,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = role,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isSelected) Color(0xFF1E3A8A) else Color(0xFF334155)
                                    )
                                    Text(
                                        text = desc,
                                        fontSize = 9.sp,
                                        lineHeight = 12.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(Color(0xFF3B82F6), CircleShape)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Bottom brand summary & Secure sign out
            val studentCount = remember(students) { students.size }
            val teacherCount = remember(teachers) { teachers.size }
            val noticeCount = remember(notices) { notices.size }
            val complianceText = remember(studentCount, teacherCount, noticeCount) {
                "• CBSE Board Success Rate: 100%\n" +
                "• Total Enrollments: ${studentCount}+ Live\n" +
                "• Registered Teachers: ${teacherCount}\n" +
                "• Active General Circulars: ${noticeCount}"
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFEFF6FF))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "SCHOOL COMPLIANCE",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3B82F6)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = complianceText,
                        fontSize = 9.sp,
                        lineHeight = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1E3A8A)
                    )
                }

                Button(
                    onClick = onLogoutClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .testTag("drawer_logout_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Secured Log Out",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Secured Log Out", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SchoolStatsGlassContainer(
    students: List<StudentEntity>,
    teachers: List<TeacherEntity>,
    notices: List<NoticeEntity>,
    invoices: List<FeeInvoiceEntity>,
    attendance: List<AttendanceEntity>
) {
    var isExpanded by remember { mutableStateOf(false) }

    val totalStudents = remember(students) { students.size }
    val totalTeachers = remember(teachers) { teachers.size }
    val totalNotices = remember(notices) { notices.size }
    val unpaidBills = remember(invoices) { invoices.count { !it.status.contains("Paid", ignoreCase = true) } }
    
    val overallAttendanceRate = remember(attendance) {
        if (attendance.isNotEmpty()) {
            val presents = attendance.count { it.status == "Present" || it.status == "Late" }
            ((presents.toDouble() / attendance.size) * 100).toInt()
        } else {
            94
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = Color(0xE6EFF6FF)), // Soft translucent blue
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color(0xFF3B82F6).copy(alpha = 0.15f), CircleShape)
                            .border(BorderStroke(1.dp, Color(0xFF3B82F6).copy(alpha = 0.3f)), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Central School Statistics Shell",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF1E3A8A)
                        )
                        Text(
                            text = "Primary monitoring center database logs",
                            fontSize = 9.sp,
                            color = Color(0xFF475569)
                        )
                    }
                }

                // Interactive Chevron toggle with springs
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Toggle Stats Panel Detail",
                        tint = Color(0xFF2563EB)
                    )
                }
            }

            // Compact horizontal stats banner
            AnimatedVisibility(
                visible = !isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatBubble("Enrollments", "$totalStudents Live")
                    StatDivider()
                    StatBubble("Faculty", "$totalTeachers Staff")
                    StatDivider()
                    StatBubble("Alerts", "$unpaidBills Pending")
                    StatDivider()
                    StatBubble("Attendance", "$overallAttendanceRate%")
                }
            }

            // Expanded detail stats cards grid (Glassmorphic Container style!)
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DetailedStatsCard(
                            title = "STUDENT BASE",
                            value = "$totalStudents Pupils",
                            subtext = "CBSE registered batch group",
                            icon = Icons.Default.School,
                            iconBg = Color(0xFFEFF6FF),
                            iconColor = Color(0xFF3B82F6),
                            modifier = Modifier.weight(1f)
                        )
                        DetailedStatsCard(
                            title = "FACULTY COUNT",
                            value = "$totalTeachers Educators",
                            subtext = "Preseeded trained staff",
                            icon = Icons.Default.Class,
                            iconBg = Color(0xFFFEF3C7),
                            iconColor = Color(0xFFD97706),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DetailedStatsCard(
                            title = "CBSE ALERTS",
                            value = "$totalNotices Bulletins",
                            subtext = "Circulars in Notice board",
                            icon = Icons.Default.Campaign,
                            iconBg = Color(0xFFF0FDF4),
                            iconColor = Color(0xFF16A34A),
                            modifier = Modifier.weight(1f)
                        )
                        DetailedStatsCard(
                            title = "BILLING STATUS",
                            value = "$unpaidBills Invoices",
                            subtext = "Unpaid parent fee dues",
                            icon = Icons.Default.AccountBalanceWallet,
                            iconBg = Color(0xFFFFF1F2),
                            iconColor = Color(0xFFE11D48),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatBubble(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label.uppercase(), fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B), letterSpacing = 0.4.sp)
        Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A))
    }
}

@Composable
fun StatDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(18.dp)
            .background(Color(0xFFCBD5E1))
    )
}

@Composable
fun DetailedStatsCard(
    title: String,
    value: String,
    subtext: String,
    icon: ImageVector,
    iconBg: Color,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White.copy(alpha = 0.65f),
        border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(iconBg, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF64748B),
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = value,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = subtext,
                    fontSize = 8.sp,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}

@Composable
fun DashboardLayoutShell(
    currentRole: String,
    students: List<StudentEntity>,
    teachers: List<TeacherEntity>,
    notices: List<NoticeEntity>,
    invoices: List<FeeInvoiceEntity>,
    homeworks: List<HomeworkEntity>,
    quizzes: List<QuizEntity>,
    results: List<ExamResultEntity>,
    attendance: List<AttendanceEntity>,
    chatMessages: List<ChatMessageEntity>,
    viewModel: MainViewModel,
    onAddNoticeClick: () -> Unit,
    onAddHomeworkClick: () -> Unit,
    onAddResultClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Upper stats center in glass theme with its own side padding to align exactly with inner dashboards
        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)) {
            SchoolStatsGlassContainer(
                students = students,
                teachers = teachers,
                notices = notices,
                invoices = invoices,
                attendance = attendance
            )
        }

        // Nested Role-specific Dashboard
        Box(modifier = Modifier.weight(1f)) {
            when (currentRole) {
                "Administrator" -> AdminDashboardScreen(
                    students = students,
                    teachers = teachers,
                    notices = notices,
                    invoices = invoices,
                    viewModel = viewModel,
                    onAddNoticeClick = onAddNoticeClick
                )
                "Teacher" -> TeacherDashboardScreen(
                    students = students,
                    homeworks = homeworks,
                    viewModel = viewModel,
                    onAddHomeworkClick = onAddHomeworkClick,
                    onAddResultClick = onAddResultClick
                )
                "Student" -> StudentDashboardScreen(
                    students = students,
                    homeworks = homeworks,
                    quizzes = quizzes,
                    results = results,
                    attendance = attendance,
                    viewModel = viewModel
                )
                "Parent" -> ParentDashboardScreen(
                    students = students,
                    results = results,
                    attendance = attendance,
                    invoices = invoices,
                    teachers = teachers,
                    chatMessages = chatMessages,
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolApp(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val currentRole by viewModel.currentRole.collectAsStateWithLifecycle()
    val students by viewModel.allStudents.collectAsStateWithLifecycle()
    val teachers by viewModel.allTeachers.collectAsStateWithLifecycle()
    val notices by viewModel.allNotices.collectAsStateWithLifecycle()
    val homeworks by viewModel.allHomework.collectAsStateWithLifecycle()
    val quizzes by viewModel.allQuizzes.collectAsStateWithLifecycle()
    val results by viewModel.allExamResults.collectAsStateWithLifecycle()
    val invoices by viewModel.allInvoices.collectAsStateWithLifecycle()
    val attendance by viewModel.allAttendance.collectAsStateWithLifecycle()
    val chatMessages by viewModel.allChatMessages.collectAsStateWithLifecycle()

    var activeTab by remember { mutableStateOf("Home") } // Home, Dashboard, Community, About
    var showRolePickerDialog by remember { mutableStateOf(false) }

    // Dialog form states
    var showAddNoticeDialog by remember { mutableStateOf(false) }
    var showAddHomeworkDialog by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            GlassmorphicDrawerContent(
                currentRole = currentRole,
                onRoleSelected = { role ->
                    viewModel.changeRole(role)
                    scope.launch { drawerState.close() }
                },
                onCloseClick = {
                    scope.launch { drawerState.close() }
                },
                students = students,
                teachers = teachers,
                notices = notices,
                invoices = invoices,
                onLogoutClick = {
                    viewModel.logout()
                    scope.launch { drawerState.close() }
                }
            )
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                VibrantAppHeader(
                    currentRole = currentRole,
                    onRoleSwitcherClick = { scope.launch { drawerState.open() } }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .border(
                            BorderStroke(1.dp, Color(0xFFF1F5F9)),
                            RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        )
                ) {
                    NavigationBarItem(
                        selected = activeTab == "Home",
                        onClick = { activeTab = "Home" },
                        icon = { Icon(Icons.Default.Home, contentDescription = "School Info") },
                        label = { Text("Home Site") },
                        modifier = Modifier.testTag("nav_home")
                    )
                    NavigationBarItem(
                        selected = activeTab == "Dashboard",
                        onClick = { activeTab = "Dashboard" },
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                        label = { Text(currentRole) },
                        modifier = Modifier.testTag("nav_dashboard")
                    )
                    NavigationBarItem(
                        selected = activeTab == "Community",
                        onClick = { activeTab = "Community" },
                        icon = { Icon(Icons.Default.Forum, contentDescription = "Communications") },
                        label = { Text("Circulars") },
                        modifier = Modifier.testTag("nav_community")
                    )
                    NavigationBarItem(
                        selected = activeTab == "Admission",
                        onClick = { activeTab = "Admission" },
                        icon = { Icon(Icons.Default.HowToReg, contentDescription = "Admission") },
                        label = { Text("Admission") },
                        modifier = Modifier.testTag("nav_admission")
                    )
                }
            },
            modifier = modifier
        ) { innerPadding ->
            Crossfade(
                targetState = activeTab,
                animationSpec = spring(stiffness = androidx.compose.animation.core.Spring.StiffnessHigh),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
                label = "ScreenSwitchTransition"
            ) { tab ->
                when (tab) {
                    "Home" -> {
                        PublicSchoolHomePage(
                            notices = notices,
                            viewModel = viewModel,
                            onSwitchToTab = { activeTab = it }
                        )
                    }
                    "Dashboard" -> {
                        DashboardLayoutShell(
                            currentRole = currentRole,
                            students = students,
                            teachers = teachers,
                            notices = notices,
                            invoices = invoices,
                            homeworks = homeworks,
                            quizzes = quizzes,
                            results = results,
                            attendance = attendance,
                            chatMessages = chatMessages,
                            viewModel = viewModel,
                            onAddNoticeClick = { showAddNoticeDialog = true },
                            onAddHomeworkClick = { showAddHomeworkDialog = true },
                            onAddResultClick = { showResultDialog = true }
                        )
                    }
                    "Community" -> {
                        CircularNoticeHub(
                            notices = notices,
                            viewModel = viewModel
                        )
                    }
                    "Admission" -> {
                        DigitalAdmissionPortal()
                    }
                }
            }
        }
    }

    // --- Role Picker dialog ---
    if (showRolePickerDialog) {
        AlertDialog(
            onDismissRequest = { showRolePickerDialog = false },
            title = {
                Text(
                    "Switch User Access Module",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Public School, Phulbani includes personalized access endpoints for 4 strategic roles. Switch role below to test views:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    val roles = listOf("Student", "Parent", "Teacher", "Administrator")
                    roles.forEach { role ->
                        Surface(
                            onClick = {
                                viewModel.changeRole(role)
                                showRolePickerDialog = false
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = if (currentRole == role) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("select_role_$role")
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = when (role) {
                                        "Student" -> Icons.Default.School
                                        "Parent" -> Icons.Default.FamilyRestroom
                                        "Teacher" -> Icons.Default.SupervisorAccount
                                        else -> Icons.Default.AdminPanelSettings
                                    },
                                    contentDescription = "$role role",
                                    tint = if (currentRole == role) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = role,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (currentRole == role) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = when(role) {
                                            "Student" -> "Access Homework, online quizzes, and AI academic doubt solver"
                                            "Parent" -> "View child attendance, pay bills instantly, message teachers"
                                            "Teacher" -> "One-tap attendance, publish homework, grade results"
                                            else -> "School treasury, publish campus circulars, view overall analytics"
                                        },
                                        style = MaterialTheme.typography.bodySmall,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showRolePickerDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    // --- Add Notice Dialog ---
    if (showAddNoticeDialog) {
        var noticeTitle by remember { mutableStateOf("") }
        var noticeContent by remember { mutableStateOf("") }
        var noticeType by remember { mutableStateOf("Notice") }
        var noticePriority by remember { mutableStateOf("Normal") }

        AlertDialog(
            onDismissRequest = { showAddNoticeDialog = false },
            title = { Text("Publish New Campus Announcement", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    TextField(
                        value = noticeTitle,
                        onValueChange = { noticeTitle = it },
                        label = { Text("Notice Title") },
                        modifier = Modifier.fillMaxWidth().testTag("notice_title_input")
                    )
                    TextField(
                        value = noticeContent,
                        onValueChange = { noticeContent = it },
                        label = { Text("Content / Circular Guidelines") },
                        modifier = Modifier.fillMaxWidth().height(100.dp).testTag("notice_content_input")
                    )
                    Text("Announcement Type", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Circular", "Event", "Holiday", "Emergency").forEach { type ->
                            ElevatedFilterChip(
                                selected = noticeType == type,
                                onClick = { noticeType = type },
                                label = { Text(type) }
                            )
                        }
                    }
                    Text("Priority Level", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Normal", "High").forEach { prio ->
                            ElevatedFilterChip(
                                selected = noticePriority == prio,
                                onClick = { noticePriority = prio },
                                label = { Text(prio) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (noticeTitle.isNotBlank() && noticeContent.isNotBlank()) {
                            viewModel.publishNotice(noticeTitle, noticeContent, noticeType, noticePriority)
                            showAddNoticeDialog = false
                        }
                    },
                    modifier = Modifier.testTag("publish_notice_confirm")
                ) {
                    Text("Publish Notice")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddNoticeDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // --- Add Homework Dialog ---
    if (showAddHomeworkDialog) {
        var hwTitle by remember { mutableStateOf("") }
        var hwDesc by remember { mutableStateOf("") }
        var hwSubject by remember { mutableStateOf("Mathematics") }
        var hwGrade by remember { mutableStateOf("Grade 10") }
        var hwDueDate by remember { mutableStateOf("2026-06-15") }

        AlertDialog(
            onDismissRequest = { showAddHomeworkDialog = false },
            title = { Text("Assign New Homework", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    TextField(
                        value = hwTitle,
                        onValueChange = { hwTitle = it },
                        label = { Text("Homework Title") },
                        modifier = Modifier.fillMaxWidth().testTag("hw_title_input")
                    )
                    TextField(
                        value = hwDesc,
                        onValueChange = { hwDesc = it },
                        label = { Text("Task Description") },
                        modifier = Modifier.fillMaxWidth().height(80.dp).testTag("hw_desc_input")
                    )
                    TextField(
                        value = hwSubject,
                        onValueChange = { hwSubject = it },
                        label = { Text("Subject") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextField(
                            value = hwGrade,
                            onValueChange = { hwGrade = it },
                            label = { Text("Class Group") },
                            modifier = Modifier.weight(1f)
                        )
                        TextField(
                            value = hwDueDate,
                            onValueChange = { hwDueDate = it },
                            label = { Text("Due Date") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (hwTitle.isNotBlank() && hwDesc.isNotBlank()) {
                            viewModel.publishHomework(hwTitle, hwDesc, hwSubject, hwGrade, hwDueDate)
                            showAddHomeworkDialog = false
                        }
                    },
                    modifier = Modifier.testTag("publish_hw_confirm")
                ) {
                    Text("Assign Task")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddHomeworkDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // --- Publish Result Card Dialog ---
    if (showResultDialog) {
        var studentSelected by remember { mutableStateOf("s1") } // मनोज
        var subjectSelected by remember { mutableStateOf("Mathematics") }
        var examSelected by remember { mutableStateOf("Term I Unit Test") }
        var marksInput by remember { mutableStateOf(22.0) }
        var totalInput by remember { mutableStateOf(25.0) }

        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text("Publish Assessment Score", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Student: Manoj Kumar Rath (s1)", fontWeight = FontWeight.SemiBold)
                    TextField(
                        value = subjectSelected,
                        onValueChange = { subjectSelected = it },
                        label = { Text("Subject") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = examSelected,
                        onValueChange = { examSelected = it },
                        label = { Text("Exam / Test Cycle") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Marks Obtained: ${marksInput.toInt()}", style = MaterialTheme.typography.bodySmall)
                            Slider(
                                value = marksInput.toFloat(),
                                onValueChange = { marksInput = it.toDouble() },
                                valueRange = 0f..totalInput.toFloat()
                            )
                        }
                        TextField(
                            value = totalInput.toInt().toString(),
                            onValueChange = { totalInput = it.toDoubleOrNull() ?: 25.0 },
                            label = { Text("Max") },
                            modifier = Modifier.width(60.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val pct = (marksInput / totalInput) * 100.0
                        val gradeChar = when {
                            pct >= 90 -> "A+"
                            pct >= 80 -> "A"
                            pct >= 70 -> "B"
                            else -> "C"
                        }
                        viewModel.publishExamScore(
                            studentId = studentSelected,
                            subject = subjectSelected,
                            examName = examSelected,
                            marksObtained = marksInput,
                            totalMarks = totalInput,
                            gradeChar = gradeChar
                        )
                        showResultDialog = false
                    }
                ) {
                    Text("Publish Score")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResultDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// ==========================================
// HOME PAGE DESIGN SECTION (PUBLIC PORTAL)
// ==========================================
@Composable
fun PublicSchoolHomePage(
    notices: List<NoticeEntity>,
    viewModel: MainViewModel,
    onSwitchToTab: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val homeNotices = remember(notices) { notices.take(2) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Hero Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                        )
                    )
                )
                .drawBehind {
                    // Geometric decorative backgrounds representing education, research, rules
                    drawCircle(
                        color = Color.White.copy(alpha = 0.05f),
                        radius = 180.dp.toPx(),
                        center = Offset(size.width - 20.dp.toPx(), 40.dp.toPx())
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.03f),
                        radius = 280.dp.toPx(),
                        center = Offset(20.dp.toPx(), size.height - 30.dp.toPx())
                    )
                }
                .padding(24.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = " AFFILIATED TO CBSE, DELHI (NO. 1530076) ",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                }
                Text(
                    text = "Public School, Phulbani",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "Nurturing Character, Scholastic Rigor, Innovation, and Indian Values in Kandhamal Since 1986.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { onSwitchToTab("Dashboard") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        modifier = Modifier.testTag("home_dashboard_shortcut")
                    ) {
                        Text("Connect Dashboard ", fontWeight = FontWeight.Bold)
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                    OutlinedButton(
                        onClick = { onSwitchToTab("Admission") },
                        border = BorderStroke(1.dp, Color.White),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("Online Admissions")
                    }
                }
            }
        }

        // Stats Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("37+ Years", "Academic Legacy")
                Box(modifier = Modifier.width(1.dp).height(40.dp).background(MaterialTheme.colorScheme.outlineVariant))
                StatItem("1200+", "Active Students")
                Box(modifier = Modifier.width(1.dp).height(40.dp).background(MaterialTheme.colorScheme.outlineVariant))
                StatItem("100%", "CBSE Board Success")
            }
        }

        // Principal Message
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Portrait, contentDescription = "Principal Profile", tint = Color.White, modifier = Modifier.size(36.dp))
                    }
                    Column {
                        Text(
                            text = "Leader's Message",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "Mr. Gopinath Rath, Principal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = "\"Public School, Phulbani stands for rigorous scholastic progress, active physical discipline, digital literacy capability, and moral grounding. We believe in providing equal platform for cognitive creativity and character modeling. With CBSE standards, high-end science labs, and Legal school clubs, our educators are guiding the visionaries of tomorrow.\"",
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Quick Notice Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Campus Circulars",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { onSwitchToTab("Community") }) {
                Text("See All Hub")
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        }

        if (notices.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No notices posted currently.")
            }
        } else {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                homeNotices.forEach { notice ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(
                                        color = if (notice.priority == "High") MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = when(notice.type) {
                                        "Emergency" -> Icons.Default.Warning
                                        "Holiday" -> Icons.Default.BeachAccess
                                        "Event" -> Icons.Default.EmojiEvents
                                        else -> Icons.Default.Campaign
                                    },
                                    contentDescription = notice.type,
                                    tint = if (notice.priority == "High") MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(notice.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(notice.content, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                            }
                        }
                    }
                }
            }
        }

        // School Facilities Grid Sections
        Text(
            text = "Modern Facilities & Infrastructure",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val facilities = listOf(
                FacilityItem("Computer Lab", "Equipped with high-speed computers, programming capabilities, and smart screen boards.", Icons.Default.Computer),
                FacilityItem("Science Laboratories", "State of the art laboratories for Physics, Chemistry, and Biology to foster practical design research.", Icons.Default.Science),
                FacilityItem("Exhaustive Library", "A silent vault containing 10,000+ reference volumes, newspapers, magazines, and reading lounges.", Icons.Default.MenuBook),
                FacilityItem("Legal Literacy Club", "Instilling citizen constitutional ideals, civil logic, and judicial awareness debates.", Icons.Default.Gavel),
                FacilityItem("House System Activities", "Four dynamic houses (Subhash, Tagore, Ashoka, Raman) driving active co-scholastic duels and sports.", Icons.Default.Stars)
            )

            facilities.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(item.icon, contentDescription = item.title, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(item.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Campus Picture Gallery
        Text(
            text = "School Campus Gallery",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GalleryCard("Academy Main Entrance", "Narayan Road campus majestic entry gates.")
            GalleryCard("Computing Wing", "Senior board batch coding workspace.")
            GalleryCard("Physics Laboratory", "Prism reflection & optical demonstration area.")
            GalleryCard("Secondary Library", "Vibrant discussion room & periodic shelf.")
        }

        // Contact Block
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Contact Secretariat",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Address", tint = MaterialTheme.colorScheme.primary)
                    Text("Narayani Road, Phulbani, Kandhamal, Odisha - 762001", fontSize = 13.sp)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.Phone, contentDescription = "Phone", tint = MaterialTheme.colorScheme.primary)
                    Text("+91-6842-253810, admin@publicschoolphulbani.edu.in", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun StatItem(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = number, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

data class FacilityItem(val title: String, val description: String, val icon: ImageVector)

@Composable
fun GalleryCard(title: String, caption: String) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(140.dp)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .padding(14.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column {
            Icon(Icons.Filled.Image, contentDescription = null, tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(10.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(caption, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ==========================================
// CENTRALIZED CIRCULARS & NOTICES PANEL (General Communique)
// ==========================================
@Composable
fun CircularNoticeHub(
    notices: List<NoticeEntity>,
    viewModel: MainViewModel
) {
    val currentRole by viewModel.currentRole.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Official Circulars & Alerts",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Consolidated bulletin board released by School Administration and Principal office. Features filters to trace events.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(notices) { notice ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(
                    width = if (notice.priority == "High") 1.5.dp else 0.dp,
                    color = if (notice.priority == "High") MaterialTheme.colorScheme.error else Color.Transparent
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            color = when (notice.type) {
                                "Emergency" -> MaterialTheme.colorScheme.errorContainer
                                "Circular" -> MaterialTheme.colorScheme.primaryContainer
                                "Holiday" -> MaterialTheme.colorScheme.secondaryContainer
                                else -> MaterialTheme.colorScheme.tertiaryContainer
                            },
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = " [${notice.type.uppercase()}] ",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (notice.type) {
                                    "Emergency" -> MaterialTheme.colorScheme.onErrorContainer
                                    "Circular" -> MaterialTheme.colorScheme.onPrimaryContainer
                                    "Holiday" -> MaterialTheme.colorScheme.onSecondaryContainer
                                    else -> MaterialTheme.colorScheme.onTertiaryContainer
                                }
                            )
                        }
                        Text(
                            text = notice.date,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = notice.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = notice.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (currentRole == "Administrator") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = { viewModel.deleteNotice(notice.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// ADMISSION PORTAL
// ==========================================
@Composable
fun DigitalAdmissionPortal() {
    var studentName by remember { mutableStateOf("") }
    var parentName by remember { mutableStateOf("") }
    var classSeeking by remember { mutableStateOf("Grade 11 (Science)") }
    var contactNo by remember { mutableStateOf("") }
    var prevSchool by remember { mutableStateOf("") }
    var submittState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Digital Admission System", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
        Text("Apply dynamically to Public School, Phulbani for academic session 2026-27. Secure digital screening form.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Registrar's Screening Form", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                
                TextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Candidate Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = parentName,
                    onValueChange = { parentName = it },
                    label = { Text("Parent / Guardian Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = classSeeking,
                    onValueChange = { classSeeking = it },
                    label = { Text("Class Solicited") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = contactNo,
                    onValueChange = { contactNo = it },
                    label = { Text("Active Contact Phone") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = prevSchool,
                    onValueChange = { prevSchool = it },
                    label = { Text("Previous Institution details & CGPA") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (submittState) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "✔ Application Submitted Successfully! Thank you, the school admission cell will contact you on $contactNo shortly for the screening tests scheduled for mid-June.",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Button(
                    onClick = {
                        if (studentName.isNotBlank() && contactNo.isNotBlank()) {
                            submittState = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().testTag("admission_submit_button")
                ) {
                    Text("Submit Screening Application")
                }
            }
        }
    }
}

// ==========================================
// ADMINISTRATOR DASHBOARD
// ==========================================
@Composable
fun AdminDashboardScreen(
    students: List<StudentEntity>,
    teachers: List<TeacherEntity>,
    notices: List<NoticeEntity>,
    invoices: List<FeeInvoiceEntity>,
    viewModel: MainViewModel,
    onAddNoticeClick: () -> Unit
) {
    val aiResponse by viewModel.aiResponse.collectAsStateWithLifecycle()
    val aiLoading by viewModel.aiResultLoading.collectAsStateWithLifecycle()

    val totalPaid = remember(invoices) { invoices.filter { it.status == "Paid" }.sumOf { it.amount } }
    val facultyCount = remember(teachers) { teachers.size }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text("Governor Secretariat Panel", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Text("Registered analytics database for Public School, Phulbani", style = MaterialTheme.typography.bodySmall)
            }
        }

        // Stats card ledger
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Ledger Collection", style = MaterialTheme.typography.bodySmall)
                        Text("Rs. ${totalPaid.toInt()}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                        Text("From parents paid bills", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Faculty Size", style = MaterialTheme.typography.bodySmall)
                        Text("$facultyCount Teachers", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                        Text("Preseeded CBSE staff", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Global Announcement control Action
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Administrative Controls", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = onAddNoticeClick, modifier = Modifier.testTag("admin_add_notice")) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Post Circular", fontSize = 12.sp)
                        }
                        OutlinedButton(
                            onClick = { viewModel.generateAiStudyPlan("Grade 10 Board Batch") }
                        ) {
                            Icon(Icons.Default.SmartToy, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("AI Study Planner", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Executive AI Progress compiler
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.SmartToy, contentDescription = "AI tool", tint = MaterialTheme.colorScheme.primary)
                        Text("AI School executive Evaluation", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    }
                    Text("Synthesize automatic progress overview evaluation remarks for Manoj (Grade 10) CBSE syllabus using LLM.", fontSize = 12.sp)
                    
                    if (aiLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        Text("AI is loading cognitive details...", style = MaterialTheme.typography.bodySmall)
                    }

                    if (aiResponse.isNotBlank()) {
                        Surface(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Gemini AI Executive Evaluation:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(aiResponse, fontSize = 12.sp, lineHeight = 16.sp)
                                Spacer(modifier = Modifier.height(10.dp))
                                TextButton(onClick = { viewModel.clearAiResponse() }) {
                                    Text("Clear Report", fontSize = 11.sp)
                                }
                            }
                        }
                    }

                    Button(
                        onClick = { viewModel.generateAiReportSummary("Manoj Kumar Rath") },
                        modifier = Modifier.testTag("admin_ai_generate_btn")
                    ) {
                        Text("Compile Progress Executive Report", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// ==========================================
// TEACHER DASHBOARD
// ==========================================
@Composable
fun TeacherDashboardScreen(
    students: List<StudentEntity>,
    homeworks: List<HomeworkEntity>,
    viewModel: MainViewModel,
    onAddHomeworkClick: () -> Unit,
    onAddResultClick: () -> Unit
) {
    var selectedStudentForAttendance by remember { mutableStateOf<String?>(null) }
    var activeMessageToParent by remember { mutableStateOf<String?>(null) }
    var chatInputText by remember { mutableStateOf("") }
    var parentContactConfirmed by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text("Teacher Administration Desk", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Text("Educator Module: Mr. Rajesh Mohanty (Mathematics)", style = MaterialTheme.typography.bodySmall)
            }
        }

        // Live attendance seeder
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Daily Smart Attendance (Today)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("One-tap tracking logs are stored in sqlite database & notifies parents dynamically.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    
                    students.forEach { student ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${student.rollNo}. ${student.name}", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Button(
                                    onClick = { viewModel.markAttendance(student.id, "Present") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                                ) {
                                    Text("Present", fontSize = 10.sp, color = Color.White)
                                }
                                Button(
                                    onClick = { viewModel.markAttendance(student.id, "Late", 10) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                                ) {
                                    Text("Late", fontSize = 10.sp)
                                }
                                Button(
                                    onClick = { viewModel.markAttendance(student.id, "Absent") },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                                ) {
                                    Text("Absent", fontSize = 10.sp, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quick published item tracker
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Syllabus & Homework Management", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = onAddHomeworkClick, modifier = Modifier.testTag("teacher_publish_homework")) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Publish Assignment", fontSize = 11.sp)
                        }
                        OutlinedButton(onClick = onAddResultClick) {
                            Icon(Icons.Default.Assessment, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Publish MarksCard", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Messages with parents
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Direct Messaging with Parents", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("Select a student to dispatch quick comments to respective parents.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = activeMessageToParent == "p1",
                            onClick = {
                                activeMessageToParent = "p1"
                                parentContactConfirmed = false
                            },
                            label = { Text("Mr. Alok Rath (Manoj)") }
                        )
                        FilterChip(
                            selected = activeMessageToParent == "p2",
                            onClick = {
                                activeMessageToParent = "p2"
                                parentContactConfirmed = false
                            },
                            label = { Text("Mrs. Minati (Subhasmita)") }
                        )
                    }

                    activeMessageToParent?.let { parentId ->
                        val parentName = if(parentId == "p1") "Mr. Alok Rath" else "Mrs. Minati Nayak"
                        OutlinedTextField(
                            value = chatInputText,
                            onValueChange = { chatInputText = it },
                            label = { Text("Secure message text to $parentName") },
                            modifier = Modifier.fillMaxWidth().testTag("teacher_to_parent_text")
                        )
                        Button(
                            onClick = {
                                if (chatInputText.isNotBlank()) {
                                    viewModel.sendConversationMessage(parentId, parentName, "Parent", chatInputText)
                                    chatInputText = ""
                                    parentContactConfirmed = true
                                }
                            },
                            modifier = Modifier.testTag("teacher_send_msg_btn")
                        ) {
                            Text("Dispatch Secure Msg")
                        }
                        if (parentContactConfirmed) {
                            Text("✔ Secure message registered & notified via cloud server.", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Homework tracker entries
        item {
            Text("Active Mathematics Tasks Assigned", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }

        items(homeworks) { hw ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(hw.subject, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("Due: ${hw.dueDate}", style = MaterialTheme.typography.bodySmall)
                    }
                    Text(hw.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text(hw.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    
                    if (hw.submissionText.isNotBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(4.dp))
                                .padding(8.dp)
                        ) {
                            Column {
                                Text("Student Answer (Manoj):", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text(hw.submissionText, fontSize = 12.sp)
                            }
                        }
                    } else {
                        Text("No response submitted by Manoj yet.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))
                    }
                    
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = { viewModel.deleteHomework(hw.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// STUDENT DASHBOARD
// ==========================================
@Composable
fun StudentDashboardScreen(
    students: List<StudentEntity>,
    homeworks: List<HomeworkEntity>,
    quizzes: List<QuizEntity>,
    results: List<ExamResultEntity>,
    attendance: List<AttendanceEntity>,
    viewModel: MainViewModel
) {
    val aiResponse by viewModel.aiResponse.collectAsStateWithLifecycle()
    val aiLoading by viewModel.aiResultLoading.collectAsStateWithLifecycle()
    val selectedQuizAnswers by viewModel.activeQuizAnswers.collectAsStateWithLifecycle()
    val quizScoreText by viewModel.quizScoreText.collectAsStateWithLifecycle()

    var activeQuizToTake by remember { mutableStateOf<QuizEntity?>(null) }
    var doubtText by remember { mutableStateOf("") }
    var activeHomeworkWorkspaceId by remember { mutableStateOf<Long?>(null) }
    var answerWorkspaceText by remember { mutableStateOf("") }

    val studentDetails = remember(students) { students.find { it.id == "s1" } ?: StudentEntity("s1", "Manoj Kumar Rath", "12", "Grade 10", "Section A", "p1", "manoj.rath@cbse.in", "", "") }

    val studentAtts = remember(attendance) { attendance.filter { it.studentId == "s1" } }
    val totalDays = remember(studentAtts) { studentAtts.size }
    val presents = remember(studentAtts) { studentAtts.count { it.status == "Present" } }
    val lates = remember(studentAtts) { studentAtts.count { it.status == "Late" } }
    val attendanceRate = remember(totalDays, presents, lates) {
        if (totalDays > 0) (((presents + lates).toDouble() / totalDays.toDouble()) * 100.0).toInt() else 94
    }
    val s1Results = remember(results) { results.filter { it.studentId == "s1" } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Quick Insights Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Attendance Quick Insight Card
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "ATTENDANCE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF94A3B8),
                                letterSpacing = 0.5.sp
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = "$attendanceRate",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF64748B)
                            )
                        }
                        // Linear Progress Indicators representing CSS w-[94%]
                        LinearProgressIndicator(
                            progress = { attendanceRate.toFloat() / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(100)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = Color(0xFFEFF6FF)
                        )
                    }
                }

                // Performance Quick Insight Card
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "PERFORMANCE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF94A3B8),
                                letterSpacing = 0.5.sp
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "A1",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF15803D)
                            )
                            Text(
                                text = "Grade",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF64748B)
                            )
                        }
                        Text(
                            text = "↑ 2.4% from last term",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF16A34A)
                        )
                    }
                }
            }
        }

        // Dynamic Scholastic Marks card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Report Card & Assessment Grades",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF0F172A)
                        )
                    }
                    if (s1Results.isEmpty()) {
                        Text("No assessment parameters analyzed yet.", fontSize = 12.sp, color = Color(0xFF64748B))
                    } else {
                        s1Results.forEach { res ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(res.subject, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("${res.marksObtained.toInt()}/${res.totalMarks.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                    Surface(
                                        color = Color(0xFFF0FDF4),
                                        border = BorderStroke(1.dp, Color(0xFFDCFCE7)),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "  ${res.gradeChar}  ",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFF16A34A),
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Active homework tasks submission workspace
        item {
            Text("Pending Homework Board", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }

        items(homeworks) { hw ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .testTag("hw_card_${hw.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(hw.subject, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
                        Text("Due: ${hw.dueDate}", fontSize = 11.sp)
                    }
                    Text(hw.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text(hw.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    
                    if (hw.submissionText.isNotBlank()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text("Your Submitted Answer", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                Text(hw.submissionText, fontSize = 12.sp)
                            }
                        }
                    } else {
                        // Submission Text field workspace
                        if (activeHomeworkWorkspaceId == hw.id) {
                            OutlinedTextField(
                                value = answerWorkspaceText,
                                onValueChange = { answerWorkspaceText = it },
                                label = { Text("Draft your math/light answers here...") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .testTag("answer_workspace_input"),
                                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp)
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        if (answerWorkspaceText.isNotBlank()) {
                                            viewModel.submitHomeworkWorkspace(hw.id, hw.subject, hw.title, answerWorkspaceText)
                                            activeHomeworkWorkspaceId = null
                                            answerWorkspaceText = ""
                                        }
                                    },
                                    modifier = Modifier.testTag("submit_workspace_btn")
                                ) {
                                    Text("Submit Workspace", fontSize = 11.sp)
                                }
                                TextButton(onClick = { activeHomeworkWorkspaceId = null }) {
                                    Text("Cancel", fontSize = 11.sp)
                                }
                            }
                        } else {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        activeHomeworkWorkspaceId = hw.id
                                        answerWorkspaceText = ""
                                    },
                                    modifier = Modifier.testTag("open_workspace_btn")
                                ) {
                                    Text("Open Workspace", fontSize = 11.sp)
                                }
                                
                                // Call AI homework assistant
                                OutlinedButton(
                                    onClick = { viewModel.askAiHomeworkHelper(hw.title, hw.description) },
                                    modifier = Modifier.testTag("homework_ai_hints_btn")
                                ) {
                                    Icon(Icons.Default.SmartToy, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("AI Guidance Hints", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Online Quizzes
        item {
            Text("CBSE Online Quizzes Support", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }

        items(quizzes) { quiz ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(quiz.subject, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 11.sp)
                    Text(quiz.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text(quiz.description, fontSize = 12.sp)

                    if (activeQuizToTake?.id == quiz.id) {
                        // Extract and decode JSON questions
                        val questions = remember(quiz.questionsJson) {
                            try {
                                val jsonArr = JSONArray(quiz.questionsJson)
                                val list = mutableListOf<Question>()
                                for (i in 0 until jsonArr.length()) {
                                    val obj = jsonArr.getJSONObject(i)
                                    val optsArr = obj.getJSONArray("options")
                                    val optsList = mutableListOf<String>()
                                    for (j in 0 until optsArr.length()) {
                                        optsList.add(optsArr.getString(j))
                                    }
                                    list.add(
                                        Question(
                                            id = obj.getInt("id"),
                                            questionText = obj.getString("questionText"),
                                            options = optsList,
                                            correctAnswerIndex = obj.getInt("correctAnswerIndex")
                                        )
                                    )
                                }
                                list
                            } catch (e: Exception) {
                                emptyList()
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            questions.forEach { question ->
                                Text("${question.id}. ${question.questionText}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                question.options.forEachIndexed { idx, opt ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { viewModel.selectQuizAnswer(question.id, idx) }
                                            .padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedQuizAnswers[question.id] == idx,
                                            onClick = { viewModel.selectQuizAnswer(question.id, idx) }
                                        )
                                        Text(opt, fontSize = 13.sp)
                                    }
                                }
                            }

                            if (quizScoreText.isNotBlank()) {
                                Surface(
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(4.dp))
                                ) {
                                    Text(quizScoreText, modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold)
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { viewModel.submitAndGradeQuiz(questions, quiz.title, quiz.subject) },
                                    modifier = Modifier.testTag("submit_quiz_btn")
                                ) {
                                    Text("Submit Responses")
                                }
                                TextButton(
                                    onClick = {
                                        activeQuizToTake = null
                                        viewModel.resetQuiz()
                                    }
                                ) {
                                    Text("Dismiss Quiz")
                                }
                            }
                        }
                    } else {
                        Button(
                            onClick = {
                                activeQuizToTake = quiz
                                viewModel.resetQuiz()
                            },
                            modifier = Modifier.testTag("start_quiz_btn")
                        ) {
                            Text("Launch Self-Assessment")
                        }
                    }
                }
            }
        }

        // Smart AI Doubts and helper panels
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(26.dp),
                border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Pulsating light blue dot representing AI activity
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFF3B82F6), CircleShape)
                        )
                        Text(
                            text = "AI Doubt Solver & Assistant",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF0F172A)
                        )
                    }
                    Text(
                        text = "Real-time doubt answering powered by Gemini AI. Ask questions about physics formula, history guides, or algebra equations.",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )

                    // Unified Pill Search Bar
                    OutlinedTextField(
                        value = doubtText,
                        onValueChange = { doubtText = it },
                        placeholder = { Text("Ask AI for doubt solving...", fontSize = 13.sp, color = Color(0xFF94A3B8)) },
                        shape = RoundedCornerShape(50),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("student_doubt_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF8FAFC),
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color(0xFFE2E8F0)
                        ),
                        trailingIcon = {
                            Button(
                                onClick = {
                                    if (doubtText.isNotBlank()) {
                                        viewModel.askAiDoubtSolver(doubtText, "General")
                                    }
                                },
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .testTag("doubt_submit_btn")
                            ) {
                                Text("SOLVE", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = Color.White)
                            }
                        }
                    )

                    if (aiLoading) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                            Text("Gemini is solving your doubt with steps...", style = MaterialTheme.typography.bodySmall, color = Color(0xFF64748B))
                        }
                    }

                    if (aiResponse.isNotBlank()) {
                        Surface(
                            color = Color(0xFFEFF6FF), // blue-50 equivalent
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, Color(0xFFDBEAFE)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("AI Expert Explanation:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(aiResponse, fontSize = 12.sp, lineHeight = 16.sp, color = Color(0xFF1E3A8A))
                                Spacer(modifier = Modifier.height(10.dp))
                                TextButton(onClick = { viewModel.clearAiResponse() }) {
                                    Text("Dismiss Solution", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.runAiPerformanceAnalysis() },
                            shape = RoundedCornerShape(50),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.testTag("intel_analysis_btn")
                        ) {
                            Icon(Icons.Default.SmartToy, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Full Academic Performance Analysis")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AttendanceChip(text: String, color: Color) {
    Surface(color = color.copy(alpha = 0.15f), shape = RoundedCornerShape(4.dp)) {
        Text(" $text ", color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

// ==========================================
// PARENT DASHBOARD
// ==========================================
@Composable
fun ParentDashboardScreen(
    students: List<StudentEntity>,
    results: List<ExamResultEntity>,
    attendance: List<AttendanceEntity>,
    invoices: List<FeeInvoiceEntity>,
    teachers: List<TeacherEntity>,
    chatMessages: List<ChatMessageEntity>,
    viewModel: MainViewModel
) {
    var chatMessageText by remember { mutableStateOf("") }
    var chatToTeacherId by remember { mutableStateOf("t1") } // Rajesh

    val studentDetail = remember(students) { students.find { it.id == "s1" } ?: StudentEntity("s1", "Manoj Kumar Rath", "12", "Grade 10", "Section A", "p1", "manoj.rath@cbse.in", "", "") }
    val relativeChat = remember(chatMessages, chatToTeacherId) {
        chatMessages.filter {
            (it.senderId == "p1" && it.receiverId == chatToTeacherId) || (it.senderId == chatToTeacherId && it.receiverId == "p1")
        }
    }
    val studentInvoices = remember(invoices) { invoices.filter { it.studentId == "s1" } }

    val parentStudentAtts = remember(attendance) { attendance.filter { it.studentId == "s1" } }
    val parentTotalDays = remember(parentStudentAtts) { parentStudentAtts.size }
    val parentPresents = remember(parentStudentAtts) { parentStudentAtts.count { it.status == "Present" } }
    val parentLates = remember(parentStudentAtts) { parentStudentAtts.count { it.status == "Late" } }
    val parentAbsents = remember(parentStudentAtts) { parentStudentAtts.count { it.status == "Absent" } }
    val parentAttendanceRate = remember(parentTotalDays, parentPresents, parentLates) {
        if (parentTotalDays > 0) (((parentPresents + parentLates).toDouble() / parentTotalDays.toDouble()) * 100.0).toInt() else 100
    }

    val s1Scores = remember(results) { results.filter { it.studentId == "s1" } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text("Guardian Information Access", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Text("Parent Account: Mr. Alok Rath (Manoj's Dad)", style = MaterialTheme.typography.bodySmall)
            }
        }

        // Smart billing invoices & receipt printer
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Fee Ledger & Online Billing", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("Pay CBSE tuition and exam fees securely. Instant digital receipt will be recorded.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    
                    studentInvoices.forEach { inv ->
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(inv.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                                    Text("Rs. ${inv.amount.toInt()}", fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Due Date: ${inv.dueDate}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        if (inv.status == "Paid") {
                                            Text("Receipt No: ${inv.receiptNo} (${inv.paymentDate})", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF10B981))
                                        }
                                    }
                                    if (inv.status == "Pending") {
                                        Button(
                                            onClick = { viewModel.payInvoice(inv.id) },
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 2.dp),
                                            modifier = Modifier.testTag("pay_fee_btn")
                                        ) {
                                            Text("Pay Online", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    } else {
                                        Surface(color = Color(0xFF10B981).copy(alpha = 0.15f), shape = RoundedCornerShape(4.dp)) {
                                            Text(" PAID ", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Attendance stats
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Child Attendance Monitoring", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("Manoj Kumar Rath's overall rate: $parentAttendanceRate%. Scheduled class attendance is verified daily by staff.", fontSize = 12.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AttendanceChip("Present: $parentPresents", Color(0xFF10B981))
                        AttendanceChip("Late: $parentLates", MaterialTheme.colorScheme.tertiary)
                        AttendanceChip("Absent: $parentAbsents", MaterialTheme.colorScheme.error)
                    }
                }
            }
        }

        // Child's grades tracker
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Child Academic Performance Tracker", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    if (s1Scores.isEmpty()) {
                        Text("No exams scored yet.", fontSize = 12.sp)
                    } else {
                        s1Scores.forEach { res ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(res.subject, fontSize = 13.sp)
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text("${res.marksObtained.toInt()}/${res.totalMarks.toInt()}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(2.dp)) {
                                        Text("  ${res.gradeChar}  ", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Secure Direct Messages with Class Teachers
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Direct Messaging with Teachers", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Text("Select teacher to begin encrypted discussion channel:", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        teachers.forEach { teach ->
                            FilterChip(
                                selected = chatToTeacherId == teach.id,
                                onClick = { chatToTeacherId = teach.id },
                                label = { Text(teach.name) }
                            )
                        }
                    }

                    // Conversation history list
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Secure Channel Log:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            if (relativeChat.isEmpty()) {
                                Text("No historic messages. Draft a message below to initialize communication.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            } else {
                                relativeChat.forEach { chat ->
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = if(chat.senderRole == "Parent") Alignment.End else Alignment.Start
                                    ) {
                                        Surface(
                                            color = if(chat.senderRole == "Parent") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(" ${chat.messageText} ", fontSize = 12.sp, modifier = Modifier.padding(6.dp))
                                        }
                                        Text(
                                            text = "By ${chat.senderName}",
                                            fontSize = 9.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Chat draft box
                    OutlinedTextField(
                        value = chatMessageText,
                        onValueChange = { chatMessageText = it },
                        label = { Text("Write query details (homework extension or late notes)...") },
                        modifier = Modifier.fillMaxWidth().testTag("parent_chat_input")
                    )

                    Button(
                        onClick = {
                            if (chatMessageText.isNotBlank()) {
                                val targetTeacher = teachers.find { it.id == chatToTeacherId }
                                val receiverName = targetTeacher?.name ?: "Teacher"
                                viewModel.sendConversationMessage(chatToTeacherId, receiverName, "Teacher", chatMessageText)
                                chatMessageText = ""
                            }
                        },
                        modifier = Modifier.testTag("parent_send_chat_btn")
                    ) {
                        Text("Send Message")
                    }
                }
            }
        }
    }
}
