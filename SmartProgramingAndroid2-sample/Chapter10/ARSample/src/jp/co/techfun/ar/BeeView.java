package jp.co.techfun.ar;

import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.View;

// 蜂表示用カスタムViewクラス
public class BeeView extends View implements SensorEventListener {

	// 乱数生成用
	private static final Random RDM = new Random();

	// カメラ視野角
	private static final int VIEW_ANGLE = 45;

	// 表示距離(単位:10cm)
	private static final int VIEW_DISTANCE = 30;

	// 画面更新間隔(単位:ミリ秒)
	private static final int REPEAT_INTERVAL = 100;

	// 端末の方位角(センサー取得値)
	private float sensorAzimuth;

	// 端末の回転角(センサー取得値)
	private float sensorRoll;

	// 端末の加速度(センサー取得値)
	// 画面に対して垂直軸の値
	private float sensorAccelZ;

	// カメラの向き(True:上向き, False:下向き)
	private boolean isUpward;

	// 蜂が端末に到着する所要時間
	private int arrivalTime;

	// 端末から見た蜂の位置
	// 緯度方向
	private double beeLat;
	// 経度方向
	private double beeLong;
	// 蜂の方位角
	private float beeAzimuth;

	// カメラ表示方向と蜂の角度(水平方向)
	private float beeAngleX;

	// カメラ表示方向と蜂の角度(垂直方向)
	private float beeAngleY;

	// 蜂表示/非表示フラグ
	private boolean isBeeVisible;

	// 蜂画像
	private BitmapDrawable beeImg;

	// 蜂移動距離
	// 緯度方向
	private double latStep;
	// 経度方向
	private double longStep;

	// 蜂撃退有効範囲
	private Rect beeTouchableArea;

	// Handler
	private final Handler handler;

	// コンストラクタ
	public BeeView(Context context) {
		super(context);

		// Handerインスタンス生成
		handler = new Handler();

		// センサーマネージャー取得
		SensorManager sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);

		// センサーリスナー登録
		// 傾きセンサー
		sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);

		// 加速度センサー
		sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);

		// 蜂画像を設定
		beeImg = new BitmapDrawable(BitmapFactory.decodeResource(
				getResources(), R.drawable.bee));

		// アンチエイリアスを有効
		beeImg.setAntiAlias(true);
	}

	// init(蜂の位置の初期化処理)
	private void init() {

		// 蜂の初期位置(端末からの距離)
		beeLat = (RDM.nextInt(VIEW_DISTANCE) + 1)
				* (RDM.nextBoolean() ? 1 : -1);
		beeLong = (RDM.nextInt(VIEW_DISTANCE) + 1)
				* (RDM.nextBoolean() ? 1 : -1);

		// １回の画面更新で移動する距離算定
		latStep = -beeLat / (arrivalTime / REPEAT_INTERVAL);
		longStep = -beeLong / (arrivalTime / REPEAT_INTERVAL);

		// 蜂の初期位置から方位角算定
		if (0 <= beeLat && 0 <= beeLong) {
			// 北東方向
			beeAzimuth = 90 - (int) (Math.atan2(beeLat, beeLong) * 180 / Math.PI);
		} else if (0 <= beeLat && beeLong < 0) {
			// 北西方向
			beeAzimuth = 270 + (int) (Math.atan2(beeLat, -beeLong) * 180 / Math.PI);
		} else if (beeLat < 0 && beeLong < 0) {
			// 南西方向
			beeAzimuth = 270 - (int) (Math.atan2(-beeLat, -beeLong) * 180 / Math.PI);
		} else {
			// 南東方向
			beeAzimuth = 90 + (int) (Math.atan2(-beeLat, beeLong) * 180 / Math.PI);
		}
	}

	// raidStartメソッド(襲撃開始処理)
	public void raidStart(int arrivalTime) {

		// 蜂が端末に到着する時間を設定
		this.arrivalTime = arrivalTime;

		// 蜂位置初期化
		init();

		// 一定時間後に実行
		handler.removeCallbacks(drawBeeTh);
		handler.postDelayed(drawBeeTh, REPEAT_INTERVAL);
	}

	// raidReset(リセット処理)
	public void raidReset(int arrivalTime) {

		// 蜂が端末に到着する時間を設定
		this.arrivalTime = arrivalTime;

		// 蜂位置初期化
		init();

		// 蜂の表示状態を更新
		updateBeeVisible();
	}

	// isBeeTouchedメソッド(蜂がタッチされたかの判定処理)
	public boolean isBeeTouched(int x, int y) {
		if (isBeeVisible) {
			return beeTouchableArea.contains(x, y);
		} else {
			return false;
		}
	}

	// updateBeeVisibleメソッド(蜂の表示状態の更新処理)
	private void updateBeeVisible() {

		// 方位角を基準にカメラ表示方向から蜂の位置までの角度を求める

		// カメラ表示方向と蜂の位置が北を跨いでいる場合
		// 角度の範囲を合わせて差を求める
		// 方位角センサー値：北東南西の順に0°<= x < 360°の範囲で変化
		if ((360 - VIEW_ANGLE) < sensorAzimuth && beeAzimuth < VIEW_ANGLE) {
			// カメラ表示方向が北西寄り、
			// 且つ、蜂の位置が北東寄りの場合

			beeAngleX = (beeAzimuth + 360) - sensorAzimuth;

		} else if ((360 - VIEW_ANGLE) < beeAzimuth
				&& sensorAzimuth < VIEW_ANGLE) {
			// カメラ表示方向が北東寄り、
			// 且つ、蜂の位置が北西寄りの場合

			beeAngleX = (beeAzimuth - 360) - sensorAzimuth;

		} else {
			beeAngleX = beeAzimuth - sensorAzimuth;
		}

		// 端末を垂直に立てた状態で0°となるよう回転角を調整
		beeAngleY = 90 - Math.abs(sensorRoll);

		// 蜂がカメラの視野角内の場合
		if (Math.abs(beeAngleX) < VIEW_ANGLE && beeAngleY < VIEW_ANGLE) {
			// 蜂を表示
			isBeeVisible = true;
		} else {
			// 蜂を非表示
			isBeeVisible = false;
		}
	}

	// onAccuracyChangedメソッド(精度変更時イベント)
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// 処理なし
	}

	// onSensorChangedメソッド(センサー情報変更時イベント)
	@Override
	public void onSensorChanged(SensorEvent e) {
		switch (e.sensor.getType()) {

		// 傾きセンサーイベントの場合
		case Sensor.TYPE_ORIENTATION:
			// センサーで検知した値取得
			// 方位角
			float eventAzimuth = e.values[SensorManager.DATA_X];

			// 回転角
			float eventRoll = e.values[SensorManager.DATA_Z];

			// 変化量を計算
			float diffAzimuth = Math.abs(sensorAzimuth - eventAzimuth);
			float diffRoll = Math.abs(sensorRoll - eventRoll);

			// 方位角：0.5°以下の変化は無視
			if (diffAzimuth > 0.5) {
				sensorAzimuth = eventAzimuth;

			}
			// 傾き：1°以下の変化は無視
			if (diffRoll > 1) {
				sensorRoll = eventRoll;
			}

			// 蜂の表示状態を更新
			updateBeeVisible();

			break;

		// 加速度センサーイベントの場合
		case Sensor.TYPE_ACCELEROMETER:
			// センサーで検知した値取得
			float eventAccelZ = e.values[SensorManager.DATA_Z];

			// 変化量を計算
			float diffAccelZ = Math.abs(sensorAccelZ - eventAccelZ);

			// 加速度：0.5以下の変化は無視
			if (diffAccelZ > 0.5) {
				sensorAccelZ = eventAccelZ;
				// 値が正の場合
				if (sensorAccelZ >= 0) {
					// 上向き(True)を設定
					isUpward = true;
				} else {
					// 下向き(False)を設定
					isUpward = false;
				}
			}
			break;

		// その他センサー
		default:
			// 処理なし
			break;
		}
	}

	// onDrawメソッド(画面描画処理)
	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		// 蜂が非表示の場合
		if (!isBeeVisible) {
			// 何も描かない
			return;
		}

		// 蜂の接近率を算出
		double distLat = Math.abs(beeLat) / VIEW_DISTANCE;
		double distLong = Math.abs(beeLong) / VIEW_DISTANCE;
		double distRate = 1.0 - ((distLat + distLong) / 2.0);

		// 蜂画像と画面解像度の比率を算出
		double windowRate = c.getHeight() * 0.75
				/ beeImg.getBitmap().getHeight();

		// 距離から画像の大きさを決定
		int bmpWidth = (int) (beeImg.getBitmap().getWidth() * distRate * windowRate);
		int bmpHeight = (int) (beeImg.getBitmap().getHeight() * distRate * windowRate);

		// カメラ表示方向と蜂の方位角の差をX座標に変換
		double centerGapRateX = beeAngleX / VIEW_ANGLE;
		double centerGapX = c.getWidth() / 2.0 * centerGapRateX;
		int x = (int) (c.getWidth() / 2 + centerGapX);
		x = x - bmpWidth / 2;

		// 端末の回転角をY座標に変換
		double centerGapRateY = beeAngleY / VIEW_ANGLE
				* (isUpward ? -1.0 : 1.0);
		double centerGapY = c.getHeight() / 2.0 * centerGapRateY;
		int y = (int) (c.getHeight() / 2 + centerGapY);
		y = y - bmpHeight / 2;

		// Bitmap画像サイズ設定(兼タッチ有効範囲)
		beeTouchableArea = new Rect(x, y, bmpWidth + x, bmpHeight + y);
		beeImg.setBounds(beeTouchableArea);

		// 蜂の透過率設定
		beeImg.setAlpha((int) (255 * distRate));

		// 表示
		beeImg.draw(c);
	}

	// 一定間隔で再表示を行うためのスレッド
	private final Runnable drawBeeTh = new Runnable() {
		@Override
		public void run() {
			// 描画処理
			invalidate();

			// 蜂を移動
			// 緯度方向
			if (Math.abs(beeLat) > Math.abs(latStep)) {
				beeLat += latStep;
			} else {
				beeLat = 0;
			}
			// 経度方向
			if (Math.abs(beeLong) > Math.abs(longStep)) {
				beeLong += longStep;
			} else {
				beeLong = 0;
			}

			// 一定時間後に実行
			handler.postDelayed(drawBeeTh, REPEAT_INTERVAL);
		}
	};
}
