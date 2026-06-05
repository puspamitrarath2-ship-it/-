package com.example.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

data class AuthUser(
    val email: String,
    val name: String,
    val role: String, // "Student", "Parent", "Teacher", "Administrator"
    val associatedId: String
)

class AuthManager(private val context: Context) {

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("school_auth_prefs", Context.MODE_PRIVATE)

    var firebaseAuth: FirebaseAuth? = null
        private set

    val isFirebaseInitialized: Boolean
        get() {
            return try {
                val apps = FirebaseApp.getApps(context)
                if (apps.isNotEmpty()) {
                    firebaseAuth = FirebaseAuth.getInstance()
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                Log.e("AuthManager", "Firebase initialization check failed: ${e.message}")
                false
            }
        }

    init {
        // Safe check to setup firebaseAuth if possible
        try {
            if (FirebaseApp.getApps(context).isNotEmpty()) {
                firebaseAuth = FirebaseAuth.getInstance()
            }
        } catch (e: Exception) {
            Log.w("AuthManager", "Firebase not initialized automatically on start: ${e.message}")
        }
    }

    /**
     * Authenticates with Firebase Auth (or fallback manager if Firebase is not initialized)
     */
    fun login(
        email: String,
        password: String,
        onResult: (Result<AuthUser>) -> Unit
    ) {
        val trimmedEmail = email.trim()
        
        if (isFirebaseInitialized) {
            firebaseAuth?.signInWithEmailAndPassword(trimmedEmail, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = firebaseAuth?.currentUser
                        if (firebaseUser != null) {
                            // Find corresponding role and details
                            val resolvedUser = resolveAuthUser(firebaseUser.email ?: trimmedEmail)
                            onResult(Result.success(resolvedUser))
                        } else {
                            onResult(Result.failure(Exception("Successfully authenticated but user remains null")))
                        }
                    } else {
                        onResult(Result.failure(task.exception ?: Exception("Authentication failed on Firebase")))
                    }
                }
        } else {
            // Local sandbox secure verification fallback
            val storedPassword = sharedPrefs.getString("pass_$trimmedEmail", null)
            val isDefaultAccount = isPreseededAccount(trimmedEmail)

            if (storedPassword != null && storedPassword == password) {
                val user = resolveAuthUser(trimmedEmail)
                onResult(Result.success(user))
            } else if (isDefaultAccount && password.length >= 6) {
                // If it is preseeded and hasn't been locally defined, auto-create password
                sharedPrefs.edit().putString("pass_$trimmedEmail", password).apply()
                val user = resolveAuthUser(trimmedEmail)
                onResult(Result.success(user))
            } else {
                onResult(Result.failure(Exception("Invalid email, password, or role credentials in offline sandbox")))
            }
        }
    }

    /**
     * Registers a new account with the Firebase Auth instance and stores credentials locally
     */
    fun register(
        email: String,
        password: String,
        name: String,
        role: String,
        associatedId: String,
        onResult: (Result<AuthUser>) -> Unit
    ) {
        val trimmedEmail = email.trim()
        val trimmedName = name.trim()

        if (isFirebaseInitialized) {
            firebaseAuth?.createUserWithEmailAndPassword(trimmedEmail, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Persist user role association info in SharedPreferences so we map it on subsequent login
                        saveUserAccountMeta(trimmedEmail, trimmedName, role, associatedId)
                        val resolvedUser = AuthUser(trimmedEmail, trimmedName, role, associatedId)
                        onResult(Result.success(resolvedUser))
                    } else {
                        onResult(Result.failure(task.exception ?: Exception("Firebase signup failed")))
                    }
                }
        } else {
            // Check if already registered
            if (sharedPrefs.contains("pass_$trimmedEmail")) {
                onResult(Result.failure(Exception("Account already exists with this email address in offline sandbox")))
                return
            }
            // Offline local register
            sharedPrefs.edit().apply {
                putString("pass_$trimmedEmail", password)
                putString("role_$trimmedEmail", role)
                putString("name_$trimmedEmail", trimmedName)
                putString("id_$trimmedEmail", associatedId)
            }.apply()

            val resolvedUser = AuthUser(trimmedEmail, trimmedName, role, associatedId)
            onResult(Result.success(resolvedUser))
        }
    }

    fun logout() {
        if (isFirebaseInitialized) {
            firebaseAuth?.signOut()
        }
    }

    fun getCurrentlyLoggedInUser(): AuthUser? {
        if (isFirebaseInitialized) {
            val firebaseUser = firebaseAuth?.currentUser
            if (firebaseUser != null) {
                val email = firebaseUser.email ?: return null
                return resolveAuthUser(email)
            }
        } else {
            // Find if there is a cached active login email in shared preferences
            val activeEmail = sharedPrefs.getString("active_session_email", null)
            if (activeEmail != null) {
                return resolveAuthUser(activeEmail)
            }
        }
        return null
    }

    fun saveActiveSession(email: String?) {
        sharedPrefs.edit().putString("active_session_email", email?.trim()).apply()
    }

    /**
     * Save registration-time metadata
     */
    private fun saveUserAccountMeta(email: String, name: String, role: String, id: String) {
        sharedPrefs.edit().apply {
            putString("role_$email", role)
            putString("name_$email", name)
            putString("id_$email", id)
        }.apply()
    }

    private fun isPreseededAccount(email: String): Boolean {
        val emailLower = email.lowercase()
        return emailLower == "admin@cbse.in" || 
               emailLower == "manoj.rath@cbse.in" || 
               emailLower == "subhasmita.nayak@cbse.in" || 
               emailLower == "priyanka.panda@cbse.in" ||
               emailLower == "rajesh.math@publicschool.edu" || 
               emailLower == "snehalata.sci@publicschool.edu" || 
               emailLower == "swadhin.comp@publicschool.edu" ||
               emailLower == "alok.rath@parent.in" || 
               emailLower == "minati.nayak@parent.in" || 
               emailLower == "bimal.panda@parent.in"
    }

    /**
     * Resolves the profile matching preseeded accounts or registers meta of new ones
     */
    fun resolveAuthUser(email: String): AuthUser {
        val emailLower = email.lowercase().trim()
        
        // Retrieve custom registered fields if they exist
        val savedRole = sharedPrefs.getString("role_$emailLower", null)
        val savedName = sharedPrefs.getString("name_$emailLower", null)
        val savedId = sharedPrefs.getString("id_$emailLower", null)

        if (savedRole != null) {
            return AuthUser(
                email = emailLower,
                name = savedName ?: emailLower.substringBefore("@"),
                role = savedRole,
                associatedId = savedId ?: "idx"
            )
        }

        // Return mapping for pre-seeded demo accounts
        return when (emailLower) {
            "admin@cbse.in" -> AuthUser(emailLower, "Academic Principal", "Administrator", "admin")
            "manoj.rath@cbse.in" -> AuthUser(emailLower, "Manoj Kumar Rath", "Student", "s1")
            "subhasmita.nayak@cbse.in" -> AuthUser(emailLower, "Subhasmita Nayak", "Student", "s2")
            "priyanka.panda@cbse.in" -> AuthUser(emailLower, "Priyanka Panda", "Student", "s3")
            "rajesh.math@publicschool.edu" -> AuthUser(emailLower, "Mr. Rajesh Mohanty", "Teacher", "t1")
            "snehalata.sci@publicschool.edu" -> AuthUser(emailLower, "Mrs. Snehalata Sahu", "Teacher", "t2")
            "swadhin.comp@publicschool.edu" -> AuthUser(emailLower, "Mr. Swadhin Pradhan", "Teacher", "t3")
            "alok.rath@parent.in" -> AuthUser(emailLower, "Mr. Alok Rath", "Parent", "p1")
            "minati.nayak@parent.in" -> AuthUser(emailLower, "Mrs. Minati Nayak", "Parent", "p2")
            "bimal.panda@parent.in" -> AuthUser(emailLower, "Mr. Bimal Panda", "Parent", "p3")
            else -> {
                // Default fallback if unknown signups occur
                val inferredRole = if (emailLower.contains("admin")) {
                    "Administrator"
                } else if (emailLower.contains("teacher") || emailLower.contains("edu")) {
                    "Teacher"
                } else if (emailLower.contains("parent") || emailLower.contains("mother") || emailLower.contains("father")) {
                    "Parent"
                } else {
                    "Student"
                }
                val inferredId = when (inferredRole) {
                    "Student" -> "s1"
                    "Teacher" -> "t1"
                    "Parent" -> "p1"
                    else -> "admin"
                }
                AuthUser(emailLower, emailLower.substringBefore("@").replaceFirstChar { it.uppercase() }, inferredRole, inferredId)
            }
        }
    }
}
