package ajou.paran.data.remote.model.response

import com.google.gson.annotations.SerializedName

typealias FindAllPlannersResponseList = List<FindAllPlannersResponse>


data class FindAllPlannersResponse(
    @SerializedName("planner_id")
    val plannerId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
)