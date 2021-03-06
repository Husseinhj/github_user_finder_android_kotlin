package com.github.husseinhj.githubuser.adapters

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.annotation.SuppressLint
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.models.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.views.viewholders.UserSearchResultViewHolder

class UserSearchResultAdapter(private val dataset: List<UserSimpleDetailsModel>):
    BaseAdapter() {

    override fun getCount(): Int {
        return dataset.size
    }

    override fun getItem(position: Int): UserSimpleDetailsModel {
        return dataset[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parentViewGroup: ViewGroup?): View {
        var currentView = convertView
        val resultModel = getItem(position)
        var rowView = currentView?.tag as? UserSearchResultViewHolder

        if (currentView == null) {
            val context = parentViewGroup?.context
            val mInflater: LayoutInflater? = context?.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as? LayoutInflater

            currentView = mInflater?.inflate(R.layout.user_result_item_layout, null)
            rowView = UserSearchResultViewHolder(currentView!!)
        }

        rowView?.setUsername(resultModel.login ?: "")
        rowView?.setImageUrl(resultModel.avatarURL ?: "")

        currentView.tag = rowView

        return currentView
    }
}