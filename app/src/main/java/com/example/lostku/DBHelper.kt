package com.example.lostku

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val context : Context?) :SQLiteOpenHelper(context, DB_NAME , null , DB_VERSION){
    companion object{
        //val name:String, val foundLoc:String, val havingLoc:String, val photo:String, val time:String
        val DB_NAME = "lostKu.db"
        val DB_VERSION = 1
        val TABLE_NAME = "Lost"
        val ID = "id"
        val NAME = "name"
        val FOUND_LOC = "foundLoc"
        val HAVING_LOC = "havingLoc"
        val PHOTO = "photo"
        val time = "time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists $TABLE_NAME("+
                "$ID integer primary key autoincrement, " +
                "$NAME text, "+
                "$FOUND_LOC text, "+
                "$HAVING_LOC text, "+
                "$PHOTO text, "+
                "$time text,);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }
}