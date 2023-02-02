package com.hossein.mywallet

import com.hossein.mywallet.db.UserDatabase
import com.hossein.mywallet.db.UserEntity
import academy.nouri.s1_room.utils.Constants
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.hossein.mywallet.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityAddUserBinding

    //Database
    private val userDb: UserDatabase by lazy {
        Room.databaseBuilder(this, UserDatabase::class.java, Constants.USER_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    //Other
    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "افزودن هزینه"

        //InitViews
        binding.apply {
            //Save
            saveBtn.setOnClickListener {
                val babat = BabatEdt.text.toString()
                val price = priceEdt.text.toString()

                if (babat.isNotEmpty() || price.isNotEmpty()) {
                    user = UserEntity(0, babat, price.toInt())
                    userDb.dao().insertUser(user)
                    finish()
                } else {
                    Snackbar.make(it, "Name and age cannot be empty", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}