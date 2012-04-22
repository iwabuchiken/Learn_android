package jp.co.techfun.lunchmap;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// データベース関連処理クラス
public class MarkerDbUtil {
    // ヘルパーインスタンス
    private SQLiteOpenHelper helper;

    // DBインスタンス
    private SQLiteDatabase db;

    // DB名
    private static final String DB_NAME = "markerdb";

    // テーブル名
    private static final String TABLE_NAME = "marker";

    // カラム名(通番)
    private static final String C_ID = "id";
    // カラム名(緯度)
    private static final String C_LATITUDE_E6 = "latitude_e6";
    // カラム名(経度)
    private static final String C_LONGITUDE_E6 = "longitude_e6";
    // カラム名(店名)
    private static final String C_SHOP_NAME = "shopname";
    // カラム名(評価)
    private static final String C_SHOP_RATE = "shoprate";
    // カラム名(平均予算)
    private static final String C_PRICE = "price";
    // カラム名(コメント)
    private static final String C_COMMENT = "comment";

    // コンストラクタ
    public MarkerDbUtil(Context con) {
        // DB作成
        helper = new SQLiteOpenHelper(con, DB_NAME, null, 1) {
            @Override
            public void onUpgrade(SQLiteDatabase database, int oldVersion,
                int newVersion) {
                // 処理なし
            }

            @Override
            public void onCreate(SQLiteDatabase database) {
                // 処理なし
            }
        };

        // テーブル作成
        try {
            // DBインスタンス取得
            db = helper.getWritableDatabase();

            // SQL生成
            StringBuilder sql = new StringBuilder();
            sql.append("create table " + TABLE_NAME + "(");
            // 通番(自動採番)
            sql.append(C_ID + " integer primary key autoincrement,");
            // 緯度
            sql.append(C_LATITUDE_E6 + " integer not null,");
            // 経度
            sql.append(C_LONGITUDE_E6 + " integer not null,");
            // 店名
            sql.append(C_SHOP_NAME + " text not null,");
            // 評価
            sql.append(C_SHOP_RATE + " integer not null,");
            // 平均予算
            sql.append(C_PRICE + " integer default 0,");
            // コメント
            sql.append(C_COMMENT + " text");
            sql.append(")");

            // SQL実行
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "テーブルの作成に失敗しました。", th);
        } finally {
            db.close();
        }
    }

    // マーカー登録処理
    public void addMarker(ShopOverlayItem item) {
        try {
            // DBインスタンス取得
            db = helper.getWritableDatabase();

            // SQL生成
            StringBuilder sql = new StringBuilder();
            sql.append("insert into " + TABLE_NAME + " values (");
            // 通番(自動採番)
            sql.append("null,");
            // 緯度
            sql.append(item.getPoint().getLatitudeE6() + ",");
            // 経度
            sql.append(item.getPoint().getLongitudeE6() + ",");
            // 店名
            sql.append("'" + item.getShopName() + "',");
            // 評価
            sql.append(item.getShopRate() + ",");
            // 平均予算
            sql.append(item.getPrice() + ",");
            // コメント
            sql.append("'" + item.getComment() + "'");
            sql.append(")");

            // SQL実行
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "テーブルへのデータ登録に失敗しました。", th);
        } finally {
            db.close();
        }
    }

    // 現在地周辺のマーカー取得処理
    public List<ShopOverlayItem> getMarker(int rate) {
        // 表示対象マーカーリスト
        List<ShopOverlayItem> itemList = new ArrayList<ShopOverlayItem>();

        // データ取得用カーソル
        Cursor cursor = null;
        try {
            // DBインスタンス取得
            db = helper.getWritableDatabase();

            // テーブルから取得する列名を定義
            String[] columns =
                { C_LATITUDE_E6, C_LONGITUDE_E6, C_SHOP_NAME, C_SHOP_RATE,
                        C_PRICE, C_COMMENT };

            // テーブルから取得する条件を定義
            String selection = C_SHOP_RATE + " = ?";

            // 取得条件に設定するパラメータ
            String[] selectionArgs = { String.valueOf(rate) };

            // データ取得
            cursor =
                db.query(TABLE_NAME, columns, selection, selectionArgs, null,
                    null, null);

            // 取得したデータよりマーカーを生成
            while (cursor.moveToNext()) {
                // 列項目番号
                int cIdx = 0;
                // マーカーの緯度・経度を生成
                GeoPoint markerGeoPoint =
                    new GeoPoint(cursor.getInt(cIdx++), cursor.getInt(cIdx++));
                // 店名
                String shopName = cursor.getString(cIdx++);
                // 評価
                int shopRate = cursor.getInt(cIdx++);
                // 平均予算
                int price = cursor.getInt(cIdx++);
                // コメント
                String comment = cursor.getString(cIdx++);

                // マーカーを生成
                ShopOverlayItem marker =
                    new ShopOverlayItem(markerGeoPoint, shopName, shopRate,
                        price, comment);

                // 表示対象マーカーリストに追加
                itemList.add(marker);
            }
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "テーブルデータの取得に失敗しました。", th);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        // 表示対象マーカーリストを返す
        return itemList;
    }

    // マーカー削除処理
    public void deleteMarker(ShopOverlayItem marker) {
        try {
            // DBインスタンス取得
            db = helper.getWritableDatabase();

            // SQL生成
            StringBuilder sql = new StringBuilder();
            sql.append("delete from " + TABLE_NAME);
            sql.append(" where ");
            // 緯度
            sql.append(C_LATITUDE_E6 + "=" + marker.getPoint().getLatitudeE6());
            // 経度
            sql.append(" and " + C_LONGITUDE_E6 + "="
                + marker.getPoint().getLongitudeE6());
            // 評価
            sql.append(" and " + C_SHOP_RATE + "=" + marker.getShopRate());

            // SQL実行
            db.execSQL(sql.toString());
        } catch (Throwable th) {
            Log.w(getClass().getSimpleName(), "データの削除に失敗しました。", th);
        } finally {
            db.close();
        }
    }
}
