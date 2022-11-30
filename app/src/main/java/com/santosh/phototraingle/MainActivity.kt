package com.santosh.phototraingle


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var imageAdapter: ImageRvAdapter
    private lateinit var imageOne: ImageView
    private lateinit var imageTwo: ImageView
    private lateinit var sizeTextView: EditText
    private lateinit var generateButton: Button
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageRecyclerView = findViewById(R.id.image_rv)
        imageOne = findViewById(R.id.image1_iv)
        imageTwo = findViewById(R.id.image2_iv)
        sizeTextView = findViewById(R.id.size_et)
        generateButton = findViewById(R.id.generate_btn)

        imageOne.setOnClickListener {
            openGalleryForImages(1)
        }
        imageTwo.setOnClickListener {
            openGalleryForImages(2)
        }

        generateButton.setOnClickListener {
            if (sizeTextView.text.isEmpty()) {
                sizeTextView.error = "Please enter size"
            } else {
                viewModel.generateTriangleImageItemList(sizeTextView.text.toString().toInt())
            }
        }
        viewModel.mutableImageItemList.observe(this) {
            Log.d("TAG", "onCreate: ${viewModel.mutableImageItemList}")
            imageAdapter = ImageRvAdapter(it)
            imageRecyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
            imageRecyclerView.adapter = imageAdapter
        }

    }

    private fun openGalleryForImages(requestCode: Int) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).also {
            it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            it.addCategory(Intent.CATEGORY_OPENABLE)
            it.type = "image/*"
        }
        startActivityForResult(intent, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                for (i in 0..1) {
                    viewModel.imageList[i] = data.clipData?.getItemAt(i)!!.uri
                }
                imageOne.setImageURI(viewModel.imageList[0])
                imageTwo.setImageURI(viewModel.imageList[1])
            } else if (data?.data != null) {
                val imageUri: Uri = data.data!!
                when (requestCode) {
                    1 -> {
                        viewModel.imageList[0] = imageUri
                        imageOne.setImageURI(imageUri)
                    }
                    2 -> {
                        imageTwo.setImageURI(imageUri)
                        viewModel.imageList[1] = imageUri
                    }
                }
            }
        }
    }

}