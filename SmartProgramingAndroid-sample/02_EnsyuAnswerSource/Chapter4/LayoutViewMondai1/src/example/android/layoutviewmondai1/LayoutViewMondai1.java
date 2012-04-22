package example.android.layoutviewmondai1;

import android.app.Activity;
import android.os.Bundle;

public class LayoutViewMondai1 extends Activity {
	// 画面初期表示処理
	public void onCreate(Bundle savedInstanceState) {
		// スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		// レイアウト設定ファイルの設定
        setContentView(R.layout.layoutviewmondai1);
    }
}