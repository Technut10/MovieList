package edu.msudenver.cs3013.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.Int

class MovieAdapter(private val movieList: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder?>() {
    class MovieViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById<TextView?>(R.id.titleTextView)
        var movieGenreTextView: TextView = itemView.findViewById<TextView?>(R.id.movie_genre_TextView)
        var yearReleaseView: TextView = itemView.findViewById<TextView?>(R.id.year_release_TextView)
        var ratingTextView: TextView = itemView.findViewById<TextView?>(R.id.rating_TextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): MovieViewHolder{
        val view: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList.get(position)
        holder.titleView.text = movie.title
        holder.yearReleaseView.setText(movie.yearOfRelease)
        holder.movieGenreTextView.setText(movie.movieGenre)
        holder.ratingTextView.setText(movie.rating)
    }

    override fun getItemCount():Int{
        return movieList.size
    }

    fun removeMovie(position: Int){
        movieList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class SwipeToDeleteCallback: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) =
            if (viewHolder is MovieViewHolder) {
                makeMovementFlags(
                    ItemTouchHelper.ACTION_STATE_IDLE,
                    ItemTouchHelper.RIGHT
                ) or makeMovementFlags(
                    ItemTouchHelper.ACTION_STATE_SWIPE,
                    ItemTouchHelper.RIGHT
                )
            }else{
                0
            }



        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            removeMovie(position)
        }

    }

}
