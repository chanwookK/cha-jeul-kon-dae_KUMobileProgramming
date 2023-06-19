package com.example.lostku

data class LostData(val id:Int,val name:String, val foundLoc:String, val havingLoc:String, val photo:String, val time:String, val password : String ) {
    constructor():this(0,"noinfo","noinfo","noinfo","noinfo","noinfo", "noinfo")
}