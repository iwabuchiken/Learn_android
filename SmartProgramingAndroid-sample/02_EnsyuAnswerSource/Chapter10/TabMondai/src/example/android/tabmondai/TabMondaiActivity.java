package example.android.tabmondai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class TabMondaiActivity extends TabActivity implements OnTabChangeListener {
	// ファイル名
    private static final String FILE_NAME = "TabMondai";	
	
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        
        // TabHost取得
        TabHost tabHost = getTabHost();
                
        // TabHostとレイアウトとビューの設定ファイルを関連付け
        LayoutInflater.from(this).inflate(R.layout.tabmondai,
                tabHost.getTabContentView(), true);
        
        // タブ1の設定
        TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setIndicator("設定",getResources().getDrawable(android.R.drawable.ic_menu_add));
        tab1.setContent(R.id.tablelayout1);
        // タブ2の設定
        TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setIndicator("表示",getResources().getDrawable(android.R.drawable.ic_menu_info_details));
        tab2.setContent(R.id.tablelayout2);
        
        // 各タブをTabHostに設定
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        
        // 初期表示のタブ設定
        tabHost.setCurrentTab(0);
        
        // タブが切り替わったときのイベントハンドラを設定   
        tabHost.setOnTabChangedListener(this);
        
        // 確認ボタンのクリックリスナー設定
        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new ButtonClickListener());
    }
    
    // onTabChangedメソッド(タブが切り替わった時のイベントハンドラ)
    public void onTabChanged(String tabId) {
    	// 切り替わったタブが表示のタブの場合
    	if(tabId.equals("tab2")){
    		// テーブルレイアウトオブジェクト取得
            TableLayout tablelayout = (TableLayout)findViewById(R.id.tablelayout2);

            // テーブルレイアウトのクリア
            tablelayout.removeAllViews();
            
            // テーブルレイアウトの表示範囲を設定
            tablelayout.setStretchAllColumns(true);
            
            // テーブル一覧のヘッダ部設定
            TextView headtxt1 = new TextView(TabMondaiActivity.this);
            headtxt1.setText("商品名");
            headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
            headtxt1.setWidth(100);
            TextView headtxt2 = new TextView(TabMondaiActivity.this);
            headtxt2.setText("価格");
            headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
            headtxt2.setWidth(60);
            TableRow headrow = new TableRow(TabMondaiActivity.this);
            headrow.addView(headtxt1);
            headrow.addView(headtxt2);
            tablelayout.addView(headrow);
    		
    		// ファイルからデータ取得
            try{
                FileInputStream stream = openFileInput(FILE_NAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                
                String line = "";
                while((line = in.readLine())!=null){
                    String lineSplit[] = line.split(",");
                    
                    TextView nametxt = new TextView(TabMondaiActivity.this);
                    nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
                    nametxt.setText(lineSplit[0]);
                    TextView pricetxt = new TextView(TabMondaiActivity.this);
                    pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
                    pricetxt.setText(lineSplit[1]);
                    
                    TableRow row = new TableRow(TabMondaiActivity.this);
                    row.addView(nametxt);
                    row.addView(pricetxt);
                    tablelayout.addView(row);
                }
                in.close();             
            }catch(Exception e){
                Log.e("ERROR", "file access error");
            }      
    	}
    }
    
    // ボタンクリックリスナー定義
    class ButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        @Override
        public void onClick(View v) {
        	// 入力データ取得
        	String name = ((EditText)findViewById(R.id.name)).getText().toString();
        	String price = ((EditText)findViewById(R.id.price)).getText().toString();

			// ファイルに保存
			try{
                FileOutputStream stream = openFileOutput(FILE_NAME, MODE_APPEND);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(stream));
                
                out.write(name + "," + price);
                out.newLine();
                out.close();
                
            }catch(Exception e){
                Log.e("FILE_ERROR", "ファイル書き込みに失敗しました");
            }
        }
    }
}