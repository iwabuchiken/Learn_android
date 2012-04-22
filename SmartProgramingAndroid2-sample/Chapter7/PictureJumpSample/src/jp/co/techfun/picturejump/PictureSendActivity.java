package jp.co.techfun.picturejump;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.co.techfun.picturejump.bluetooth.PictureSendJob;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

// 画像送信画面Activity
public class PictureSendActivity extends Activity {

	// ギャラリー表示用リクエストコード
	private static final int REQUEST_CODE_GALLARY = 0;

	// カメラ画面表示用リクエストコード
	private static final int REQUEST_CODE_CAMERA = 1;

	// デバイス検出画面表示用リクエストコード
	private static final int REQUEST_CODE_DEVICE = 2;

	// 送信デバイス
	private BluetoothDevice device;

	// 送信画像
	private Bitmap picture;

	// 非同期実行用スレッドプール
	private ExecutorService executorService;

	// 進捗ダイアログ
	private ProgressDialog progressDialog;

	// onCreateメソッド(画面初期表示イベント)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 非同期実行用のスレッドプール生成
		executorService = Executors.newCachedThreadPool();

		// レイアウト設定ファイル指定
		setContentView(R.layout.picture_send);

		// 「送信先選択」ボタンにリスナー設定
		ImageButton ibtnSelectDevice = (ImageButton) findViewById(R.id.ibtn_select_device);
		ibtnSelectDevice.setOnClickListener(new OnClickListener() {
			// onClickメソッド(クリックイベント)
			@Override
			public void onClick(View v) {
				// 送信デバイス選択画面呼び出し
				Intent intent = new Intent(PictureSendActivity.this,
						DeviceSelectActivity.class);
				startActivityForResult(intent, REQUEST_CODE_DEVICE);
			}
		});

		// 「ギャラリー」ボタンにリスナー設定
		ImageButton ibtnGallery = (ImageButton) findViewById(R.id.ibtn_gallery);
		ibtnGallery.setOnClickListener(new OnClickListener() {
			// onClickメソッド(クリックイベント)
			@Override
			public void onClick(View v) {
				// インテント生成
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				// インテントタイプを画像で設定
				intent.setType("image/*");
				// ギャラリー起動
				startActivityForResult(Intent.createChooser(intent, AppUtil
						.getString(PictureSendActivity.this,
								R.string.picture_select_title)),
						REQUEST_CODE_GALLARY);
			}
		});

		// 「カメラ」ボタンにリスナー設定
		ImageButton ibtnCamera = (ImageButton) findViewById(R.id.ibtn_camera);
		ibtnCamera.setOnClickListener(new OnClickListener() {
			// onClickメソッド(クリックイベント)
			@Override
			public void onClick(View v) {
				// インテント生成
				Intent intent = new Intent(PictureSendActivity.this,
						CameraActivity.class);
				// カメラ画面起動
				startActivityForResult(intent, REQUEST_CODE_CAMERA);
			}
		});

		// 「回転」ボタンにリスナー設定
		ImageButton ibtnPictureRotate = (ImageButton) findViewById(R.id.ibtn_picture_rotate);
		ibtnPictureRotate.setOnClickListener(new OnClickListener() {
			// onClickメソッド(クリックイベント)
			@Override
			public void onClick(View v) {
				// 画像回転処理
				setPicture(AppUtil.rotateBitmap(picture));
			}
		});

		// 「クリア」ボタンにリスナー設定
		Button btnClear = (Button) findViewById(R.id.btn_clear);
		btnClear.setOnClickListener(new OnClickListener() {
			// onClickメソッド(クリックイベント)
			@Override
			public void onClick(View v) {
				// デバイス、画像表示をクリア
				setDevice(null);
				setPicture(null);
			}
		});

		// 「送信」ボタンにリスナー設定
		Button btnSend = (Button) findViewById(R.id.btn_send);
		btnSend.setOnClickListener(new OnClickListener() {
			// onClickメソッド(クリックイベント)
			@Override
			public void onClick(View v) {
				// 画像送信処理
				send();
			}
		});

		// ボタンの使用可能状態を更新
		updateButtonStatus();
	}

	// onDestroyメソッド(画面破棄イベント)
	@Override
	protected void onDestroy() {
		// スレッドプール終了
		executorService.shutdownNow();
		super.onDestroy();
	}

	// sendメソッド(画像送信処理)
	private void send() {

		// 進捗ダイアログを表示
		showProgress();

		// 画像送信処理を別スレッドで実行
		executorService.execute(new PictureSendJob(device, picture) {

			// handleSendStartedメソッド(画像送信開始イベント)
			@Override
			protected void handleSendStarted(BluetoothDevice device) {
				// UIスレッド実行
				runOnUiThread(new Runnable() {
					// runメソッド(実行処理)
					@Override
					public void run() {
						// 送信開始時に進捗メッセージ変更
						updateProgressMessage(AppUtil.getString(
								PictureSendActivity.this,
								R.string.picture_send_progress_sending));
					}
				});
			}

			// handleSendFinishメソッド(画像送信終了イベント)
			@Override
			protected void handleSendFinish() {
				// UIスレッド実行
				runOnUiThread(new Runnable() {
					// runメソッド(実行処理)
					@Override
					public void run() {
						// 進捗ダイアログ非表示
						hideProgress();
						// メッセージ表示
						AppUtil.showToast(PictureSendActivity.this,
								R.string.picture_send_succeed);
					}
				});
			}

			// handleProgressメソッド(画像送信進捗変更イベント)
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

			// handleExceptionメソッド(画像送信例外発生イベント)
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
						AppUtil.showToast(PictureSendActivity.this,
								R.string.picture_send_failed);
					}
				});

			}
		});
	}

	// onActivityResultメソッド(画面再表示時イベント)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 処理結果がOKの場合、処理終了
		if (resultCode != RESULT_OK) {
			return;
		}

		// デバイス検出の結果の場合
		if (requestCode == REQUEST_CODE_DEVICE) {
			// 検出したデバイス取得
			device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			setDevice(device);

			// ギャラリー画像取得結果の場合
		} else if (requestCode == REQUEST_CODE_GALLARY) {

			try {
				// 選択画像のURIから画像ストリーム取得
				InputStream in = getContentResolver().openInputStream(
						data.getData());
				// 画像表示
				setPicture(BitmapFactory.decodeStream(in));
			} catch (FileNotFoundException e) {
				Log.e(getClass().getSimpleName(), "get image failed.", e);
			}

			// カメラ画像取得結果の場合
		} else if (requestCode == REQUEST_CODE_CAMERA) {
			// 一時保存画像表示
			setPicture(BitmapFactory.decodeFile(AppUtil.getPictureTempPath()));
			// 一時保存画像削除
			AppUtil.deletePicture();
		}
	}

	// setDeviceメソッド(デバイス表示処理)
	private void setDevice(BluetoothDevice device) {
		this.device = device;
		TextView textView = (TextView) findViewById(R.id.tv_selected_device);
		textView.setText(device != null ? device.getName() : "");

		updateButtonStatus();
	}

	// setPictureメソッド(画像表示処理)
	private void setPicture(Bitmap picture) {
		this.picture = picture;
		ImageView imageView = (ImageView) findViewById(R.id.iv_selected_picture);
		imageView.setImageBitmap(AppUtil.resizePicture(picture, 180, 180));

		updateButtonStatus();
	}

	// updateButtonStatusメソッド(ボタンの使用可能状態更新処理)
	private void updateButtonStatus() {

		View send = findViewById(R.id.btn_send);
		send.setEnabled(picture != null && device != null);

		View rotate = findViewById(R.id.ibtn_picture_rotate);
		rotate.setEnabled(picture != null);
	}

	// showProgressメソッド(進捗ダイアログ表示処理)
	private void showProgress() {
		// 進捗ダイアログ生成
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle(AppUtil.getString(this,
				R.string.picture_send_progress_title));
		progressDialog.setMessage(AppUtil.getString(this,
				R.string.picture_send_progress_waiting));
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setMax(100);
		progressDialog.setCancelable(false);
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

	// updateProgressMessageメソッド(進捗メッセージ更新処理)
	private void updateProgressMessage(String message) {
		if (progressDialog != null) {
			progressDialog.setMessage(message);
		}
	}

	// updateProgressValueメソッド(進捗値を更新処理)
	private void updateProgressValue(int value) {
		if (progressDialog != null) {
			progressDialog.setProgress(value);
		}
	}
}
