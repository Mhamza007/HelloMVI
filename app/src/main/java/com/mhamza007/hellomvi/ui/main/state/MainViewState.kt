package com.mhamza007.hellomvi.ui.main.state

import com.mhamza007.hellomvi.model.BlogPost
import com.mhamza007.hellomvi.model.User

data class MainViewState(

    var user: User? = null,
    var blogPosts: List<BlogPost>? = null
)