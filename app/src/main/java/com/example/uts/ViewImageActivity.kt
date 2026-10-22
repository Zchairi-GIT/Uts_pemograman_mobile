package com.example.uts

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.uts.databinding.ActivityViewImageBinding
import com.example.uts.helpers.Config

class ViewImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val images = intent.getStringArrayListExtra(Config.EXTRA_IMAGES) ?: arrayListOf()
        val margin = (16 * resources.displayMetrics.density).toInt()

        images.forEach { url ->
            val iv = ImageView(this)
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = margin
            iv.layoutParams = lp
            iv.adjustViewBounds = true
            iv.contentDescription = getString(R.string.image_view_product)

            Glide.with(this)
                .load(url)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_dialog_alert)
                .into(iv)

            binding.llImages.addView(iv)
        }
    }
}