package io.husaynhakeem.tradur.feature.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.husaynhakeem.tradur.R
import io.husaynhakeem.tradur.core.model.Post
import kotlinx.android.synthetic.main.layout_post.view.*


class PostsAdapter(private val posts: Array<Post>) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(posts[position])

    override fun getItemCount() = posts.size

    inner class PostViewHolder(parent: ViewGroup) :
            RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_post, parent, false)) {

        fun bind(post: Post) {
            Picasso.get()
                    .load(post.imageUrl)
                    .placeholder(R.drawable.gray_background)
                    .into(itemView.postImageView)
            itemView.postUsername.text = post.username
            itemView.postDescription.text = post.description
            itemView.postDate.text = post.publicationDate
        }
    }
}