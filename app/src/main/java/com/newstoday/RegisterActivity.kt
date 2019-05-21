package com.newstoday

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_btn.setOnClickListener {
            onPerformRegister()
        }

        register_text.setOnClickListener {
            onSignin()
        }
    }

    private fun onPerformRegister() {
        val email = email.text.toString()
        val password = password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "email and password cannot be empty", Toast.LENGTH_LONG).show()
        } else {
            Log.d("RegisterActivity", "email = $email")
            Log.d("RegisterActivity", "password = $password")

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        Toast.makeText(this, "Enter text in email & password", Toast.LENGTH_LONG).show()
                        return@addOnCompleteListener
                    }
                    Log.d("RegisterActivity", "Successfully created user with ${it.result?.user?.uid}")
                    Toast.makeText(this, "User created successfully", Toast.LENGTH_LONG).show()
                    saveUserToFirebaseDatabase()
                    finish()
                }
                .addOnFailureListener {
                    Log.d("RegisterActivity", "Failed to create user ${it.message}")
                    Toast.makeText(this, "Failed!!", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun saveUserToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, username.text.toString())
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Saved user data to firebase Database")

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "saving user to firebase failed : ${it.message}")
            }
    }

    private fun onSignin() {
        finish()
    }
}

class User(val uid: String, val username: String)
