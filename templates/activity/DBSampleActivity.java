package example.android.dbsample;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
import android.widget.Toast;

public class DBSampleActivity extends Activity implements OnClickListener{
	
	// class members
	CreateProductHelper helper = null;
	SQLiteDatabase db = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbsample);
        
        // set a listener on the button: insert
        Button insertBtn = (Button) findViewById(R.id.insert);
        insertBtn.setTag("insert");
        insertBtn.setOnClickListener(this);
        
        // set a listener on the button: update
        Button updateBtn = (Button) findViewById(R.id.update);
        updateBtn.setTag("update");
        updateBtn.setOnClickListener(this);

        // set a listener on the button: delete
        Button delBtn = (Button) findViewById(R.id.delete);
        delBtn.setTag("delete");
        delBtn.setOnClickListener(this);

        // set a listener on the button: select
        Button selectBtn = (Button) findViewById(R.id.select);
        selectBtn.setTag("select");
        selectBtn.setOnClickListener(this);
        
        // gen a DB
        helper = new CreateProductHelper(DBSampleActivity.this);
    }//public void onCreate(Bundle savedInstanceState)

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		// get the tag
		String tag = (String) v.getTag();
		
		// for message
		String message = "";
		TextView label = (TextView) findViewById(R.id.message);
		
		// get the input info
		EditText productid = (EditText) findViewById(R.id.id);
		EditText name = (EditText) findViewById(R.id.name);
		EditText price = (EditText) findViewById(R.id.price);
		
		// get a TableLayout object
		TableLayout tablelayout = (TableLayout) findViewById(R.id.list);
		
		// clear the layout
		tablelayout.removeAllViews();
		
		// get the given DB object
		db = helper.getWritableDatabase();
		
		// handle each event
		if (tag.equals("insert")) {
			try {
				// create a table
				// define an SQL sentence
				String sql = "create table product("
						+ "_id integer primary key autoincrement,"
//						+ "productid text not null," + "name text not null" +
						+ "productid text not null," + "name text not null," +
						"price integer default 0)";
				// execute sql
				db.execSQL(sql);
				// set the message
				message = "テーブルを作った！";
//			} catch (SQLiteException e) {
//				message = "テーブルはすでにある\n";
//				Log.e("ERROR", e.toString());
			} catch (Exception e) {
				// TODO: handle exception
				message = "テーブルはすでにある\n";
				Log.e("ERROR", e.toString());
			}//try
			
			try {
				// record data
				db.beginTransaction();
				
				// setup the data
				ContentValues val = new ContentValues();
				val.put("productid", productid.getText().toString());
				val.put("name", name.getText().toString());
				val.put("price", price.getText().toString());
				
				// register the data
				db.insert("product", null, val);
				
				// commit
				db.setTransactionSuccessful();
				
				// end transaction
				db.endTransaction();
				
				// set the message
				message += "データを登録した！";
				
			} catch (Exception e) {
				// TODO: handle exception
				message = "データ登録　=>　失敗";
				Log.e("ERROR", e.toString());
			}//try
			
			//debug
			// toast
//			Toast.makeText(this, sql, Toast.LENGTH_SHORT).show();
			
		} else if (tag.endsWith("update")) {//if (tag.equals("insert"))
			try {
				// set the condition
				String condition = null;
				if (productid != null && !productid.equals("")) {
					condition = "productid = '"
							+ productid.getText().toString() + "'";
				}//if (productid != null && !productid.equals(""))
					// start transaction
				db.beginTransaction();
				// setup the data
				ContentValues val = new ContentValues();
				val.put("name", name.getText().toString());
				val.put("price", price.getText().toString());
				// register the data
				db.update("product", val, condition, null);
				// commit
				db.setTransactionSuccessful();
				// end transaction
				db.endTransaction();
				// set the message
				message = "データを更新した！";
			} catch (Exception e) {
				// TODO: handle exception
				message = "データ更新　=>　失敗";
				Log.e("ERROR", e.toString());
			}//try
		} else if (tag.endsWith("delete")) {//if (tag.equals("insert"))
			// delete data
			try {
				// set the condition
				String condition = null;
				if (productid != null && !productid.equals("")) {
					condition = "productid = '"
							+ productid.getText().toString() + "'";
				}//if (productid != null && !productid.equals(""))
					// start transaction
				db.beginTransaction();

				// delete the data
				db.delete("product", condition, null);

				// commit
				db.setTransactionSuccessful();
				
				// end transaction
				db.endTransaction();
				
				// set the message
				message = "データを削除した！";
			} catch (Exception e) {
				// TODO: handle exception
				message = "データ削除　=>　失敗";
				Log.e("ERROR", e.toString());
			}//try
		} else if (tag.endsWith("select")) {//if (tag.equals("insert"))
			// get data
			try {
				// get a db object
				db = helper.getReadableDatabase();
				
				// define columns
				String columns[] = {"productid", "name", "price"};
				
				// get data
				Cursor cursor = db.query(
						"product", columns, null, null, null, null, "productid");
				
				// set the range
				tablelayout.setStretchAllColumns(true);
				
				// set the header section
				TableRow headrow = new TableRow(DBSampleActivity.this);
				
				TextView headtxt1 = new TextView(DBSampleActivity.this);
				headtxt1.setText("商品ID");
				headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
				headtxt1.setWidth(60);
				
				TextView headtxt2 = new TextView(DBSampleActivity.this);
				headtxt2.setText("商品名");
				headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
				headtxt2.setWidth(100);
				
				TextView headtxt3 = new TextView(DBSampleActivity.this);
				headtxt3.setText("価格");
				headtxt3.setGravity(Gravity.CENTER_HORIZONTAL);
				headtxt3.setWidth(60);
				
				headrow.addView(headtxt1);
				headrow.addView(headtxt2);
				headrow.addView(headtxt3);
				
				tablelayout.addView(headrow);
				
				// set the data on the table
				while (cursor.moveToNext()) {
					
					TableRow row = new TableRow(DBSampleActivity.this);
					TextView productidtxt 
										= new TextView(DBSampleActivity.this);
					
					productidtxt.setGravity(Gravity.CENTER_HORIZONTAL);
					productidtxt.setText(cursor.getString(0));
					
					TextView nametxt = new TextView(DBSampleActivity.this);
					nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
					nametxt.setText(cursor.getString(1));
					
					TextView pricetxt = new TextView(DBSampleActivity.this);
					pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
					pricetxt.setText(cursor.getString(2));
					
					row.addView(productidtxt);
					row.addView(nametxt);
					row.addView(pricetxt);
					
					tablelayout.addView(row);
					
					// set the message
					message = "表示用のデータを取得した！";
					
				}//while (cursor.moveToNext())
				
			} catch (Exception e) {
				// TODO: handle exception
				// set the message
				message = "表示用のデータ取得　=>　失敗";
				Log.e("ERROR", e.toString());
			}//try
			
		}//if (tag.equals("insert"))
		
		// close the db object
		db.close();
		
		// show message
		label.setText(message);		
	}//public void onClick(View v)
}