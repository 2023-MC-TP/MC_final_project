package com.example.mc_finalproject

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mc_finalproject.databinding.SearchFragDateBinding
import java.util.Calendar

class SearchFragmentDate : Fragment() {
    private lateinit var dbHelper: MyDatabase.MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // 날짜 검색으로 바꿀 경우
        return SearchFragDateBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = SearchFragDateBinding.bind(view)
        dbHelper = MyDatabase.MyDbHelper(requireContext())

        // 날짜로 검색 > 검색 버튼
        binding.searchDateBut.setOnClickListener {
            val datePicker = binding.datePicker
            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth

            // 검색한 날짜 띄워주기
            val searchDate = formatDate(year, month, day)
            binding.dateTxt.text = "${searchDate}에 찍은 사진"

            val selectedDate = "$year/$month/$day"
            val getList = dbHelper.selectDate(selectedDate)

            val adapter = MyAdapter(getList)

            // 롱클릭 시 사진 삭제
            adapter.setItemLongClickListener(object: MyAdapter.OnItemLongClickListener {
                override fun onLongClick(v: View, position: Int) {
                    val db = dbHelper.writableDatabase
                    val itemId = adapter.getElement(position).image_id
                    db?.delete(MyDatabase.MyDBContract.MyEntry.TABLE_NAME, "${MyDatabase.MyDBContract.MyEntry.image_id}=?", arrayOf(itemId.toString()))
                    loadAndUpdateUI(view)
                    Toast.makeText(requireContext(), "사진이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                }
            })

            // 그냥 클릭 시 사진 상세보기
            adapter.setItemClickListener(object: MyAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    val intent: Intent = Intent(v.context, Detail::class.java).apply{
                        putExtra("id", adapter.getElement(position).image_id)
                    }
                    v.context.startActivity(intent)
                }
            })

            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.recyclerView.adapter = adapter
        }
    }
    private fun formatDate(year: Int, month: Int, day: Int): String {
        return String.format("%04d년 %02d월 %02d일", year, month, day)
    }

    private fun loadAndUpdateUI(view: View) {
        var binding = SearchFragDateBinding.bind(view)

        val datePicker = binding.datePicker
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        val selectedDate = "$year/$month/$day"

        binding.searchDateBut.setOnClickListener {
            val getList = dbHelper.selectDate(selectedDate)
            val adapter = MyAdapter(getList)

            adapter.setItemClickListener(object : MyAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    val db = dbHelper.writableDatabase
                    val itemId = adapter.getElement(position).image_id
                    db?.delete(
                        MyDatabase.MyDBContract.MyEntry.TABLE_NAME,
                        "${MyDatabase.MyDBContract.MyEntry.image_id}=?",
                        arrayOf(itemId.toString())
                    )
                    loadAndUpdateUI(view)
                }
            })

            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun byteArrayToDrawable(context: Context, byteArray: ByteArray): Drawable? {
        val options = BitmapFactory.Options().apply {
            inPreferredConfig = Bitmap.Config.ARGB_8888
        }
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
        return BitmapDrawable(context.resources, bitmap)
    }
}