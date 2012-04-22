package example.android.optionmenusample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class OptionMenuSampleActivity extends Activity {
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.optionmenusample);        
    }
    
    // onCreateOptionsMenuメソッド(オプションメニュー生成)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        // メニューアイテム1の追加
        @SuppressWarnings("unused")
		MenuItem item1=menu.add(0,0,0,"item1");
        
        // メニューアイテム2の追加
        MenuItem item2=menu.add(0,1,0,"item2");
        item2.setIcon(android.R.drawable.ic_menu_search);
        
        // メニューアイテム3の追加
        MenuItem item3=menu.add(0,2,0,"item3");
        item3.setIcon(android.R.drawable.ic_menu_save);
        
        //メニューアイテム4の追加 ←追加
        MenuItem item4=menu.add(0,3,0,"item4");
        item4.setIcon(android.R.drawable.ic_menu_call);
        
        //メニューアイテム5の追加 ←追加
        MenuItem item5=menu.add(0,4,0,"item5");
        item5.setIcon(android.R.drawable.ic_menu_camera);
        
        //メニューアイテム6の追加 ←追加
        SubMenu item6=menu.addSubMenu(0,5,0,"その他");
        item6.setIcon(android.R.drawable.ic_menu_more);
        item6.add(0,10,0,"subitem1");
        item6.add(0,20,0,"subitem2");

        return true;
    }    

    // onOptionsItemSelectedメソッド(メニューアイテム選択処理)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showDialog("メニューアイテム1を選択しました。");
                return true;
            case 1:
                showDialog("メニューアイテム2を選択しました。");
                return true;
            case 2:
                showDialog("メニューアイテム3を選択しました。");
                return true;
            case 3:
                showDialog("メニューアイテム4を選択しました。");
                return true;
            case 4:
                showDialog("メニューアイテム5を選択しました。");
                return true;
            case 10:
                showDialog("サブメニューアイテム1を選択しました。");
                return true;
            case 20:
                showDialog("サブメニューアイテム2を選択しました。");
                return true;            
        }
        return true;
    }    
    
    // showDialogメソッド(ダイアログ表示)
    private void showDialog(String text) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(OptionMenuSampleActivity.this);
        dialog.setTitle("メニューアイテム選択結果");
        dialog.setMessage(text);
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
            	OptionMenuSampleActivity.this.setResult(Activity.RESULT_OK);
            }
        });
        dialog.create();
        dialog.show();
    }
}