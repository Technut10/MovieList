package edu.msudenver.cs3013.movielist

open class Movie(val title:String, val yearOfRelease: String, val movieGenre:String, val rating: String) {
    override fun toString(): String {
        return "Title: $title, Year: $yearOfRelease, Movie: $movieGenre, Rating: $rating"
    }

}