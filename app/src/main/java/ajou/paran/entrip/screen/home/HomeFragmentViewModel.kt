package ajou.paran.entrip.screen.home

import ajou.paran.entrip.model.test.RecommendationItem
import ajou.paran.entrip.repository.Impl.PlannerRepository
import ajou.paran.entrip.repository.Impl.RecommendRepository
import ajou.paran.entrip.util.ApiState
import ajou.paran.entrip.util.network.BaseResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel
@Inject
constructor(
    private val plannerRepository: PlannerRepository,
    private val recommendRepository: RecommendRepository
)
: ViewModel() {
    companion object{
        const val TAG = "[HomeFragmentVM]"
    }

    private val _state = MutableStateFlow<ApiState>(ApiState.Init)
    val state : StateFlow<ApiState> get() = _state

    private val _recommendItemList: MutableLiveData<List<RecommendationItem>> = MutableLiveData()
    val recommendItemList: LiveData<List<RecommendationItem>>
        get() = _recommendItemList

    fun setLoading() {
        _state.value = ApiState.IsLoading(true)
    }

    fun hideLoading() {
        _state.value = ApiState.IsLoading(false)
    }

    suspend fun selectAllPlanner() = plannerRepository.selectAllPlanner()

    fun createPlanner(userId : String){
        viewModelScope.launch(Dispatchers.IO){
            setLoading()
            val res = plannerRepository.createPlanner(userId)
            delay(500)
            hideLoading()
            when(res){
                is BaseResult.Success -> _state.value = ApiState.Success(res.data)
                is BaseResult.Error -> _state.value = ApiState.Failure(res.err.code)
            }
        }
    }

    fun deletePlanner(plannerId : Long){
        viewModelScope.launch(Dispatchers.IO){
            setLoading()
            val res = plannerRepository.deletePlanner(plannerId)
            delay(500)
            hideLoading()
            when(res){
                is BaseResult.Success -> _state.value = ApiState.Success(Unit)
                is BaseResult.Error -> _state.value = ApiState.Failure(res.err.code)
            }
        }
    }

    fun selectPlanner(plannerId : Long){
        viewModelScope.launch(Dispatchers.IO){
            setLoading()
            val res = plannerRepository.findPlanner(plannerId)
            delay(500)
            hideLoading()
            when(res){
                is BaseResult.Success -> _state.value = ApiState.Success(res.data)
                is BaseResult.Error -> _state.value = ApiState.Failure(res.err.code)
            }
        }
    }

    fun getFakeTestItem() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            _recommendItemList.postValue(recommendRepository.getRecommendItem())
        }
    }

}