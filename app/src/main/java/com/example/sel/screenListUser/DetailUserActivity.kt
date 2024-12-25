package com.example.sel.screenListUser

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sel.R
import com.example.sel.base.BaseBindingActivity
import com.example.sel.base.Constants
import com.example.sel.base.Utils
import com.example.sel.databinding.ActivityDetailUserBinding

class DetailUserActivity  : BaseBindingActivity<DetailUserViewModel, ActivityDetailUserBinding>(), View.OnClickListener {

    override fun setActivityLayout(): Int {
        return R.layout.activity_detail_user
    }

    override fun onActivityCreated() {
        super.onActivityCreated()
        initListener()
        viewModel.loadUsers()

    }

    override fun createViewModel(): DetailUserViewModel {
        return ViewModelProvider(this).get(DetailUserViewModel::class.java)
    }

    override fun fetchData() {
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        binding?.executePendingBindings()
    }

    override fun setActivityName(): String {
        return getString(R.string.detail_user)
    }

    override fun observeData() {
        var id = (intent.extras?.getString(Constants.BundleParam.INDEX) ?:"")
        viewModel.userData.observe(this) { userList ->
            val matchingUser = userList.find { it.index == id.toInt() }
            if (matchingUser != null) {
                viewModel.title.value = matchingUser.title
                viewModel.date.value = matchingUser.date
                viewModel.description.value = matchingUser.description
            }
        }
    }
    private fun initListener() {
        binding?.btnBack?.setOnClickListener(this)
        binding?.btnDelete?.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
               onBackPressed()
            }
            R.id.btn_delete -> {
                val id = intent.extras?.getString(Constants.BundleParam.INDEX)?.toIntOrNull()
                if (id != -1) {
                    viewModel.deleteUser(id?:0)
                    Toast.makeText(this, "User deleted successfully!", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }
}