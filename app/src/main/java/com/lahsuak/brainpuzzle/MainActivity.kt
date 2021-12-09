package com.lahsuak.brainpuzzle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lahsuak.brainpuzzle.PuzzleAdapter.Companion.count
import com.lahsuak.brainpuzzle.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager




class MainActivity : AppCompatActivity(R.layout.activity_main), PuzzleListener {
    private lateinit var binding: ActivityMainBinding

    companion object {
        var lastPosition = -1
        var secondLastPosition = -1
        var checked = false
        var matchCount = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtMoves.text = "$count Moves"
        binding.txtBoardSize.text = "8 X 2"

        val gm: GridLayoutManager = object : GridLayoutManager(this,2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.recyclerView.layoutManager = gm//GridLayoutManager(this, 2)

        val list = ArrayList<Puzzle>()
        list.add(Puzzle(2, R.drawable.green_tea))
        list.add(Puzzle(3, R.drawable.apricot))
        list.add(Puzzle(5, R.drawable.mint))
        list.add(Puzzle(6, R.drawable.strawberry))
        list.add(Puzzle(7, R.drawable.apricot))
        list.add(Puzzle(8, R.drawable.mint))
        list.add(Puzzle(9, R.drawable.strawberry))
        list.add(Puzzle(10, R.drawable.green_tea))
        val adapter = PuzzleAdapter(this, list, this)
        binding.recyclerView.adapter = adapter

        binding.btnReset.setOnClickListener {
            lastPosition = -1
            secondLastPosition = -1
            checked = false
            matchCount = 0
            count = 0
            adapter.notifyDataSetChanged()
            binding.txtMoves.text = "$count Moves"
        }
    }

    override fun onItemClicked(position: Int) {
        binding.txtMoves.text = "$count Moves"
    }
}