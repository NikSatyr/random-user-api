package com.niksatyr.randomuser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.niksatyr.randomuser.api.RandomUserApi
import com.niksatyr.randomuser.repo.RemoteUserRepository
import com.niksatyr.randomuser.repo.UserRepository
import com.niksatyr.randomuser.viewmodel.MainViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        val userRepository: UserRepository by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val userApi = retrofit.create(RandomUserApi::class.java)
            return@lazy RemoteUserRepository(userApi)
        }
        MainViewModel.Factory(userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
