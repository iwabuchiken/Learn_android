package example.android.contentprovidersample1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ProductContentProvider extends ContentProvider {
    
    // CreateProductHelper�I�u�W�F�N�g
    CreateProductHelper databaseHelper;

	// onCreate���\�b�h(�f�[�^�x�[�X�̍쐬����)
    @Override
    public boolean onCreate() {
        databaseHelper = new CreateProductHelper(getContext());
        return true;
    }
    
    // insert���\�b�h(�f�[�^�x�[�X�ւ̓o�^����)
    @Override
    public Uri insert(Uri arg0, ContentValues values) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.insert("product", null, values); 
        return null;
    }
	
    // query���\�b�h(�f�[�^�x�[�X����f�[�^�擾�����j
    @Override
    public Cursor query(Uri arg0, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("product"); 
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, null);
        return c;
    }

    // update���\�b�h(�f�[�^�x�[�X�̍X�V����)
    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {	
    	 SQLiteDatabase db = databaseHelper.getWritableDatabase();
    	 int row = db.update("product", arg1, arg2, arg3);
        return row;
    }
    
    // delete���\�b�h(�f�[�^�x�[�X�̍폜����)
    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
    	SQLiteDatabase db = databaseHelper.getWritableDatabase();
    	int row = db.delete("product", arg1, arg2);
        return row;
    }

    // getType���\�b�h(��ʂ̎擾�����F����g�p���Ă��܂���B)
    @Override
    public String getType(Uri arg0) {
        return null;
    }
}
