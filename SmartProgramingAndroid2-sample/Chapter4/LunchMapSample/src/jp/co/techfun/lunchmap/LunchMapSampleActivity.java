package jp.co.techfun.lunchmap;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

// ランチマップ画面Activity
public class LunchMapSampleActivity extends MapActivity {

    // 店情報入力画面とのインテント用リクエストコード
    private static final int REQUEST_CODE_INPUT = 0;

    // メニューアイテムID
    // アイテム追加ボタン用
    private static final int MENU_ITEM_ID_ADD = 0;

    // アイテム削除ボタン用
    private static final int MENU_ITEM_ID_DELETE = 1;

    // マーカー作成時のGeoPointを保持する
    private GeoPoint geoPoint;

    // マーカー表示用オーバーレイ
    private MarkerOverlay[] markerOverlays;

    // マーカー情報保存用DBインスタンス
    private MarkerDbUtil dbUtil;

    // onCreateメソッド(画面初期表示イベント)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウトを設定
        setContentView(R.layout.main);

        // DBUtilインスタンス生成
        dbUtil = new MarkerDbUtil(this);

        // Google Map 表示クラス
        MapView mapView = (MapView) findViewById(R.id.mapview);

        // ズームコントロールを使用するように指定
        mapView.setBuiltInZoomControls(true);

        // コントローラを使用してズームレベルを設定
        MapController mapController = mapView.getController();
        mapController.setZoom(15);

        // 現在地表示オーバーレイを生成
        MyLocationOverlay myOverlay =
            new MyLocationOverlay(getApplicationContext(), mapView);

        // GPS機能有効設定
        myOverlay.onProviderEnabled(LocationManager.GPS_PROVIDER);

        // GPSで取得した現在地を表示
        myOverlay.enableMyLocation();

        // MapViewに現在地表示オーバーレイ設定
        mapView.getOverlays().add(myOverlay);

        // 初回現在地情報取得スレッド起動
        myOverlay.runOnFirstFix(firstUpdateMyLocationTh);

        // マーカー画像設定
        // 評価の最大値を取得
        Integer rbNumStarts =
            Integer.parseInt(getString(R.string.rb_num_starts));

        // マーカー画像リソースIDを配列化
        int[] drawableResIds =
            { R.drawable.shop_rate_1, R.drawable.shop_rate_2,
                    R.drawable.shop_rate_3, R.drawable.shop_rate_4,
                    R.drawable.shop_rate_5 };

        // マーカー画像用配列を生成
        Drawable[] shopRateMarkers = new Drawable[rbNumStarts];

        // マーカー表示オーバーレイ用配列を生成
        markerOverlays = new MarkerOverlay[rbNumStarts];

        // 評価毎のマーカー表示オーバーレイを生成し、MapViewに設定
        for (int rateIndex = 0; rateIndex < rbNumStarts; rateIndex++) {
            // マーカー画像設定
            shopRateMarkers[rateIndex] =
                getResources().getDrawable(drawableResIds[rateIndex]);

            // 各マーカー画像の大きさを設定
            shopRateMarkers[rateIndex].setBounds(0, 0,
                shopRateMarkers[rateIndex].getMinimumWidth(),
                shopRateMarkers[rateIndex].getMinimumHeight());

            // マーカー表示オーバーレイ生成
            markerOverlays[rateIndex] =
                new MarkerOverlay(shopRateMarkers[rateIndex], this);

            // MapViewに設定
            mapView.getOverlays().add(markerOverlays[rateIndex]);
        }
    }

    // onResumeメソッド(画面表示前イベント)
    @Override
    protected void onResume() {
        super.onResume();

        // 各評価の店舗マーカーを表示
        int rateIndex = 1;
        for (MarkerOverlay overlay : markerOverlays) {
            // DBから特定評価の店舗マーカーリストを取得
            List<ShopOverlayItem> newMarkerList = dbUtil.getMarker(rateIndex++);

            // 取得したマーカーを表示
            overlay.setNewMarkerList(newMarkerList);
        }
    }

    // onActivityResultメソッド(メイン画面再表示時イベント)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE_INPUT || resultCode != RESULT_OK) {
            return;
        }
        // 引数で戻ってきた値を取得
        Bundle extra = data.getExtras();

        // 店名
        String shopName = extra.getString(InputShopInfoActivity.SHOP_NAME_KEY);

        // 評価
        int shopRate = extra.getInt(InputShopInfoActivity.SHOP_RATE_KEY);
        // 最小値を1とする
        shopRate = shopRate <= 0 ? 1 : shopRate;

        // 平均予算
        Integer price = extra.getInt(InputShopInfoActivity.PRICE_KEY);

        // コメント
        String comment = extra.getString(InputShopInfoActivity.COMMENT_KEY);

        // 新しく店マーカーを生成
        ShopOverlayItem newMarker =
            new ShopOverlayItem(geoPoint, shopName, shopRate, price, comment);

        // DBへマーカーを登録
        dbUtil.addMarker(newMarker);

        // マーカーを表示
        markerOverlays[shopRate - 1].addPoint(newMarker);
    }

    // isRouteDisplayedメソッド(ルート情報を表示するか否か)
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    // onCreateOptionsMenuメソッド(オプションメニュー生成)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // メニューアイテムの追加(マーカー追加)
        MenuItem menuItem1 =
            menu.add(0, MENU_ITEM_ID_ADD, 0, R.string.add_menu_text);
        menuItem1.setIcon(android.R.drawable.ic_menu_add);

        // メニューアイテムの追加(マーカー削除)
        MenuItem menuItem2 =
            menu.add(0, MENU_ITEM_ID_DELETE, 0, R.string.delete_menu_text);
        menuItem2.setIcon(android.R.drawable.ic_menu_add);

        return true;
    }

    // onOptionsItemSelectedメソッド(メニュー選択時イベント)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Google Map 表示クラス
        MapView mapView = (MapView) findViewById(R.id.mapview);

        // 現在地のジオポイント取得
        geoPoint = mapView.getMapCenter();

        // 選択されたメニューアイテムを判定
        switch (item.getItemId()) {

        // アイテム追加ボタンの処理
        case MENU_ITEM_ID_ADD:
            // インテントの生成(呼び出すクラスの指定)
            Intent intent = new Intent(this, InputShopInfoActivity.class);

            // 店情報入力用のアクティビティの起動
            startActivityForResult(intent, REQUEST_CODE_INPUT);
            break;

        // アイテム削除ボタンの処理
        case MENU_ITEM_ID_DELETE:
            // 最後にタップしたマーカーを取得
            ShopOverlayItem lastTapMarker = MarkerOverlay.getLastTapMarker();

            // 最後にタップしたマーカーが取得できなかった場合
            if (lastTapMarker ==null) {
            	return true;
            }
            
            // 最後にタップしたマーカーをDBから削除
            dbUtil.deleteMarker(lastTapMarker);

            // 最後にタップしたマーカーをオーバーレイから削除
            markerOverlays[lastTapMarker.getShopRate() - 1].deleteItem();
            break;

        default:
            // 処理なし
            break;
        }

        // 画面を再描画
        mapView.invalidate();
        return true;
    }

    // 初回現在地情報取得スレッド
    private Runnable firstUpdateMyLocationTh = new Runnable() {

        @Override
        public void run() {
            // Google Map View
            MapView mapView = (MapView) findViewById(R.id.mapview);

            // 現在地表示オーバーレイ(MyLocationOverlay)
            MyLocationOverlay myOverlay =
                (MyLocationOverlay) mapView.getOverlays().get(0);

            // 初回現在地情報が取得されるまで繰り返し
            while (true) {
                // 現在地情報取得
                GeoPoint firstLoc = myOverlay.getMyLocation();

                // 現在地が取得できた場合
                if (firstLoc != null) {

                    // 画面中央に表示
                    mapView.getController().setCenter(firstLoc);

                    // 現在地取得処理終了
                    break;
                }
            }
        }
    };
}
