package com.ashmit.mvvmwithroomwithoutretrofit.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.Quote
import javax.inject.Inject

@Database (entities = [Quote::class] , version = 1 , exportSchema = false)
abstract class QuoteDB :RoomDatabase() {

    /*
Why Can’t You Access the DAO Directly?

DAO is an Interface, Not a Class:
A DAO like NoteDao is just an interface. It doesn’t have a concrete implementation that you can instantiate directly.
Room generates the implementation at compile time, and this implementation is tied to the specific RoomDatabase instance.
that s why we first have to create an instance of the db and then call the abstract quoteDoa method !!
The DAO needs to be tied to a specific database instance because it operates on the tables and data within that database. Simply calling NoteDao() without a database context would result in an incomplete and non-functional DAO.
*/
    abstract fun quoteDao():  QuoteDao

//    without hilt , we had to use the companion object !
    /*
    companion object{
    @Volatile
    private var INSTANCE :QuoteDB? = null
    fun getDatabase(context :Context): QuoteDB{
    synchronized(this){
    if(INSTANCE == null){
        INSTANCE = Room.databaseBuilder(context.applicationContext ,QuoteDB::class.java ,"quote_db" )
        .createFromAsset("quotes.db")
    return INSTANCE!!
    }
    }
    return INSTANCE!!
    }
    }
    */
}