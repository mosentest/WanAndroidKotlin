package com.ziqi.wanandroid.commonlibrary.util.glide;

public interface OnProgressListener {
    void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes);
}