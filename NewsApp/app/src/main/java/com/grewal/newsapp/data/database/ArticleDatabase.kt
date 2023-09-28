package com.grewal.newsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grewal.newsapp.data.dao.ArticleDao
import com.grewal.newsapp.data.entities.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(converter::class)
abstract class ArticleDatabase :RoomDatabase(){
     abstract fun getDao():ArticleDao

     companion object{
         @Volatile
         private var INSTANCE :ArticleDatabase?= null
         private val LOCK =Any()

         operator fun invoke(context: Context)= INSTANCE?: synchronized(LOCK){
             INSTANCE ?: createDatabase(context).also{
                 INSTANCE = it
             }
         }
         private fun createDatabase(context: Context) =
             Room.databaseBuilder(context.applicationContext,
                 ArticleDatabase::class.java,
                 "article_db")
                 .build()

     }
}