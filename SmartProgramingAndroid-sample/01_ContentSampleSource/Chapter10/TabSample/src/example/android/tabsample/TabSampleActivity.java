package example.android.tabsample;

import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class TabSampleActivity extends TabActivity {
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        
        // TabHost取得
        TabHost tabHost = getTabHost();
        // TabHostとレイアウトとビューの設定ファイルを関連付け
        LayoutInflater.from(this).inflate(R.layout.tabsample,
                tabHost.getTabContentView(), true);
        // テキストビューに色を設定
        TextView txt1 = (TextView) findViewById(R.id.textview1);
        txt1.setBackgroundColor(Color.GREEN);
        TextView txt2 = (TextView) findViewById(R.id.textview2);
        txt2.setBackgroundColor(Color.BLUE);
        
        // タブ1の設定
        TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setIndicator("タブ1");
        tab1.setContent(R.id.linearlayout1);
        // タブ2の設定
        TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setIndicator("タブ2",getResources().getDrawable(android.R.drawable.ic_menu_edit));
        tab2.setContent(R.id.textview1);
        // タブ3の設定
        TabSpec tab3 = tabHost.newTabSpec("tab3");
        tab3.setIndicator("タブ3",getResources().getDrawable(android.R.drawable.ic_menu_search));
        tab3.setContent(R.id.textview2);
        
        // 各タブをTabHostに設定
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        
        // 初期表示のタブ設定
        tabHost.setCurrentTab(0);
    }
}