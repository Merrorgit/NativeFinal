package com.example.financialtracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var noAccountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Инициализация элементов интерфейса
        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        noAccountText = findViewById(R.id.noAccountText)

        // Обработчик нажатия на кнопку входа
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Реализуй логику входа (например, проверку данных пользователя)

                // Переход на следующий экран после успешного входа
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Закрыть LoginActivity после перехода
            } else {
                // Обработать ошибку (например, показать сообщение об ошибке)
            }
        }

        // Обработчик нажатия на кнопку регистрации
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Обработчик текста "Have no account?"
        noAccountText.setOnClickListener {
            // Можешь добавить свою логику для перехода на страницу регистрации
        }
    }
}
