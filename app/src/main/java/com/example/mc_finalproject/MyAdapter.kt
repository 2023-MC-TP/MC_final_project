package com.example.mc_finalproject

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mc_finalproject.databinding.HomePhotoListBinding

class MyAdapter(private var dataset: MutableList<MyElement>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: HomePhotoListBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = dataset.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(HomePhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    fun setList(newList: MutableList<MyElement>){
        this.dataset = newList
    }
    fun getElement(pos: Int): MyElement{
        return dataset[pos]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = holder.binding
        val element = dataset[position]

        // ByteArray를 Drawable로 변환
        val drawable = byteArrayToDrawable(binding.root.context, element.image)
        Log.d("TAG", drawable.toString())

        // Drawable을 ImageView에 설정
        binding.photo1.setImageDrawable(drawable)
    }
}
private fun byteArrayToDrawable(context: Context, byteArray: ByteArray): Drawable? {
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    return BitmapDrawable(context.resources, bitmap)
}

