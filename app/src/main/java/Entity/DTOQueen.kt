package Entity

import com.google.gson.annotations.SerializedName

data class DTOQueen(
    @SerializedName("id") val id: String = "",
    @SerializedName("hiveId") val hiveId: String = "",
    @SerializedName("personId") val personId: String = "",
    @SerializedName("zoneID") val zoneID: String = "",
    @SerializedName("typeInternal") val typeInternal: String = "",
    @SerializedName("entryDateInternal") val entryDateInternal: String = "" // ISO yyyy-MM-dd
)
