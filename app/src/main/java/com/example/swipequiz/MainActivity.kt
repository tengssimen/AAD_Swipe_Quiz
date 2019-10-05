package com.example.swipequiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Console


class MainActivity : AppCompatActivity() {



    /*
    for (i in Question.questionStrings.indices) {
        questions.add(Question(Question.questionStrings[i], Question.questionAnswers[i]))
    } */


    private val questions = arrayListOf(
        Question("The moon is a blue cheese", false),
        Question("2 + 2 = 5", false),
        Question("Noten means nuts in dutch", true),
        Question("The inside of an apple is called a clock house in dutch", true)
    )
    private val questionAdapter = QuestionAdapter(questions)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        rvQuestion.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestion.adapter = questionAdapter
        rvQuestion.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelper().attachToRecyclerView(rvQuestion)

        questionAdapter.notifyDataSetChanged()
    }


    fun snackbar(text: String) {
        Snackbar.make(
            findViewById(R.id.mainLayout),
            text,
            Snackbar.LENGTH_SHORT
        ).show()
    }


    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                //The user chose to swipe LEFT, which means the users answer is that the statement is correct.
                if (direction == ItemTouchHelper.LEFT) {

                    //questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()

                    val selectedItem = questions[position]
                    val answer = selectedItem.answer
                    val response: String

                    response = when (answer) {

                        true -> {
                            "Correct"
                        }
                        false -> {
                            "False"
                        }

                    }

                    snackbar(response)

                } else {

                    //questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()

                    val selectedItem = questions[position]
                    val answer = selectedItem.answer
                    val response: String

                    response = when (answer) {

                        true -> {
                            "False"
                        }
                        false -> {
                            "Correct"
                        }

                    }

                    snackbar(response)


                }
                questionAdapter.notifyItemChanged(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        // itemTouchHelper.attachToRecyclerView(rvQuestion)
        return itemTouchHelper
    }

}