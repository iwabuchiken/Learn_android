package example.android.filesample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FileSampleActivity extends Activity {
    // ファイル名
    private static final String FILE_NAME = "FileSampleFile";   

    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.filesample);
        
        // 保存ボタンのクリックリスナー設定
        Button saveBtn = (Button)findViewById(R.id.save);
        saveBtn.setTag("save");
        saveBtn.setOnClickListener(new ButtonClickListener());
        // 表示ボタンのクリックリスナー設定
        Button readBtn = (Button)findViewById(R.id.display); 
        readBtn.setTag("display");
        readBtn.setOnClickListener(new ButtonClickListener());   
        // 削除ボタンのクリックリスナー設定
        Button delBtn = (Button)findViewById(R.id.delete);
        delBtn.setTag("delete");
        delBtn.setOnClickListener(new ButtonClickListener());  
    }
    
    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
    	// onClickメソッド(ボタンクリック時イベントハンドラ)
    	public void onClick(View v){
    		// タグの取得
    		String tag = (String)v.getTag();
    		
    		// メッセージ表示用
            String str  = "";
            TextView label = (TextView)findViewById(R.id.message); 
    		
    		// 保存ボタンが押された場合
    		if(tag.equals("save")){
    			// 入力情報取得
                EditText name = (EditText)findViewById(R.id.name);
                EditText score = (EditText)findViewById(R.id.score);
                
                // ファイルにデータ保存
                try{
                    FileOutputStream stream = openFileOutput(FILE_NAME, MODE_APPEND);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(stream));
                    
                    String hantei = "不合格";
                    if(Integer.parseInt(score.getText().toString()) >= 210){
                    	hantei = "合格";
                    }
                    
                    out.write(name.getText().toString() + "," + 
                    		  score.getText().toString() + "," + 
                    		  hantei);
                    out.newLine();
                    out.close();
                    str  = "保存しました！";
                }catch(Exception e){
                    str  = "データ保存に失敗しました！";
                }

    		
            // 表示ボタンが押された場合
    		}else if(tag.equals("display")){
                
                // ファイルからデータ取得
                try{
                    FileInputStream stream = openFileInput(FILE_NAME);
                    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                    
                    String line = "";
                    while((line = in.readLine())!=null){
                        str += line + "\n";
                    }
                    in.close();             
                }catch(Exception e){
                    str  = "データ取得に失敗しました！";
                }      

    			
    		// 削除ボタンが押された場合
    		}else if(tag.endsWith("delete")){
                
    			// ファイルのデータ削除
                try{
                    deleteFile(FILE_NAME);
                    str = "削除しました！";                                
                }catch(Exception e){
                    str  = "データ削除に失敗しました！";
                }
    		}            
            // メッセージ表示  
            label.setText(str);
    	}
    }
}