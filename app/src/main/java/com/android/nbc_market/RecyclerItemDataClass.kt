package com.android.nbc_market

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecyclerItemDataClass(
    val num: Int,
    val img: Int,
    val usedThing: String,
    val description: String,
    val seller: String,
    val price: Int,
    val address: String,
    var like: Int,
    var chat: Int,
): Parcelable
