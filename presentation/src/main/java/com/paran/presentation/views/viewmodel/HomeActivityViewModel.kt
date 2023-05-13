package com.paran.presentation.views.viewmodel

import ajou.paran.domain.model.BasePlanner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paran.presentation.common.route.HomeRoute
import com.paran.presentation.utils.state.PlannerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel
@Inject
constructor(

) : ViewModel() {
    companion object {
        private const val TAG = "HomeActVM"
    }

    private val _route = MutableLiveData<HomeRoute>(HomeRoute.Home)
    val route: LiveData<HomeRoute>
        get() = _route

    private val _detailPlannerState = MutableLiveData<PlannerState>(PlannerState.Init)
    val detailPlannerState: LiveData<PlannerState>
        get() = _detailPlannerState

    fun pushRoute(routeName: String) {
        when (routeName) {
            HomeRoute.Planner.tag -> _route.value = HomeRoute.Planner
            HomeRoute.PlannerDetail.tag -> _route.value = HomeRoute.PlannerDetail
            HomeRoute.Recommendation.tag -> _route.value = HomeRoute.Recommendation
            HomeRoute.Community.tag -> _route.value = HomeRoute.Community
            HomeRoute.MyPage.tag -> _route.value = HomeRoute.MyPage
            else -> _route.value = HomeRoute.Home
        }
    }

    fun initPlannerData(planner: BasePlanner) {
        _detailPlannerState.value = PlannerState.Store(planner)
        _route.value = HomeRoute.PlannerDetail
    }

    fun cleanPlannerData() {
        _detailPlannerState.value = PlannerState.Init
        _route.value = HomeRoute.Planner
    }

}