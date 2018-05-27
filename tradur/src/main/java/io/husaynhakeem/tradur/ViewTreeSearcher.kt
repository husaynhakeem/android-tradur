package io.husaynhakeem.tradur

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*


internal object ViewTreeSearcher {

    fun findTextViewWithIdClosestTo(textView: TextView, resId: Int): TextView? {
        return findFirstTextViewWithId(textView.parent as? ViewGroup, resId)
    }

    private fun findFirstTextViewWithId(parent: ViewGroup?, resId: Int): TextView? {
        if (parent == null) {
            return null
        }

        val views = LinkedList<View>()
        views.addAll(getAllChildrenViewsOf(parent))

        while (views.isNotEmpty()) {
            val view = views.pollFirst()
            if (view.id == resId && view is TextView) {
                return view
            }
        }

        return findFirstTextViewWithId(parent.parent as? ViewGroup, resId)
    }

    private fun getAllChildrenViewsOf(view: View?) = mutableListOf<View>().apply {
        if (view == null || view !is ViewGroup) {
            return@apply
        }

        for (i in 0 until view.childCount) {
            this.add(view.getChildAt(i))
        }
    }
}