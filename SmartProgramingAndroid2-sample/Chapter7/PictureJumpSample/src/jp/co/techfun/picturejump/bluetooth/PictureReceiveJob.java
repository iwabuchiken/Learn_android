package jp.co.techfun.picturejump.bluetooth;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

// 画像受信処理クラス
public class PictureReceiveJob implements Runnable {

    // サーバーソケット
    private BluetoothServerSocket serverSocket;

    // 受信停止フラグ
    private volatile boolean stop;

    // stopメソッド(受信終了処理)
    public void stop() {
        // サーバーソケットがnullの場合
        if (serverSocket != null) {
            try {
                // 受信停止フラグ設定
                stop = true;
                // サーバーソケットクローズ
                serverSocket.close();
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "stop listening failed", e);
            }
        }
    }

    // runメソッド(非同期処理)
    @Override
    public void run() {
        try {

            // BluetoothAdapterオブジェクト取得
            BluetoothAdapter bluetoothAdapter =
                BluetoothAdapter.getDefaultAdapter();

            // 接続待機用のサーバーソケット取得
            serverSocket =
                bluetoothAdapter.listenUsingRfcommWithServiceRecord(
                    BluetoothConstants.SERVICE_NAME,
                    BluetoothConstants.SERIAL_PORT_PROFILE);

            BluetoothSocket socket = null;

            try {

                // 他端末から接続されるまで待機
                socket = serverSocket.accept();

                // 画像受信開始時の処理
                handleReceiveStarted(socket.getRemoteDevice());

                // ソケット入出力用オブジェクト生成
                DataInputStream sockIn =
                    new DataInputStream(socket.getInputStream());
                DataOutputStream sockOut =
                    new DataOutputStream(socket.getOutputStream());

                // 画像出力バッファ生成
                ByteArrayOutputStream imageOut = new ByteArrayOutputStream();

                // 画像サイズ取得
                int total = sockIn.readInt();
                int progress = 0;

                int len = -1;
                byte[] buff = new byte[1024];

                // 画像サイズになるまで、読み込み
                while (total > progress) {
                    len = sockIn.read(buff);
                    imageOut.write(buff, 0, len);
                    progress += len;
                    handleProgress(total, progress);
                }

                imageOut.flush();

                // 画像受信完了を出力
                sockOut.writeInt(BluetoothConstants.CONNECT_SUCCESS);

                // ビットマップに変換
                Bitmap picture =
                    BitmapFactory.decodeByteArray(imageOut.toByteArray(), 0,
                        imageOut.size());

                // 画像受信終了時の処理
                handleReceiveFinished(socket.getRemoteDevice(), picture);

            } finally {
                // ソケットクローズ
                if (socket != null) {
                    socket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            }
        } catch (IOException e) {
            // 停止時に発生する例外は無視
            if (!stop) {
                handleException(e);
            }
        }
    }

    // 画像受信開始時の処理をサブクラス側で記述するためのテンプレートメソッド
    protected void handleReceiveStarted(BluetoothDevice device) {
    }

    // 画像受信終了時の処理をサブクラス側で記述するためのテンプレートメソッド
    protected void handleReceiveFinished(BluetoothDevice device, Bitmap picture) {
    }

    // 画像受信途中の処理をサブクラス側で記述するためのテンプレートメソッド
    protected void handleProgress(int total, int progress) {
    }

    // 画像取得失敗時の処理をサブクラス側で記述するためのテンプレートメソッド
    protected void handleException(IOException e) {
    }
}
