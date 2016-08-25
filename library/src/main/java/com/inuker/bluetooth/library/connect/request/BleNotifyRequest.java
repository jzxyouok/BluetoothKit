package com.inuker.bluetooth.library.connect.request;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;

import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.connect.IBleRequestProcessor;
import com.inuker.bluetooth.library.connect.gatt.WriteDescriptorListener;
import com.inuker.bluetooth.library.connect.response.BleResponse;

import java.util.UUID;

/**
 * Created by liwentian on 2015/11/6.
 */
public class BleNotifyRequest extends BleRequest implements WriteDescriptorListener {

    public BleNotifyRequest(UUID service, UUID character, BleResponse response) {
        super(response);
        mRequestType = REQUEST_TYPE_NOTIFY;
        mServiceUUID = service;
        mCharacterUUID = character;
    }

    @Override
    public void process(IBleRequestProcessor processor) {
        super.process(processor);

        switch (getConnectStatus()) {
            case STATUS_DEVICE_SERVICE_READY:
                if (setCharacteristicNotification(mServiceUUID, mCharacterUUID, true)) {
                    registerGattResponseListener(this);
                } else {
                    notifyRequestResult(Code.REQUEST_FAILED, null);
                }
                break;

            default:
                notifyRequestResult(Code.REQUEST_FAILED, null);
                break;
        }
    }

    @Override
    int getGattResponseListenerId() {
        return GATT_RESP_DESCRIPTOR_WRITE;
    }

    @Override
    public void onDescriptorWrite(int status, BluetoothGattDescriptor descriptor) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            notifyRequestResult(Code.REQUEST_SUCCESS, null);
        } else {
            notifyRequestResult(Code.REQUEST_FAILED, null);
        }
    }
}
