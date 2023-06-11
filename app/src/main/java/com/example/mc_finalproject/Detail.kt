package com.example.mc_finalproject

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.mc_finalproject.databinding.DetailBinding

class Detail: AppCompatActivity() {
    private lateinit var dbHelper: MyDatabase.MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbId = intent.getIntExtra("id", 0)

        dbHelper = MyDatabase.MyDbHelper(this)
        val item = dbHelper.findById(dbId)

        binding.detailImg.setImageDrawable(byteArrayToDrawable(binding.root.context, item.image))
        binding.detailName.text = item.name
        binding.detailDate.text = item.date
        binding.detailPlace.text = item.place
        binding.detailComment.text = item.comment

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}

private fun byteArrayToDrawable(context: Context, byteArray: ByteArray): Drawable? {
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    return BitmapDrawable(context.resources, bitmap)
}