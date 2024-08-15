package com.ashmit.mvvmwithroomwithoutretrofit.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.Quote

@Dao
interface QuoteDao {
//DAOs are interfaces that define the methods to interact with the database. These methods are responsible for performing operations like inserting, querying, updating, and deleting data.
    @Insert
    suspend fun insertQuote(quote: Quote)

    @Update
    suspend fun updateQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)

    @Query("SELECT * FROM quote")
    fun getQuotes() :LiveData<List<Quote>>

    @Query("SELECT * FROM quote WHERE id = :userId")
    fun getQuoteById(userId :Int) : LiveData<Quote>


}