package jp.co.techfun.lunchmap;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// �f�[�^�x�[�X�֘A�����N���X
public class MarkerDbUtil {
    // �w���p�[�C���X�^���X
    private SQLiteOpenHelper helper;

    // DB�C���X�^���X
    private SQLiteDatabase db;

    // DB��
    private static final String DB_NAME = "markerdb";

    // �e�[�u����
    private static final String TABLE_NAME = "marker";

    // �J������(�ʔ�)
    private static final String C_ID = "id";
    // �J������(�ܓx)
    private static final String C_LATITUDE_E6 = "latitude_e6";
    // �J������(�o�x)
    private static final String C_LONGITUDE_E6 = "longitude_e6";
    // �J������(�X��)
    private static final String C_SHOP_NAME = "shopname";
    // �J������(�]��)
    private static final String C_SHOP_RATE = "shoprate";
    // �J������(���ϗ\�Z)
    private static final String C_PRICE = "price";
    // �J������(�R�����g)
    private static final String C_COMMENT = "comment";

    // �R���X�g���N�^
    public MarkerDbUtil(Context con) {
        // DB�쐬
        helper = new SQLiteOpenHelper(con, DB_NAME, null, 1) {
            @Override
            public void onUpgrade(SQLiteDatabase database, int oldVersion,
                int newVersion) {
                // �����Ȃ�
            }

            @Override
            public void onCreate(SQLiteDatabase database) {
                // �����Ȃ�
            }
        };

        // �e�[�u���쐬
        try {
            // DB�C���X�^���X�擾
            db = helper.getWritableDatabase();

            // SQL����
            StringBuilder sql = new StringBuilder();
            sql.append("create table " + TABLE_NAME + "(");
            // �ʔ�(�����̔�)
            sql.append(C_ID + " integer primary key autoincrement,");
            // �ܓx
            sql.append(C_LATITUDE_E6 + " integer not null,");
            // �o�x
            sql.append(C_LONGITUDE_E6 + " integer not null,");
            // �X��
            sql.append(C_SHOP_NAME + " text not null,");
            // �]��
            sql.append(C_SHOP_RATE + " integer not null,");
            // ���ϗ\�Z
            sql.append(C_PRICE + " integer default 0,");
            // �R�����g
            sql.append(C_COMMENT + " text");
            sql.append(")");

            // SQL���s
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "�e�[�u���̍쐬�Ɏ��s���܂����B", th);
        } finally {
            db.close();
        }
    }

    // �}�[�J�[�o�^����
    public void addMarker(ShopOverlayItem item) {
        try {
            // DB�C���X�^���X�擾
            db = helper.getWritableDatabase();

            // SQL����
            StringBuilder sql = new StringBuilder();
            sql.append("insert into " + TABLE_NAME + " values (");
            // �ʔ�(�����̔�)
            sql.append("null,");
            // �ܓx
            sql.append(item.getPoint().getLatitudeE6() + ",");
            // �o�x
            sql.append(item.getPoint().getLongitudeE6() + ",");
            // �X��
            sql.append("'" + item.getShopName() + "',");
            // �]��
            sql.append(item.getShopRate() + ",");
            // ���ϗ\�Z
            sql.append(item.getPrice() + ",");
            // �R�����g
            sql.append("'" + item.getComment() + "'");
            sql.append(")");

            // SQL���s
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "�e�[�u���ւ̃f�[�^�o�^�Ɏ��s���܂����B", th);
        } finally {
            db.close();
        }
    }

    // ���ݒn���ӂ̃}�[�J�[�擾����
    public List<ShopOverlayItem> getMarker(int rate) {
        // �\���Ώۃ}�[�J�[���X�g
        List<ShopOverlayItem> itemList = new ArrayList<ShopOverlayItem>();

        // �f�[�^�擾�p�J�[�\��
        Cursor cursor = null;
        try {
            // DB�C���X�^���X�擾
            db = helper.getWritableDatabase();

            // �e�[�u������擾����񖼂��`
            String[] columns =
                { C_LATITUDE_E6, C_LONGITUDE_E6, C_SHOP_NAME, C_SHOP_RATE,
                        C_PRICE, C_COMMENT };

            // �e�[�u������擾����������`
            String selection = C_SHOP_RATE + " = ?";

            // �擾�����ɐݒ肷��p�����[�^
            String[] selectionArgs = { String.valueOf(rate) };

            // �f�[�^�擾
            cursor =
                db.query(TABLE_NAME, columns, selection, selectionArgs, null,
                    null, null);

            // �擾�����f�[�^���}�[�J�[�𐶐�
            while (cursor.moveToNext()) {
                // �񍀖ڔԍ�
                int cIdx = 0;
                // �}�[�J�[�̈ܓx�E�o�x�𐶐�
                GeoPoint markerGeoPoint =
                    new GeoPoint(cursor.getInt(cIdx++), cursor.getInt(cIdx++));
                // �X��
                String shopName = cursor.getString(cIdx++);
                // �]��
                int shopRate = cursor.getInt(cIdx++);
                // ���ϗ\�Z
                int price = cursor.getInt(cIdx++);
                // �R�����g
                String comment = cursor.getString(cIdx++);

                // �}�[�J�[�𐶐�
                ShopOverlayItem marker =
                    new ShopOverlayItem(markerGeoPoint, shopName, shopRate,
                        price, comment);

                // �\���Ώۃ}�[�J�[���X�g�ɒǉ�
                itemList.add(marker);
            }
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "�e�[�u���f�[�^�̎擾�Ɏ��s���܂����B", th);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // �\���Ώۃ}�[�J�[���X�g��Ԃ�
        return itemList;
    }

    // �}�[�J�[�폜����
    public void deleteMarker(ShopOverlayItem marker) {
        try {
            // DB�C���X�^���X�擾
            db = helper.getWritableDatabase();

            // SQL����
            StringBuilder sql = new StringBuilder();
            sql.append("delete from " + TABLE_NAME);
            sql.append(" where ");
            // �ܓx
            sql.append(C_LATITUDE_E6 + "=" + marker.getPoint().getLatitudeE6());
            // �o�x
            sql.append(" and " + C_LONGITUDE_E6 + "="
                + marker.getPoint().getLongitudeE6());
            // �]��
            sql.append(" and " + C_SHOP_RATE + "=" + marker.getShopRate());

            // SQL���s
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "�f�[�^�̍폜�Ɏ��s���܂����B", th);
        } finally {
            db.close();
        }
    }
}
