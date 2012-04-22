package example.android.implicitintentsample1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
            
            // インテントの生成(データを表示するアクション指定)
            Intent intent = new Intent(Intent.ACTION_VIEW);
            
            // URI設定
            String uriStr = "";
            if(item.equals("Apple")){
            	uriStr += "intentsample://fruit/apple?selecteditem="+item;
            }else if(item.equals("Banana")){
            	uriStr += "intentsample://fruit/banana?selecteditem="+item;
            }else if(item.equals("Grape")){
            	uriStr += "intentsample://fruit/grape?selecteditem="+item;
            }else{
            	uriStr += "intentsample://fruitall?selecteditem=all";
            }
            Uri uri = Uri.parse(uriStr);
            
            // URIをインテントに設定
            intent.setData(uri);
            
            // 次のアクティビティの起動
            startActivity(intent);
        }
    }
}