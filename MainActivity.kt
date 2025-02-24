package com.example.lista_brn

import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText = findViewById(R.id.timerText)
        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            startPomodoro() // Começar o Pomodoro ao clicar no botão
        }
    }

    private fun startPomodoro() {
        // Começar o Pomodoro com 1 minuto para teste (alterar para 25 minutos depois)
        val workTimeInMillis = 1 * 60 * 1000L
        startTimer(workTimeInMillis)
    }

    private fun startTimer(timeInMillis: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerText.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timerText.text = "00:00" // Exibe 00:00 quando o tempo acaba
                vibratePhone() // Chama a função de vibração quando o tempo acaba
            }
        }.start()
    }

    private fun vibratePhone() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        // Verificar a versão do Android
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Para versões mais recentes (API 26 ou superior), usar o VibrationEffect
            val vibrationEffect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            // Para versões anteriores (abaixo da API 26), usar o método antigo
            @Suppress("DEPRECATION")
            vibrator.vibrate(1000)
        }
    }
}
