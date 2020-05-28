package renetik.android.controller.pager

import androidx.viewpager.widget.ViewPager.*
import renetik.android.controller.base.CSViewController
import renetik.android.java.extensions.notNull
import renetik.android.java.math.CSMath.between

class CSOnPagerPageChange<PageType>(private val pager: CSPagerController<PageType>) : OnPageChangeListener
        where PageType : CSViewController<*>, PageType : CSPagerPage {

    private var onPageDragged: ((Int) -> Unit)? = null
    private var state: Int = 0
    private var draggingPageIndex: Int? = null
    private var draggedPage: Int = 0
    private var onPageReleased: ((Int) -> Unit)? = null

    init {
        pager.view.addOnPageChangeListener(this)
    }

    fun onDragged(function: (Int) -> Unit) = apply { onPageDragged = function }

    fun onReleased(function: (Int) -> Unit) = apply { onPageReleased = function }

    override fun onPageScrollStateChanged(state: Int) {
        this.state = state
        if (SCROLL_STATE_IDLE == this.state) onPageReleased()
    }

    override fun onPageScrolled(firstVisible: Int, offset: Float, offsetPixels: Int) {
        if (SCROLL_STATE_DRAGGING == state && draggingPageIndex != firstVisible) {
            draggingPageIndex.notNull { onPageReleased() }
            draggingPageIndex = firstVisible
            val draggedIndex = if (firstVisible < pager.currentIndex!!)
                firstVisible else pager.currentIndex!! + 1
            onPageDragged(draggedIndex)
        }
    }

    protected fun onPageDragged(index: Int) {
        draggedPage = index
        if (between(draggedPage, 0, pager.controllers.size)) onPageDragged?.invoke(draggedPage)
    }

    protected fun onPageReleased() {
        if (between(draggedPage, 0, pager.controllers.size)) onPageReleased?.invoke(draggedPage)
        draggingPageIndex = null
    }

    override fun onPageSelected(position: Int) {}

}