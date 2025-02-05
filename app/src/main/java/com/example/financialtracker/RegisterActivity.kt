package com.example.financialtracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        // Инициализация элементов UI
        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val alreadyHaveAccountText = findViewById<TextView>(R.id.alreadyHaveAccountText)

        // Логика для перехода на экран входа
        loginButton.setOnClickListener {
            // Перейти в LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Логика для регистрации нового пользователя
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Проверить, что все поля заполнены
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Здесь можно добавить логику для регистрации пользователя (например, запрос в базу данных)
                // Для примера просто выводим сообщение
                // Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()

                // После успешной регистрации, переход на главный экран или другую активность
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Закрываем текущую активность
            } else {
                // Вывести ошибку, если одно из полей не заполнено
                // Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Логика для перехода на страницу входа
        alreadyHaveAccountText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
