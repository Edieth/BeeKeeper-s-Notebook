package Interfaces

import Entity.ApiGetResponse
import Entity.ApiResponse
import Entity.DTOZone
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IZoneAPIService {
    // Obtener zonas de un apicultor específico
    @GET("zones/by-person/{personID}")
    suspend fun getByPerson(@Path("personID") personID: String): ApiGetResponse<DTOZone>

    @GET("zones/{id}")
    suspend fun getById(@Path("id") id: String): ApiGetResponse<DTOZone>

    @Headers("Content-Type: application/json")
    @POST("zones")
    suspend fun create(@Body zone: DTOZone): ApiResponse<DTOZone>

    @Headers("Content-Type: application/json")
    @PUT("zones/{id}")
    suspend fun update(@Path("id") id: String, @Body zone: DTOZone): ApiResponse<DTOZone>

    @DELETE("zones/{id}")
    suspend fun delete(@Path("id") id: String): ApiResponse<DTOZone>
}