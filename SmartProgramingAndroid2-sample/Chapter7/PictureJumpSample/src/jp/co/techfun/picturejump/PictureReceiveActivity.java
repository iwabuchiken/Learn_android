package jp.co.techfun.picturejump;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.co.techfun.picturejump.bluetooth.BluetoothConstants;
import jp.co.techfun.picturejump.bluetooth.PictureReceiveJob;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

// 画像受信画面Activity
public class PictureReceiveActivity extends Activity {

    // Bluetoothデバイス検出可能リクエストコード
    private static final int REQUEST_CODE_BLUETOOTH_DISCOVERABLE = 1;

    // 非同期実行用スレッドプール
    private ExecutorService executorService;

    // 画像受信処理
    private PictureReceiveJob receiveJob;

    // 受信画像
    private Bitmap picture;

    // 進捗ダイアログ
    private ProgressDialog progressDialog;

    // onCreateメソッド(画面初期表示イベント)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 非同期実行用のスレッドプールを作成
        executorService = Executors.newCachedThreadPool();

        // レイアウト設定ファイル指定
        setContentView(R.layout.picture_receive);

        // 「再受信」ボタンにリスナー設定
        Button btnReset = (Button) findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new OnClickListener() {
            // onClickメソッド(クリックイベント)
            @Override
            public void onClick(View v) {
                // デバイス、画像表示をクリア
                setDevice(null);
                setPicture(null);
                // 受信開始処理呼び出し
                startReceiving();
            }
        });

        // 「保存」ボタンにリスナー設定
        Button btnStore = (Button) findViewById(R.id.btn_store);
        btnStore.setOnClickListener(new OnClickListener() {
            // onClickメソッド(クリックイベント)
            @Override
            public void onClick(View v) {
                // 画像保存処理呼び出し
                storePicture();
            }
        });
    }

    // onStartメソッド(画面表示イベント)
    @Override
    protected void onStart() {
        super.onStart();

        // デバイス検出可能要求用のインテントを作成
        Intent intent =
            new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        // 検出可能期間として300秒を指定
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
            BluetoothConstants.DURATION);
        // デバイス検出を可能にするために要求
        startActivityForResult(intent, REQUEST_CODE_BLUETOOTH_DISCOVERABLE);
    }

    // onDestroyメソッド(画面破棄イベント)
    @Override
    protected void onDestroy() {
        // 受信処理停止
        if (receiveJob != null) {
            receiveJob.stop();
        }
        // スレッドプール終了
        executorService.shutdownNow();
        super.onDestroy();
    }

    // onActivityResultメソッド(画面再表示時イベント)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // デバイス検出を可能する要求処理から戻ってきた場合
        if (requestCode == REQUEST_CODE_BLUETOOTH_DISCOVERABLE) {

            // 検出可能をユーザーが選択しなかった場合了
            if (resultCode == RESULT_CANCELED) {
                // トーストでメッセージ表示
                AppUtil
                    .showToast(this, R.string.bluetooth_can_not_discoverable);
                // アクティビティ終了
                finish();
                return;
            }

            // 画像受信処理を開始
            startReceiving();
        }
    }

    // startReceivingメソッド(画像受信処理)
    private void startReceiving() {

        // 進捗ダイアログ表示
        showProgress();

        // 画像受信処理を別スレッドで実行
        receiveJob = new PictureReceiveJob() {

            // handleReceiveStartedメソッド(画像受信開始イベント)
            @Override
            protected void handleReceiveStarted(BluetoothDevice device) {
                // UIスレッド実行
                runOnUiThread(new Runnable() {
                    // runメソッド(実行処理)
                    @Override
                    public void run() {
                        // ダイアログのキャンセル不可設定
                        progressDialog.setCancelable(false);
                        // 進捗メッセージ更新
                        updateProgressMessage(AppUtil.getString(
                            PictureReceiveActivity.this,
                            R.string.picture_receive_progress_receiving));
                    }
                });
            }

            // handleReceiveFinishedメソッド(画像受信終了イベント)
            @Override
            protected void handleReceiveFinished(
                final BluetoothDevice remoteDevice, final Bitmap picture) {
                // UIスレッド実行
                runOnUiThread(new Runnable() {
                    // runメソッド(実行処理)
                    @Override
                    public void run() {

                        // 送信元デバイスと受信画像を表示
                        setDevice(remoteDevice);
                        setPicture(picture);

                        // 進捗ダイアログ非表示設定
                        hideProgress();
                        // メッセージ表示
                        AppUtil.showToast(PictureReceiveActivity.this,
                            R.string.picture_receive_succeed);
                    }
                });
            }

            // handleProgressメソッド(画像受信進捗変更イベント)
            @Override
            protected void handleProgress(final int total, final int progress) {
                // UIスレッド実行
                runOnUiThread(new Runnable() {
                    // runメソッド(実行処理)
                    @Override
                    public void run() {
                        // 進捗表示切り替え
                        int value = Math.round(progress * 100 / total);
                        updateProgressValue(value);
                    }
                });
            }

            // handleExceptionメソッド(画像受信例外発生イベント)
            @Override
            protected void handleException(IOException e) {
                // UIスレッド実行
                runOnUiThread(new Runnable() {
                    // runメソッド(実行処理)
                    @Override
                    public void run() {
                        // 進捗ダイアログ非表示
                        hideProgress();
                        // エラーメッセージ表示
                        AppUtil.showToast(PictureReceiveActivity.this,
                            R.string.picture_receive_failed);
                    }
                });
            }
        };
        // 受信処理開始
        executorService.submit(receiveJob);
    }

    // setDeviceメソッド(デバイス名表示処理)
    private void setDevice(BluetoothDevice device) {
        TextView textView = (TextView) findViewById(R.id.tv_received_device);
        textView.setText(device != null ? device.getName() : "");
    }

    // setPictureメソッド(画像表示処理)
    private void setPicture(Bitmap picture) {
        this.picture = picture;
        ImageView imageView =
            (ImageView) findViewById(R.id.iv_received_picture);
        imageView.setImageBitmap(AppUtil.resizePicture(picture, 180, 180));
    }

    // storePictureメソッド(画像保存処理)
    private void storePicture() {

        if (picture != null) {

            try {

                // SDカードのルートディレクトリ取得
                File dir = Environment.getExternalStorageDirectory();
                File baseDir = new File(dir, "picture_jump");
                baseDir.mkdirs();

                // 日付形式のファイル名で画像をSDカードに保存
                SimpleDateFormat format =
                    new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

                FileOutputStream out =
                    new FileOutputStream(new File(baseDir, format
                        .format(new Date())
                        + ".png"));
                picture.compress(CompressFormat.PNG, 100, out);

                // メッセージ表示
                AppUtil.showToast(this, AppUtil.getString(this,
                    R.string.picture_save_succeed));

            } catch (FileNotFoundException e) {
                Log.e(getClass().getSimpleName(), "picture store failed.", e);
                AppUtil.showToast(this, AppUtil.getString(this,
                    R.string.picture_save_failed));
            }
        }

    }

    // showProgressメソッド(進捗ダイアログ表示処理)
    private void showProgress() {
        // ダイアログ生成
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(AppUtil.getString(this,
            R.string.picture_receive_progress_title));
        progressDialog.setMessage(AppUtil.getString(this,
            R.string.picture_receive_progress_waiting));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);

        // ダイアログにキャンセルリスナー設定
        progressDialog.setOnCancelListener(new OnCancelListener() {
            // onCancelメソッド(キャンセル時イベント)
            @Override
            public void onCancel(DialogInterface dialog) {
                // 処理終了
                finish();
            }
        });

        progressDialog.show();
    }

    // hideProgressメソッド(進捗ダイアログ非表示処理)
    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    // updateProgressMessageメソッド(進捗メッセージ更新処理)
    private void updateProgressMessage(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
        }
    }

    // updateProgressValueメソッド(進捗値更新処理)
    private void updateProgressValue(int value) {
        if (progressDialog != null) {
            progressDialog.setProgress(value);
        }
    }
}
