package ajou.paran.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class FindUserByIdResponse(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("token")
    val token : String,
)
