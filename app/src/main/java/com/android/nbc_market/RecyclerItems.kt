package com.android.nbc_market

import android.content.res.Resources

object RecyclerItems {
    val listData: MutableList<RecyclerItemDataClass> = mutableListOf()
    private var num = 0
    private val arrPhoto = intArrayOf(
        R.drawable.sample1,
        R.drawable.sample2,
        R.drawable.sample3,
        R.drawable.sample4,
        R.drawable.sample5,
        R.drawable.sample6,
        R.drawable.sample7,
        R.drawable.sample8,
        R.drawable.sample9,
        R.drawable.sample10,
    )
    private val arrPrice = intArrayOf(
        1000,
        20000,
        10000,
        10000,
        150000,
        50000,
        150000,
        180000,
        30000,
        190000,
    )
    private val arrLike = intArrayOf(
        13,
        8,
        23,
        14,
        22,
        25,
        142,
        31,
        7,
        40,
    )
    private val arrChat = intArrayOf(
        25,
        28,
        5,
        17,
        9,
        16,
        54,
        7,
        28,
        6,
    )


    fun initData(resources: Resources) {
        val arrThing = resources.getStringArray(R.array.used_thing_name)
        val arrDesc = resources.getStringArray(R.array.used_description)
        val arrSeller = resources.getStringArray(R.array.used_seller)
        val arrAddress = resources.getStringArray(R.array.used_address)
        repeat(10) {
            addItem(
                RecyclerItemDataClass(
                    num, arrPhoto[it], arrThing[it], arrDesc[it], arrSeller[it],
                    arrPrice[it], arrAddress[it], arrLike[it], arrChat[it], false
                )
            )
        }
    }

    fun addItem(item: RecyclerItemDataClass) {
        num++
        listData.add(item)
    }

    fun removeItem(position: Int) {
        listData.removeAt(position)
    }

//    fun getList(): List<RecyclerItemDataClass> = listData

}