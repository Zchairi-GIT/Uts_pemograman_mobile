package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.adapter.ProductAdapter
import com.example.uts.databinding.FragmentDashboardBinding
import com.example.uts.helpers.Config
import com.example.uts.model.ProductItem
import com.example.uts.response.ProductResponse
import com.example.uts.retrofit.ApiConfig
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "DashboardFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvKabKota.layoutManager = layoutManager
        binding.rvKabKota.addItemDecoration(
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        )

        getProductList()

        binding.swipeRefresh.setOnRefreshListener {
            getProductList()
        }
    }

    private fun getProductList() {
        showLoading(true)
        val client = ApiConfig.getApiService().getListProducts(Config.PRODUCT_LIMIT)
        client.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (_binding == null) return
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.let { setProductData(it.products) }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                if (_binding == null) return
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setProductData(products: List<ProductItem>) {
        val adapter = ProductAdapter()
        adapter.submitList(products)
        binding.rvKabKota.adapter = adapter

        adapter.setOnItemClickCallback(object : ProductAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ProductItem) {
                val json = Gson().toJson(data)
                val intent = Intent(requireContext(), DetailProductActivity::class.java)
                intent.putExtra(Config.EXTRA_PRODUCT, json)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (!binding.swipeRefresh.isRefreshing) {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        if (!isLoading) {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}