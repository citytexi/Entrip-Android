package ajou.paran.entrip.screen.planner.top.useradd

import ajou.paran.entrip.repository.Impl.PlannerRepositoryImpl
import ajou.paran.entrip.repository.Impl.UserAddRepositoryImpl
import ajou.paran.entrip.util.ApiState
import ajou.paran.entrip.util.network.BaseResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlannerUserAddActivityViewModel
@Inject
constructor(private val userAddRepository: UserAddRepositoryImpl) : ViewModel() {
    companion object {
        const val TAG = "[PlannerUserAddActivityViewModel]"
    }

    private val _state = MutableStateFlow<ApiState>(ApiState.Init)
    val state : StateFlow<ApiState> get() = _state

    fun setLoading() {
        _state.value = ApiState.IsLoading(true)
    }

    fun hideLoading() {
        _state.value = ApiState.IsLoading(false)
    }

    fun findAllUserWithPlannerId(planner_id : Long){
        viewModelScope.launch(Dispatchers.IO){
            setLoading()
            val res = userAddRepository.findAllUsersWithPlannerId(planner_id)
            delay(500)
            hideLoading()
            when(res){
                is BaseResult.Success -> _state.value = ApiState.Success(res.data)
                is BaseResult.Error -> _state.value = ApiState.Failure(res.err.code)
            }
        }
    }

    fun searchUser(user_id_or_nickname : String){

    }
}