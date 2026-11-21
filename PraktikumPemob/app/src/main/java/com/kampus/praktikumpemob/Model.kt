package com.kampus.praktikumpemob

data class Post(
    val userId : Int,
    val id : Int,
    val title : String,
    val body : String
)

data class AdviceRespon(
    val slip: Slip
)


data class  slip(
    val id : Int,
    val advice : String
)