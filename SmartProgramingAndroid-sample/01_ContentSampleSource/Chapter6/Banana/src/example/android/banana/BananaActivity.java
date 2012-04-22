package example.android.banana;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BananaActivity extends Activity {
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.bananalayout);
        
        // URI取得
        Uri uri = getIntent().getData();
        if(uri!=null){
            // URIのQueryString情報取得
            String fruitname = uri.getQueryParameter("selecteditem");
            // TextViewオブジェクト取得
            TextView furittext = (TextView)findViewById(R.id.fruitname);
            // 果物の名前表示
            furittext.setText(fruitname);
        } 
        
        // Buttonオブジェクト取得
        Button button = (Button)findViewById(R.id.backbutton);
        // ボタンオブジェクトにクリックリスナー設定
        button.setOnClickListener(new ButtonClickListener());
    }
    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        public void onClick(View v) {
        	// アクティビティ終了(画面クローズ)
        	finish();
        }
    }
}