package com.newstoday

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            onPerformLogin()
        }

        not_registered_text.setOnClickListener {
            onNotRegistered()
        }
    }

    private fun onPerformLogin() {
        val email = login_email?.text.toString()
        val password = login_password?.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "email and password cannot be empty", Toast.LENGTH_LONG).show()
        } else {
            Log.d("LOGIN", "email =$login_email \t password=$login_password")
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful){
                        return@addOnCompleteListener
                    }
                    Toast.makeText(this, "Logging in", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, ChannelActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "User or Passowrd is wrong", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun onNotRegistered() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
