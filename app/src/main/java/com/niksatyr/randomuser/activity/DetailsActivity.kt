package com.niksatyr.randomuser.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.niksatyr.randomuser.R
import com.niksatyr.randomuser.model.User
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
        val fullBirthdayInfo = "${dateFormat.format(user.birthday)} (${user.age})"
        Picasso.get().load(user.largePhotoUrl).into(imgDetailsPhoto)
        txtDetailsName.text = user.fullName
        txtDetailsBirthday.text = fullBirthdayInfo
        txtDetailsEmail.text = user.email
        txtDetailsPhone.text = user.phone
        txtDetailsAddress.text = user.address
    }

    companion object {
        const val KEY_USER = "user"
    }

}