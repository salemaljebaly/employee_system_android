package com.almaki.employeeabsense.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.model.response.EmployeeDailyRecordResponse
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.fragment_timeline.view.*
import kotlinx.android.synthetic.main.record_item.view.*

class DailyRecordsAdapter(private val recordList: ArrayList<EmployeeDailyRecordResponse>) : RecyclerView.Adapter<DailyRecordsAdapter.TimeLineViewHolder>() {

    private lateinit var mLayoutInflater: LayoutInflater
    /** ========================================================================================= */

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }
    /** ========================================================================================= */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {

        if(!::mLayoutInflater.isInitialized) {
            mLayoutInflater = LayoutInflater.from(parent.context)
        }

        return TimeLineViewHolder(mLayoutInflater.inflate(R.layout.record_item, parent, false), viewType)
    }
    /** ========================================================================================= */
    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

        val timeLineModel = recordList[position]

//        when {
//            timeLineModel.status == OrderStatus.INACTIVE -> {
//                setMarker(holder, R.drawable.ic_marker_inactive, R.color.material_grey_500)
//            }
//            timeLineModel.status == OrderStatus.ACTIVE -> {
//                setMarker(holder, R.drawable.ic_marker_active, R.color.material_grey_500)
//            }
//            else -> {
//                setMarker(holder, R.drawable.ic_marker, R.color.material_grey_500)
//            }
//        }

//        if (timeLineModel.date.isNotEmpty()) {
//            holder.date.setVisible()
//            holder.date.text = timeLineModel.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
//        } else
//            holder.date.setGone()

        holder.come_at.text = timeLineModel.come_at
        holder.leave_at.text = timeLineModel.leave_at
    }
    /** ========================================================================================= */

//    private fun setMarker(holder: TimeLineViewHolder, drawableResId: Int, colorFilter: Int) {
//        holder.timeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, drawableResId, ContextCompat.getColor(holder.itemView.context, colorFilter))
//    }
    /** ========================================================================================= */

    override fun getItemCount() = recordList.size
    /** ========================================================================================= */

    inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val come_at = itemView.record_item_come_at
        val leave_at = itemView.record_item_leave_at
        val timeline = itemView.timeline

        init {
            timeline.initLine(viewType)
        }
    }
    /** ========================================================================================= */

}
