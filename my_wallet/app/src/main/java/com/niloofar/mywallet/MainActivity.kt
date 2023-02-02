package com.hossein.mywallet

import academy.nouri.s1_room.utils.Constants
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hossein.mywallet.databinding.ActivityMainBinding
import com.hossein.mywallet.db.UserDatabase

class MainActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityMainBinding
    var sumprice = 0

    //Database
    private val userDb: UserDatabase by lazy {
        Room.databaseBuilder(this, UserDatabase::class.java, Constants.USER_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    //Other
    private val usersAdapter by lazy { UsersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //InitViews
        binding.apply {
            //Add new user
            addUserBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddUserActivity::class.java))
            }
            addBtnMony.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddMony::class.java))
            }


        }
    }

    override fun onResume() {
        super.onResume()
        //Show Items
        checkItems()
    }

    private fun checkItems() {
        binding.apply {
            if (userDb.dao().getAllUser().isNotEmpty()) {
                usersList.visibility = View.VISIBLE
                emptyText.visibility = View.GONE
                //Setup recycler
                usersAdapter.differ.submitList(userDb.dao().getAllUser())

                val size = userDb.dao().getAllUser().size
                    sumprice = 0
                for (i in 1..size) {
                    val check = userDb.dao().getUser(i).Price
                    if(check != null){
                        sumprice += check
                    }


                }

                setupRecyclerView()
            } else {
                usersList.visibility = View.GONE
                emptyText.visibility = View.VISIBLE
            }
            val a = SavePref().GET(this@MainActivity)
            val q = a - sumprice
            mojodi.text = "$q تومان"
        }
    }


    private fun setupRecyclerView() {
        binding.usersList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
        }
    }


}