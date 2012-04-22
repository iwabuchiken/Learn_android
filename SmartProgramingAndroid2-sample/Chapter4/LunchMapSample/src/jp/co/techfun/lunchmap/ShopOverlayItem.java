package jp.co.techfun.lunchmap;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

// �X���i�[�p�N���X
class ShopOverlayItem extends OverlayItem {

    // ���ϗ\�Z
    private Integer price;

    // �]��
    private int shopRate;

    // �R���X�g���N�^
    public ShopOverlayItem(GeoPoint geoPoint, String shopName, int shopRate,
        Integer price, String comment) {

        // �ܓx�E�o�x�E�X���E�R�����g��ݒ�
        super(geoPoint, shopName, comment);

        // ���ϗ\�Z��ݒ�
        this.price = price;

        // �]����ݒ�
        this.shopRate = shopRate;
    }

    // �X����Ԃ����\�b�h
    public String getShopName() {
        return this.getTitle();
    }

    // �]����Ԃ����\�b�h
    public int getShopRate() {
        return shopRate;
    }

    // ���ϗ\�Z��Ԃ����\�b�h
    public Integer getPrice() {
        return price;
    }

    // �R�����g��Ԃ����\�b�h
    public String getComment() {
        return this.getSnippet();
    }

    // ������\����Ԃ����\�b�h
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("�X���F" + getTitle() + "\n");
        sb.append("�]���F" + shopRate + "\n");
        sb.append("���ϗ\�Z�F" + price + "\n");
        sb.append("�R�����g�F" + getSnippet());
        return sb.toString();
    }
}
