package Entity

import com.google.gson.annotations.SerializedName

data class DTOBeehive(
    @SerializedName("id") val id: String = "",
    @SerializedName("zoneId") val zoneId: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("boxType") val boxType: String = "",
    @SerializedName("queenId") val queenId: String = "",
    // ✅ este NO existe en Beehive, pero sí lo necesita el backend para filtrar por usuario
    // y lo pasamos desde FirebaseAuth como parámetro (igual que en Queens).
    @SerializedName("personId") val personId: String = ""

)