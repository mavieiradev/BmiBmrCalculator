package com.mavieiradev.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OnItemClickListener {



    private lateinit var btnImc: ImageView
    private lateinit var btnBmi: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startComponents()
        configCliques()


    }

    override fun onClick(id: Int) {

    }

    private fun configCliques() {


        // click BMR
       btnImc.setOnClickListener {
            val intent = Intent(this, ImcActivity::class.java)
            startActivity(intent)
        }

        // click BMI
        btnBmi.setOnClickListener {
            val intent = Intent(this, TmbActivity::class.java)
            startActivity(intent)
        }

    }

    private fun startComponents() {

        btnImc = findViewById(R.id.btn_imc)
        btnBmi = findViewById(R.id.btn_bmi)

    }
}


