package com.example.mc_finalproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mc_finalproject.databinding.HomeBinding
import com.example.mc_finalproject.databinding.HomePhotoListBinding

class HomeFragment: Fragment() {
    private lateinit var dbHelper: MyDatabase.MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return HomeBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = HomeBinding.bind(view)

        // 테스트 해보려고 주석처리 했어요
//        dbHelper = MyDatabase.MyDbHelper(requireContext())
//        val db = dbHelper.writableDatabase
//
//        val getList = dbHelper.selectAll()
//        if (getList.isNotEmpty()){
//            val data = getList[0]
//            val drawable = byteArrayToDrawable(requireContext(), data.image)
//        }
//        val adapter = MyAdapter(getList)
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.adapter = adapter
//        binding.recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                requireContext(),
//                LinearLayoutManager.VERTICAL
//            )
//        )


        // Adapter,RecyclerView로 이미지 띄우기 test
        val imageList = listOf(
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background
        )

        val testA = TestAdapter(imageList)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = testA
    }
}

private fun byteArrayToDrawable(context: Context, byteArray: ByteArray): Drawable? {
    val options = BitmapFactory.Options().apply {
        inPreferredConfig = Bitmap.Config.ARGB_8888
    }
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
    return BitmapDrawable(context.resources, bitmap)
}

