package com.example.writeonthego

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.room.Room
import com.example.writeonthego.Persistance.NotesDao
import com.example.writeonthego.Persistance.NotesDatabase

class PhotoNotesApp : Application() {
    private var db: NotesDatabase? = null

    init{
        instance = this
    }

    private fun getDb(): NotesDatabase{
        if (db != null) {
            return db!!
        }
        else{
            db = Room.databaseBuilder(
                instance!!.applicationConstext,
                NotesDatabase::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
            return db!!
        }
    }

    companion object{
        private var instance: PhotoNotesApp? = null

        fun getDao(): NotesDao {
            return instance!!.getDb().NotesDao()
        }

        fun getUriPermission(uri: Uri){
            instance!!.applicationContext.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }
}