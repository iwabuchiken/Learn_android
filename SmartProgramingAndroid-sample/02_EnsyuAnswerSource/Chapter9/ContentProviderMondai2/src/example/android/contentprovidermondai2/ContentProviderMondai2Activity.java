package example.android.contentprovidermondai2;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ContentProviderMondai2Activity extends Activity {
	TableLayout tablelayout = null;
	
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.contentprovidermondai2);
        
        // テーブルレイアウト取得
        tablelayout = (TableLayout)findViewById(R.id.tablelayout);
        
        try {
            // データ取得
            Cursor cur = managedQuery(CallLog.Calls.CONTENT_URI,
                    				   null,
                    				   null,
                    				   null,
                    				   null);
            
            // データが取得できた場合
            if(cur.getCount() != 0){
            	// 取得したデータ表示
                while (cur.moveToNext()) {
                	String name = cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER));
                    setItems(name);
                }
            }else{
            	TextView message = new TextView(this);
            	message.setText("データが取得できませんでした。");
            	LinearLayout linearlayout = (LinearLayout)findViewById(R.id.linearlayout);
            	linearlayout.addView(message);
            }            
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

    }
    // setItemsメソッド(テーブル表示処理)
    private void setItems(String name) {
    	// 名前を設定
        TableRow row = new TableRow(this);
        TextView displayName = new TextView(this);
        displayName.setText(name);
        row.addView(displayName);
        // テーブルレイアウトに設定
        tablelayout.addView(row);
    }
}