package example.android.graphics.canvas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

// キャンバス画面アクティビティ
public class CanvasActivity extends Activity {
	// キャンバスクリアボタンのクリックリスナー定義
	private OnClickListener onClickListener = new View.OnClickListener() {
		public void onClick(View arg0) {
			CanvasView view = (CanvasView) findViewById(R.id.cv_canvas);
			view.clearDrawList();
		}
	};

	// onCreateメソッド(画面初期表示イベント)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		// 描画領域を広げるためにタイトル非表示設定
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// レイアウト設定ファイルを設定
		setContentView(R.layout.canvassample);

		// キャンバスクリアボタンオブジェクト生成
		Button btnClear = (Button) findViewById(R.id.btn_clear);
		btnClear.setOnClickListener(onClickListener);
	}
}
