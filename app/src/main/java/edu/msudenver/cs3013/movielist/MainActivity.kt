package edu.msudenver.cs3013.movielist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.util.Scanner
import kotlin.jvm.java
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    val movieList: MutableList<Movie?> = ArrayList<Movie?>()
    val movieAdapter = MovieAdapter(movieList as MutableList<Movie>)
    var myPlacer:String? = null


    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){activityResult ->
        val data = activityResult.data
        val title = data?.getStringExtra("title") ?:""
        val yearOfRelease = data?.getStringExtra("year") ?:""
        val movieGenre = data?.getStringExtra("genre") ?:""
        val rating = data?.getStringExtra("rating") ?:""
        movieList.add(Movie(title, yearOfRelease, movieGenre, rating))
        movieAdapter.notifyDataSetChanged()
        Log.d("backToFirst", movieList.toString())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDirectory = this.getFilesDir()
        val myDirName  = myDirectory.getAbsolutePath()
        val button = findViewById<Button>(R.id.save_button)

        myPlacer  = myDirName



        readFile(movieList as MutableList<Movie>)

        button.setOnClickListener{
            writeFile()
        }

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

    fun readFile(movieList: MutableList<Movie>){
        Log.d("READFILE", "<<<<<<reading in file>>>>>>" )
        try {
            val f = File("$myPlacer/Top20.csv")
            f.createNewFile()
//            val file = File("$myPlacer/MOVIELIST.csv")
//            file.createNewFile()
            val myReader = Scanner(f)
            while (myReader.hasNextLine()) {
                val data = myReader.nextLine()
                Log.d("INPUTSS", "Line of input data: " + data)
                val parts = data.split(",")
                movieList.add(Movie(parts[0], parts[1], parts[2], parts[3]))
            }
            myReader.close()
        }catch(e: IOException){
            Log.d("READ", "FAILED >>> EXCEPTION>>>>>>>>>>>>>>>>>>>>> $e")
            println("An error occured.")
            e.printStackTrace()
        }
    }


    fun writeFile(){
        val count = movieList.size

        Log.d("WRITEFILE", "writeFile() entered")
        try {
            val f = File("$myPlacer/Top20.csv")
            val fw = FileWriter(f, false)
            if (f.exists()) {
                for (i in 0..<count) {
                    val s = movieList[i]?.toString()
                    fw.write("$s \n")
                }
                Log.d("WRITEFILE", "<<<<<<<<<<FILE EXIST>>>>>>>>>>>>>>>")
            } else {
                Log.d("WRITEFILE", "<<<<<<<<<<<<<<<<FILE DOES NOT EXIST!!!!!!>>>>>>>>>>>>")
            }

//            val fw = FileWriter(f, false)
//            val count = movieList.size
//            for (i in 0..<count) {
//                    val s = movieList[i]?.toString() as String?
//                    fw.write("$s \n")
//                }

            fw.flush()
            fw.close()
        }catch (iox: IOException){
            Log.d("WRITEFILE", "EXCEP: " + iox)

        }catch (e: FileNotFoundException){
            print("errrrrrroooooorrrrr")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.xml, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ratingSort -> {
                movieList?.sortBy{it?.rating}
                movieAdapter.notifyDataSetChanged()
            }
            R.id.yearSort ->{
                movieList?.sortBy { it?.yearOfRelease }
                movieAdapter.notifyDataSetChanged()
            }
            R.id.genreSort -> {
                movieList?.sortBy{it?.movieGenre }
                movieAdapter.notifyDataSetChanged()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}