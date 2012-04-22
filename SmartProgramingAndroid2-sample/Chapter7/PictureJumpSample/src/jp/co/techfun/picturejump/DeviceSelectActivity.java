package jp.co.techfun.picturejump;

import jp.co.techfun.picturejump.bluetooth.BluetoothDeviceAdapter;
import jp.co.techfun.picturejump.bluetooth.BluetoothDeviceDescoveryReceiver;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

// 送信デバイス選択画面Activity
public class DeviceSelectActivity extends ListActivity {

    // デバイス一覧表示用アダプター
    private BluetoothDeviceAdapter adapter;

    // 検出デバイス取得用レシーバー
    private BluetoothDeviceDescoveryReceiver receiver;

    // onCreateメソッド(画面初期表示イベント)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_select);

        // デバイスリスト表示用アダプター生成
        adapter = new BluetoothDeviceAdapter(this);
        // 検出デバイス取得用レシーバー生成
        receiver = new BluetoothDeviceDescoveryReceiver(this, adapter);
        // 表示用アダプター設定
        setListAdapter(adapter);

        // 「デバイス検索」ボタンにリスナー設定
        Button button = (Button) findViewById(R.id.btn_search_device);
        button.setOnClickListener(new OnClickListener() {
            // onClickメソッド(クリックイベント)
            @Override
            public void onClick(View v) {
                // BluetoothAdapterオブジェクト取得
                BluetoothAdapter bluetoothAdapter =
                    BluetoothAdapter.getDefaultAdapter();

                // デバイス検索中の場合
                if (bluetoothAdapter.isDiscovering()) {
                    // 検索中止
                    bluetoothAdapter.cancelDiscovery();
                }
                // 新規デバイス検索開始
                bluetoothAdapter.startDiscovery();
            }
        });
    }

    // getBluetoothDeviceAdapterメソッド(デバイス一覧表示用アダプター取得）
    private BluetoothDeviceAdapter getBluetoothDeviceAdapter() {
        return (BluetoothDeviceAdapter) getListAdapter();
    }

    // onStartメソッド(画面表示イベント)
    @Override
    protected void onStart() {
        super.onStart();

        // BluetoothAdapterオブジェクト取得
        BluetoothAdapter bluetoothAdapter =
            BluetoothAdapter.getDefaultAdapter();

        // 既にペアリングされているデバイス取得
        for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
            getBluetoothDeviceAdapter().add(device);
        }
    }

    // onResumeメソッド(入力受付開始イベント)
    @Override
    protected void onResume() {
        super.onResume();
        // 検出デバイス取得用レシーバー登録
        receiver.register();
    }

    // onPauseメソッド(画面非表示イベント)
    @Override
    protected void onPause() {
        super.onPause();
        // デバイス取得用レシーバー削除
        receiver.unregister();
    }

    // onListItemClickメソッド(リスト選択時処理)
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // 選択されたデバイス取得
        BluetoothDevice device = adapter.getItem(position);
        // 選択されたデバイスをインテントに設定
        getIntent().putExtra(BluetoothDevice.EXTRA_DEVICE, device);
        setResult(RESULT_OK, getIntent());
        // 送信デバイス画面クローズ
        finish();
    }
}
