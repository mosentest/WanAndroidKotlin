// ITimerManagerService.aidl
package com.ziqi.baselibrary;

// Declare any non-default types here with import statements
parcelable MyTime;

interface ITimerManagerService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean setTime(in String time);

    void start();

    void stop();

    void getTime(in String dataFormat, inout MyTime time);
}
