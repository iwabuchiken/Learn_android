package example.android.dbmondai;

import android.app.Activity;
import android.os.Bundle;

public class ThirdActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.thirdlayout); 
    }
}
