package example.android.buttonclicksample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ButtonClickSample extends Activity {
	// onCreateメソッド(画面初期表示イベントハンドラ)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		// レイアウト設定ファイルの指定
		setContentView(R.layout.buttonclicksample);
		// ボタンオブジェクト取得
		Button button = (Button) findViewById(R.id.button1);
		// ボタンオブジェクトにクリックリスナー設定
		button.setOnClickListener(new ButtonClickListener());
	}

	// クリックリスナー定義
	class ButtonClickListener implements OnClickListener {
		// onClickメソッド(ボタンクリック時イベントハンドラ)
		public void onClick(View v) {
			// テキストボックスオブジェクト取得
			EditText input = (EditText) findViewById(R.id.nametext);
			// 入力情報をトースト機能で画面表示
			Toast.makeText(ButtonClickSample.this, input.getText(),
					Toast.LENGTH_SHORT).show();
		}
	}
}