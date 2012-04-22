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

// �����`�}�b�v���Activity
public class LunchMapSampleActivity extends MapActivity {

    // �X�����͉�ʂƂ̃C���e���g�p���N�G�X�g�R�[�h
    private static final int REQUEST_CODE_INPUT = 0;

    // ���j���[�A�C�e��ID
    // �A�C�e���ǉ��{�^���p
    private static final int MENU_ITEM_ID_ADD = 0;

    // �A�C�e���폜�{�^���p
    private static final int MENU_ITEM_ID_DELETE = 1;

    // �}�[�J�[�쐬����GeoPoint��ێ�����
    private GeoPoint geoPoint;

    // �}�[�J�[�\���p�I�[�o�[���C
    private MarkerOverlay[] markerOverlays;

    // �}�[�J�[���ۑ��pDB�C���X�^���X
    private MarkerDbUtil dbUtil;

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g��ݒ�
        setContentView(R.layout.main);

        // DBUtil�C���X�^���X����
        dbUtil = new MarkerDbUtil(this);

        // Google Map �\���N���X
        MapView mapView = (MapView) findViewById(R.id.mapview);

        // �Y�[���R���g���[�����g�p����悤�Ɏw��
        mapView.setBuiltInZoomControls(true);

        // �R���g���[�����g�p���ăY�[�����x����ݒ�
        MapController mapController = mapView.getController();
        mapController.setZoom(15);

        // ���ݒn�\���I�[�o�[���C�𐶐�
        MyLocationOverlay myOverlay =
            new MyLocationOverlay(getApplicationContext(), mapView);

        // GPS�@�\�L���ݒ�
        myOverlay.onProviderEnabled(LocationManager.GPS_PROVIDER);

        // GPS�Ŏ擾�������ݒn��\��
        myOverlay.enableMyLocation();

        // MapView�Ɍ��ݒn�\���I�[�o�[���C�ݒ�
        mapView.getOverlays().add(myOverlay);

        // ���񌻍ݒn���擾�X���b�h�N��
        myOverlay.runOnFirstFix(firstUpdateMyLocationTh);

        // �}�[�J�[�摜�ݒ�
        // �]���̍ő�l���擾
        Integer rbNumStarts =
            Integer.parseInt(getString(R.string.rb_num_starts));

        // �}�[�J�[�摜���\�[�XID��z��
        int[] drawableResIds =
            { R.drawable.shop_rate_1, R.drawable.shop_rate_2,
                    R.drawable.shop_rate_3, R.drawable.shop_rate_4,
                    R.drawable.shop_rate_5 };

        // �}�[�J�[�摜�p�z��𐶐�
        Drawable[] shopRateMarkers = new Drawable[rbNumStarts];

        // �}�[�J�[�\���I�[�o�[���C�p�z��𐶐�
        markerOverlays = new MarkerOverlay[rbNumStarts];

        // �]�����̃}�[�J�[�\���I�[�o�[���C�𐶐����AMapView�ɐݒ�
        for (int rateIndex = 0; rateIndex < rbNumStarts; rateIndex++) {
            // �}�[�J�[�摜�ݒ�
            shopRateMarkers[rateIndex] =
                getResources().getDrawable(drawableResIds[rateIndex]);

            // �e�}�[�J�[�摜�̑傫����ݒ�
            shopRateMarkers[rateIndex].setBounds(0, 0,
                shopRateMarkers[rateIndex].getMinimumWidth(),
                shopRateMarkers[rateIndex].getMinimumHeight());

            // �}�[�J�[�\���I�[�o�[���C����
            markerOverlays[rateIndex] =
                new MarkerOverlay(shopRateMarkers[rateIndex], this);

            // MapView�ɐݒ�
            mapView.getOverlays().add(markerOverlays[rateIndex]);
        }
    }

    // onResume���\�b�h(��ʕ\���O�C�x���g)
    @Override
    protected void onResume() {
        super.onResume();

        // �e�]���̓X�܃}�[�J�[��\��
        int rateIndex = 1;
        for (MarkerOverlay overlay : markerOverlays) {
            // DB�������]���̓X�܃}�[�J�[���X�g���擾
            List<ShopOverlayItem> newMarkerList = dbUtil.getMarker(rateIndex++);

            // �擾�����}�[�J�[��\��
            overlay.setNewMarkerList(newMarkerList);
        }
    }

    // onActivityResult���\�b�h(���C����ʍĕ\�����C�x���g)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE_INPUT || resultCode != RESULT_OK) {
            return;
        }
        // �����Ŗ߂��Ă����l���擾
        Bundle extra = data.getExtras();

        // �X��
        String shopName = extra.getString(InputShopInfoActivity.SHOP_NAME_KEY);

        // �]��
        int shopRate = extra.getInt(InputShopInfoActivity.SHOP_RATE_KEY);
        // �ŏ��l��1�Ƃ���
        shopRate = shopRate <= 0 ? 1 : shopRate;

        // ���ϗ\�Z
        Integer price = extra.getInt(InputShopInfoActivity.PRICE_KEY);

        // �R�����g
        String comment = extra.getString(InputShopInfoActivity.COMMENT_KEY);

        // �V�����X�}�[�J�[�𐶐�
        ShopOverlayItem newMarker =
            new ShopOverlayItem(geoPoint, shopName, shopRate, price, comment);

        // DB�փ}�[�J�[��o�^
        dbUtil.addMarker(newMarker);

        // �}�[�J�[��\��
        markerOverlays[shopRate - 1].addPoint(newMarker);
    }

    // isRouteDisplayed���\�b�h(���[�g����\�����邩�ۂ�)
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    // onCreateOptionsMenu���\�b�h(�I�v�V�������j���[����)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // ���j���[�A�C�e���̒ǉ�(�}�[�J�[�ǉ�)
        MenuItem menuItem1 =
            menu.add(0, MENU_ITEM_ID_ADD, 0, R.string.add_menu_text);
        menuItem1.setIcon(android.R.drawable.ic_menu_add);

        // ���j���[�A�C�e���̒ǉ�(�}�[�J�[�폜)
        MenuItem menuItem2 =
            menu.add(0, MENU_ITEM_ID_DELETE, 0, R.string.delete_menu_text);
        menuItem2.setIcon(android.R.drawable.ic_menu_add);

        return true;
    }

    // onOptionsItemSelected���\�b�h(���j���[�I�����C�x���g)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Google Map �\���N���X
        MapView mapView = (MapView) findViewById(R.id.mapview);

        // ���ݒn�̃W�I�|�C���g�擾
        geoPoint = mapView.getMapCenter();

        // �I�����ꂽ���j���[�A�C�e���𔻒�
        switch (item.getItemId()) {

        // �A�C�e���ǉ��{�^���̏���
        case MENU_ITEM_ID_ADD:
            // �C���e���g�̐���(�Ăяo���N���X�̎w��)
            Intent intent = new Intent(this, InputShopInfoActivity.class);

            // �X�����͗p�̃A�N�e�B�r�e�B�̋N��
            startActivityForResult(intent, REQUEST_CODE_INPUT);
            break;

        // �A�C�e���폜�{�^���̏���
        case MENU_ITEM_ID_DELETE:
            // �Ō�Ƀ^�b�v�����}�[�J�[���擾
            ShopOverlayItem lastTapMarker = MarkerOverlay.getLastTapMarker();

            // �Ō�Ƀ^�b�v�����}�[�J�[���擾�ł��Ȃ������ꍇ
            if (lastTapMarker ==null) {
            	return true;
            }
            
            // �Ō�Ƀ^�b�v�����}�[�J�[��DB����폜
            dbUtil.deleteMarker(lastTapMarker);

            // �Ō�Ƀ^�b�v�����}�[�J�[���I�[�o�[���C����폜
            markerOverlays[lastTapMarker.getShopRate() - 1].deleteItem();
            break;

        default:
            // �����Ȃ�
            break;
        }

        // ��ʂ��ĕ`��
        mapView.invalidate();
        return true;
    }

    // ���񌻍ݒn���擾�X���b�h
    private Runnable firstUpdateMyLocationTh = new Runnable() {

        @Override
        public void run() {
            // Google Map View
            MapView mapView = (MapView) findViewById(R.id.mapview);

            // ���ݒn�\���I�[�o�[���C(MyLocationOverlay)
            MyLocationOverlay myOverlay =
                (MyLocationOverlay) mapView.getOverlays().get(0);

            // ���񌻍ݒn��񂪎擾�����܂ŌJ��Ԃ�
            while (true) {
                // ���ݒn���擾
                GeoPoint firstLoc = myOverlay.getMyLocation();

                // ���ݒn���擾�ł����ꍇ
                if (firstLoc != null) {

                    // ��ʒ����ɕ\��
                    mapView.getController().setCenter(firstLoc);

                    // ���ݒn�擾�����I��
                    break;
                }
            }
        }
    };
}
