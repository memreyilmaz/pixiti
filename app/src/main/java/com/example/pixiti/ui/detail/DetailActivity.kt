package com.example.pixiti.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pixiti.R
import com.example.pixiti.databinding.ActivityDetailBinding
import com.example.pixiti.model.Image

class DetailActivity : AppCompatActivity() {
    private var binding: ActivityDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val image : Image? = intent.extras?.getParcelable(BUNDLE_IMAGE)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentDetail: DetailFragment = DetailFragment.newInstance(image)
        fragmentTransaction.replace(R.id.frame_layout_detail, fragmentDetail)
        fragmentTransaction.commit()
    }

    companion object {
        const val BUNDLE_IMAGE = "image"
    }
}