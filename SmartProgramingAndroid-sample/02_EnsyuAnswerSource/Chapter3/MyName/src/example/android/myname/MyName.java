package example.android.myname;

import android.app.Activity;
import android.os.Bundle;

public class MyName extends Activity {
	// 画面初期表示処理
	public void onCreate(Bundle savedInstanceState) {
		// スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		// レイアウト設定ファイルの設定
		setContentView(R.layout.myname);
	}
}