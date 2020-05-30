package com.ziqi.wanandroid.commonlibrary.util.imageload

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ziqi.baselibrary.util.LogUtil
import com.ziqi.wanandroid.commonlibrary.util.glide.OnProgressListener
import com.ziqi.wanandroid.commonlibrary.util.glide.ProgressManager
import com.ziqi.wanandroid.commonlibrary.view.ImageViewX
import java.io.File
import java.util.concurrent.ExecutionException


/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/16 4:25 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object ImageLoadUtils : IImageLoad {

    override fun loadUrl(context: Context, url: String?, imageView: ImageView?) {
        try {
            imageView?.apply { Glide.with(context).load(url).into(this) }
        } catch (e: Exception) {
            LogUtil.e("context.loadUrl", e)
        }
    }

    override fun loadUrl(fragment: Fragment, url: String?, imageView: ImageView?) {
        try {
            imageView?.apply { Glide.with(fragment).load(url).into(this) }
        } catch (e: Exception) {
            LogUtil.e("fragment.loadUrl", e)
        }
    }

    override fun loadUrl(
        fragment: Fragment,
        url: String?,
        imageViewX: ImageViewX?,
        @DrawableRes resourceId: Int,
        scaleType: ImageView.ScaleType
    ) {
        try {
            imageViewX?.apply {
                ProgressManager.addListener(url, object : OnProgressListener {
                    override fun onProgress(
                        isComplete: Boolean,
                        percentage: Int,
                        bytesRead: Long,
                        totalBytes: Long
                    ) {
                        imageViewX.setProgress(percentage)
                    }
                })
                //点我重试
                imageViewX.setRetryTip("点我重试")
                //先设置默认的占位模式
                imageViewX.getTarget().scaleType = ImageView.ScaleType.FIT_CENTER
                //回调重试
                imageViewX.mRetryListener = object : ImageViewX.RetryListener {
                    override fun onRetry() {
                        loadUrl(
                            fragment,
                            url,
                            imageViewX,
                            resourceId,
                            scaleType
                        )
                    }
                }
                Glide.with(fragment)
                    .load(url)
                    .placeholder(resourceId)
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            imageViewX.showRetry(true)
                            imageViewX.setProgress(100)
                            //删除map的缓存
                            ProgressManager.removeListener(url)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            //调整这个为scaleType
                            imageViewX.getTarget().scaleType = scaleType
                            imageViewX.showRetry(false)
                            imageViewX.setProgress(100)
                            //删除map的缓存
                            ProgressManager.removeListener(url)
                            return false
                        }
                    })
                    .into(imageViewX.getTarget())
            }
        } catch (e: Exception) {
            LogUtil.e("fragment.loadUrl", e)
        }
    }

    /**
     * https://www.jianshu.com/p/b5246e210b07
     */
    fun getImagePathFromCache(
        fragment: Fragment,
        url: String?,
        expectW: Int,
        expectH: Int
    ): String? {
        val future: FutureTarget<File> =
            Glide.with(fragment).load(url).downloadOnly(expectW, expectH)
        try {
            val cacheFile: File = future.get()
            return cacheFile.getAbsolutePath()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return null
    }

}