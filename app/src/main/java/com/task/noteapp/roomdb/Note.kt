package com.task.noteapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title:String?,
    var description: String?,
    var imageURL: String?,
    var creationDate: String?,
    var edited: Boolean = false): java.io.Serializable {
}