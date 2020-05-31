# WanAndroidKotlin

基于kotlin实现玩安卓app,目前还继续在开发中，主要是练手kotlin和jetpack


app部分截图效果，图片不分先后

![Image text](https://github.com/moz1q1/WanAndroidKotlin/blob/master/img/WX20200531-161210@2x.png)
![Image text](https://github.com/moz1q1/WanAndroidKotlin/blob/master/img/WX20200531-161304@2x.png)
![Image text](https://github.com/moz1q1/WanAndroidKotlin/blob/master/img/WX20200531-161318@2x.png)
![Image text](https://github.com/moz1q1/WanAndroidKotlin/blob/master/img/WX20200531-161416@2x.png)
![Image text](https://github.com/moz1q1/WanAndroidKotlin/blob/master/img/WX20200531-161450@2x.png)
![Image text](https://github.com/moz1q1/WanAndroidKotlin/blob/master/img/WX20200531-161533@2x.png）


开发环境 macbook、android studio

基于Androidx，使用recyclerview和viewpager2实现页面交互

基于kotlin+viewmodel+livedata实现布局页面

基于kotlin协程+retrofit实现网络请求

抽出基础库，拆分模块，方便开发其他项目

baselibrary->commonlibrary->app

baselibrary->commonlibrary->moduleme

功能介绍：
- 1.首页视差效果滚动banner
- 2.底部菜单切换，状态栏的改变效果
- 3.使用拦截器对Retrofit的出入参数进行挟持修改
```
class WanAndroidInterceptor : Interceptor {
    private val TAG = WanAndroidInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val method = request.method()
        var response: Response? = null
        if ("POST" == method) {
            val body = request.body()
            //处理请求
            if (body is FormBody) {
                //https://blog.csdn.net/wenyingzhi/article/details/80510249
                val builder = FormBody.Builder()
                for (i in 0 until body.size()) {
                    //这里改为addEncoded
                    builder.addEncoded(body.encodedName(i), body.encodedValue(i))
                    LogUtil.i(
                        TAG,
                        "WanAndroidInterceptor>>key:" + body.encodedName(i) + ",value:" + body.encodedValue(
                            i
                        )
                    )
                }
                //增加参数或者加密在这里操作
                builder.add("time", SystemClock.elapsedRealtime().toString())
                //构造新的请求体,覆盖之前的body
                val newRequestBody = builder.build()
                //构建新的request
                val newRequest = request.newBuilder().post(newRequestBody).build()
                //proceed
                response = chain.proceed(newRequest)
            } else if (body is MultipartBody) {
                //后面再考虑
                //var builder = MultipartBody.Builder()
                //proceed
                response = chain.proceed(request)
            } else {
                response = chain.proceed(request)
            }
            //处理返回
            if (response != null && HttpHeaders.hasBody(response)) {
                val responseBody = response.body()
                responseBody?.apply {
                    val source: BufferedSource = source()
                    source.request(Long.MAX_VALUE) // Buffer the entire body.
                    val buffer: Buffer = source.buffer()
                    val charset = Charset.forName("UTF-8")
                    val content = buffer.clone().readString(charset)

                    //===打印返回内容，如果解密之类操作，也可以在这里操作
                    LogUtil.i(
                        TAG,
                        "WanAndroidInterceptor>>>>content:$content"
                    )
                    //===打印返回内容，如果解密之类操作，也可以在这里操作

                    if (!StringUtil.isEmpty(content)) {
                        val obj =
                            GsonUtil.gson.fromJson<WanResponse<Any>>(
                                content,
                                WanResponse::class.java
                            )
                        if (obj.errorCode != 0) {
                            LogUtil.i(
                                TAG,
                                "WanAndroidInterceptor>>errorCode:" + obj.errorCode
                            )
                            throw ResponseThrowable(obj.errorCode + 1, obj.errorMsg)
                        }
                    }
                    val newResponseBody = ResponseBody.create(contentType(), content)
                    response = response?.newBuilder()?.body(newResponseBody)?.build()
                }
            }
        }
        if (response == null) {
            response = chain.proceed(request)
        }
        return response!!
    }
}
```
- 4.对携程请求做了二次封装，简单使用
```
fun asyncExt(
        block: suspend CoroutineScope.() -> Unit,
        onError: (rt: ResponseThrowable) -> Unit = {},
        isLoading: Boolean = false
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            onError(ExceptionHandle.handleException(e))
        }) {
            try {
                if (isLoading) {
                    zShowLoadingDialog()
                }
                block.invoke(this)
            } finally {
                if (isLoading) {
                    zHideLoadingDialog()
                }
            }
        }
    }
```
- 5.对imageview增加类型微信上传文件的交互效果
```
public class DstOutView extends View {
    private int mH;
    private int mW;


    private Paint mPaint = null;
    private PorterDuffXfermode mPorterDuffXfermode;

    public DstOutView(Context context) {
        super(context);
        init();
    }

    public DstOutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DstOutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DstOutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mH = getHeight();
        mW = getWidth();
    }

    /**
     * 教程：http://www.360doc.com/content/16/0705/15/21631240_573292156.shtml
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);
        /**
         * 设置View的离屏缓冲。在绘图的时候新建一个“层”，所有的操作都在该层而不会影响该层以外的图像
         * 必须设置，否则设置的PorterDuffXfermode会无效，具体原因不明
         */
        int sc = canvas.saveLayer(-mW / 2f, -mH / 2f, mW + mW / 2f, mH + mH / 2f, mPaint, Canvas.ALL_SAVE_FLAG);
        drawBackground(canvas);
        mPaint.setXfermode(mPorterDuffXfermode);
        drawHollowFileds(canvas);
        mPaint.setXfermode(null);
        /**
         * 还原画布，与canvas.saveLayer配套使用
         */
        canvas.restoreToCount(sc);
    }


    private void drawBackground(Canvas canvas) {
        mPaint.setColor(0x88000000);
        canvas.drawRect(0, 0, mW, mH, mPaint);
    }


    private void drawHollowFileds(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(-mW / 2f,
                -mH / 2f,
                mW + mW / 2f,
                mH + mH / 2f,
                -90,
                360f * progress / 100f,
                true,
                mPaint);
    }

    private int progress = 1;

    public void setProgress(int progress) {
        this.progress = progress;
        setVisibility(progress >= 100 ? GONE : VISIBLE);
        invalidate();
    }
}
```

引用库：
```
    //第三方库
    def eventbus_version = '3.2.0'
    //参考博客文章：https://www.jianshu.com/p/7ed3b4ca0d8b
    api "org.greenrobot:eventbus:$eventbus_version"
    kapt "org.greenrobot:eventbus-annotation-processor:$eventbus_version"

    //https://github.com/square/retrofit
    def retrofit_version = '2.6.0'
    api "com.squareup.retrofit2:retrofit:$retrofit_version"
    api "com.squareup.retrofit2:converter-gson:$retrofit_version"
    //https://github.com/square/okhttp
    //okhttp提供的请求日志拦截器
    api 'com.squareup.okhttp3:logging-interceptor:3.12.0'

    //https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter
    api 'com.jakewharton.retrofit:retrofit2-kotlin-coroutines-experimental-adapter:1.0.0'
    //https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter
    api("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.5.0")

    api 'com.tencent:mmkv-static:1.1.2'

    api 'com.github.bumptech.glide:glide:4.10.0'
    kapt 'com.github.bumptech.glide:compiler:4.10.0'

    //https://developer.android.google.cn/jetpack/androidx/releases/swiperefreshlayout?hl=zh-cn
    api "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"

    //banner广告图
    //https://github.com/youth5201314/banner
    api 'com.youth.banner:banner:2.0.6'

    //https://github.com/CymChad/BaseRecyclerViewAdapterHelper
    //新版本的3.0.x用起来太复杂
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.45-androidx'

    //https://github.com/Bigkoo/Android-PickerView
    api 'com.contrarywind:Android-PickerView:4.1.9'

    //https://github.com/mcxtzhang/SwipeDelMenuLayout/blob/master/README-cn.md
    api 'com.github.mcxtzhang:SwipeDelMenuLayout:V1.3.0'

    //https://github.com/dongjunkun/DropDownMenu
    api 'com.github.dongjunkun:DropDownMenu:1.0.4'

    //https://github.com/florent37/FiftyShadesOf
    api 'com.github.florent37:fiftyshadesof:1.0.0'

    //https://juejin.im/post/5d3fdc3af265da03c02bdbde
    //https://github.com/luckybilly/SmartSwipe
    api 'com.billy.android:smart-swipe:1.1.0'
    //compat for android x
    api 'com.billy.android:smart-swipe-x:1.1.0'
```
阿里巴巴的icon下载库：
https://www.iconfont.cn/collections/index?spm=a313x.7781069.1998910419.4&type=1

Kotlin FP, Coroutine/Flow

最后感谢hongyang提供的api接口

转载请标明来自：
https://github.com/moz1q1/WanAndroidKotlin

联系方式qq#709847739