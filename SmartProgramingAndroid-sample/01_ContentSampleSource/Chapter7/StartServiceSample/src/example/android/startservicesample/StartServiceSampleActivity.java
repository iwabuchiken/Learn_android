package example.android.startservicesample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartServiceSampleActivity extends Activity {
	
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.startservicesample);

        // 開始ボタンにクリックリスナー設定
        Button startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartButtonClickListener());        
    }
    // 開始ボタンリスナークラス
    class StartButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
            // インテント生成
        	Intent intent = new Intent(StartServiceSampleActivity.this, 
            		                     StartServiceSampleService.class);
        	
        	// 終了時間をインテントに設定
        	EditText stopcount = (EditText)findViewById(R.id.stopcount);
        	intent.putExtra("STOPTIME",stopcount.getText().toString());
        	
        	// サービス開始
            startService(intent);
        }
    }
}