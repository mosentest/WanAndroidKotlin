package com.ziqi.baselibrary.util

import android.os.Environment
import android.os.StatFs
import android.util.Log
import java.io.File

/**
 *
 * https://www.jianshu.com/p/3b82a5c468d6
 *
 * 完成代码的健壮性，其实现在的Android手机的储存都的够用的。
 * 一般不会没有空间，有这个类来判断就更健壮一些
 */
object MemoryStatus {
    private const val ERROR = -1
    // 判断SD卡是否存在?
    fun externalMemoryAvailable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // 获取内部存储器有用空间大小?
    val availableInternalMemorySize: Long
        get() {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            return availableBlocks * blockSize
        }

    // 获取内部存储器空间的大小
    val totalInternalMemorySize: Long
        get() {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            return totalBlocks * blockSize
        }

    // 获取SD卡有用空间大小，错误返回-1
    val availableExternalMemorySize: Long
        get() = if (externalMemoryAvailable()) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            availableBlocks * blockSize
        } else {
            ERROR.toLong()
        }

    // 获取SD卡的空间大小，错误返码1
    val totalExternalMemorySize: Long
        get() = if (externalMemoryAvailable()) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            totalBlocks * blockSize
        } else {
            ERROR.toLong()
        }

    /**
     * 根据给定的文件的路径来计算文件夹的大小
     *
     * @param dir 文件的路径
     * @return 文件夹的大小
     */
    fun getFileSize(dir: File?): Long {
        if (dir == null) {
            return 0
        }
        if (!dir.isDirectory) {
            return 0
        }
        var dirSize: Long = 0
        val files = dir.listFiles()
        for (file in files) {
            if (file.isFile) {
                dirSize += file.length()
            } else if (file.isDirectory) {
                dirSize += getFileSize(file) //如果是目标那就进行递归 来计算文件的大小
            }
        }
        return dirSize
    }

    // 把文件大小转化字符串
    fun formatSize(size: Long): String {
        var size = size
        LogUtil.i("WL-gui", "文件的大小为:$size")
        var suffix: String? = null
        if (size == 0L) {
            return ""
        }
        if (size >= 1024) {
            suffix = "KB"
            size /= 1024
            if (size >= 1024) {
                suffix = "MB"
                size /= 1024
                if (size >= 1024) {
                    suffix = "G"
                    size /= 1024
                }
            }
        }
        val resultBuffer =
            StringBuilder(java.lang.Long.toString(size))
        var commaOffset = resultBuffer.length - 3
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',')
            commaOffset -= 3
        }
        if (suffix != null) resultBuffer.append(suffix)
        return resultBuffer.toString()
    }
}