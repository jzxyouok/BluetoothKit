package com.inuker.bluetooth.library.connect.request;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import com.inuker.bluetooth.library.connect.gatt.WriteCharacterListener;
import com.inuker.bluetooth.library.connect.response.BluetoothResponse;

import java.util.UUID;

public class BleWriteRequest extends BleRequest implements WriteCharacterListener {

    public BleWriteRequest(UUID service, UUID character, byte[] bytes,
                           BluetoothResponse response) {
        super(response);
        mServiceUUID = service;
        mCharacterUUID = character;
        mBytes = bytes;
    }

    @Override
    void processRequest() {
        switch (getConnectStatus()) {
            case STATUS_DEVICE_SERVICE_READY:
                if (writeCharacteristic(mServiceUUID, mCharacterUUID, mBytes)) {
                    registerGattResponseListener(this);
                } else {
                    onRequestFinished(REQUEST_FAILED);
                }
                break;

            default:
                onRequestFinished(REQUEST_FAILED);
                break;
        }
    }

    @Override
    int getGattResponseListenerId() {
        return GATT_RESP_CHARACTER_WRITE;
    }

    @Override
    public void onCharacteristicWrite(BluetoothGattCharacteristic characteristic, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            onRequestFinished(REQUEST_SUCCESS);
        } else {
            onRequestFinished(REQUEST_FAILED);
        }
    }
}
