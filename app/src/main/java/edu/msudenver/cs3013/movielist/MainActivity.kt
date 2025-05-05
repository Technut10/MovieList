package edu.msudenver.cs3013.movielist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    val movieList: MutableList<Movie?> = ArrayList<Movie?>()
    val movieAdapter = MovieAdapter(movieList as MutableList<Movie>)

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){activityResult ->
        val data = activityResult.data
        val title = data?.getStringExtra("title")?:""
        val yearOfRelease = data?.getStringExtra("yearOfRelease")?:""
        val movieGenre = data?.getStringExtra("movieGenre")?:""
        val rating = data?.getStringExtra("rating")?:""

        movieList.add(Movie(title, yearOfRelease, movieGenre, rating))
        movieAdapter.notifyDataSetChanged()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        movieList.add(Movie("Relic", "1988", "Sci-fi", "9.2"))
        movieList.add(Movie("Chessman", "1993", "Drama", "4.2"))

        val recyclerView = findViewById<RecyclerView?>(R.id.movie_view)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        // lines for swiping
         val itemTouchHelper = ItemTouchHelper(movieAdapter.SwipeToDeleteCallback())
         itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.setAdapter(movieAdapter)

        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()

    }

    fun startSecond(v:View){
        startForResult.launch(Intent(this, AddMovieActivity::class.java))
    }
}