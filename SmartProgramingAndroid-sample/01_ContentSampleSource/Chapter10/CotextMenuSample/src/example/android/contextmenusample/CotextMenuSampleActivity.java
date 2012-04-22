package example.android.contextmenusample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CotextMenuSampleActivity extends Activity {
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.contextmenusample);
        
        // リストビューに表示するリスト作成
        List<String> list = new ArrayList<String>();
        list.add("選択1");
        list.add("選択2");
        list.add("選択3");


        // リストアダプタ生成
        ListAdapter adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1
                ,list);

        // リストビューにアダプタを設定
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        // コンテキストメニューにリストビューを登録
        registerForContextMenu(listView);
    }
    
    // onCreateContextMenuメソッド(コンテキストメニュー生成)
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo menuInfo) {
        // コンテキストメニュー生成
        menu.setHeaderTitle("コンテキストメニュー");

        menu.add("メニュー1");
        menu.add("メニュー2");
        menu.add("メニュー3");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    // onContextItemSelectedメソッド(コンテキストメニュー選択)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 選択されたメニューアイテムをテキストビューに表示
        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText("コンテキストメニューで選択: " + item.getTitle());

        return super.onContextItemSelected(item);

    }
}