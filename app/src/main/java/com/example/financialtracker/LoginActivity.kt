package com.example.financialtracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financialtracker.API.services.AuthService

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var noAccountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // Убедись, что ты указываешь правильный layout файл

        // Инициализируем элементы
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        noAccountText = findViewById(R.id.noAccountText)

        // Обработка нажатия кнопки "Log in"
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Проверка на пустые поля
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Вызов метода для логина
                AuthService.logIn(username, password, this) { isSuccess ->
                    if (isSuccess) {
                        // Если логин успешен, переходим в MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Если логин не успешен, выводим сообщение
                        Toast.makeText(this, "Login failed, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Обработка нажатия кнопки "REGISTER"
        registerButton.setOnClickListener {
            // Переход на экран регистрации
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Обработка нажатия текста "Have no account?"
        noAccountText.setOnClickListener {
            // Переход на экран регистрации
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}