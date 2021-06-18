package com.example.mock_shop.domain

import com.example.mock_shop.database.Product


class Cart {
    val products = listOf<Product>()
    var total: Double = 0.0
}



