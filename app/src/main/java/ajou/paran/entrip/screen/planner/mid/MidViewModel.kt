package ajou.paran.entrip.screen.planner.mid

import ajou.paran.entrip.model.PlanEntity
import ajou.paran.entrip.repository.Impl.PlanRepository
import ajou.paran.entrip.repository.Impl.PlanRepositoryImpl
import ajou.paran.entrip.util.network.BaseResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MidViewModel @Inject constructor(
    private val planRepository: PlanRepositoryImpl
) : ViewModel() {

    /*
    lateinit var date : String
    lateinit var plannerId : String
    val plans = planRepository.getPlans(date, plannerId).asLiveData()
     */

    companion object {
        private const val TAG = "[MidViewModel]"
    }

    fun loadPlan(date: String, plannerId : Long) : Flow<List<PlanEntity>> = planRepository.selectPlan(date,plannerId)

    fun deletePlan(plan_id: Long, planner_id : Long){
        viewModelScope.launch(Dispatchers.IO) {
            val result = planRepository.deletePlan(plan_id, planner_id)
            if(result is BaseResult.Success){

            }else{

            }
        }
    }

    fun updateQueryPlan(planEntity:PlanEntity){
        viewModelScope.launch(Dispatchers.IO) {
            planRepository.updatePlan(
                planEntity.todo,
                planEntity.rgb,
                planEntity.time,
                planEntity.location.toString(),
                planEntity.id)
        }
    }
}