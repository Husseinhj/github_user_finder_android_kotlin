package com.github.husseinhj.githubuser.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.github.husseinhj.githubuser.R
import com.github.husseinhj.githubuser.models.data.UserSimpleDetailsModel
import com.github.husseinhj.githubuser.views.viewholders.UserSearchResultViewHolder

typealias OnUserCellClickedListener = (UserSimpleDetailsModel) -> Unit
class UserSearchResultAdapter(private val dataset: List<UserSimpleDetailsModel>, private val context: Context, private val clickListener: OnUserCellClickedListener):
    BaseAdapter() {
    private val mInflater: LayoutInflater? = context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE
    ) as? LayoutInflater

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
            currentView = mInflater?.inflate(R.layout.user_result_item_layout, null)
            rowView = UserSearchResultViewHolder(currentView!!, context)
        }

        rowView?.setUsername(resultModel.login ?: "")
        rowView?.setImageUrl(resultModel.avatarURL ?: "")
        rowView?.userCellView?.setOnClickListener {
            clickListener.invoke(resultModel)
        }
        currentView.tag = rowView
        return currentView
    }
}