package com.android.nbc_market

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.nbc_market.databinding.ActivityDetailBinding
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

        binding.ivBack.setOnClickListener {
            finish()
        }

    }
}