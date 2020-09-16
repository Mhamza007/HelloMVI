package com.mhamza007.hellomvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mhamza007.hellomvi.model.BlogPost
import com.mhamza007.hellomvi.model.User
import com.mhamza007.hellomvi.repository.Repository
import com.mhamza007.hellomvi.ui.main.state.MainStateEvent
import com.mhamza007.hellomvi.ui.main.state.MainStateEvent.GetBlogPostsEvent
import com.mhamza007.hellomvi.ui.main.state.MainViewState
import com.mhamza007.hellomvi.util.AbsentLiveData
import com.mhamza007.hellomvi.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(it)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when (stateEvent) {
            is GetBlogPostsEvent -> {
                return Repository.getBlogPosts()
            }
            is MainStateEvent.GetUserEvent -> {
                return Repository.getUser(stateEvent.userId)
            }
            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }

    // if no state it creates or if present it gets
    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value ?: MainViewState()
    }

    fun setBlogListData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}