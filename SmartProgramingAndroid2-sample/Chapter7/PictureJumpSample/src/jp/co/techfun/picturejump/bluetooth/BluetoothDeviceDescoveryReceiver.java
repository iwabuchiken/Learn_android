package jp.co.techfun.picturejump.bluetooth;

import jp.co.techfun.picturejump.AppUtil;
import jp.co.techfun.picturejump.R;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;

// デバイス検索通知を受けるブロードキャストレシーバー
public class BluetoothDeviceDescoveryReceiver extends BroadcastReceiver {

    // 検出デバイス一覧表示用アダプター
    private BluetoothDeviceAdapter adapter;

    // 呼び出し元コンテキスト
    private Context context;

    // 進捗ダイアログ
    private ProgressDialog progressDialog;

    // コンストラクタ
    public BluetoothDeviceDescoveryReceiver(Context context,
        BluetoothDeviceAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    // onReceiveメソッド(デバイス検索通知イベント)
    @Override
    public void onReceive(Context context, Intent intent) {
        // インテントのアクション取得
        String action = intent.getAction();

        // デバイス検出開始の場合
        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            // 進捗ダイアログ表示
            showProgress();
        }

        // デバイス検出終了の場合
        if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            // 進捗ダイアログ非表示
            hideProgress();
        }

        // デバイス発見の場合
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // デバイスを表示用アダプターに設定
            addDevice(intent);
        }

        // デバイス名変更の場合
        if (BluetoothDevice.ACTION_NAME_CHANGED.equals(action)) {
            // デバイスを表示用アダプターに設定映
            addDevice(intent);
        }
    }

    // addDeviceメソッド(デバイスを表示用アダプターに設定処理)
    private void addDevice(Intent intent) {
        // デバイスオブジェクト取得
        BluetoothDevice device =
            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        // デバイス名が無効な場合
        if (device.getName() == null) {
            // 設定せずに終了
            return;
        }

        // デバイスが既にペアリングされている場合
        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            // 設定せずに終了
            return;
        }

        // デバイスが既に一覧に表示されている場合
        for (int i = 0; i < adapter.getCount(); i++) {
            if (device.equals(adapter.getItem(i))) {
                // 設定せずに終了
                return;
            }
        }

        // アダプターにデバイス設定
        adapter.add(device);
    }

    // registerメソッド(レシーバー登録処理)
    public void register() {
        // インテントフィルタ生成
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        // コンテキストにレシーバー設定
        context.registerReceiver(this, filter);
    }

    // unregisterメソッド(レシーバー削除処理)
    public void unregister() {
        // コンテキストからレシーバー削除
        if (context != null) {
            context.unregisterReceiver(this);
        }
    }

    // showProgressメソッド(進捗ダイアログ表示処理)
    private void showProgress() {
        // ダイアログ生成
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(AppUtil.getString(context,
            R.string.device_search_title));
        progressDialog.setMessage(AppUtil.getString(context,
            R.string.device_search_progress));
        progressDialog.setCancelable(true);

        // ダイアログにキャンセルリスナー設定
        progressDialog.setOnCancelListener(new OnCancelListener() {
            // onCancelメソッド(キャンセルイベント)
            @Override
            public void onCancel(DialogInterface dialog) {
                // BluetoothAdapterオブジェクト取得
                BluetoothAdapter bluetoothAdapter =
                    BluetoothAdapter.getDefaultAdapter();
                // 検索中止
                bluetoothAdapter.cancelDiscovery();
            }
        });
        // ダイアログ表示
        progressDialog.show();
    }

    // hideProgressメソッド(進捗ダイアログ非表示処理)
    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
