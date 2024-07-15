package com.android.nbc_market

import android.graphics.Rect
import android.os.Bundle
import android.view.TouchDelegate
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnPreDraw
import com.android.nbc_market.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<RecyclerItemDataClass>("data")
        binding.ivUsedThing.setImageResource(data?.img ?: R.drawable.ic_launcher_background)
        binding.tvName.text = data?.seller ?: "null"
        binding.tvDescAddress.text = data?.address ?: "null"
        binding.tvDetailThing.text = data?.usedThing ?: "null"
        binding.tvDetailDesc.text = data?.description ?: "null"
        binding.tvDetailPrice.text = NumberFormat.getInstance().format(data?.price) + "원"
        if(data?.isLiked == true) binding.ivDetailLike.setImageResource(R.drawable.full_heart)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivDetailLike.setOnClickListener {
            for(i in 0 until RecyclerItems.listData.size) {
                if(data?.num == RecyclerItems.listData[i].num) {
                    if(!RecyclerItems.listData[i].isLiked) {
                        RecyclerItems.listData[i].isLiked = true
                        RecyclerItems.listData[i].like++
                        binding.ivDetailLike.setImageResource(R.drawable.full_heart)
                        createSnackBar("관심 목록에 추가됐습니다.")
                        break
                    }
                    else {
                        RecyclerItems.listData[i].isLiked = false
                        RecyclerItems.listData[i].like--
                        binding.ivDetailLike.setImageResource(R.drawable.heart)
                        createSnackBar("관심 목록에서 삭제됐습니다.")
                        break
                    }
                }
            }
        }

    }

    private fun createSnackBar(str: String) {
        val snackbar = Snackbar.make(binding.root, str, 700)
        snackbar.show()
    }
}