package com.smn.exam.viewmodel

import androidx.lifecycle.ViewModel
import com.smn.exam.model.DataModel

class MainViewModel constructor()  : ViewModel() {


    fun getAllRVData() : ArrayList<DataModel>{
        val list  = ArrayList<DataModel>()
        for (i in 1..11) {
            if (i % 2 == 0)
                list?.add(DataModel("Test $i", "https://d1nhio0ox7pgb.cloudfront.net/_img/g_collection_png/standard/256x256/crash_test_dummy.png"))
            else
                list?.add(DataModel("Test $i", ""))

            println("Test $i")
        }
        return list
    }
}