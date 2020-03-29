package com.niksatyr.randomuser

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.niksatyr.randomuser.adapter.UserAdapter
import com.niksatyr.randomuser.api.RandomUserApi
import com.niksatyr.randomuser.repo.RemoteUserRepository
import com.niksatyr.randomuser.util.Failed
import com.niksatyr.randomuser.util.Loaded
import com.niksatyr.randomuser.util.Loading
import com.niksatyr.randomuser.util.State
import com.niksatyr.randomuser.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter

    private val viewModel: MainViewModel by viewModels {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val userApi = retrofit.create(RandomUserApi::class.java)
        val repo = RemoteUserRepository(userApi)
        MainViewModel.Factory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel.state.observe(this, androidx.lifecycle.Observer {
            updateState(it)
        })
        viewModel.loadUsers()
        btnRetry.setOnClickListener {
            viewModel.loadUsers()
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(this@MainActivity, Collections.emptyList())
        rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        viewModel.usersLiveData.observe(this, androidx.lifecycle.Observer {
            userAdapter.setUsers(it)
        })
    }

    private fun updateState(state: State) {
        when (state) {
            is Loading -> {
                progressBar.visibility = View.VISIBLE
                arrayOf(txtError, btnRetry).forEach {
                    it.visibility = View.INVISIBLE
                }
            }
            is Loaded -> {
                arrayOf(progressBar, txtError, btnRetry).forEach {
                    it.visibility = View.INVISIBLE
                }
            }
            is Failed -> {
                progressBar.visibility = View.INVISIBLE
                txtError.visibility = View.VISIBLE
                btnRetry.visibility = View.VISIBLE
            }
        }
    }

}
