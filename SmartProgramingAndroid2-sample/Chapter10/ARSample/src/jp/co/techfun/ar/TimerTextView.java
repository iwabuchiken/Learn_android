package jp.co.techfun.ar;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

// Timer用カスタムTextViewクラス
public class TimerTextView extends TextView {

	// 時刻表示用日付フォーマット
	private static final SimpleDateFormat SDF = new SimpleDateFormat("m:s.SSS");

	// 警告カラー表示閾値
	private static final int WARN_TIME = 7000;

	// 危険カラー表示閾値
	private static final int DANGER_TIME = 3000;

	// タイマー稼働開始時時刻
	private long startTime;

	// 制限時間
	private long limitTime;

	// 残時間
	private long viewTime;

	// タイマー稼働状態(True:稼働, False:非稼働)
	private boolean isTimerEnabled;

	// コンストラクタ
	public TimerTextView(Context context, AttributeSet attSet) {
		super(context, attSet);

		// タイマー稼働状態を非稼働に設定
		isTimerEnabled = false;
	}

	// setTimerメソッド(制限時間の設定処理)
	public void setTimer(int limitTime) {
		this.limitTime = limitTime;
		viewTime = limitTime;
	}

	// startTimerメソッド(タイマー開始処理)
	public void startTimer() {
		// タイマー稼働状態を稼働に設定
		isTimerEnabled = true;

		// 現在時刻を設定
		startTime = System.currentTimeMillis();
	}

	// isTimerEnabledメソッド(タイマー稼働状態処理)
	public boolean isTimerEnabled() {
		return isTimerEnabled;
	}

	// onDrawメソッド(残時間表示処理)
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 残時間を表示
		this.setText(SDF.format(new Date(viewTime)));

		// タイマーが稼働状態の場合
		if (isTimerEnabled) {
			// 現在時刻を取得
			long currTime = System.currentTimeMillis();

			// 経過時間を取得
			long diffTime = currTime - startTime;

			// 残時間を設定
			viewTime = limitTime - diffTime;

			// 時間切れの場合
			if (viewTime <= 0) {
				viewTime = 0;
				isTimerEnabled = false;

				// 残時間に応じて背景色を設定
			} else if (viewTime < DANGER_TIME) {
				this.setBackgroundColor(Color.parseColor(getContext()
						.getString(R.color.danger)));
			} else if (viewTime < WARN_TIME) {
				this.setBackgroundColor(Color.parseColor(getContext()
						.getString(R.color.warn)));
			} else {
				this.setBackgroundColor(Color.parseColor(getContext()
						.getString(R.color.clear)));
			}

			// 再描画
			invalidate();
		}
	}
}
