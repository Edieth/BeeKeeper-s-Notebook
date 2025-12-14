package Interfaces

import Entity.ApiGetResponse
import Entity.DTOInventoryItem
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface IInventoryAPIService { @GET("inventory")
suspend fun getAll(): ApiGetResponse<DTOInventoryItem>

    @GET("inventory/{id}")
    suspend fun getById(@Path("id") id: String): ApiGetResponse<DTOInventoryItem>

    @GET("inventory/by-person/{personId}")
    suspend fun getByPerson(@Path("personId") personId: String): ApiGetResponse<DTOInventoryItem>

    @Headers("Content-Type: application/json")
    @POST("inventory")
    suspend fun create(@Body item: DTOInventoryItem): ApiGetResponse<DTOInventoryItem>

    @Headers("Content-Type: application/json")
    @PUT("inventory/{id}")
    suspend fun update(@Path("id") id: String, @Body item: DTOInventoryItem): ApiGetResponse<DTOInventoryItem>

    @Headers("Content-Type: application/json")
    @DELETE("inventory/{id}")
    suspend fun delete(@Path("id") id: String): ApiGetResponse<DTOInventoryItem>
}