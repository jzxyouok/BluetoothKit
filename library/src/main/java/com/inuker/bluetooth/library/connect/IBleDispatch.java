package com.inuker.bluetooth.library.connect;

import android.os.Handler;

import com.inuker.bluetooth.library.connect.request.BleRequest;

public interface IBleDispatch {

    void notifyWorkerResult(BleRequest request);
    void notifyHandlerReady(Handler handler);
}
