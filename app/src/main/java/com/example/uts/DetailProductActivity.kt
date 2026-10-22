package com.example.uts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.example.uts.databinding.ActivityDetailProductBinding
import com.example.uts.helpers.Config
import com.example.uts.model.ProductItem

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val json = intent.getStringExtra(Config.EXTRA_PRODUCT)
        val product = Gson().fromJson(json, ProductItem::class.java)

        Glide.with(applicationContext)
            .load(product.thumbnail)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(binding.imgItemPhoto)

        binding.tvNama.text = product.title
        binding.tvId.text = product.id.toString()
        binding.tvBrand.text = product.brand ?: "-"
        binding.tvCategory.text = product.category
        binding.tvPrice.text = "$${product.price}"
        binding.tvDiscount.text = "${product.discountPercentage}%"
        binding.tvRating.text = product.rating.toString()
        binding.tvStock.text = product.stock.toString()
        binding.tvAvailability.text = product.availabilityStatus
        binding.tvTags.text = product.tags.joinToString(", ")
        binding.tvDimensions.text =
            "${product.dimensions.width} x ${product.dimensions.height} x ${product.dimensions.depth}"
        binding.tvDescription.text = product.description

        binding.tvReviews.text = if (product.reviews.isEmpty()) {
            "-"
        } else {
            product.reviews.joinToString("\n\n") {
                "${it.reviewerName} (${it.rating}/5)\n\"${it.comment}\"\n${it.reviewerEmail}"
            }
        }

        binding.btnViewImage.setOnClickListener {
            val images = ArrayList(product.images.ifEmpty { listOf(product.thumbnail) })
            val intent = Intent(this@DetailProductActivity, ViewImageActivity::class.java)
            intent.putStringArrayListExtra(Config.EXTRA_IMAGES, images)
            startActivity(intent)
        }
    }
}