package example.android.activitymondai2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ActivityMondai2 extends Activity {
	// onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymondai2);
        
        // Spinnerオブジェクト情報取得
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// Spinnerオブジェクトにイベントリスナーを関連付け
        spinner.setOnItemSelectedListener(new SelectItemSelectedListener());
        
    }
    // イベントリスナー定義
	class SelectItemSelectedListener implements OnItemSelectedListener {
		// onItemSelectedメソッド
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			
				// クリック時のSpinnerオブジェクト情報取得
				Spinner spinner = (Spinner)parent;
				// 選択されたリストの値取得
				String item = (String) spinner.getItemAtPosition(position);
				
				// テキストボックスの入力情報取得
	    		EditText input = (EditText)findViewById(R.id.text_name);
	    		
				// 選択された値をトースト機能で画面表示
	    		Toast.makeText(ActivityMondai2.this, item+"\n"+input.getText()+"さん",Toast.LENGTH_SHORT).show();

		}
		// onNothingSelectedメソッド
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	}
}