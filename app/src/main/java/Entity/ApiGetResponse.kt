package Entity

data class ApiGetResponse<T>(
    val data: List<T>,
    val message: String ="",
    val responseCode: Int = 0,
    )