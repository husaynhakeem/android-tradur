package io.husaynhakeem.tradur.feature.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.husaynhakeem.tradur.R
import kotlinx.android.synthetic.main.activity_main.*

class PostsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPosts()
    }

    private fun setupPosts() {
        val viewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)
        postsRecyclerView.layoutManager = LinearLayoutManager(this)
        postsRecyclerView.adapter = PostsAdapter(viewModel.loadPosts())
    }
}
