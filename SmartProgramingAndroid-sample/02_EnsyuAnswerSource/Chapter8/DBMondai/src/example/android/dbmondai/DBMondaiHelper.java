package example.android.dbmondai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMondaiHelper  extends SQLiteOpenHelper{
    
    // コンストラクタ定義
    public DBMondaiHelper(Context con){
        // SQLiteOpenHelperのコンストラクタ呼び出し
        super(con,"dbmondai",null,1);
    }
    
    // onCreateメソッド
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    
    // onUpgradeメソッド
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
    }
}
