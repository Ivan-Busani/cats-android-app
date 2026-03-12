package com.mivanba.catsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mivanba.catsapp.data.Cat

@Entity(tableName = "cats_table")
data class CatEntity(
    @PrimaryKey val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val isFavorite: Boolean = false
)

fun CatEntity.toDomain(): Cat {
    return Cat(
        id = this.id,
        url = this.url,
        width = this.width,
        height = this.height,
        breeds = null
    )
}

fun Cat.toEntity(): CatEntity {
    return CatEntity(
        id = this.id,
        url = this.url,
        width = this.width,
        height = this.height
    )
}