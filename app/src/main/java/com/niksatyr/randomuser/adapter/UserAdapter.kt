package com.niksatyr.randomuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.niksatyr.randomuser.R
import com.niksatyr.randomuser.model.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class UserAdapter(
    context: Context,
    var onUserSelectedListener: OnUserSelectedListener? = null,
    private var users: List<User> = Collections.emptyList()
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        val fullBirthdayInfo = "${dateFormat.format(user.birthday)} (${user.age})"
        holder.apply {
            Picasso.get().load(user.largePhotoUrl).into(thumbnailPhoto)
            txtName.text = user.fullName
            txtBirthday.text = fullBirthdayInfo
            cardView.setOnClickListener {
                onUserSelectedListener?.onUserSelected(users[position])
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtBirthday: TextView = itemView.findViewById(R.id.txtBirthday)
        val thumbnailPhoto: CircleImageView = itemView.findViewById(R.id.thumbnail)
        val cardView: CardView = itemView.findViewById(R.id.cardViewUser)
    }

    interface OnUserSelectedListener {
        fun onUserSelected(user: User)
    }

}