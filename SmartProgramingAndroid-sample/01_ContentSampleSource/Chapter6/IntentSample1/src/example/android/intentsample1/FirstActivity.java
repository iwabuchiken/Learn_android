package example.android.intentsample1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FirstActivity extends Activity {
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.firstlayout);

        // ListViewオブジェクト取得
        ListView listview = (ListView) findViewById(R.id.fruitlist);
        // ListViewオブジェクトにクリックリスナーを関連付け
        listview.setOnItemClickListener(new ListItemClickListener());
    }

    // アイテムクリックリスナー定義
    class ListItemClickListener implements OnItemClickListener {
        // onItemClickメソッド(値選択時イベントハンドラ)
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // ListViewオブジェクト取得
            ListView listview = (ListView) parent;
            // 選択された値取得
            String item = (String) listview.getItemAtPosition(position);
            
            // インテントの生成(呼び出すクラスの指定)
            Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
            // 選択された値をインテントに設定
            intent.putExtra("SELECTED_PICT",item);
            // 次のアクティビティの起動
            startActivity(intent);
        }
    }
}