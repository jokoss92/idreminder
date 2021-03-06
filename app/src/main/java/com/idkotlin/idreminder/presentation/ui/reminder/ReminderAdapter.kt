package com.idkotlin.idreminder.presentation.ui.reminder

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.idkotlin.idreminder.R
import com.idkotlin.idreminder.data.entity.Reminder
import com.idkotlin.idreminder.presentation.ui.base.MultiSelectAdapter
import com.tutorial.learnlinuxpro.presentation.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.adapter_reminder.view.*

/**
 * Created by kodeartisan on 29/11/17.
 */
class ReminderAdapter(context: Context): MultiSelectAdapter<Reminder>(context) {

    private val TAG = ReminderAdapter::class.java.simpleName
     val mColorGenerator = ColorGenerator.DEFAULT

    private lateinit var mActiveListener: (ImageView, Int) -> Unit

    fun setActiveListener(itemClick: (ImageView, Int) -> Unit) {
        mActiveListener = itemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val view = mLayoutInflater.inflate(R.layout.adapter_reminder, parent, false)
        return ReminderViewholder(view)

    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int, payloads: MutableList<Any>?) {
        super.onBindViewHolder(holder, position, payloads)
        (holder as ReminderViewholder)?.mIvActive?.setOnClickListener{mActiveListener(it.active_image, position)}
    }


    inner class ReminderViewholder(itemView: View?) : MultiSelectViewHolder(itemView) {

        private val TAG = ReminderViewholder::class.java.simpleName

        private lateinit var mReminder: Reminder
        private var mColor: Int? = null
        private val mTxtTitle: TextView? by lazy { itemView?.findViewById<TextView>(R.id.title)}
        val mIvThumbnail: ImageView? by lazy { itemView?.findViewById<ImageView>(R.id.thumbnail_image) }
        val mIvActive: ImageView? by lazy { itemView?.findViewById<ImageView>(R.id.active_image) }
        private val mTxtDatetime: TextView? by lazy { itemView?.findViewById<TextView>(R.id.date_time) }
        private val mTxtRepeatInfo: TextView? by lazy { itemView?.findViewById<TextView>(R.id.repeat_info) }

        override fun fillView(position: Int) {
            mReminder = mData[position]
            setTitleDrawable()
            setTitle()
            setDateTime()
            setRepeatInfo()
            setActiveDrawable()
        }

        override fun onChangeState(state: Boolean, position: Int) {
            super.onChangeState(state, position)
            Log.d(TAG, "onChangeState ${state} position ${position}")
        }

        private fun setTitle() {
            mTxtTitle?.text = mReminder.title
        }

        private fun setTitleDrawable() {
            val letter = if(mReminder.title.isNotEmpty()) mReminder.title.substring(0, 1) else "A"
            val textDrawable = TextDrawable.builder().buildRound(letter, mColorGenerator.randomColor)

            mIvThumbnail?.setImageDrawable(textDrawable)
        }

        private fun setDateTime() {
            mTxtDatetime?.text = "${mReminder.date} - ${mReminder.time}"
        }

        private fun setRepeatInfo() {
            mTxtRepeatInfo?.text = if(mReminder.repeat) "Every ${mReminder.repeatNo} ${mReminder.repeatType}(s)"
                                   else "Repeat Off"

        }

        private fun setActiveDrawable() {
            val imageDrawable = if(mReminder.active) R.drawable.ic_notifications_active else R.drawable.ic_notifications_off
            mIvActive?.setImageResource(imageDrawable)

        }

    }




}