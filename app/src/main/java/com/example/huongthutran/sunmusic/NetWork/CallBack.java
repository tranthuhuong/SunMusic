package com.example.huongthutran.sunmusic.NetWork;

public interface CallBack<T> {
    public void onSuccess(T result);
    public void onFail(T result);
}
