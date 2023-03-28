package com.task.noteapp.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.task.noteapp.R
import com.task.noteapp.roomdb.Note
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class NoteRecyclerAdapter @Inject constructor(val glide: RequestManager): RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>(){

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffUtil = object: DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerViewDiffer = AsyncListDiffer(this, diffUtil)

    var notes: List<Note>
        get() = recyclerViewDiffer.currentList
        set(value) = recyclerViewDiffer.submitList(value)

    var itemClickListener: ((itemId:Int, position:Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_note, parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val imgNote = holder.itemView.findViewById<ImageView>(R.id.imgNote)
        val txtTitle = holder.itemView.findViewById<TextView>(R.id.txtTitle)
        val txtDescription = holder.itemView.findViewById<TextView>(R.id.txtDescription)
        val txtCreationDate = holder.itemView.findViewById<TextView>(R.id.txtCreationDate)
        val txtIsEdited = holder.itemView.findViewById<TextView>(R.id.txtIsEdited)
        val btnOptions = holder.itemView.findViewById<ImageButton>(R.id.btnOptions)

        val note = notes[position]

        holder.itemView.apply {

            glide.load(note.imageURL).into(imgNote)

            txtTitle.text = note.title
            txtDescription.text = note.description
            txtCreationDate.text = note.creationDate

            if(note.edited) {
                txtIsEdited.text = context.getString(R.string.yes)
            }
            else {
                txtIsEdited.text = context.getString(R.string.no)
            }

            btnOptions.setOnClickListener {
                val popupMenu = PopupMenu(holder.itemView.context, it, Gravity.RIGHT)
                popupMenu.inflate(R.menu.options_menu)

                popupMenu.setOnMenuItemClickListener {
                    itemClickListener?.invoke(it.itemId, position)
                    true
                }

                popupMenu.show()

                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java).invoke(menu,true)
            }
        }
    }
}