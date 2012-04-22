package example.android.contentprovidersample1;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ContentProviderSample1Activity extends Activity {
	CreateProductHelper helper = null;
    SQLiteDatabase db = null;	
	
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.contentprovidersample1);
        
        // 登録ボタンのクリックリスナー設定
        Button insertBtn = (Button)findViewById(R.id.insert);
        insertBtn.setTag("insert");
        insertBtn.setOnClickListener(new ButtonClickListener());
        // 更新ボタンのクリックリスナー設定
        Button updatetBtn = (Button)findViewById(R.id.update);
        updatetBtn.setTag("update");
        updatetBtn.setOnClickListener(new ButtonClickListener());
        // 削除ボタンのクリックリスナー設定
        Button delBtn = (Button)findViewById(R.id.delete);
        delBtn.setTag("delete");
        delBtn.setOnClickListener(new ButtonClickListener());
        // 表示ボタンのクリックリスナー設定
        Button selectBtn = (Button)findViewById(R.id.select); 
        selectBtn.setTag("select");
        selectBtn.setOnClickListener(new ButtonClickListener());
        
        // DB作成
        helper = new CreateProductHelper(ContentProviderSample1Activity.this);
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
            
            // テーブルレイアウトオブジェクト取得
            TableLayout tablelayout = (TableLayout)findViewById(R.id.list);

            // テーブルレイアウトのクリア
            tablelayout.removeAllViews();
            
            // 該当DBオブジェクト取得
            db = helper.getWritableDatabase();
    		
    		// 登録ボタンが押された場合
    		if(tag.equals("insert")){
                
                // テーブル作成
                try{
                    
                	// SQL文定義
                    String sql 
                         = "create table product(_id integer primary key autoincrement," +
                           "productid text not null," +
                           "name text not null," +
                           "price integer default 0)";
                    // SQL実行
                    db.execSQL(sql);
                    
                    // メッセージ設定
                    message = "テーブルを作成しました！\n";
                    
                }catch(Exception e){
                	message  = "テーブルは作成されています！\n";
                	Log.e("ERROR",e.toString());
                }
                
                // データ登録
                try{
                    
                    // トランザクション制御開始
                    db.beginTransaction();
                    
                    // 登録データ設定
                    ContentValues val = new ContentValues();
                    val.put("productid", productid.getText().toString());
                    val.put("name", name.getText().toString());
                    val.put("price", price.getText().toString());
                    // データ登録
                    db.insert("product", null, val);
                    
                    // コミット
                    db.setTransactionSuccessful();
                    
                    // トランザクション制御終了
                    db.endTransaction();

                	// メッセージ設定
                    message += "データを登録しました！";

                }catch(Exception e){
                	message  = "データ登録に失敗しました！";
                	Log.e("ERROR",e.toString());
                }
                
            // 更新ボタンが押された場合
    		}else if(tag.endsWith("update")){    			
                
    			// ファイルのデータ削除
    			try{
    				// 更新条件
    				String condition = null;
    				if(productid != null && !productid.equals("")){
    					condition = "productid = '" + productid.getText().toString() + "'";
    				}
    				
                    // トランザクション制御開始
                    db.beginTransaction();
                    
                    // 更新データ設定
                    ContentValues val = new ContentValues();
                    val.put("name", name.getText().toString());
                    val.put("price", price.getText().toString());
                    // データ更新
                    db.update("product", val, condition, null);

                    // コミット
                    db.setTransactionSuccessful();
                    
                    // トランザクション制御終了
                    db.endTransaction();
                    
                    // メッセージ設定
                    message = "データを更新しました！";
                }catch(Exception e){
                    // メッセージ設定
                    message = "データ更新に失敗しました！";
                    Log.e("ERROR",e.toString());
                }  
    		
        	// 削除ボタンが押された場合
    		}else if(tag.endsWith("delete")){   			
                
    			// ファイルのデータ削除
    			try{
    				// 削除条件
    				String condition = null;
    				if(productid != null && !productid.equals("")){
    					condition = "productid = '" + productid.getText().toString() + "'";
    				}
    				
                    // トランザクション制御開始
                    db.beginTransaction();
                    
                    // データ削除
                    db.delete("product", condition, null);

                    // コミット
                    db.setTransactionSuccessful();
                    
                    // トランザクション制御終了
                    db.endTransaction();
                    
                    // メッセージ設定
                    message = "データを削除しました！";
                }catch(Exception e){
                    // メッセージ設定
                    message = "データ削除に失敗しました！";
                    Log.e("ERROR",e.toString());
                }           
            
            // 表示ボタンが押された場合
    		}else if(tag.equals("select")){
                
                // データ取得
                try{
                	// 該当DBオブジェクト取得
                    db = helper.getReadableDatabase();
                
                    // 列名定義
                    String columns[] = {"productid","name","price"};
                    
                    // データ取得
                    Cursor cursor = db.query("product", columns, null, null, null, null, "productid");
                    
                    // テーブルレイアウトの表示範囲を設定
                    tablelayout.setStretchAllColumns(true);
                    
                    // テーブル一覧のヘッダ部設定
                    TableRow headrow = new TableRow(ContentProviderSample1Activity.this);
                    TextView headtxt1 = new TextView(ContentProviderSample1Activity.this);
                    headtxt1.setText("商品ID");
                    headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt1.setWidth(60);
                    TextView headtxt2 = new TextView(ContentProviderSample1Activity.this);
                    headtxt2.setText("商品名");
                    headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt2.setWidth(100);
                    TextView headtxt3 = new TextView(ContentProviderSample1Activity.this);
                    headtxt3.setText("価格");
                    headtxt3.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt3.setWidth(60);          
                    headrow.addView(headtxt1);
                    headrow.addView(headtxt2);
                    headrow.addView(headtxt3);
                    tablelayout.addView(headrow);
                    
                    // 取得したデータをテーブル明細部に設定
                    while(cursor.moveToNext()){                	
                        
                        TableRow row = new TableRow(ContentProviderSample1Activity.this);
                        TextView productidtxt = new TextView(ContentProviderSample1Activity.this);
                        productidtxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        productidtxt.setText(cursor.getString(0));
                        TextView nametxt = new TextView(ContentProviderSample1Activity.this);
                        nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        nametxt.setText(cursor.getString(1));
                        TextView pricetxt = new TextView(ContentProviderSample1Activity.this);
                        pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        pricetxt.setText(cursor.getString(2));
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
    		
            // DBオブジェクトクローズ
            db.close(); 
            
            // メッセージ表示  
            label.setText(message);
    	}
    }
}