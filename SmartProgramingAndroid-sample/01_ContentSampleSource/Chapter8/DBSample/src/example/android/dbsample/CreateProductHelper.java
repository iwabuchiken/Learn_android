package example.android.dbsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateProductHelper  extends SQLiteOpenHelper{
    
    // コンストラクタ定義
    public CreateProductHelper(Context con){
        // SQLiteOpenHelperのコンストラクタ呼び出し
        super(con,"dbsample",null,1);
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
