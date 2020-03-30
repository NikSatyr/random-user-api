package com.niksatyr.randomuser.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.niksatyr.randomuser.R
import com.niksatyr.randomuser.dto.User
import com.niksatyr.randomuser.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity(R.layout.activity_details) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        viewModel.user.observe(this, Observer {
            displayUserDetails(it)
        })
        if (savedInstanceState == null) {
            val user: User = intent?.extras?.getParcelable(KEY_USER)
                ?: throw IllegalArgumentException("This activity requires a user object to be passed")
            viewModel.setUser(user)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish() // We won't need this activity, hence may be killed
    }

    override fun onSupportNavigateUp(): Boolean {
        finish() // We won't need this activity, hence may be killed
        return super.onSupportNavigateUp()
    }

    private val viewModel: DetailsViewModel by viewModels()

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    private fun displayUserDetails(user: User) {
        Picasso.get().load(user.photoUrls.large).into(imgDetailsPhoto)
        val fullName = "${user.name.title} ${user.name.first} ${user.name.last}"
        txtDetailsName.text = fullName
        txtDetailsBirthday.text = dateFormat.format(user.dateOfBirth.date)
        txtDetailsEmail.text = user.email
        txtDetailsPhone.text = user.phone
    }

    companion object {
        const val KEY_USER = "user"
    }

}