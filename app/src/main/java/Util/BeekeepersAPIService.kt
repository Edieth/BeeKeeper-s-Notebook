package Util

import Interfaces.IHiveAPIService
import Interfaces.IInventoryAPIService
import Interfaces.IPersonAPIService
import Interfaces.IQueenAPIService
import Interfaces.IHarvestAPIService // <--- Asegúrate de que esta línea se agregue
import Interfaces.IZoneAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BeekeepersAPIService {
    private const val BASE_URL = "http://10.144.72.127:3001/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiInventory: IInventoryAPIService by lazy {
        retrofit.create(IInventoryAPIService::class.java)
    }
    val apiPerson: IPersonAPIService by lazy {
        retrofit.create(IPersonAPIService::class.java)
    }
    val apiQueen: IQueenAPIService by lazy {
        retrofit.create(IQueenAPIService::class.java)
    }
    val apiHives: IHiveAPIService by lazy {
        retrofit.create(IHiveAPIService::class.java)
    }

   val apiHarvest: IHarvestAPIService by lazy {
        retrofit.create(IHarvestAPIService::class.java)
    }

   val apiZone: IZoneAPIService by lazy {
        retrofit.create(IZoneAPIService::class.java)
    }
}