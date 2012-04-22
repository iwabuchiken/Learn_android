package jp.co.techfun.lunchmap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

// マーカー表示オーバーレイ
public class MarkerOverlay extends ItemizedOverlay<OverlayItem> {

    // コンテキスト定義(トースト, 削除確認ダイアログ表示用)
    private Context context;

    // オーバーレイマーカー格納用リスト
    private List<ShopOverlayItem> markerList;

    // 最後にタップされたマーカーのリスト番号
    private int lastTapMarkerIndex;

    // 最後にタップしたマーカーの評価
    private static ShopOverlayItem lastTapMarker;

    // コンストラクタ
    public MarkerOverlay(Drawable drawable, Context con) {
        super(boundCenterBottom(drawable));

        // オーバーレイマーカー格納用リストを生成
        markerList = new ArrayList<ShopOverlayItem>();

        // コンテキスト定義を設定
        this.context = con;

        // マーカーの個数が変化したことを親クラスに通知
        populate();
    }

    // createItemメソッド(オーバーレイマーカー生成)
    @Override
    protected ShopOverlayItem createItem(int i) {
        return markerList.get(i);
    }

    // sizeメソッド(リストサイズ取得)
    @Override
    public int size() {
        return markerList.size();
    }

    // addPointメソッド(マーカー追加)
    public void addPoint(ShopOverlayItem newMarker) {

        // マーカーリストへ追加
        markerList.add(newMarker);

        // マーカーの個数が変化したことを親クラスに通知
        populate();
    }

    // deleteItemメソッド(直前に選択したマーカー削除)
    public void deleteItem() {
        if (lastTapMarkerIndex < markerList.size()) {

            // マーカーを削除
            markerList.remove(lastTapMarkerIndex);

            // マーカーからフォーカスを外す
            setLastFocusedIndex(-1);

            // マーカーの個数が変化したことを親クラスに通知
            populate();
        }
    }

    // onTapメソッド(マーカータップ時イベント)
    @Override
    protected boolean onTap(int index) {
        // タップしたマーカーのリスト番号
        lastTapMarkerIndex = index;

        // 選択したマーカーのインスタンスを取得
        ShopOverlayItem item = markerList.get(index);

        // 最後にタップしたマーカーとして設定
        lastTapMarker = item;

        // トーストにマーカーの情報を表示
        Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show();

        return true;
    }

    // setNewMarkerListメソッド(新しいマーカーリスト設定)
    public void setNewMarkerList(List<ShopOverlayItem> newMarkerList) {
        // マーカーからフォーカスを外す
        setLastFocusedIndex(-1);

        // 既存マーカーリストをクリア
        markerList.clear();

        // 新しいマーカーリスト設定
        markerList.addAll(newMarkerList);

        // マーカーの個数が変化したことを親クラスに通知
        populate();
    }

    // getLastTapMarkerメソッド(最後にタップしたマーカー取得)
    public static ShopOverlayItem getLastTapMarker() {
        return lastTapMarker;
    }
}
