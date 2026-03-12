package com.mivanba.catsapp.data

import com.mivanba.catsapp.data.local.CatDao
import com.mivanba.catsapp.data.local.toDomain
import com.mivanba.catsapp.data.local.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatRepository(
    private val apiService: CatApiService,
    private val catDao: CatDao
) {
    val cats: Flow<List<Cat>> = catDao.getAllCats().map { localCats ->
        localCats.map { it.toDomain() }
    }

    suspend fun refreshCats(page: Int, isRefresh: Boolean = false) {
        val remoteCats = apiService.getCats(limit = 15, page = page, hasBreeds = 1)

        val entities = remoteCats.map { it.toEntity() }

        if (isRefresh) {
            catDao.clearAllCats()
        }

        catDao.insertCat(entities)
    }
}