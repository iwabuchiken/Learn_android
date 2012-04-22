package jp.co.techfun.picturejump.bluetooth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

// 画像送信処理クラス
public class PictureSendJob implements Runnable {

    // 送信デバイス　
    private BluetoothDevice device;

    // 送信画像
    private Bitmap picture;

    // コンストラクタ
    public PictureSendJob(BluetoothDevice device, Bitmap picture) {
        this.device = device;
        this.picture = picture;
    }

    // runメソッド(非同期処理)
    @Override
    public void run() {
        try {
            BluetoothSocket socket = null;

            try {

                // BluetoothAdapterオブジェクト取得
                BluetoothAdapter bluetoothAdapter =
                    BluetoothAdapter.getDefaultAdapter();
                // デバイス検索中の場合
                if (bluetoothAdapter.isDiscovering()) {
                    // 検索中止
                    bluetoothAdapter.cancelDiscovery();
                }

                // 送信用のソケット生成
                socket =
                    device
                        .createRfcommSocketToServiceRecord(
                        		BluetoothConstants.SERIAL_PORT_PROFILE);

                // デバイスに接続
                socket.connect();

                // 画像送信開始時の処理
                handleSendStarted(device);

                // 画像入出力用オブジェクト生成
                ByteArrayOutputStream imageOutput = new ByteArrayOutputStream();
                picture.compress(CompressFormat.PNG, 100, imageOutput);
                ByteArrayInputStream imageIn =
                    new ByteArrayInputStream(imageOutput.toByteArray());

                // ソケット入出力用オブジェクト生成
                DataOutputStream sockOut =
                    new DataOutputStream(socket.getOutputStream());
                DataInputStream sockIn =
                    new DataInputStream(socket.getInputStream());

                // 画像サイズ取得
                int total = imageOutput.size();
                int progress = 0;

                int len = -1;
                byte[] buff = new byte[1024];

                // 画像サイズ書き込み
                sockOut.writeInt(total);

                // 画像情報書き込み
                while ((len = imageIn.read(buff)) != -1) {
                    sockOut.write(buff, 0, len);
                    progress += len;
                    handleProgress(total, progress);
                }

                sockOut.flush();

                // 送信結果取得
                int result = sockIn.readInt();
                if (result != BluetoothConstants.CONNECT_SUCCESS) {
                    throw new IOException("connect failed.");
                }

                // 画像送信終了時の処理
                handleSendFinish();

            } finally {

                if (socket != null) {
                    socket.close();
                }
            }
        } catch (IOException e) {
            handleException(e);
        }
    }

    // 画像送信開始時の処理をサブクラス側で記述するためのテンプレートメソッド
    protected void handleSendStarted(BluetoothDevice device) {
    }

    // 画像送信終了時の処理をサブクラス側で記述するためのテンプレートメソッド
    protected void handleSendFinish() {
    }

    // 画像送信中の処理をサブクラス側で記述するためのテンプレートメソッド
    protected void handleProgress(int total, int progress) {
    }

    // 画像送信失敗時の処理をサブクラス側で記述するためのテンプレートメソッド
    protected void handleException(IOException e) {
    }
}
