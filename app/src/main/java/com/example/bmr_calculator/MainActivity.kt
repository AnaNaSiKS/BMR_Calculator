package com.example.bmr_calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var buttonCalculate: Button
    private lateinit var buttonClear: Button
    private lateinit var textHeight: EditText
    private lateinit var textWidth: EditText
    private lateinit var textAge: EditText
    private lateinit var showBMR: TextView
    private lateinit var textViewSit: TextView
    private lateinit var textAverageActivity: TextView
    private lateinit var textLittleActivity: TextView
    private lateinit var textStrongActivity: TextView
    private lateinit var textMaxActivity: TextView
    private lateinit var genderMale: ImageView
    private lateinit var genderFemale: ImageView
    private lateinit var faq: ImageView
    private var choosedGender: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCalculate = findViewById(R.id.buttonCalculate)
        buttonClear = findViewById(R.id.buttonClear)
        textHeight = findViewById(R.id.editTextHeight)
        textWidth = findViewById(R.id.editTextWeight)
        textAge = findViewById(R.id.editTextAge)
        textViewSit = findViewById(R.id.textViewSit)
        textAverageActivity = findViewById(R.id.textViewAverageActivity)
        textLittleActivity = findViewById(R.id.textViewLittleActivity)
        textStrongActivity = findViewById(R.id.textViewStrongActivity)
        textMaxActivity = findViewById(R.id.textViewMaxActivity)
        genderMale = findViewById(R.id.imageViewMale)
        genderFemale = findViewById(R.id.imageViewFemale)
        faq = findViewById(R.id.imageViewFAQ)
        showBMR = findViewById(R.id.textViewShowBMR)

        buttonCalculate.setOnClickListener{
            try {
                if (choosedGender != null) {
                    if (valueValidate()) {
                        if (choosedGender == "male") {
                            val bmr: Double = 66 + (13.7 * textWidth.text.toString()
                                .toInt()) + (5 * textHeight.text.toString()
                                .toInt()) - (6.8 * textAge.text.toString().toInt())
                            calculateActivity(bmr)
                        } else {
                            val bmr: Double = 655 + (9.6 * textWidth.text.toString()
                                .toInt()) + (1.8 * textHeight.text.toString()
                                .toInt()) - (4.7 * textAge.text.toString().toInt())
                            calculateActivity(bmr)
                        }
                    }
                }
                else {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setMessage("Выберите пол").setTitle("Ошибка")

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
            catch (ex: Exception){
                Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()}

        }

        buttonClear.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Вы уверены?")
                .setPositiveButton("Да") {
                    _, _ ->
                textHeight.text = null
                textWidth.text = null
                textAge.text = null
                showBMR.text = null
                textViewSit.text = "Сидячий образ:"
                textLittleActivity.text = "Маленькая активность:"
                textAverageActivity.text = "Средняя активность:"
                textStrongActivity.text = "Сильная активность:"
                textMaxActivity.text = "Максимальная активность:"
                genderMale.background = resources.getDrawable(R.drawable.black_border, null)
                genderFemale.background = resources.getDrawable(R.drawable.black_border, null)
                choosedGender = null;
            }
                .setNegativeButton("Нет"){
                    _,_ ->
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        genderMale.setOnClickListener {
            choosedGender = "male"
            genderFemale.background = resources.getDrawable(R.drawable.black_border, null)
            genderMale.background = resources.getDrawable(R.drawable.black_green_border, null)
        }

        genderFemale.setOnClickListener {
            choosedGender = "female"
            genderFemale.background = resources.getDrawable(R.drawable.black_green_border, null)
            genderMale.background = resources.getDrawable(R.drawable.black_border, null)
        }

        faq.setOnClickListener {
            val intent: Intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun calculateActivity(bmr: Double) {
        showBMR.text = String.format("%.2f", bmr)
        textViewSit.text = String.format("Сидячий образ: %.2f", bmr * 1.2)
        textLittleActivity.text = String.format("Маленькая активность: %.2f",bmr * 1.375)
        textAverageActivity.text = String.format("Средняя активность: %.2f",bmr * 1.55)
        textStrongActivity.text = String.format("Сильная активность: %.2f",bmr * 1.725)
        textMaxActivity.text = String.format("Максимальная активность: %.2f",bmr * 1.9)
    }

    private fun valueValidate() : Boolean{
        try {
            if (textHeight.text.toString().trim().isEmpty()){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Ошибка").setMessage("Поле роста не введено")

                val dialog: AlertDialog = builder.create()
                dialog.show()

                return false
            }
            else if (textWidth.text.toString().trim().isEmpty()){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Ошибка").setMessage("Поле веса не введено")

                val dialog: AlertDialog = builder.create()
                dialog.show()

                return false
            }
            else if (textAge.text.toString().trim().isEmpty()){
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setTitle("Ошибка").setMessage("Поле возраста не введено")

                val dialog: AlertDialog = builder.create()
                dialog.show()

                return false
            }
            else {
                val width: Int = textWidth.text.toString().toInt()
                val height: Int = textHeight.text.toString().toInt()
                val age: Int = textAge.text.toString().toInt()
                if (width > 600 || width < 1){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Ошибка").setMessage("Поле веса введено не корректно")

                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    return false
                }
                else if (height > 300 || height < 1){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Ошибка").setMessage("Поле роста введено не корректно")

                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    return false
                }
                else if (age > 150 || age < 0){
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Ошибка").setMessage("Поле возраста введено не корректно")

                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    return false
                }
                else
                    return true
            }
        }
        catch (ex: Exception){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show()
            return false
        }
    }
}