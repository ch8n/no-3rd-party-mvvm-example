package com.example.airtel.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.airtel.R
import com.example.airtel.detail.MovieDetailActivity
import com.example.airtel.di.Injector
import com.example.airtel.search.adapter.MovieSearchAdapter
import com.example.airtel.search.adapter.SearchListItem
import kotlinx.android.synthetic.main.activity_movie_search.*
import timber.log.Timber


class MovieSearchActivity : AppCompatActivity() {

    private lateinit var movieSearchAdapter: MovieSearchAdapter


    lateinit var viewModel: MovieSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        viewModel = Injector.searchViewModel(this)

        with(viewModel) {
            searchMovieLiveData.observe(this@MovieSearchActivity) { searchResult ->
                onSearchResult(searchResult)
                progress.visibility = View.GONE
            }
            errorLiveData.observe(this@MovieSearchActivity) {
                onErrorResult(it)
                progress.visibility = View.GONE
            }
        }

        val onSearchItemClick = { position: Int ->
            val searchItem = movieSearchAdapter.getItemAt(position)
            if (searchItem != null) {
                startActivity(
                    Intent(
                        this@MovieSearchActivity,
                        MovieDetailActivity::class.java
                    ).also {
                        it.putExtra(MovieDetailActivity.MOVIE_ID, searchItem.movieId)
                    })

            } else {
                Toast.makeText(
                    this@MovieSearchActivity,
                    MovieDetailActivity.MOVIE_ID_ERROR,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        list_movie.adapter = MovieSearchAdapter.newInstance(onSearchItemClick)
            .also {
                movieSearchAdapter = it
            }

        edit_movie_query.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                val query = editable.toString()
                progress.visibility = View.VISIBLE
                viewModel.searchMovieNative(query)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        image_back.setOnClickListener {
            finish()
        }
    }

    fun onSearchResult(items: List<SearchListItem>) {
        if (items.isEmpty()) {
            text_error.visibility = View.VISIBLE
            progress.visibility = View.GONE
            return
        }
        text_error.visibility = View.GONE
        movieSearchAdapter.submitList(items)
    }

    fun onErrorResult(error: Throwable) {
        Timber.e(error)
        text_error.visibility = View.VISIBLE
    }


}
