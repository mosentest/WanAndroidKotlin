package com.ziqi.baselibrary.view.viewpager

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * https://blog.csdn.net/c6E5UlI1N/article/details/90307961
 * https://www.jianshu.com/p/116e5749bb3e
 *
 *
 *
 *
 * hongyang 博客
 * https://mp.weixin.qq.com/s/MOWdbI5IREjQP1Px-WJY1Q
 *
 *
 * #每日一问 | Fragment 是如何被存储与恢复的？
 * https://www.wanandroid.com/wenda/show/12574
 * #每日一问 ViewPager 这个流传广泛的写法，其实是有问题的！
 */
abstract class MaxLifecyclePagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    private val registeredFragments = SparseArray<Fragment>()

    /**
     * https://mp.weixin.qq.com/s/MOWdbI5IREjQP1Px-WJY1Q
     *
     * @param container
     * @param position
     * @return
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments[position]
    }

    companion object {
        /**
         * https://www.jianshu.com/p/ad810a0bef6b
         * @param activity
         * @param viewPager
         * @param position
         * @param defaultResult
         * @return
         */
        fun instantiateFragment(
            activity: FragmentActivity,
            viewPager: ViewPager,
            position: Int,
            defaultResult: Fragment
        ): Fragment {
            val tag = "android:switcher:" + viewPager.id + ":" + position
            val fragment =
                activity.supportFragmentManager.findFragmentByTag(tag)
            return fragment ?: defaultResult
        }
    }
}