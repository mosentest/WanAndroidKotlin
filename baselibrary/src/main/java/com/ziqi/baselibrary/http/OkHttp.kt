package com.ziqi.baselibrary.http

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.ziqi.baselibrary.http.error.ResponseThrowable
import com.ziqi.baselibrary.util.gson.GsonUtil
import com.ziqi.baselibrary.util.MemoryStatus.availableExternalMemorySize
import com.ziqi.baselibrary.util.MemoryStatus.availableInternalMemorySize
import com.ziqi.baselibrary.util.MemoryStatus.externalMemoryAvailable
import okhttp3.*
import org.jetbrains.annotations.NotNull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URLConnection
import java.util.*

/**
 * https://www.jianshu.com/p/3b82a5c468d6
 */
class OkHttp {
    private var METHOD = 1000
    private var updateFile: File? = null
    private var okHttpClient: OkHttpClient? = null
    private var requestBody: RequestBody? = null
    private var request: Request? = null
    private var requestUrl: String? = null
    private var params: MutableMap<String, String> = HashMap()
    private val files: MutableMap<String, File> = HashMap()

    fun add(key: String, value: String): OkHttp {
        params[key] = value
        return this
    }

    fun add(map: Map<String, String>): OkHttp {
        params.putAll(map)
        return this
    }

    fun add(key: String, file: File): OkHttp {
        files[key] = file
        return this
    }

    private var BUILD_TYPE = 300

    private val JSON_TYPE = 301

    private val FILE_TYPE = 302

    private val JSONType = MediaType.parse("application/json; charset=utf-8")

    fun <T> buildByJson(httpCallBack: HttpCallBack<T>) {
        BUILD_TYPE = JSON_TYPE
        build(httpCallBack)
    }

    /**
     * 基于http的文件上传（传入文件数组和key）混合参数和文件请求
     * 通过addFormDataPart可以添加多个上传的文件
     */
    private fun <T> buildByFile(myDataCallBack: HttpCallBack<T>) {
        val builder = FormBody.Builder()
        //form表单提交
        for (key in params.keys) {
            val value = params[key]
            builder.add(key, value)
        }
        val body: RequestBody = builder.build()
        val multipartBody = MultipartBody.Builder()
        multipartBody.setType(MultipartBody.ALTERNATIVE).addPart(body)
        var fileBody: RequestBody? = null
        for (key in files.keys) {
            val file = files[key]
            val fileName = file!!.name
            fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file)
            multipartBody.addPart(
                Headers.of(
                    "Content-Disposition",
                    "form-data; name=\"$key\"; filename=\"$fileName\""
                ), fileBody
            )
        }
        requestBody = multipartBody.build()
        BUILD_TYPE = FILE_TYPE
        build(myDataCallBack)
    }

    private fun guessMimeType(fileName: String): String {
        val fileNameMap = URLConnection.getFileNameMap()
        var contentTypeFor = fileNameMap.getContentTypeFor(fileName)
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream"
        }
        return contentTypeFor
    }

    fun <T> build(@NotNull httpCallBack: HttpCallBack<T>) {
        when (BUILD_TYPE) {
            JSON_TYPE -> {
                val content = GsonUtil.gson.toJson(params)
                requestBody = RequestBody.create(JSONType, content)
            }
            FILE_TYPE -> {
            }
            else -> {
                //form表单提交
                val builder = FormBody.Builder()
                for (key in params.keys) {
                    builder.add(key, params[key])
                }
                requestBody = builder.build()
            }
        }
        if (METHOD == GET) {
            request = Request.Builder()
                .url(requestUrl)
                .get() //默认就是GET请求，可以不写
                .build()
        } else if (METHOD == POST) {
            request = Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                .build()
        }
        handler.post {
            httpCallBack.start()
        }
        val call = okHttpClient!!.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    httpCallBack.error(e)
                    httpCallBack.end()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                try {
                    val jsonStr = response.body()!!.string()
                    val obj = GsonUtil.gson.fromJson<T>(jsonStr, httpCallBack.mType)
                    handler.post {
                        httpCallBack.success(obj)
                    }
                } catch (e: Exception) {
                    handler.post {
                        httpCallBack.error(e)
                    }
                } finally {
                    handler.post {
                        httpCallBack.end()
                    }
                }
            }
        })
    }

    @SuppressLint("HandlerLeak")
    var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {}
    }
    /**
     * 下载文件******************************************************************************************************************************************
     */
    /**
     * 文件下载
     *
     * @param url path路径
     * @param destFileDir 本地存储的文件夹路径
     * @param myDataCallBack 自定义回调接口
     */
    private var downUrl: String? = null

    private var filrDir: String? = null

    private var totalSize = 0L //APK总大小

    private var downloadSize = 0L // 下载的大小

    private var count = 0f //下载百分比

    fun down(@NotNull httpFileCallBack: HttpFileCallBack) {
        val request = Request.Builder()
            .url(downUrl)
            .build()
        val call = okHttpClient!!.newCall(request)
        fileHandler.post {
            httpFileCallBack.start()
        }
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                fileHandler.post {
                    httpFileCallBack.error(e)
                    httpFileCallBack.end()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(
                call: Call,
                response: Response
            ) {
                try {
                    if (response.code() == 200) {
                        var `is`: InputStream? = null
                        val buf = ByteArray(4096)
                        var len = 0
                        var fos: FileOutputStream? = null
                        try {
                            totalSize = response.body()!!.contentLength()
                            downloadSize = 0L
                            if (memoryAvailable(totalSize)) {
                                `is` = response.body()!!.byteStream()
                                fos = FileOutputStream(updateFile, true)
                                while (`is`.read(buf).also { len = it } != -1) {
                                    downloadSize += len.toLong()
                                    fos.write(buf, 0, len)
                                    if (count == 0f || (downloadSize * 100 / totalSize).toInt() >= count) {
                                        count += 5f
                                        //文本进度（百分比）
                                        fileHandler.post {
                                            httpFileCallBack.progress(
                                                count.toInt()
                                            )
                                        }
                                    }
                                }
                                fos.flush()
                                fileHandler.post {
                                    httpFileCallBack.success(
                                        updateFile
                                    )
                                }
                            } else {
                                fileHandler.post {
                                    httpFileCallBack.error(
                                        ResponseThrowable(
                                            -1,
                                            "内存不足"
                                        )
                                    )
                                }
                            }
                        } catch (e: IOException) {
                            fileHandler.post { httpFileCallBack.error(e) }
                        } finally {
                            try {
                                `is`?.close()
                                fos?.close()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        fileHandler.post {
                            httpFileCallBack.error(
                                ResponseThrowable(
                                    response.code(),
                                    "请求状态码不对,下载失败"
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    fileHandler.post { httpFileCallBack.error(e) }
                } finally {
                    fileHandler.post { httpFileCallBack.end() }
                }
            }
        })
    }

    private fun getFileName(url: String?): String? {
        val separatorIndex = url!!.lastIndexOf("/")
        return if (separatorIndex < 0) url else url.substring(separatorIndex + 1)
    }

    /**
     * 可用内存大小
     *
     * @param fileSize
     * @return
     */
    private fun memoryAvailable(fileSize: Long): Boolean {
        var fileSize = fileSize
        fileSize += (1024 shl 10).toLong()
        return if (externalMemoryAvailable()) {
            if (availableExternalMemorySize <= fileSize) {
                if (availableInternalMemorySize > fileSize) {
                    createFile()
                    true
                } else {
                    false
                }
            } else {
                createFile()
                true
            }
        } else {
            if (availableInternalMemorySize <= fileSize) {
                false
            } else {
                createFile()
                true
            }
        }
    }

    private fun createFile() {
        val file = File(filrDir + getFileName(downUrl))
        if (file.exists()) {
            file.delete()
        }
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        updateFile = file
    }

    private val fileHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {}
    }

    /***************************************************************下载完文件 */


    companion object {
        private const val POST = 1001
        private const val GET = 1002
        private fun instance(): OkHttp {
            return OkHttp()
        }

        fun post(url: String?): OkHttp {
            val instance = instance()
            instance.okHttpClient = OkHttpUtils.instance.okHttpClient
            instance.METHOD = POST
            instance.requestUrl = url
            return instance
        }

        operator fun get(url: String?): OkHttp {
            val instance = instance()
            instance.okHttpClient = OkHttpUtils.instance.okHttpClient
            instance.METHOD = GET
            instance.requestUrl = url
            return instance
        }

        fun downFile(realURL: String?, destFileDir: String?): OkHttp {
            val instance = instance()
            instance.okHttpClient = OkHttpUtils.instance.okHttpClient
            instance.downUrl = realURL
            instance.filrDir = destFileDir
            return instance
        }
    }
}