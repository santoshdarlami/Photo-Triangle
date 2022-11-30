package com.santosh.phototraingle

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val imageList = arrayOfNulls<Uri>(2)
    val mutableImageItemList: MutableLiveData<Array<ImageItem?>> = MutableLiveData()

    fun generateTriangleImageItemList(size: Int): MutableLiveData<Array<ImageItem?>>? {

        val imageItemList = arrayOfNulls<ImageItem>(size)
        if (imageList[1] == null) {
            Toast.makeText(getApplication(), "Please add Two images", Toast.LENGTH_SHORT).show()
            return null
        }
        var k = 1
        for (i in 0 until size) {
            if (k < size) {
                imageItemList[k - 1] = ImageItem(imageList[1]!!, "$k")
                k += i + 2
            }
            if (imageItemList[i] == null) {
                imageItemList[i] = ImageItem(imageList[0]!!, "${i + 1}")
            }
        }
        mutableImageItemList.value = imageItemList
        return mutableImageItemList
    }

}