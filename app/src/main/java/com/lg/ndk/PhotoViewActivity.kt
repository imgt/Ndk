package com.lg.ndk

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lg.ndk.util.Util
import uk.co.senab.photoview.PhotoView
import java.io.File

/**
 */
class PhotoViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //隐藏标题栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //隐藏状态栏
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view)
        val path = intent.getStringExtra("path")
        val photoView = findViewById<View>(R.id.image) as PhotoView
        Glide.with(this).load(path).skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE).into(photoView)
        val size = findViewById<View>(R.id.size) as TextView
        size.text = Util.getFormatSize(File(path).length().toDouble())
    }
}