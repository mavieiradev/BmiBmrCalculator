package com.mavieiradev.fitnesstracker

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mavieiradev.fitnesstracker.model.App
import com.mavieiradev.fitnesstracker.model.Calc


class ImcActivity : AppCompatActivity() {


    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var icBack: ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        startComponents()
        configCliques()


        val imageButton = findViewById<ImageButton>(R.id.search_image_button)
        imageButton.setOnClickListener {
            showPopupMenu(it)
        }




        icBack = findViewById(R.id.ic_back)
        icBack.setOnClickListener {
            val back = Intent(this, MainActivity::class.java)
            startActivity(back)
        }



    }





    private fun configCliques() {



        val btnSend: Button = findViewById(R.id.btn_imc_send)
        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)
            Log.d("teste", "resultado: $result")

            val imcResponseId = imcResponse(result)

            val dialog = AlertDialog.Builder(this)



            .setTitle(getString(R.string.imc_response, result))
            .setMessage(imcResponseId)
            .setPositiveButton(android.R.string.ok
            ) { dialog, which ->
                // aqui roda depois do click
            }
                .setNegativeButton(R.string.save) {
                    dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc", res = result ))

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()

                }
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }


    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_search -> {
                    finish()
                    openListActivity()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun openListActivity(){
        val intent = Intent(this@ImcActivity, ListCalcActivity::class.java)
        intent.putExtra("type", "imc")
        startActivity(intent)
    }

    private fun imcResponse(imc: Double): Int {
        if (imc < 15.0) {
            return R.string.imc_severely_low_weight
        } else if (imc < 16.0) {
            return R.string.imc_very_low_weight
        } else if (imc < 18.5) {
            return R.string.imc_low_weight
        } else {
            return R.string.imc_extreme_weight
        }
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        return weight / ( (height / 100.0) * (height / 100.0) )

    }


    private fun validate(): Boolean {
        if (editWeight.text.toString().isNotEmpty()
            && editHeight.text.toString().isNotEmpty()
            && !editWeight.text.toString().startsWith("0")
            && !editHeight.text.toString().startsWith("0")) {
            return true
        }
            return false

    }




    private fun startComponents() {

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)
        val btnSend: Button = findViewById(R.id.btn_imc_send)

    }

}


