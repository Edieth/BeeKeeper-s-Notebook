package Interfaces

import Entity.ApiGetResponse
import Entity.DTOPerson
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IPersonAPIService {
    @GET("person")
    suspend fun getAll(): ApiGetResponse<DTOPerson>

    @GET("person/{id}")
    suspend fun getById(@Path("id") id: String): ApiGetResponse<DTOPerson>

    @GET("person/by-fullname/{fullname}")
    suspend fun getByFullName(@Path("fullname") fullname: String): ApiGetResponse<DTOPerson>

    @Headers("Content-Type: application/json")
    @POST("person")
    suspend fun create(@Body person: DTOPerson): ApiGetResponse<DTOPerson>

    @Headers("Content-Type: application/json")
    @PUT("person/{id}")
    suspend fun update(@Path("id") id: String, @Body person: DTOPerson): ApiGetResponse<DTOPerson>

    @Headers("Content-Type: application/json")
    @DELETE("person/{id}")
    suspend fun delete(@Path("id") id: String): ApiGetResponse<DTOPerson>

}