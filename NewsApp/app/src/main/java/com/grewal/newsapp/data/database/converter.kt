package com.grewal.newsapp.data.database

import androidx.room.TypeConverter
import com.grewal.newsapp.data.entities.Source

class converter {
    @TypeConverter
    fun fromSource(source:Source):String
    {
        return source.name
    }

    @TypeConverter
    fun toSource(name:String):Source
    {
        return Source(name,name)
    }
}