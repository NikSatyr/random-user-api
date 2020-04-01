package com.niksatyr.randomuser.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.niksatyr.randomuser.R
import com.niksatyr.randomuser.adapter.UserAdapter
import com.niksatyr.randomuser.api.RandomUserApi
import com.niksatyr.randomuser.model.User
import com.niksatyr.randomuser.repo.LocalSettingsRepository
import com.niksatyr.randomuser.repo.RemoteUserRepository
import com.niksatyr.randomuser.util.Failed
import com.niksatyr.randomuser.util.Loaded
import com.niksatyr.randomuser.util.Loading
import com.niksatyr.randomuser.util.State
import com.niksatyr.randomuser.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var userAdapter: UserAdapter

    private val viewModel: MainViewModel by viewModels {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val userApi = retrofit.create(RandomUserApi::class.java)
        val usersRepo = RemoteUserRepository(userApi)
        MainViewModel.Factory(usersRepo, LocalSettingsRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        viewModel.state.observe(this, androidx.lifecycle.Observer {
            displayState(it)
        })
        btnRetry.setOnClickListener {
            loadUsers()
        }
        // This is to avoid redundant loading on screen rotation
        if (savedInstanceState == null) {
            loadUsers()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSettings -> startActivity(Intent(this, SettingsActivity::class.java))
            R.id.menuRefresh -> loadUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadUsers() {
        val count = viewModel.getFetchedUsersCount()
        viewModel.apply {
            clearLoadedUsers() // To avoid cached users on error on this request
            loadUsers(count)
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(this@MainActivity, object : UserAdapter.OnUserSelectedListener {
                override fun onUserSelected(user: User) {
                    openUserDetails(user)
                }
            })
        rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        viewModel.usersLiveData.observe(this, androidx.lifecycle.Observer {
            userAdapter.setUsers(it)
        })
    }

    private fun displayState(state: State) {
        when (state) {
            is Loading -> {
                progressBar.visibility = View.VISIBLE
                arrayOf(txtError, btnRetry, rvUsers).forEach {
                    it.visibility = View.INVISIBLE
                }
            }
            is Loaded -> {
                arrayOf(progressBar, txtError, btnRetry).forEach {
                    it.visibility = View.INVISIBLE
                }
                rvUsers.visibility = View.VISIBLE
            }
            is Failed -> {
                progressBar.visibility = View.INVISIBLE
                txtError.visibility = View.VISIBLE
                btnRetry.visibility = View.VISIBLE
            }
        }
    }

    private fun openUserDetails(user: User) {
        val intent = Intent(this, DetailsActivity::class.java)
            .putExtra(DetailsActivity.KEY_USER, user)
        startActivity(intent)
    }

}
