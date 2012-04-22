package example.android.implicitintentmondai2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ImplicitIntentMondai2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.main);
        
        // ボタンオブジェクト取得
        Button button1=(Button)findViewById(R.id.button1);        
        // ボタンオブジェクトにクリックリスナー設定
        button1.setOnClickListener(new ButtonClickListener()); 
    }
    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベント)
        public void onClick(View v) {
            // テキストボックスの入力情報取得
            EditText content = (EditText) findViewById(R.id.content);
            String forwardstr = content.getText().toString();
            
            // 選択リストの選択情報取得
            Spinner selectapli = (Spinner) findViewById(R.id.selectapli);
            String seleteditem = (String)selectapli.getSelectedItem();
            
            // アクションの設定
            String action = null;
            if(seleteditem.equals("電話")){
                action = "android.intent.action.DIAL";
                forwardstr = "tel:"+content.getText().toString();
            }else if(seleteditem.equals("WEBブラウザ")){
                action = "android.intent.action.VIEW";
            }else if(seleteditem.equals("地図")){
                action = "android.intent.action.VIEW";
                forwardstr = "geo:"+content.getText().toString();
            }else if(seleteditem.equals("メッセージ")){
            	action = "android.intent.action.VIEW";
            }
            
            // インテントの生成
            Intent intent = new Intent(action,Uri.parse(forwardstr));
            
            if(seleteditem.equals("メッセージ")){
            	intent.putExtra("sms_body",content.getText().toString()); 
            	intent.setType("vnd.android-dir/mms-sms"); 
            }
            
            // 次のアクティビティの起動
            startActivity(intent);
        }
    }
}