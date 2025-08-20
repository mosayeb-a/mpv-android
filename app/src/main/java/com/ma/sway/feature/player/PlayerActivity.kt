package com.ma.sway.feature.player

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ma.sway.common.VIDEO_PATH_EXTRA_KEY
import com.ma.sway.databinding.PlayerLayoutBinding

class PlayerActivity : AppCompatActivity() {
        private lateinit var binding: PlayerLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = PlayerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.player.initialize(filesDir.path, cacheDir.path)
        val videoPath = intent.getStringExtra(VIDEO_PATH_EXTRA_KEY)
        videoPath?.let {
            binding.player.playFile(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.player.destroy()
    }
}