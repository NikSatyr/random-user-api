package com.niksatyr.randomuser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.niksatyr.randomuser.api.RandomUserApi
import com.niksatyr.randomuser.repo.RemoteUserRepository
import com.niksatyr.randomuser.viewmodel.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

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
    }
}
