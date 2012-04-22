package jp.co.techfun.picturejump;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//トップ画面Activity
public class PictureJumpActivity extends Activity {

    // Bluetooth有効化リクエストコード
    private static final int REQUEST_CODE_BLUETOOTH_ENABELD = 0;

    // onCreateメソッド(画面初期表示イベント)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイル指定
        setContentView(R.layout.picture_jump);
        
        // アプリケーション用ユーティリティを初期化
        AppUtil.initialize(this);

        // 「画像を送信」ボタンにリスナー設定
        Button btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new OnClickListener() {
            // onClickメソッド(クリックイベント)
            @Override
            public void onClick(View view) {
                // 画像送信画面へ遷移
                Intent intent =
                    new Intent(PictureJumpActivity.this,
                        PictureSendActivity.class);
                startActivity(intent);
            }
        });

        // 「画像を受信」ボタンにリスナー設定
        Button btnReceive = (Button) findViewById(R.id.btn_receive);
        btnReceive.setOnClickListener(new OnClickListener() {
            // onClickメソッド(クリックイベント)
            @Override
            public void onClick(View view) {
                // 画像受信画面へ遷移
                Intent intent =
                    new Intent(PictureJumpActivity.this,
                        PictureReceiveActivity.class);
                startActivity(intent);
            }
        });
    }

    // onStartメソッド(画面表示イベント)
    @Override
    protected void onStart() {
        super.onStart();

        // BluetoothAdapterオブジェクト取得
        BluetoothAdapter bluetoothAdapter =
            BluetoothAdapter.getDefaultAdapter();
        // Bluetoothがサポートされていない端末の場合
        if (bluetoothAdapter == null) {
            // アプリケーション終了
            AppUtil.showToast(this, R.string.bluetooth_not_supported);
            finish();
            return;
        }
        // Bluetoothが有効に設定されていない場合
        if (!bluetoothAdapter.isEnabled()) {
            // Bluetooth有効設定画面呼び出し
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_CODE_BLUETOOTH_ENABELD);
            return;
        }
    }

    // onActivityResultメソッド(メイン画面再表示時イベント)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Bluetooth有効設定画面から戻ってきた場合
        if (requestCode == REQUEST_CODE_BLUETOOTH_ENABELD) {
            // Bluetoothが有効に設定できない場合
            if (resultCode != RESULT_OK) {
                // アプリケーション終了
                AppUtil.showToast(this, R.string.bluetooth_not_valid);
                finish();
                return;
            }
        }
    }
}
