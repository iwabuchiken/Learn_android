package example.android.intentmondai1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondlayout);
        
        // インテントの付加情報取得
        Bundle extra = getIntent().getExtras();
        
        // 付加情報から入力データ取得
        String name = extra.getString("NAME");
        String address = extra.getString("ADDRESS");
        String month = extra.getString("MONTH");
        String day = extra.getString("DAY");
        String gendar = extra.getString("GENDAR");
        String apple = extra.getString("APPLE");
        String orange = extra.getString("ORANGE");
        String peach = extra.getString("PEACH");
        
        // 出力用テキストオブジェクト取得
        TextView inputName = (TextView)findViewById(R.id.name);
        TextView inputAddress = (TextView)findViewById(R.id.address);
        TextView inputMonth = (TextView)findViewById(R.id.month);
        TextView inputDay = (TextView)findViewById(R.id.day);
        TextView inputGendar = (TextView)findViewById(R.id.gender);
        TextView inputApple = (TextView)findViewById(R.id.apple);
        TextView inputOrange = (TextView)findViewById(R.id.orange);
        TextView inputPeach = (TextView)findViewById(R.id.peach);
        
        // 出力用テキストオブジェクに入力データ設定
        inputName.setText(name);
        inputAddress.setText(address);
        inputMonth.setText(month);
        inputDay.setText(day);
        inputGendar.setText(gendar);
        if(apple != null)inputApple.setText(apple);
        if(orange != null)inputOrange.setText(orange);
        if(peach != null)inputPeach.setText(peach);
        
        // ボタンオブジェクト取得
        Button button=(Button)findViewById(R.id.backbutton);
        
        // ボタンオブジェクトにクリックリスナー設定
        button.setOnClickListener(new ButtonBackClickListener());
        

    }
    // 戻るボタンクリックリスナー定義
    class ButtonBackClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        public void onClick(View v) {           
            // 画面をクローズ
            finish();
        }

    }
}
