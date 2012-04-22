package jp.co.techfun.picturejump.bluetooth;

import java.util.UUID;

// Bluetooth接続用定数定義クラス
public final class BluetoothConstants {

    // コンストラクタ
    private BluetoothConstants() {
    }

    // サービス名
    public static final String SERVICE_NAME = "Picture Jump";

    // UUID（一意な識別子）
    public static final UUID SERIAL_PORT_PROFILE =
        UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    // 受信成功通知用定数
    public static final int CONNECT_SUCCESS = 1;

    // 自身のデバイスを他のデバイスから発見可能とする期間
    public static final int DURATION = 300;
}
