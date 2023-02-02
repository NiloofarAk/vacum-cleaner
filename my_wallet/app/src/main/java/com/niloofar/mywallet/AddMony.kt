package com.hossein.mywallet


import com.hossein.mywallet.db.UserEntity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hossein.mywallet.databinding.ActivityAddMonyBinding

class AddMony : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityAddMonyBinding


    //Other
    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMonyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = " افزایش موجودی"

        //InitViews
        binding.apply {
            //Save
            val a = SavePref().GET(this@AddMony)
            saveBtn.setOnClickListener {
                SavePref().SAVE(this@AddMony,a + afzayesh.text.toString().toInt())
              //  startActivity(Intent(this@AddMony, MainActivity::class.java))
                finish()
            }
        }
    }
}