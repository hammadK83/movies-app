package com.sampleapp.movies.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return list?.joinToString(",")  // Convert List<Int> to a comma-separated String
    }

    @TypeConverter
    fun toList(data: String?): List<Int>? {
        return data?.split(",")?.map { it.toInt() }  // Convert String back to List<Int>
    }
}