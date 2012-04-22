package example.android.intentsample2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class SecondActivity extends Activity{
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.secondlayout);
        
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
            // インテント取得
            Intent intent = getIntent();
            
            // Spinnerオブジェクト取得
            Spinner spinner = (Spinner)findViewById(R.id.greeting);
            // 選択されたあいさつ取得
            String greeting = (String)spinner.getSelectedItem();
            
            // 入力用テキストオブジェクトの値をインテントに設定
            intent.putExtra("SELECTED_GREETING", greeting);
            
            // 結果情報の設定
            setResult(RESULT_OK, intent);        	
        	
        	// アクティビティ終了(画面クローズ)
        	finish();
        }
    }
}
