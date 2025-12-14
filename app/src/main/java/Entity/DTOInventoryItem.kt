package Entity

import com.google.gson.annotations.SerializedName

data class DTOInventoryItem(
    @SerializedName("id") val id: String = "",
    @SerializedName("personId") val personId: String = "",
    @SerializedName("nameInventoryItem") val nameInventoryItem: String = "",
    @SerializedName("inventoryItemTotalQuantity") val inventoryItemTotalQuantity: Int = 0,
    @SerializedName("inventoryDataType") val inventoryDataType: String = ""
)
