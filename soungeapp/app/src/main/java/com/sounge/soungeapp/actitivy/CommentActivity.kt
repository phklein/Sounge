package com.sounge.soungeapp.actitivy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sounge.soungeapp.adapter.CommentAdapter
import com.sounge.soungeapp.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}