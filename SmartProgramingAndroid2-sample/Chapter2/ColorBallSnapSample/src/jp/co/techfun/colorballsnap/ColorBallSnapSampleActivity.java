package jp.co.techfun.colorballsnap;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

// ゲーム画面Activity
public class ColorBallSnapSampleActivity extends Activity {

    // SensorManagerインスタンス
    private SensorManager sensorManager;

    // DrawableViewインスタンス
    private DrawableView drawableView;

    // onCreateメソッド(画面初期表示イベント)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DrawableViewインスタンス生成
        drawableView = new DrawableView(this);

        // バックライトが自動消灯しないように設定
        drawableView.setKeepScreenOn(true);

        // 画面に表示するDrawableViewインスタンスを指定
        setContentView(drawableView);

        // SensorManagerインスタンス取得
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

    }

    // onResumeメソッド(画面表示前イベント)
    @Override
    protected void onResume() {
        super.onResume();

        // 加速度センサーオブジェクト取得
        List<Sensor> accelerometerSensors =
            sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        // 加速度センサーオブジェクトが取得できた場合
        if (accelerometerSensors.size() > 0) {
            // SensorManagerインスタンスにセンサーイベントリスナーを設定
            sensorManager.registerListener(sensorEventListener,
                accelerometerSensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    // onStopメソッド(処理終了時イベント)
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(sensorEventListener);
    }

    // センサーイベントリスナーの定義
    private final SensorEventListener sensorEventListener =
        new SensorEventListener() {
            // ローパスフィルタ用変数
            private float lowX;
            private float lowY;
            private float lowZ;

            // ローパスフィルタ対象範囲
            private static final float FILTERING_VALUE = 0.2f;

            // onAccuracyChangedメソッド(精度変更時イベント)
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // 処理なし
            }

            // onSensorChangedメソッド(センサー情報変更時イベント)
            @Override
            public void onSensorChanged(SensorEvent event) {
                // センサーが検知した値を取得
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];

                // ローパスフィルタ処理(高周波振動(手の震えや微妙な振動の影響)を除去)
                lowX = getLowPassFilterValue(x, lowX);
                lowY = getLowPassFilterValue(y, lowY);
                lowZ = getLowPassFilterValue(z, lowZ);

                switch (event.sensor.getType()) {

                // 検知されたセンサー情報が加速度センサーの場合
                case Sensor.TYPE_ACCELEROMETER:
                    drawableView.effectAccelaration(lowX, lowY, lowZ);

                    // 画面を再描画
                    drawableView.invalidate();
                    break;

                // 加速度センサー以外は無視
                default:
                    break;
                }
            }

            // ローパスフィルタ処理(高周波振動(手の震えや微妙な振動の影響)を除去)
            private float getLowPassFilterValue(float eventValue, float lowValue) {
                return eventValue * FILTERING_VALUE + lowValue
                    * (1.0f - FILTERING_VALUE);
            }
        };
}
