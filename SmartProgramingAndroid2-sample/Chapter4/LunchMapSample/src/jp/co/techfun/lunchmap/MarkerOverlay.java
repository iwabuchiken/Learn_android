package jp.co.techfun.lunchmap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

// �}�[�J�[�\���I�[�o�[���C
public class MarkerOverlay extends ItemizedOverlay<OverlayItem> {

    // �R���e�L�X�g��`(�g�[�X�g, �폜�m�F�_�C�A���O�\���p)
    private Context context;

    // �I�[�o�[���C�}�[�J�[�i�[�p���X�g
    private List<ShopOverlayItem> markerList;

    // �Ō�Ƀ^�b�v���ꂽ�}�[�J�[�̃��X�g�ԍ�
    private int lastTapMarkerIndex;

    // �Ō�Ƀ^�b�v�����}�[�J�[�̕]��
    private static ShopOverlayItem lastTapMarker;

    // �R���X�g���N�^
    public MarkerOverlay(Drawable drawable, Context con) {
        super(boundCenterBottom(drawable));

        // �I�[�o�[���C�}�[�J�[�i�[�p���X�g�𐶐�
        markerList = new ArrayList<ShopOverlayItem>();

        // �R���e�L�X�g��`��ݒ�
        this.context = con;

        // �}�[�J�[�̌����ω��������Ƃ�e�N���X�ɒʒm
        populate();
    }

    // createItem���\�b�h(�I�[�o�[���C�}�[�J�[����)
    @Override
    protected ShopOverlayItem createItem(int i) {
        return markerList.get(i);
    }

    // size���\�b�h(���X�g�T�C�Y�擾)
    @Override
    public int size() {
        return markerList.size();
    }

    // addPoint���\�b�h(�}�[�J�[�ǉ�)
    public void addPoint(ShopOverlayItem newMarker) {

        // �}�[�J�[���X�g�֒ǉ�
        markerList.add(newMarker);

        // �}�[�J�[�̌����ω��������Ƃ�e�N���X�ɒʒm
        populate();
    }

    // deleteItem���\�b�h(���O�ɑI�������}�[�J�[�폜)
    public void deleteItem() {
        if (lastTapMarkerIndex < markerList.size()) {

            // �}�[�J�[���폜
            markerList.remove(lastTapMarkerIndex);

            // �}�[�J�[����t�H�[�J�X���O��
            setLastFocusedIndex(-1);

            // �}�[�J�[�̌����ω��������Ƃ�e�N���X�ɒʒm
            populate();
        }
    }

    // onTap���\�b�h(�}�[�J�[�^�b�v���C�x���g)
    @Override
    protected boolean onTap(int index) {
        // �^�b�v�����}�[�J�[�̃��X�g�ԍ�
        lastTapMarkerIndex = index;

        // �I�������}�[�J�[�̃C���X�^���X���擾
        ShopOverlayItem item = markerList.get(index);

        // �Ō�Ƀ^�b�v�����}�[�J�[�Ƃ��Đݒ�
        lastTapMarker = item;

        // �g�[�X�g�Ƀ}�[�J�[�̏���\��
        Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show();

        return true;
    }

    // setNewMarkerList���\�b�h(�V�����}�[�J�[���X�g�ݒ�)
    public void setNewMarkerList(List<ShopOverlayItem> newMarkerList) {
        // �}�[�J�[����t�H�[�J�X���O��
        setLastFocusedIndex(-1);

        // �����}�[�J�[���X�g���N���A
        markerList.clear();

        // �V�����}�[�J�[���X�g�ݒ�
        markerList.addAll(newMarkerList);

        // �}�[�J�[�̌����ω��������Ƃ�e�N���X�ɒʒm
        populate();
    }

    // getLastTapMarker���\�b�h(�Ō�Ƀ^�b�v�����}�[�J�[�擾)
    public static ShopOverlayItem getLastTapMarker() {
        return lastTapMarker;
    }
}
