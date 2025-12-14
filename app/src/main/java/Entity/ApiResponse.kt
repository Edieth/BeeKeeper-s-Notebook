package Entity

data class ApiResponse<T>(
    val data: T?,
    val message: String ="",
    val responseCode: Int = 0
    )
