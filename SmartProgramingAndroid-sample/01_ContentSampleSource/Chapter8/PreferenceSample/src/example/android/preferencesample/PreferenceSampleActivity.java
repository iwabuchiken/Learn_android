package example.android.preferencesample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class PreferenceSampleActivity extends Activity {
    
    // プリファレンスファイル名
    private static final String FILE_NAME = "PreferenceSampleFile";   

    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.preferencesample);
        
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
    		
    		// プリファレンスオブジェクト取得
    		SharedPreferences preference = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
    		
    		// TextView取得
    		TextView message = (TextView)findViewById(R.id.message);
    		
    		// 保存ボタンが押された場合
    		if(tag.equals("save")){
    			// プリファレンスの編集用オブジェクト取得
                SharedPreferences.Editor editor = preference.edit();
                
                // 選択されたフォント取得
                Spinner fontarray = (Spinner)findViewById(R.id.font);
                String font = (String)fontarray.getSelectedItem();
                
                // 選択されたスタイル取得
                CheckBox italic = (CheckBox)findViewById(R.id.italic);
                CheckBox bold = (CheckBox)findViewById(R.id.bold);
                String check = "通常";
                if(italic.isChecked()){
                	check = "斜体";
                }
                if(bold.isChecked()){
                	if(italic.isChecked()){
                		check += "|太字";
                	}else{
                		check = "太字";
                	}
                }
                
                // プリファレンスファイルに保存
                editor.putString("FONT", font);
                editor.putString("STYLE", check);
                editor.commit();
                
                // メッセージ表示
                message.setText("保存しました！");
    		
            // 表示ボタンが押された場合
    		}else if(tag.equals("display")){
    			// 保存データ取得
    			String font = preference.getString("FONT", "見つかりません");
    			String style = preference.getString("STYLE", "見つかりません");
    			
    			// メッセージ表示用フォント、スタイル設定
    			Typeface fonttype = Typeface.DEFAULT;
    			if(font.equals("明朝体")){
    				fonttype = Typeface.SERIF;
    			}else if(font.equals("ゴシック体")){
    				fonttype = Typeface.SANS_SERIF;
    			}else if(font.equals("等幅フォント")){
    				fonttype = Typeface.MONOSPACE;
    			}
    			int styleflg = Typeface.NORMAL;
    			if(style.equals("斜体")){
    				styleflg = Typeface.ITALIC;
    			}else if(style.equals("太字")){
    				styleflg = Typeface.BOLD;
    			}else if(style.equals("斜体|太字")){
    				styleflg = Typeface.BOLD_ITALIC;
    			}
    			
    			// メッセージ表示
    			message.setText("Preference Sample\n" + "フォント：" + font + "\nスタイル：" + style);
    			message.setTypeface(Typeface.create(fonttype, styleflg));

    			
    		// 削除ボタンが押された場合
    		}else if(tag.endsWith("delete")){
    			// プリファレンスの編集用オブジェクト取得
                SharedPreferences.Editor editor = preference.edit();  
                
                // すべての保存データ削除
                editor.clear();
                editor.commit();
                
                // メッセージ表示
                message.setText("削除しました！");
    		}
    	}
    }
}