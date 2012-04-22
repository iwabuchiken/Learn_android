package jp.co.techfun.picturejump;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//�g�b�v���Activity
public class PictureJumpActivity extends Activity {

    // Bluetooth�L�������N�G�X�g�R�[�h
    private static final int REQUEST_CODE_BLUETOOTH_ENABELD = 0;

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.picture_jump);
        
        // �A�v���P�[�V�����p���[�e�B���e�B��������
        AppUtil.initialize(this);

        // �u�摜�𑗐M�v�{�^���Ƀ��X�i�[�ݒ�
        Button btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new OnClickListener() {
            // onClick���\�b�h(�N���b�N�C�x���g)
            @Override
            public void onClick(View view) {
                // �摜���M��ʂ֑J��
                Intent intent =
                    new Intent(PictureJumpActivity.this,
                        PictureSendActivity.class);
                startActivity(intent);
            }
        });

        // �u�摜����M�v�{�^���Ƀ��X�i�[�ݒ�
        Button btnReceive = (Button) findViewById(R.id.btn_receive);
        btnReceive.setOnClickListener(new OnClickListener() {
            // onClick���\�b�h(�N���b�N�C�x���g)
            @Override
            public void onClick(View view) {
                // �摜��M��ʂ֑J��
                Intent intent =
                    new Intent(PictureJumpActivity.this,
                        PictureReceiveActivity.class);
                startActivity(intent);
            }
        });
    }

    // onStart���\�b�h(��ʕ\���C�x���g)
    @Override
    protected void onStart() {
        super.onStart();

        // BluetoothAdapter�I�u�W�F�N�g�擾
        BluetoothAdapter bluetoothAdapter =
            BluetoothAdapter.getDefaultAdapter();
        // Bluetooth���T�|�[�g����Ă��Ȃ��[���̏ꍇ
        if (bluetoothAdapter == null) {
            // �A�v���P�[�V�����I��
            AppUtil.showToast(this, R.string.bluetooth_not_supported);
            finish();
            return;
        }
        // Bluetooth���L���ɐݒ肳��Ă��Ȃ��ꍇ
        if (!bluetoothAdapter.isEnabled()) {
            // Bluetooth�L���ݒ��ʌĂяo��
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_CODE_BLUETOOTH_ENABELD);
            return;
        }
    }

    // onActivityResult���\�b�h(���C����ʍĕ\�����C�x���g)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Bluetooth�L���ݒ��ʂ���߂��Ă����ꍇ
        if (requestCode == REQUEST_CODE_BLUETOOTH_ENABELD) {
            // Bluetooth���L���ɐݒ�ł��Ȃ��ꍇ
            if (resultCode != RESULT_OK) {
                // �A�v���P�[�V�����I��
                AppUtil.showToast(this, R.string.bluetooth_not_valid);
                finish();
                return;
            }
        }
    }
}
