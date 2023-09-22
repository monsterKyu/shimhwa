package com.example.imagesearch.Search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.Constants
import com.example.imagesearch.data.Image
import com.example.imagesearch.data.retrofit_client.apiService
import com.example.imagesearch.databinding.FragmentSearchBinding
import com.example.imagesearch.model.SearchImage
import com.example.imagesearch.util.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Search : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mContext: Context
    private lateinit var adapter: SearchAdapter
    private lateinit var gridmanager: StaggeredGridLayoutManager

    private var mItems: ArrayList<SearchImage> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupViews()
        setupListeners()

        return binding.root
    }


    private fun setupViews() {gridmanager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSearch.layoutManager = gridmanager

        adapter = SearchAdapter(mContext)
        binding.rvSearch.adapter = adapter
        binding.rvSearch.itemAnimator = null

        // 최근 검색어를 가져와 EditText에 설정
        val lastSearch = Utils.getLastSearch(requireContext())
        binding.edtSearch.setText(lastSearch)
    }


    private fun setupListeners() {
        val imm = requireActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.btnSearch.setOnClickListener {
            val query = binding.edtSearch.text.toString()
            if (query.isNotEmpty()) {
                Utils.saveLastSearch(requireContext(), query)
                adapter.clearItem()
                fetchImageResults(query)
            } else {
                Toast.makeText(mContext, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }

            // 키보드 숨기기
            imm.hideSoftInputFromWindow(binding.edtSearch.windowToken, 0)
        }
    }

    private fun fetchImageResults(query: String) {
        apiService.image_search(Constants.AUTH_HEADER, query, "recency", 1, 80)
            ?.enqueue(object : Callback<Image?> {
                override fun onResponse(call: Call<Image?>, response: Response<Image?>) {
                    response.body()?.meta?.let { meta ->
                        if (meta.totalCount > 0) {
                            response.body()!!.documents.forEach { document ->
                                val title = document.displaySitename
                                val datetime = document.datetime
                                val url = document.thumbnailUrl
                                mItems.add(SearchImage(title, datetime, url))
                            }
                        }
                    }
                    adapter.items = mItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Image?>, t: Throwable) {
                    Log.e("#kookie", "onFailure: ${t.message}")
                }
            })
    }

}

