package com.example.mc_finalproject

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mc_finalproject.databinding.HomePhotoListBinding

// 테스트 할려고 만든 파일.
// 이미지 뜨는거 확인 완료.
class TestAdapter(private val imageList: List<Int>) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    class TestViewHolder(val binding : HomePhotoListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(HomePhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val binding = holder.binding
        val imageResource = imageList[position]

        binding.photo1.setImageDrawable(ContextCompat.getDrawable(binding.root.context, imageResource))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}