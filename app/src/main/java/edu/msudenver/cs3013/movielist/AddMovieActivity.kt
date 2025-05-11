package edu.msudenver.cs3013.movielist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.msudenver.cs3013.movielist.databinding.ActivityAddMovieBinding
import android.util.Log

/**
 *  This class bundles the entered data and sends it back to .MainActivity to be used within a view.
 *  @author Anthony Putman
 *  @version 1.0.0
 *
 */
class AddMovieActivity : AppCompatActivity() {

    var passList = mutableListOf<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

    }

    fun backToFirst(v: View){
        var textTitle = findViewById<EditText>(R.id.title)
        var textYearOfRelease = findViewById<EditText>(R.id.year_of_release)
        var textMovieGenre = findViewById<EditText>(R.id.movie_genre)
        var textRating  = findViewById<EditText>(R.id.rating)

        var title =textTitle.getText().toString()
        var year = textYearOfRelease.getText().toString()
        var movieGenre = textMovieGenre.getText().toString()
        var rating = textRating.getText().toString()

        Log.d("backToFirst", "Data Sent: $title, $year, $movieGenre, $rating")

        setMovieInfo(title, year, movieGenre, rating)
        finish()

    }

    private fun setMovieInfo(title:String, year:String, genre:String, rating:String){
        var movieOfIntent = Intent()
        movieOfIntent.putExtra("title", title)
        movieOfIntent.putExtra("year", year)
        movieOfIntent.putExtra("genre", genre)
        movieOfIntent.putExtra("rating", rating)
        Intent(this, MainActivity::class.java).let{
                dataIntent ->
            dataIntent.putExtra("COUNT",passList.size.toString())
            for (i in 0 until passList.size) {
                dataIntent.putExtra("PASS${i + 1}", passList[i].toString())
            }
        Log.d("backToFirst", "This Set: $title, $year, $genre, $rating")
        setResult(Activity.RESULT_OK, movieOfIntent)
        finish()
    }
}}