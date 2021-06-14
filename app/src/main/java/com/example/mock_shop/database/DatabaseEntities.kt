package com.example.mock_shop.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int,
    val product_name: String,
    val description: String?,
    val price: Double,
    val img_url: String,
    var quantity: Int
)
