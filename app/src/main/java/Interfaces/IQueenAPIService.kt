package Interfaces

import Entity.ApiGetResponse
import Entity.DTOQueen
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IQueenAPIService {
    @GET("queens")
    suspend fun getAll(): ApiGetResponse<DTOQueen>

    @GET("queens/{id}")
    suspend fun getById(@Path("id") id: String): ApiGetResponse<DTOQueen>

    @Headers("Content-Type: application/json")
    @POST("queens")
    suspend fun create(@Body queen: DTOQueen): ApiGetResponse<DTOQueen>

    @Headers("Content-Type: application/json")
    @PUT("queens/{id}")
    suspend fun update(@Path("id") id: String, @Body queen: DTOQueen): ApiGetResponse<DTOQueen>

    @Headers("Content-Type: application/json")
    @DELETE("queens/{id}")
    suspend fun delete(@Path("id") id: String): ApiGetResponse<DTOQueen>

    // --- NUEVAS FUNCIONES AGREGADAS ---

    @GET("queens/person/{personId}")
    suspend fun getByPerson(@Path("personId") personId: String): ApiGetResponse<DTOQueen>

    @GET("queens/hive/{hiveId}")
    suspend fun getByHive(@Path("hiveId") hiveId: String): ApiGetResponse<DTOQueen>
}