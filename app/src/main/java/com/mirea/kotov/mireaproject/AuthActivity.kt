package com.mirea.kotov.mireaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mirea.kotov.mireaproject.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAuthBinding

    private var TAG = AuthActivity::class.java.simpleName

    // START declare_auth
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //region Event handlers

        binding.apply{
            signInButton.setOnClickListener(this@AuthActivity)
            signUpButton.setOnClickListener(this@AuthActivity)
            signOutButton.setOnClickListener(this@AuthActivity)
        }

        mAuth = FirebaseAuth.getInstance()

        //endregion
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth?.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) = with(binding) {
        if(user != null){
            emailPassButtons.visibility = View.GONE
            emailPassFields.visibility = View.GONE
            signOutButtons.visibility = View.VISIBLE

            verifyEmailButton.isEnabled = !user.isEmailVerified
        } else {
            tvEmailPass.text = null

            emailPassFields.visibility = View.VISIBLE
            emailPassButtons.visibility = View.VISIBLE
            signOutButtons.visibility = View.GONE
        }
    }

    private fun validateForm(): Boolean = with(binding){
        var valid = true

        val email = etEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            etEmail.error = "Required."
            valid = false
        } else {
            etEmail.error = null
        }

        val password = etPass.text.toString()

        if (TextUtils.isEmpty(password)) {
            etPass.error = "Required."
            valid = false
        } else {
            etPass.error = null
        }

        return valid
    }

    private fun createAccount(email: String, password: String){
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    val user = mAuth!!.currentUser
                    updateUI(user)

                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)

                    Toast.makeText(
                        this@AuthActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun signIn(email: String, password: String): Unit = with(binding){
        Log.d(TAG, "signIn:$email")

        if (!validateForm()) {
            return
        }

        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@AuthActivity
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")

                    val user = mAuth!!.currentUser
                    updateUI(user)

                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)

                    Toast.makeText(
                        this@AuthActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                if (!task.isSuccessful) {
                    tvSignStatus.text = "Auth failed"
                }
            }
    }

    private fun signOut(){
        mAuth!!.signOut()
        updateUI(null)
    }

    override fun onClick(v: View?) = with(binding) {
        when (v!!.id) {
            signUpButton.id -> {
                createAccount(etEmail.text.toString(), etPass.text.toString())
            }
            signInButton.id -> {
                signIn(etEmail.text.toString(), etPass.text.toString())
            }
            signOutButton.id -> {
                signOut()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        signOut()
    }
}