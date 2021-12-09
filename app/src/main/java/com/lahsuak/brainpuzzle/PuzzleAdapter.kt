package com.lahsuak.brainpuzzle

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.lahsuak.brainpuzzle.MainActivity.Companion.checked
import com.lahsuak.brainpuzzle.MainActivity.Companion.lastPosition
import com.lahsuak.brainpuzzle.MainActivity.Companion.matchCount
import com.lahsuak.brainpuzzle.MainActivity.Companion.secondLastPosition

private const val TAG = "PuzzleAdapter"

class PuzzleAdapter constructor(
    private var context: Context,
    var list: ArrayList<Puzzle>, var listener: PuzzleListener
) : RecyclerView.Adapter<PuzzleAdapter.PuzzleViewHolder?>() {

    companion object {
        var count = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuzzleViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.puzzle_item, parent, false)
        return PuzzleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PuzzleViewHolder, position: Int) {
        holder.puzzleImage.setImageResource(R.drawable.bitcoin)
        holder.itemView.setOnClickListener {
            if (matchCount == list.size / 2) {
                Toast.makeText(
                    context,
                    "You already won the game!\n Tap on restart button to play the game again",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (list[position].isSelected) {
                    Toast.makeText(context, "Invalid move!!", Toast.LENGTH_SHORT).show()
                } else {
                    count++
                    listener.onItemClicked(position)
                    holder.puzzleImage.setImageResource(list[position].image)
                    if (checked) {
                        Log.d(TAG, "onBindViewHolder: count $count and lastpos $lastPosition")
                        if (lastPosition != -1 && secondLastPosition != -1) {
                            notifyItemChanged(lastPosition)
                            notifyItemChanged(secondLastPosition)
                        }
                        checked = false
                    }
                    if (lastPosition != -1) {
                        if (count % 2 == 0) {
                            if (list[position].image == list[lastPosition].image) {
                                list[position].isSelected = true
                                list[lastPosition].isSelected = true
                                lastPosition = -1
                                secondLastPosition = -1
                                matchCount++
                                Log.d(TAG, "onBindViewHolder: match ")
                                if (matchCount == list.size / 2)
                                    Toast.makeText(
                                        context,
                                        "CONGRATULATION!! YOU WON THE GAME IN $count 's moves",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            } else {
                                secondLastPosition = lastPosition
                                lastPosition = position
                                Log.d(TAG, "onBindViewHolder: unmatched")
                            }
                            checked = true
                        }
                    }
                    if (!checked) {
                        Log.d(TAG, "onBindViewHolder: unmatched $count , pos $position")
                        lastPosition = position
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PuzzleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var puzzleImage: ImageView = itemView.findViewById(R.id.puzzle_image)
    }
}

interface PuzzleListener {
    fun onItemClicked(position: Int)
}