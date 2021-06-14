package com.example.mock_shop.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("select * from products")
    fun getProducts(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product>)

    @Query("select * from products where id = :id")
    fun getProductById(id: Int): Product

}

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase: RoomDatabase(){

    abstract val productDao: ProductDao
}

private lateinit var INSTANCE: ProductDatabase

fun getDatabase(context: Context): ProductDatabase {
    synchronized(ProductDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            ProductDatabase::class.java,
            "products").build()
        }
    }
    return INSTANCE
}