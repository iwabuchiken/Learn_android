package jp.co.techfun.lunchmap;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

// 店情報格納用クラス
class ShopOverlayItem extends OverlayItem {

    // 平均予算
    private Integer price;

    // 評価
    private int shopRate;

    // コンストラクタ
    public ShopOverlayItem(GeoPoint geoPoint, String shopName, int shopRate,
        Integer price, String comment) {

        // 緯度・経度・店名・コメントを設定
        super(geoPoint, shopName, comment);

        // 平均予算を設定
        this.price = price;

        // 評価を設定
        this.shopRate = shopRate;
    }

    // 店名を返すメソッド
    public String getShopName() {
        return this.getTitle();
    }

    // 評価を返すメソッド
    public int getShopRate() {
        return shopRate;
    }

    // 平均予算を返すメソッド
    public Integer getPrice() {
        return price;
    }

    // コメントを返すメソッド
    public String getComment() {
        return this.getSnippet();
    }

    // 文字列表現を返すメソッド
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("店名：" + getTitle() + "\n");
        sb.append("評価：" + shopRate + "\n");
        sb.append("平均予算：" + price + "\n");
        sb.append("コメント：" + getSnippet());
        return sb.toString();
    }
}
