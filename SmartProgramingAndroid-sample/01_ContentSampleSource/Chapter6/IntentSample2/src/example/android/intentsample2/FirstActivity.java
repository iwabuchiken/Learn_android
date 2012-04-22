package example.android.intentsample2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends Activity {
	// リクエストコード定義
    private static final int SHOSW_CALC = 0;

    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.firstlayout);
        
        // ボタンオブジェクト取得
        Button button = (Button) findViewById(R.id.nextbutton);       
        // ボタンオブジェクトにクリックリスナー設定
        button.setOnClickListener(new ButtonClickListener());
    }
    
    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
            // インテントの生成(呼び出すクラスの指定)
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            
            // 次のアクティビティの起動
            startActivityForResult(intent, SHOSW_CALC);
        }

    }

    // onActivityResultメソッド(メイン画面再表示時イベントハンドラ)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SHOSW_CALC) {
            if (resultCode == RESULT_OK) {
                // インテントから付加情報取得
                Bundle extra = data.getExtras();
                // 選択されたあいさつ取得
                String selectedGreeting = extra.getString("SELECTED_GREETING");

                // テキストボックスの入力情報取得
                EditText input = (EditText) findViewById(R.id.nametext);

                // トースト機能で画面表示
                Toast.makeText(FirstActivity.this, 
                		        selectedGreeting+"\n"+input.getText()+"さん",
                		        Toast.LENGTH_SHORT).show();
            }
        }
    }
}