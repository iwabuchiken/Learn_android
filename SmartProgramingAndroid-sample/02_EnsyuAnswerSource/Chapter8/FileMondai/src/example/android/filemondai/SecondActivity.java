package example.android.filemondai;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {
	
	// ファイル名
    private static final String FILE_NAME = "FileMondai";
    
    // 入力データ
    String name = "";
    String address = "";
    String month = "";
    String day = "";
    String gendar = "";
    String apple = "";
    String orange = "";
    String peach = "";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondlayout);
        
        // インテントの付加情報取得
        Bundle extra = getIntent().getExtras();
        
        // 付加情報から入力データ取得
        name = extra.getString("NAME");
        address = extra.getString("ADDRESS");
        month = extra.getString("MONTH");
        day = extra.getString("DAY");
        gendar = extra.getString("GENDAR");
        apple = extra.getString("APPLE");
        orange = extra.getString("ORANGE");
        peach = extra.getString("PEACH");
        
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
        
        // 確認ボタンのクリックリスナー設定
        Button confirmbutton = (Button)findViewById(R.id.confirmbutton);
        confirmbutton.setTag("confirm");
        confirmbutton.setOnClickListener(new ButtonClickListener());
        // 戻るボタンのクリックリスナー設定
        Button backbutton = (Button)findViewById(R.id.backbutton);
        backbutton.setTag("back");
        backbutton.setOnClickListener(new ButtonClickListener());
    }
    
    // ボタンクリックリスナー定義
    class ButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        public void onClick(View v) { 
        	// タグの取得
    		String tag = (String)v.getTag();
    		
    		// 確認ボタンが押された場合
    		if(tag.equals("confirm")){
    			// ファイルに保存
    			try{
                    FileOutputStream stream = openFileOutput(FILE_NAME, MODE_APPEND);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(stream));
                    
                    out.write(name + "," + 
                    		  address + "," + 
                    		  month + "/" + day + "," + 
                    		  gendar + "," + 
                    		  apple + "," + 
                    		  orange + "," + 
                    		  peach);
                    out.newLine();
                    out.close();
                    
                }catch(Exception e){
                    Log.e("FILE_ERROR", "ファイル書き込みに失敗しました");
                }

                
            	// インテントの生成(呼び出すクラスの指定)
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                
                // 次のアクティビティの起動
                startActivity(intent);
    			
    		// 戻るボタンが押された場合
    		}else if(tag.endsWith("back")){   		
	            // 画面をクローズ
	            finish();
	    	}
        }
    }
}
