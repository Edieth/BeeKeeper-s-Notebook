package Entity

import com.google.gson.annotations.SerializedName

data class HarvestDTO(
    @SerializedName("id") val id: String = "",
    @SerializedName("beehiveId") val beehiveId: String = "",
    @SerializedName("zoneId") val zoneId: String = "",
    @SerializedName("dateHarvest") val dateHarvest: String = "",
    @SerializedName("honeyFrames") val honeyFrames: Int = 0,
    @SerializedName("honeyAmountKg") val honeyAmountKg: Double = 0.0,
    @SerializedName("honeyAmountKgNeta") val honeyAmountKgNeta: Double = 0.0,
    @SerializedName("personId") val personId: String = ""
)