package example.android.graphics.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

// アニメーション画面アクティビティ
public class AnimationActivity extends Activity {
	// リピートフラグ
	private boolean repeat = false;
	// アニメーション描画用クラス
	private AnimationDrawable drawable;

	// 選択ボックスのイベントリスナー実装
	private OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
		// onItemSelectedメソッド(アイテム選択時イベント)
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// 選択されたアイテム取得
			Spinner spinner = (Spinner) parent;
			String item = (String) spinner.getSelectedItem();

			// 画像オブジェクト取得
			ImageView image = (ImageView) findViewById(R.id.img_andy);

			// 選択アイテムを判定して、該当アニメーションを設定
			Animation animation;
			if (item.equals("(無し)")) {
				animation = null;
			} else if (item.equals("回転")) {
				animation = AnimationUtils.loadAnimation(
						AnimationActivity.this, R.anim.animation_rotate_1);
			} else if (item.equals("拡大縮小")) {
				animation = AnimationUtils.loadAnimation(
						AnimationActivity.this, R.anim.animation_scale_1);
			} else if (item.equals("移動")) {
				animation = AnimationUtils.loadAnimation(
						AnimationActivity.this, R.anim.animation_translate_1);
			} else if (item.equals("全部！")) {
				animation = AnimationUtils.loadAnimation(
						AnimationActivity.this, R.anim.animation_multi_1);
			} else {
				animation = null;
			}

			// アニメーション起動
			if (animation == null) {
				image.clearAnimation();
			} else {
				image.startAnimation(animation);
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	// onCreateメソッド(画面初期表示イベント)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		// レイアウト設定ファイルを設定
		setContentView(R.layout.animationsample);

		// 選択ボックスにイベントリスナー設定
		Spinner spinner = (Spinner) findViewById(R.id.spn_anim_control);
		spinner.setOnItemSelectedListener(listener);

		// 画像を順次切り替え表示するアニメーションを描画オブジェクトに設定
		drawable = (AnimationDrawable) getResources().getDrawable(
				R.anim.animation_animation_1);
	}

	// onTouchEventメソッド(タッチイベント)
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		// タッチイベントがタッチ処理の場合
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// アニメーション描画オブジェクトをイメージビューに設定
			ImageView image = (ImageView) findViewById(R.id.img_andy);
			image.setImageDrawable(drawable);

			// アニメーション起動
			repeat = !repeat;
			if (repeat) {
				drawable.start();
			} else {
				drawable.stop();
			}
		}
		return result;
	}
}
