package example.android.dbmondai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMondaiHelper  extends SQLiteOpenHelper{
    
    // �R���X�g���N�^��`
    public DBMondaiHelper(Context con){
        // SQLiteOpenHelper�̃R���X�g���N�^�Ăяo��
        super(con,"dbmondai",null,1);
    }
    
    // onCreate���\�b�h
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    
    // onUpgrade���\�b�h
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
    }
}
