package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        StudentEntity::class,
        TeacherEntity::class,
        ParentEntity::class,
        AttendanceEntity::class,
        HomeworkEntity::class,
        QuizEntity::class,
        ExamResultEntity::class,
        FeeInvoiceEntity::class,
        NoticeEntity::class,
        ChatMessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun schoolDao(): SchoolDao
}
