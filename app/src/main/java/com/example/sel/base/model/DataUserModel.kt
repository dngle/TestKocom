package com.example.sel.base.model

import com.example.sel.R
import com.example.sel.interfaces.ItemBaseModel

class DataUserModel(
    var index: Int? = null,
    var title: String? = null,
    var date: String? = null,
    var description: String? = null,
    override val layoutId: Int = R.layout.item_user,
    override val viewType: Int = 0
) : ItemBaseModel
