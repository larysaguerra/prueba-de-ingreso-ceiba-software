package com.lguerra.ceibasoftware.ui.view

import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.lguerra.ceibasoftware.databinding.ActivityPostsBinding
import com.lguerra.ceibasoftware.ui.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostsBinding

    private val viewModel: PostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let { extras ->
            extras.getInt(UsersActivity.USER_ID_KEY).let { userId ->
                viewModel.onCreate(userId)
            }
        }
        setUpObservers()

    }

    private fun setUpObservers() {
        viewModel.user.observe(this) { user ->
            binding.textName.apply { text = user.name }
            binding.textPhone.apply { text = user.phone }
            binding.textEmail.apply { text = user.email }
        }
        viewModel.posts.observe(this) { posts ->
            binding.textPosts.apply {

                val stringBuilder = StringBuilder()

                posts.forEachIndexed { index, post ->
                    stringBuilder.append("<b> ${index + 1}) ${post.title}</b><br><br>${post.body}.<br><br><br>")
                }

                text = Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_COMPACT)

            }
        }

        viewModel.isLoading.observe(this) { binding.loading.isVisible = it }
    }

}