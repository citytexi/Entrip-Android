package ajou.paran.entrip.repository.network.dto

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("httpStatus")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T
)