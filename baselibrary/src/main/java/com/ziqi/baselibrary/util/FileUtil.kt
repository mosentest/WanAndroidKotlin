package com.ziqi.baselibrary.util

import java.io.File

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020/4/10 2:30 PM
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object FileUtil {
    //删除文件夹和文件夹里面的文件
    fun deleteDir(pPath: String?) {
        val dir = File(pPath)
        deleteDirWihtFile(dir)
    }

    fun deleteDirWihtFile(dir: File?) {
        if (dir == null || !dir.exists() || !dir.isDirectory) return
        for (file in dir.listFiles()) {
            if (file.isFile) file.delete() // 删除所有文件
            else if (file.isDirectory) deleteDirWihtFile(file) // 递规的方式删除文件夹
        }
        dir.delete() // 删除目录本身
    }
}