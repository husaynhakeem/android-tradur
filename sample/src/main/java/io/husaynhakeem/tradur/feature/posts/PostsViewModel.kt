package io.husaynhakeem.tradur.feature.posts

import androidx.lifecycle.ViewModel


class PostsViewModel : ViewModel() {

    private val posts by lazy { PostsRepository.get() }

    fun loadPosts() = posts
}