package com.android.nbc_market

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.nbc_market.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var recyclerItems: List<RecyclerItemDataClass>
    private val permissionList = Manifest.permission.POST_NOTIFICATIONS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerItems = RecyclerItems.getList(resources)
        val recyclerAdapter = RecyclerAdapter(recyclerItems)
        val recycler = binding.recycler

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                createBackDialog()
            }
        }

        val requestPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            when(it) {
                true -> { Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show() }
                false -> { Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show() }
            }
        }

        val floatingFadeInAnimation = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val floatingFadeOutAnimation = AlphaAnimation(1f, 0f).apply { duration = 500 }

        onBackPressedDispatcher.addCallback(this, callback)

        recycler.adapter = recyclerAdapter
        recycler.layoutManager = LinearLayoutManager(this)

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy != 0 && recycler.computeVerticalScrollOffset() > 0 && !binding.ivBtnFloating.isVisible) {
                    binding.ivBtnFloating.isVisible = true
                    binding.ivBtnFloating.startAnimation(floatingFadeInAnimation)
                }
                else if(recycler.computeVerticalScrollOffset() == 0) {
                    binding.ivBtnFloating.isVisible = false
                    binding.ivBtnFloating.startAnimation(floatingFadeOutAnimation)
                }
            }
        })

        binding.ivNotice.setOnClickListener {
            // 왜 이 권한을 요청하는지에 대한 근거를 설명해줄 수 있음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                requestPermission.launch(permissionList)
            }
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "알림 권한이 없어 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            else notification()
        }

        binding.ivBtnFloating.isVisible = false

        binding.ivBtnFloating.setOnClickListener {
            binding.recycler.smoothScrollToPosition(0)
        }

        recyclerAdapter.itemClick = object : RecyclerAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                startIntentToDetailActivity(position)
            }
        }

    }

    fun createBackDialog() {
        val dialog = AlertDialog.Builder(this@MainActivity)
        dialog.setTitle("종료")
        dialog.setIcon(R.drawable.chat)
        dialog.setMessage("정말 종료하시겠습니까?")

        val clickListener = DialogInterface.OnClickListener { _, which ->
            when(which) {
                DialogInterface.BUTTON_NEGATIVE -> {}
                DialogInterface.BUTTON_POSITIVE -> {
                    finish()
                }
            }
        }

        dialog.setPositiveButton("확인", clickListener)
        dialog.setNegativeButton("취소", clickListener)

        dialog.show()
    }

    fun startIntentToDetailActivity(position: Int) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("data", recyclerItems[position])
        startActivity(intent)
    }

    private fun notification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "keyword-channel"
            val channelName = "Notify Keyword Channel"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "When Keyword Add, Notify"
                setShowBadge(true)
            }
            manager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, channelId)
        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.run {
            setSmallIcon(R.mipmap.ic_launcher_round)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
        }
        manager.notify(1, builder.build())
    }

}