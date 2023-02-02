package com.hossein.mywallet


import academy.nouri.s1_room.utils.Constants
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.hossein.mywallet.databinding.ActivityUpdateUserBinding
import com.hossein.mywallet.db.UserDatabase
import com.hossein.mywallet.db.UserEntity

class UpdateUserActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityUpdateUserBinding

    //Database
    private val userDb: UserDatabase by lazy {
        Room.databaseBuilder(this, UserDatabase::class.java, Constants.USER_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    //Other
    private lateinit var userEntity: UserEntity
    private var userID = 0
    private var defaultBabat = ""
    private var defaultPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "ویرایش هزینه ها"

        //Intent
        intent.extras?.let {
            userID = it.getInt(Constants.BUNDLE_USER_ID)
        }
        //Binding
        binding.apply {
            defaultBabat = userDb.dao().getUser(userID).Babat
            defaultPrice = userDb.dao().getUser(userID).Price
            babatEdt.setText(defaultBabat)
            priceEdt.setText(defaultPrice.toString())
            //Delete
            deleteBtn.setOnClickListener {
                userEntity = UserEntity(userID, defaultBabat, defaultPrice)
                userDb.dao().deleteUser(userEntity)
                finish()
            }
            //Update
            updateBtn.setOnClickListener {
                val Babat = babatEdt.text.toString()
                val Price = priceEdt.text.toString()

                if (Babat.isNotEmpty() || Price.isNotEmpty()) {
                    userEntity = UserEntity(userID, Babat, Price.toInt())
                    userDb.dao().updateUser(userEntity)
                    finish()
                } else {
                    Snackbar.make(it, "Name and age cannot be empty", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}