package Interfaces

import Entity.ApiGetResponse
import Entity.ApiResponse
import Entity.DTOBeehive
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IHiveAPIService {
    @GET("/hives")
    suspend fun getAll(): ApiGetResponse<DTOBeehive>

    @GET("/hives/{id}")
    suspend fun getById(@Path("id") id: String): ApiGetResponse<DTOBeehive>

    @GET("/hives/by-person/{personId}")
    suspend fun getByPerson(@Path("personId") personId: String): ApiGetResponse<DTOBeehive>

    @GET("/hives/by-zone/{zoneId}")
    suspend fun getByZone(@Path("zoneId") zoneId: String): ApiGetResponse<DTOBeehive>

    @Headers("Content-Type: application/json")
    @POST("/hives")
    suspend fun create(@Body dto: DTOBeehive): ApiResponse<DTOBeehive>

    @Headers("Content-Type: application/json")
    @PUT("/hives/{id}")
    suspend fun update(@Path("id") id: String, @Body dto: DTOBeehive): ApiResponse<DTOBeehive>

    @DELETE("/hives/{id}")
    suspend fun delete(@Path("id") id: String): ApiResponse<DTOBeehive>

}