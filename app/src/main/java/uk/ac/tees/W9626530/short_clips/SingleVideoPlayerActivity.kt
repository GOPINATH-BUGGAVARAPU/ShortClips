package uk.ac.tees.W9626530.short_clips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import uk.ac.tees.W9626530.short_clips.adapter.VideoListAdapter
import uk.ac.tees.W9626530.short_clips.databinding.ActivitySingleVideoPlayerBinding
import uk.ac.tees.W9626530.short_clips.model.VideoModel

class SingleVideoPlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingleVideoPlayerBinding
    lateinit var videoId : String
    lateinit var adapter: VideoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoId = intent.getStringExtra("videoId")!!
        setupViewPager()


    }

    fun setupViewPager(){
        val options = FirestoreRecyclerOptions.Builder<VideoModel>()
            .setQuery(
                Firebase.firestore.collection("videos")
                    .whereEqualTo("videoId",videoId),
                VideoModel::class.java
            ).build()
        adapter = VideoListAdapter(options)
        binding.viewPager.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }

}