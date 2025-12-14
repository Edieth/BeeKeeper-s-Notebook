package Interfaces

import Entity.ApiGetResponse
import Entity.DTOBeehive
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HiveApi { @GET("hives")
fun getAll(): Call<ApiGetResponse<DTOBeehive>>

    @GET("hives/{id}")
    fun getById(@Path("id") id: String): Call<ApiGetResponse<DTOBeehive>>

    @POST("hives")
    fun create(@Body hive: DTOBeehive): Call<ApiGetResponse<DTOBeehive>>

    @PUT("hives/{id}")
    fun update(
        @Path("id") id: String,
        @Body hive: DTOBeehive
    ): Call<ApiGetResponse<DTOBeehive>>

    @DELETE("hives/{id}")
    fun delete(@Path("id") id: String): Call<ApiGetResponse<DTOBeehive>>
}