package example.android.contentprovideraccesssample1;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ContentProviderAccessSample1Activity extends Activity {
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.accesssample1);
        
        // 登録ボタンのクリックリスナー設定
        Button insertBtn = (Button)findViewById(R.id.insert);
        insertBtn.setTag("insert");
        insertBtn.setOnClickListener(new ButtonClickListener());
        // 表示ボタンのクリックリスナー設定
        Button selectBtn = (Button)findViewById(R.id.select); 
        selectBtn.setTag("select");
        selectBtn.setOnClickListener(new ButtonClickListener());
    }
    
    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener {
    	// onClickメソッド(ボタンクリック時イベントハンドラ)
    	public void onClick(View v){
    		// タグの取得
    		String tag = (String)v.getTag();
    		
    		// メッセージ表示用
            String message  = "";
            TextView label = (TextView)findViewById(R.id.message);
            
            // 入力情報取得
			EditText productid = (EditText)findViewById(R.id.id);
            EditText name = (EditText)findViewById(R.id.name);
            EditText price = (EditText)findViewById(R.id.price);
            
            // Uri取得
            Uri uri = Uri.parse("content://example.android.contentprovidersample1");
            
            // テーブルレイアウトオブジェクト取得
            TableLayout tablelayout = (TableLayout)findViewById(R.id.list);

            // テーブルレイアウトのクリア
            tablelayout.removeAllViews();
    		
    		// 登録ボタンが押された場合
    		if(tag.equals("insert")){
                
                // データ登録
                try{
                    // 登録データ設定
                    ContentValues val = new ContentValues();
                    val.put("productid", productid.getText().toString());
                    val.put("name", "***" + name.getText().toString() + "***");
                    val.put("price", price.getText().toString());
                    // データ登録
                    getContentResolver().insert(uri, val);
                	// メッセージ設定
                    message += "データを登録しました！";

                }catch(Exception e){
                	message  = "データ登録に失敗しました！";
                	Log.e("ERROR",e.toString());
                } 
                
            // 表示ボタンが押された場合
    		}else if(tag.equals("select")){
                
                // データ取得
                try{                   
                    // データ取得
                    Cursor cursor = managedQuery(uri, null, null, null, null);
                    
                    // テーブルレイアウトの表示範囲を設定
                    tablelayout.setStretchAllColumns(true);
                    
                    // テーブル一覧のヘッダ部設定
                    TableRow headrow = new TableRow(ContentProviderAccessSample1Activity.this);
                    TextView headtxt1 = new TextView(ContentProviderAccessSample1Activity.this);
                    headtxt1.setText("商品ID");
                    headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt1.setWidth(60);
                    TextView headtxt2 = new TextView(ContentProviderAccessSample1Activity.this);
                    headtxt2.setText("商品名");
                    headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt2.setWidth(100);
                    TextView headtxt3 = new TextView(ContentProviderAccessSample1Activity.this);
                    headtxt3.setText("価格");
                    headtxt3.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt3.setWidth(60);          
                    headrow.addView(headtxt1);
                    headrow.addView(headtxt2);
                    headrow.addView(headtxt3);
                    tablelayout.addView(headrow);
                    
                    // 取得したデータをテーブル明細部に設定
                    while(cursor.moveToNext()){                	
                        
                        TableRow row = new TableRow(ContentProviderAccessSample1Activity.this);
                        TextView productidtxt = new TextView(ContentProviderAccessSample1Activity.this);
                        productidtxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        productidtxt.setText(cursor.getString(1));
                        TextView nametxt = new TextView(ContentProviderAccessSample1Activity.this);
                        nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        nametxt.setText(cursor.getString(2));
                        TextView pricetxt = new TextView(ContentProviderAccessSample1Activity.this);
                        pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        pricetxt.setText(cursor.getString(3));
                        row.addView(productidtxt);
                        row.addView(nametxt);
                        row.addView(pricetxt);
                        tablelayout.addView(row);
                        
                        // メッセージ設定
                        message = "データを取得しました！";
                    }
             
                }catch(Exception e){
                	// メッセージ設定
                    message = "データ取得に失敗しました！";
                    Log.e("ERROR",e.toString());
                }      
    		}           
            // メッセージ表示  
            label.setText(message);
    	}
    }
}