package example.android.listclicksample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListClickSample extends Activity {
    // onCreateメソッド(画面初期表示イベント)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.listclicksample);
        
        // ListViewオブジェクト取得
        ListView listview = (ListView) findViewById(R.id.listView1);
        // ListViewオブジェクトにクリックリスナーを関連付け
        listview.setOnItemClickListener(new ListItemClickListener());
    }
    
    // アイテムクリックリスナー定義
    class ListItemClickListener implements OnItemClickListener {
        // onItemClickメソッド(リストの値クリック時イベント)
        public void onItemClick(AdapterView<?> parent, 
        							View view, 
        							int position, 
        							long id) {
            // クリック時のListViewオブジェクト取得
            ListView listview = (ListView) parent;
            // 選択された値取得
            String item = (String) listview.getItemAtPosition(position);
            
            // 選択された値をトースト機能で画面表示
            Toast.makeText(ListClickSample.this, 
            				item,
            				Toast.LENGTH_SHORT).show();
        }
    }
}
