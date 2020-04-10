package com.ziqi.baselibrary.http.cookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * https://blog.csdn.net/zhujiangtaotaise/article/details/88129355
 */
public class MyCookieJarImpl implements CookieJar {
    private final PersistentCookieStore cookieStore;

    public MyCookieJarImpl(Context context) {
        cookieStore = PersistentCookieStore.getInstance(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        //本地可校验cookie，并根据需要存储
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        //从本地拿取需要的cookie
        return cookieStore.get(url);
    }
}