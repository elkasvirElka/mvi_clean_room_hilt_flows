package com.example.mvi_clean_room_hilt_flows

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.mvi_clean_room_hilt_flows.data.local.MovieDatabase
import com.example.mvi_clean_room_hilt_flows.data.local.dao.MovieInfoDao
import com.example.mvi_clean_room_hilt_flows.data.local.entity.MovieInfoEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDatabaseTest {
    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieInfoDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries() // For testing purposes only
            .build()
        dao = database.dao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveMovies() = runBlocking {
        // Given a list of movies
        val movie1 = MovieInfoEntity(
            id = 1,
            adult = false,
            backdrop_path = "/path1.jpg",
            genre_ids = listOf(28, 12),
            original_language = "en",
            original_title = "Movie 1",
            overview = "Overview 1",
            popularity = 7.8,
            poster_path = "/poster1.jpg",
            release_date = "2023-01-01",
            title = "Movie 1",
            video = false,
            vote_average = 8.0,
            vote_count = 100
        )

        val movie2 = MovieInfoEntity(
            id = 2,
            adult = false,
            backdrop_path = "/path2.jpg",
            genre_ids = listOf(16, 35),
            original_language = "en",
            original_title = "Movie 2",
            overview = "Overview 2",
            popularity = 6.5,
            poster_path = "/poster2.jpg",
            release_date = "2023-02-01",
            title = "Movie 2",
            video = false,
            vote_average = 7.5,
            vote_count = 80
        )

        val movies = listOf(movie1, movie2)

        // Insert movies into the database
        dao.insertMovies(movies)

        // When retrieving all movies
        val allMovies = dao.getMoviesInfo()

        // Then the retrieved movies match the inserted movies
        assertEquals(2, allMovies.size)
        assertTrue(allMovies.contains(movie1))
        assertTrue(allMovies.contains(movie2))
    }

    @Test
    fun upsertAndRetrieveMovies() = runBlocking {
        // Given an initial list of movies
        val movie = MovieInfoEntity(
            id = 1,
            adult = false,
            backdrop_path = "/path.jpg",
            genre_ids = listOf(28),
            original_language = "en",
            original_title = "Original Movie",
            overview = "Original Overview",
            popularity = 5.0,
            poster_path = "/poster.jpg",
            release_date = "2023-03-01",
            title = "Original Movie",
            video = false,
            vote_average = 6.0,
            vote_count = 50
        )
        dao.insertMovies(listOf(movie))

        // When updating with new data using upsert
        val updatedMovie = movie.copy(
            overview = "Updated Overview",
            popularity = 9.0
        )
        dao.upsertAll(listOf(updatedMovie))

        // Then the retrieved movie reflects the updated data
        val retrievedMovie = dao.getMoviesInfo().first()
        assertEquals("Updated Overview", retrievedMovie.overview)
        assertEquals(9.0, retrievedMovie.popularity, 0.0)
    }

    @Test
    fun deleteSpecificMovies() = runBlocking {
        // Insert movies
        val movie1 = MovieInfoEntity(id = 1, title = "Movie 1", adult = false, backdrop_path = "/path1.jpg", genre_ids = listOf(28), original_language = "en", original_title = "Movie 1", overview = "Overview 1", popularity = 7.8, poster_path = "/poster1.jpg", release_date = "2023-01-01", video = false, vote_average = 8.0, vote_count = 100)
        val movie2 = MovieInfoEntity(id = 2, title = "Movie 2", adult = false, backdrop_path = "/path2.jpg", genre_ids = listOf(16), original_language = "en", original_title = "Movie 2", overview = "Overview 2", popularity = 6.5, poster_path = "/poster2.jpg", release_date = "2023-02-01", video = false, vote_average = 7.5, vote_count = 80)
        dao.insertMovies(listOf(movie1, movie2))

        // Delete movie1 by title
        dao.deleteMovies(listOf("Movie 1"))

        // Verify only movie2 remains
        val remainingMovies = dao.getMoviesInfo()
        assertEquals(1, remainingMovies.size)
        assertTrue(remainingMovies.contains(movie2))
    }

    @Test
    fun deleteAllMovies() = runBlocking {
        // Insert movies
        val movies = listOf(
            MovieInfoEntity(id = 1, title = "Movie 1", adult = false, backdrop_path = "/path1.jpg", genre_ids = listOf(28), original_language = "en", original_title = "Movie 1", overview = "Overview 1", popularity = 7.8, poster_path = "/poster1.jpg", release_date = "2023-01-01", video = false, vote_average = 8.0, vote_count = 100),
            MovieInfoEntity(id = 2, title = "Movie 2", adult = false, backdrop_path = "/path2.jpg", genre_ids = listOf(16), original_language = "en", original_title = "Movie 2", overview = "Overview 2", popularity = 6.5, poster_path = "/poster2.jpg", release_date = "2023-02-01", video = false, vote_average = 7.5, vote_count = 80)
        )
        dao.insertMovies(movies)

        // Delete all movies
        dao.deleteAllMovies()

        // Verify the database is empty
        val allMovies = dao.getMoviesInfo()
        assertTrue(allMovies.isEmpty())
    }
}
