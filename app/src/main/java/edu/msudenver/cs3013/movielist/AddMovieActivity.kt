package edu.msudenver.cs3013.movielist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.msudenver.cs3013.movielist.databinding.ActivityAddMovieBinding

class AddMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_movie)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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


        setMovieInfo(title, year, movieGenre, rating)

    }

    private fun setMovieInfo(title:String, year:String, genre:String, rating:String){
        var movieOfIntent = Intent()

        movieOfIntent.putExtra("title", title)
        movieOfIntent.putExtra("year", year)
        movieOfIntent.putExtra("genre", genre)
        movieOfIntent.putExtra("rating", rating)

        setResult(RESULT_OK, movieOfIntent)
        finish()
    }
}