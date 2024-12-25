package com.example.sel.base.model

import com.example.sel.R
import com.example.sel.interfaces.ItemBaseModel


data class ItemUserConverted (
    var id : String?=null,
    var title : String?= null,
    var date : String?= null,
    var description : String?= null,
    private val onItemClick: (ItemUserConverted) -> Unit = {},
    override val layoutId :Int = R.layout.item_user,
    override val viewType: Int = 0
): ItemBaseModel{
    fun onItemClick() {
        onItemClick(this)
    }
}
