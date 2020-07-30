package com.jagruti.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jagruti.todonotesapp.utils.AppConstant
import com.jagruti.todonotesapp.utils.PrefConstant
import com.jagruti.todonotesapp.R
import com.jagruti.todonotesapp.utils.StoreSession

public class LoginActivity : AppCompatActivity(){
    lateinit var editTextFullName : EditText
    lateinit var editTextUserName : EditText
    lateinit var buttonLogin : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindViews()
        setupSharedPreferences()

    }

    private fun bindViews() {
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
        val clickAction = object :View.OnClickListener{
            override fun onClick(v: View?) {
                val fullName = editTextFullName.text.toString()
                val userName = editTextUserName.text.toString()
                if(fullName.isNotEmpty() && userName.isNotEmpty()) {
                    val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstant.FULL_NAME, fullName)
                    startActivity(intent)
                    saveLoginStatus()
                    saveFullName(fullName)
                }else{
                    Toast.makeText(this@LoginActivity,"UserName and FullName can't be Empty",Toast.LENGTH_SHORT).show()
                }
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveFullName(fullName: String) {
        StoreSession.write(PrefConstant.FULL_NAME,fullName)
    }

    private fun saveLoginStatus() {
        StoreSession.write(PrefConstant.IS_LOGGED_IN, true)
    }

    private fun setupSharedPreferences() {
        StoreSession.init(this)
    }
}