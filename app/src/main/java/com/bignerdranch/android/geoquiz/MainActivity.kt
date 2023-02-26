package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0

    // Create a variable to store the count of correct answers
    private var correctAnswerCount = 0

    // Create a variable to store the question bank size
    private val questionBankSize = questionBank.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)

            // Disable both true and false buttons if you've clicked true
            disableTrueFalseButtons()

            // Show toast with your score if you've answered the last question
            showResultsAndResetCorrectAnswerCount()

        }

        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)

            // Disable both true and false buttons if you've clicked false
            disableTrueFalseButtons()

            // Show toast with your score if you've answered the last question
            showResultsAndResetCorrectAnswerCount()
        }


        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()

            // Enable the true and false buttons once you've clicked the next button
            enableTrueFalseButtons()

        }

        updateQuestion()

    }

    // 3.3 (p58): Overriding  more lifecycle functions
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        // If the user gets the correct answer, increment correctAnswerCountInteger
        if (userAnswer == correctAnswer)
        {
            correctAnswerCount ++
        }

        // Set the string based on if the user got the correct
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast

        } else {
            R.string.incorrect_toast
        }
        // Show a toast that tells the user if they got the answer correct or incorrect
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }

    // Create a function that disables both the true and false buttons
    private fun disableTrueFalseButtons() {
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false

        // Call in binding.trueButton.setOnClickListener && binding.falseButton.setOnClickListener
    }

    // Create a function that enables both the true and false buttons
    private fun enableTrueFalseButtons() {
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true

        // Call in binding.nextButton.setOnClickListener {}
    }

    // Create a function that calculates the score using correctAnswerCount and
    private fun calculateScore (correctAnswerCount: Int, questionBankSize: Int): String { // I tried using questionBank.size: Int as input but it didn't like that so I made it a variable
        // Create variable "scoreString" and use it to store the correctAnswerCount/questionBankSize result
        // Convert the result into a string since we'll use it for a toast
        var score = (correctAnswerCount.toDouble()/questionBankSize.toDouble()) * 100 // They have to be double or our answer will be 0
        var scoreString = String.format("%.1f", score) +"%"
        return scoreString

    }

    private fun showResultsAndResetCorrectAnswerCount(){
        // Call in trueButton && falseButton setOnClickListeners
        if (currentIndex +1 == questionBankSize){

            // If the last question is answered, display a toast with the percentage score
            // get the score from the calculateScore function and format it to string and percent
            var scoreString = calculateScore (correctAnswerCount, questionBankSize)

            // Create a toast that displays the user's score in a percentage
            Toast.makeText(this, scoreString, Toast.LENGTH_SHORT).show()

            // set correctAnswerCount to 0
            correctAnswerCount = 0

        }
    }
}
