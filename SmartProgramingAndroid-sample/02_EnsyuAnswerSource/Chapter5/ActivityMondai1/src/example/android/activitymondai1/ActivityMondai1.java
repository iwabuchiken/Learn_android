package example.android.activitymondai1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityMondai1 extends Activity {
	// onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymondai1);
        
        // ボタンオブジェクト取得
        Button button=(Button)findViewById(R.id.button1);
        
        // ボタンオブジェクトにクリックリスナー設定
        button.setOnClickListener(new ButtonClickListener());
    }
    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベント)
        @Override
        public void onClick(View v) {
            // テキストボックスの入力情報取得
            EditText input = (EditText)findViewById(R.id.text_name);
            
            // 選択ボックスの入力情報取得
            Spinner spinner = (Spinner)findViewById(R.id.spinner1);
            String str = (String)spinner.getSelectedItem();
            
            // 入力情報をトースト機能で画面表示
            Toast.makeText(ActivityMondai1.this, str+"\n"+input.getText()+"さん",Toast.LENGTH_SHORT).show();
        }
    }
}