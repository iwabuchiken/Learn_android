package example.android.intentmondai1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class FirstActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.firstlayout);
        
        // ボタンオブジェクト取得
        Button button1=(Button)findViewById(R.id.nextbutton);        
        // ボタンオブジェクトにクリックリスナー設定
        button1.setOnClickListener(new ButtonClickListener()); 
    }
    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
            // 名前取得
            EditText name = (EditText)findViewById(R.id.name);
            
            // 住所取得
            EditText address = (EditText)findViewById(R.id.address);
            
            // 生年月日取得
            Spinner month = (Spinner)findViewById(R.id.month);
            Spinner day = (Spinner)findViewById(R.id.day);
            
            // 性別取得
            RadioGroup radio = (RadioGroup)findViewById(R.id.gendar);
            RadioButton radiobutton = (RadioButton)findViewById(radio.getCheckedRadioButtonId());
            
            // 希望商品取得
            CheckBox applecheck = (CheckBox)findViewById(R.id.applecheck);
            CheckBox orangecheck = (CheckBox)findViewById(R.id.orangecheck);
            CheckBox peachcheck = (CheckBox)findViewById(R.id.peachcheck);
            
            // 注文数量取得
            EditText appleqty = (EditText)findViewById(R.id.appleqty); 
            EditText orangeqty = (EditText)findViewById(R.id.orangeqty); 
            EditText peachqty = (EditText)findViewById(R.id.peachqty); 
            
            // インテントの生成(呼び出すクラスの指定)
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            
            // 入力データをインテントに設定 
            intent.putExtra("NAME", name.getText().toString());
            intent.putExtra("ADDRESS", address.getText().toString());
            intent.putExtra("MONTH", month.getSelectedItem().toString());
            intent.putExtra("DAY", day.getSelectedItem().toString());
            intent.putExtra("GENDAR", radiobutton.getText().toString());
            if(applecheck.isChecked())intent.putExtra("APPLE", appleqty.getText().toString());
            if(orangecheck.isChecked())intent.putExtra("ORANGE", orangeqty.getText().toString());
            if(peachcheck.isChecked())intent.putExtra("PEACH", peachqty.getText().toString());
            
            // 次のアクティビティの起動
            startActivity(intent);
        }

    }    
}