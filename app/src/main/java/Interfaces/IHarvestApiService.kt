package Interfaces

import Entity.ApiGetResponse
import Entity.ApiResponse
import Entity.HarvestDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IHarvestAPIService {

    // Obtener todas las cosechas de un usuario
    @GET("harvests/person/{personId}")
    suspend fun getByPerson(@Path("personId") personId: String): ApiGetResponse<HarvestDTO>

    // Obtener una cosecha por ID
    @GET("harvests/{id}")
    suspend fun getById(@Path("id") id: String): ApiGetResponse<HarvestDTO>

    // Crear nueva cosecha
    @Headers("Content-Type: application/json")
    @POST("harvests")
    suspend fun create(@Body dto: HarvestDTO): ApiResponse<HarvestDTO>

    // Actualizar cosecha
    @Headers("Content-Type: application/json")
    @PUT("harvests/{id}")
    suspend fun update(@Path("id") id: String, @Body dto: HarvestDTO): ApiResponse<HarvestDTO>

    // Eliminar cosecha
    @DELETE("harvests/{id}")
    suspend fun delete(@Path("id") id: String): ApiResponse<HarvestDTO>
}