package com.lguerra.ceibasoftware.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.databinding.ActivityUsersBinding
import com.lguerra.ceibasoftware.onTextChanged
import com.lguerra.ceibasoftware.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsersBinding

    private val viewModel: UsersViewModel by viewModels()

    private val userAdapter = UserAdapter(this::onClickItem)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.onCreate()

        setUpListAdapter()
        setUpObservers()
        setUpSearch()
    }

    private fun setUpSearch() {
        binding.editTextSearch.onTextChanged { viewModel.searchByName(it) }
        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchByName(binding.editTextSearch.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setUpObservers() {
        viewModel.users.observe(this) {
            userAdapter.submitList(it)
            binding.textEmptyList.isVisible = it.isEmpty()
        }
        viewModel.isLoading.observe(this) { binding.loading.isVisible = it }
    }

    private fun setUpListAdapter() {
        binding.recyclerUsers.apply {
            layoutManager = LinearLayoutManager(this@UsersActivity)
            adapter = userAdapter
        }
    }

    private fun onClickItem(user: User) {
        val intent = Intent(this, PostsActivity::class.java)
        intent.putExtra(USER_ID_KEY, user.id)
        startActivity(intent)
    }

    companion object {
        const val USER_ID_KEY = "user_id_key"
    }

}