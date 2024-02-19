package com.example.writeonthego

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.writeonthego.persistance.NotesDao
import com.example.writeonthego.persistance.NotesDatabase


class WriteOnTheGoApp : Application(){

    private var db : NotesDatabase? = null


    init {
        instance = this
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDb(): NotesDatabase {
        return if (db != null){
            db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                NotesDatabase::class.java, Constants.DATABASE_NAME
            ).fallbackToDestructiveMigration()// remove in prod
                .build()
            db!!
        }
    }


    companion object {
        private var instance: WriteOnTheGoApp? = null

        @RequiresApi(Build.VERSION_CODES.O)
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