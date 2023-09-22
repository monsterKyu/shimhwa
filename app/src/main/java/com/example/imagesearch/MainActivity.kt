package com.example.imagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.imagesearch.Like.Like
import com.example.imagesearch.Search.Search
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.model.SearchImage

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    var likedItems: ArrayList<SearchImage> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.run {
            SearchBtn.setOnClickListener{
                setFragment(Search())
            }
            likeBtn.setOnClickListener {
                setFragment(Like())
            }
        }

        setFragment(Search())
    }


    private fun setFragment(frag : Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    fun addLikedItem(item: SearchImage) {
        if(!likedItems.contains(item)) {
            likedItems.add(item)
        }
    }

    fun removeLikedItem(item: SearchImage) {
        likedItems.remove(item)
    }
}


