package com.lguerra.ceibasoftware.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lguerra.ceibasoftware.data.model.User
import com.lguerra.ceibasoftware.databinding.ItemUserBinding

class UserAdapter(
    private val onClick: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(PermissionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            ItemUserBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
                .root,
            onClick
        )

    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    class UserViewHolder(view: View, onClick: (User) -> Unit) : RecyclerView.ViewHolder(view) {

        private val binding = ItemUserBinding.bind(itemView)
        private lateinit var currentUser: User

        init {
            binding.buttonShowPost.setOnClickListener {
                if (this::currentUser.isInitialized) {
                    onClick(currentUser)
                }
            }
        }

        fun bind(permission: User) {
            currentUser = permission
            binding.textName.apply { text = currentUser.name }
            binding.textPhone.apply { text = currentUser.phone }
            binding.textEmail.apply { text = currentUser.email }
        }

    }

}

object PermissionDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: User, newItem: User) =
        oldItem.email == newItem.email
}