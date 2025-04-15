package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.ui.theme.QuizAppTheme
import android.app.Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                QuizApp()
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun QuizApp() {
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var currentScreen by remember { mutableStateOf("welcome") }
    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    val context = LocalContext.current as Activity

    val questions = listOf(
        "Nelson Mandela was the president in 1994",
        "The Great Wall of China was built in the 1800s",
        "World War II ended in 1945",
        "The first human landed on the moon in 1970",
        "Egypt is in Europe"
    )

    val answers = listOf(true, false, true, false, false)

    when (currentScreen) {
        "welcome" -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Welcome to the Flashcard Quiz!", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Enter your name") }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { currentScreen = "quiz" }) {
                    Text("Start Quiz")
                }
            }
        }

        "quiz" -> {
            if (currentIndex < questions.size) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Question ${currentIndex + 1}:", fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(questions[currentIndex], fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(onClick = {
                            if (answers[currentIndex]) score++
                            currentIndex++
                        }) { Text("True") }

                        Spacer(modifier = Modifier.width(20.dp))

                        Button(onClick = {
                            if (!answers[currentIndex]) score++
                            currentIndex++
                        }) { Text("False") }
                    }
                }
            } else {
                currentScreen = "score"
            }
        }

        "score" -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Quiz Completed, ${userName.text}!", fontSize = 22.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text("You scored $score out of ${questions.size}", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (score >= 3) "Great job!" else "Keep practicing!",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { currentScreen = "review" }) {
                    Text("Review")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { context.finish() }) {
                    Text("Exit")
                }
            }
        }

        "review" -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text("Review Questions", fontSize = 22.sp)
                Spacer(modifier = Modifier.height(10.dp))
                questions.forEachIndexed { index, question ->
                    Text("Q${index + 1}: $question", fontSize = 16.sp)
                    Text("Answer: ${if (answers[index]) "True" else "False"}", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { context.finish() }) {
                    Text("Exit")
                }
            }
        }
    }
}