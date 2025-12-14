package Entity

import com.google.gson.annotations.SerializedName

data class DTOZone(
    @SerializedName("id") val id: String = "",
    @SerializedName("personID") val personID: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("state") val state: String = "",
    @SerializedName("district") val district: String = "",
    @SerializedName("address") val address: String = "",
    @SerializedName("latitude") val latitude: Double = 0.0,
    @SerializedName("longitude") val longitude: Double = 0.0
)
