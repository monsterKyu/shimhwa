package com.example.imagesearch.Like

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.MainActivity
import com.example.imagesearch.databinding.FragmentLikeBinding
import com.example.imagesearch.model.SearchImage

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Like.newInstance] factory method to
 * create an instance of this fragment.
 */
class Like : Fragment() {
    private lateinit var mContext: Context

    private var binding: FragmentLikeBinding? = null
    private lateinit var adapter: LikeAdapter

    private var likedItems: List<SearchImage> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        likedItems = mainActivity.likedItems

        Log.d("like", "#kookie likedItems size = ${likedItems.size}")


        adapter = LikeAdapter(mContext).apply {
            var items = likedItems.toMutableList()
        }


        binding = FragmentLikeBinding.inflate(inflater, container, false).apply {
            rvLike.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvLike.adapter = adapter
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}