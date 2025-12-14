package Entity

import com.google.gson.annotations.SerializedName

data class DTOPerson(
@SerializedName("id") val id: String = "",
@SerializedName("name") val name: String = "",
@SerializedName("fLastName") val fLastName: String = "",
@SerializedName("sLastName") val sLastName: String = "",
@SerializedName("email") val email: String = "",
@SerializedName("password") val password: String = "",
@SerializedName("phonePerson") val phonePerson: String = "",
@SerializedName("photoBase64") val photoBase64: String = ""

)