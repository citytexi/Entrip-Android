package ajou.paran.entrip.repository.Impl

import ajou.paran.entrip.model.PlannerEntity
import ajou.paran.entrip.repository.network.UserRemoteSource
import ajou.paran.entrip.repository.network.dto.PlannerResponse
import ajou.paran.entrip.repository.network.dto.UserRequest
import ajou.paran.entrip.repository.network.dto.UserTemp
import ajou.paran.entrip.repository.room.plan.dao.PlanDao
import ajou.paran.entrip.util.network.BaseResult
import ajou.paran.entrip.util.network.Failure
import ajou.paran.entrip.util.network.networkinterceptor.NoInternetException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val userRemoteSource: UserRemoteSource,
    private val planDao: PlanDao
): UserRepository{

    override fun getUserPlanners(user_id: String): Flow<BaseResult<List<PlannerResponse>, Failure>>
    = flow{
        try{
            val response = userRemoteSource.getUserPlanners(user_id)
            if (response.status == 200) {
                for(data in response.data)
                    planDao.insertPlanner(
                        PlannerEntity(
                            planner_id = data.planner_id,
                            title = data.title,
                            start_date = data.start_date,
                            end_date = data.end_date,
                            time_stamp = data.timeStamp,
                            comment_timeStamp = data.comment_timeStamp
                        )
                    )
                emit(BaseResult.Success(response.data))
            } else {
                emit(BaseResult.Error(Failure(response.status, response.message)))
            }
        } catch (e: NoInternetException) {
            emit(BaseResult.Error(Failure(0, e.message)))
        } catch (e: Exception) {
            emit(BaseResult.Error(Failure(-1, e.message.toString())))
        }
    }

    override fun isExistUserId(user_id: String): Flow<BaseResult<Boolean, Failure>>
     = flow {
        try {
            val response = userRemoteSource.isExistUserId(user_id)
            if (response.status == 200) {
                emit(BaseResult.Success(response.data))
            } else {
                emit(BaseResult.Error(Failure(response.status, response.message)))
            }
        } catch (e: NoInternetException) {
            emit(BaseResult.Error(Failure(0, e.message)))
        } catch (e: Exception) {
            emit(BaseResult.Error(Failure(-1, e.message.toString())))
        }
    }

    override fun isExistNickname(nickname: String) : Flow<BaseResult<Boolean, Failure>>
     = flow{
        try {
            val response = userRemoteSource.isExistNickname(nickname)
            if (response.status == 200) {
                emit(BaseResult.Success(response.data))
            } else {
                emit(BaseResult.Error(Failure(response.status, response.message)))
            }
        } catch (e: NoInternetException) {
            emit(BaseResult.Error(Failure(0, e.message)))
        } catch (e: Exception) {
            emit(BaseResult.Error(Failure(-1, e.message.toString())))
        }
    }

    override fun saveUser(user_id: String, gender: Int, nickname: String): Flow<BaseResult<UserRequest, Failure>>
     = flow {
        try {
            val initial_photoUrl = "https://user-images.githubusercontent.com/77181865/169517449-f000a59d-5659-4957-9cb4-c6e5d3f4b197.png"
            val userResponse = UserTemp(user_id, gender, nickname, initial_photoUrl)

            val response = userRemoteSource.saveUser(userResponse)
            if (response.status == 200) {
                val dto = response.data
                emit(BaseResult.Success(dto))
            } else {
                emit(BaseResult.Error(Failure(response.status, response.message)))
            }
        } catch (e: NoInternetException) {
            emit(BaseResult.Error(Failure(0, e.message)))
        } catch (e: Exception) {
            emit(BaseResult.Error(Failure(-1, e.message.toString())))
        }
    }
}