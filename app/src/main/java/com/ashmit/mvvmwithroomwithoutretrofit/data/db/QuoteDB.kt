package com.ashmit.mvvmwithroomwithoutretrofit.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ashmit.mvvmwithroomwithoutretrofit.data.model.QuoteModel

@Database(entities =[QuoteModel::class] , version =  1 , exportSchema = false )
abstract class QuoteDB :RoomDatabase(){
    abstract fun quoteDao() : QuoteDAO

    //singleton object as there should only be a single INSTANCE of the whole db model !
    companion object{
        @Volatile
        private var INSTANCE : QuoteDB? = null

        fun getDatabase(context : Context):QuoteDB{
                synchronized(this ){
                 if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,QuoteDB::class.java,"quote_database")
                        .createFromAsset("quotes.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}
