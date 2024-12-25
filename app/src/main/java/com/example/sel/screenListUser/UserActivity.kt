package com.example.sel.screenListUser

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import com.example.sel.R
import com.example.sel.base.BaseBindingActivity
import com.example.sel.base.Constants
import com.example.sel.base.Utils
import com.example.sel.base.model.ItemUserConverted
import com.example.sel.databinding.ActivityUserBinding

class UserActivity : BaseBindingActivity<UserViewModel, ActivityUserBinding>(),
    View.OnClickListener {

    companion object {
        const val REQUEST_CODE = 100
    }

    override fun setActivityLayout(): Int {
        return R.layout.activity_user
    }

    override fun onActivityCreated() {
        super.onActivityCreated()
        viewModel.resetUsersToDefault()
        viewModel.loadUsers()
        initSortFeature()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.loadUsers()
        }
    }

    override fun createViewModel(): UserViewModel {
        return ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun fetchData() {
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        binding?.executePendingBindings()
    }

    override fun setActivityName(): String {
        return getString(R.string.list_user)
    }

    override fun observeData() {
        viewModel.userData.observe(this) { userList ->
            viewModel.convertListUser(userList)
        }
        viewModel.onClickItemUser = ::onClickItemUser
    }

    private fun initSortFeature() {
        binding?.spinnerSort?.let { spinner ->
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val criteria = when (position) {
                        0 -> Constants.BundleParam.INDEX
                        1 -> Constants.BundleParam.TITLE
                        2 -> Constants.BundleParam.DATE
                        else -> Constants.BundleParam.INDEX
                    }
                    viewModel.sortUsers(criteria)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
    }

    private fun onClickItemUser(itemUserConverted: ItemUserConverted) {
        Utils.startActivityWithResultCode(
            this@UserActivity,
            DetailUserActivity(),
            REQUEST_CODE,
            Bundle().apply {
                putString(Constants.BundleParam.INDEX, itemUserConverted.id)
            }
        )
    }

    override fun onClick(v: View?) {}
}