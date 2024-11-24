package com.littlelemon.interviewapp.data.remote

data class StocksResponce(
    val id: String,
    val name: String,
    val ticker: String,
    val instrument_type: String,
    val description: String
)