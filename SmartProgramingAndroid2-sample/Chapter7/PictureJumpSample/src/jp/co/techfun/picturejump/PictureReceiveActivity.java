package jp.co.techfun.picturejump;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.co.techfun.picturejump.bluetooth.BluetoothConstants;
import jp.co.techfun.picturejump.bluetooth.PictureReceiveJob;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

// �摜��M���Activity
public class PictureReceiveActivity extends Activity {

    // Bluetooth�f�o�C�X���o�\���N�G�X�g�R�[�h
    private static final int REQUEST_CODE_BLUETOOTH_DISCOVERABLE = 1;

    // �񓯊����s�p�X���b�h�v�[��
    private ExecutorService executorService;

    // �摜��M����
    private PictureReceiveJob receiveJob;

    // ��M�摜
    private Bitmap picture;

    // �i���_�C�A���O
    private ProgressDialog progressDialog;

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // �񓯊����s�p�̃X���b�h�v�[�����쐬
        executorService = Executors.newCachedThreadPool();

        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.picture_receive);

        // �u�Ď�M�v�{�^���Ƀ��X�i�[�ݒ�
        Button btnReset = (Button) findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new OnClickListener() {
            // onClick���\�b�h(�N���b�N�C�x���g)
            @Override
            public void onClick(View v) {
                // �f�o�C�X�A�摜�\�����N���A
                setDevice(null);
                setPicture(null);
                // ��M�J�n�����Ăяo��
                startReceiving();
            }
        });

        // �u�ۑ��v�{�^���Ƀ��X�i�[�ݒ�
        Button btnStore = (Button) findViewById(R.id.btn_store);
        btnStore.setOnClickListener(new OnClickListener() {
            // onClick���\�b�h(�N���b�N�C�x���g)
            @Override
            public void onClick(View v) {
                // �摜�ۑ������Ăяo��
                storePicture();
            }
        });
    }

    // onStart���\�b�h(��ʕ\���C�x���g)
    @Override
    protected void onStart() {
        super.onStart();

        // �f�o�C�X���o�\�v���p�̃C���e���g���쐬
        Intent intent =
            new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        // ���o�\���ԂƂ���300�b���w��
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
            BluetoothConstants.DURATION);
        // �f�o�C�X���o���\�ɂ��邽�߂ɗv��
        startActivityForResult(intent, REQUEST_CODE_BLUETOOTH_DISCOVERABLE);
    }

    // onDestroy���\�b�h(��ʔj���C�x���g)
    @Override
    protected void onDestroy() {
        // ��M������~
        if (receiveJob != null) {
            receiveJob.stop();
        }
        // �X���b�h�v�[���I��
        executorService.shutdownNow();
        super.onDestroy();
    }

    // onActivityResult���\�b�h(��ʍĕ\�����C�x���g)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // �f�o�C�X���o���\����v����������߂��Ă����ꍇ
        if (requestCode == REQUEST_CODE_BLUETOOTH_DISCOVERABLE) {

            // ���o�\�����[�U�[���I�����Ȃ������ꍇ��
            if (resultCode == RESULT_CANCELED) {
                // �g�[�X�g�Ń��b�Z�[�W�\��
                AppUtil
                    .showToast(this, R.string.bluetooth_can_not_discoverable);
                // �A�N�e�B�r�e�B�I��
                finish();
                return;
            }

            // �摜��M�������J�n
            startReceiving();
        }
    }

    // startReceiving���\�b�h(�摜��M����)
    private void startReceiving() {

        // �i���_�C�A���O�\��
        showProgress();

        // �摜��M������ʃX���b�h�Ŏ��s
        receiveJob = new PictureReceiveJob() {

            // handleReceiveStarted���\�b�h(�摜��M�J�n�C�x���g)
            @Override
            protected void handleReceiveStarted(BluetoothDevice device) {
                // UI�X���b�h���s
                runOnUiThread(new Runnable() {
                    // run���\�b�h(���s����)
                    @Override
                    public void run() {
                        // �_�C�A���O�̃L�����Z���s�ݒ�
                        progressDialog.setCancelable(false);
                        // �i�����b�Z�[�W�X�V
                        updateProgressMessage(AppUtil.getString(
                            PictureReceiveActivity.this,
                            R.string.picture_receive_progress_receiving));
                    }
                });
            }

            // handleReceiveFinished���\�b�h(�摜��M�I���C�x���g)
            @Override
            protected void handleReceiveFinished(
                final BluetoothDevice remoteDevice, final Bitmap picture) {
                // UI�X���b�h���s
                runOnUiThread(new Runnable() {
                    // run���\�b�h(���s����)
                    @Override
                    public void run() {

                        // ���M���f�o�C�X�Ǝ�M�摜��\��
                        setDevice(remoteDevice);
                        setPicture(picture);

                        // �i���_�C�A���O��\���ݒ�
                        hideProgress();
                        // ���b�Z�[�W�\��
                        AppUtil.showToast(PictureReceiveActivity.this,
                            R.string.picture_receive_succeed);
                    }
                });
            }

            // handleProgress���\�b�h(�摜��M�i���ύX�C�x���g)
            @Override
            protected void handleProgress(final int total, final int progress) {
                // UI�X���b�h���s
                runOnUiThread(new Runnable() {
                    // run���\�b�h(���s����)
                    @Override
                    public void run() {
                        // �i���\���؂�ւ�
                        int value = Math.round(progress * 100 / total);
                        updateProgressValue(value);
                    }
                });
            }

            // handleException���\�b�h(�摜��M��O�����C�x���g)
            @Override
            protected void handleException(IOException e) {
                // UI�X���b�h���s
                runOnUiThread(new Runnable() {
                    // run���\�b�h(���s����)
                    @Override
                    public void run() {
                        // �i���_�C�A���O��\��
                        hideProgress();
                        // �G���[���b�Z�[�W�\��
                        AppUtil.showToast(PictureReceiveActivity.this,
                            R.string.picture_receive_failed);
                    }
                });
            }
        };
        // ��M�����J�n
        executorService.submit(receiveJob);
    }

    // setDevice���\�b�h(�f�o�C�X���\������)
    private void setDevice(BluetoothDevice device) {
        TextView textView = (TextView) findViewById(R.id.tv_received_device);
        textView.setText(device != null ? device.getName() : "");
    }

    // setPicture���\�b�h(�摜�\������)
    private void setPicture(Bitmap picture) {
        this.picture = picture;
        ImageView imageView =
            (ImageView) findViewById(R.id.iv_received_picture);
        imageView.setImageBitmap(AppUtil.resizePicture(picture, 180, 180));
    }

    // storePicture���\�b�h(�摜�ۑ�����)
    private void storePicture() {

        if (picture != null) {

            try {

                // SD�J�[�h�̃��[�g�f�B���N�g���擾
                File dir = Environment.getExternalStorageDirectory();
                File baseDir = new File(dir, "picture_jump");
                baseDir.mkdirs();

                // ���t�`���̃t�@�C�����ŉ摜��SD�J�[�h�ɕۑ�
                SimpleDateFormat format =
                    new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

                FileOutputStream out =
                    new FileOutputStream(new File(baseDir, format
                        .format(new Date())
                        + ".png"));
                picture.compress(CompressFormat.PNG, 100, out);

                // ���b�Z�[�W�\��
                AppUtil.showToast(this, AppUtil.getString(this,
                    R.string.picture_save_succeed));

            } catch (FileNotFoundException e) {
                Log.e(getClass().getSimpleName(), "picture store failed.", e);
                AppUtil.showToast(this, AppUtil.getString(this,
                    R.string.picture_save_failed));
            }
        }

    }

    // showProgress���\�b�h(�i���_�C�A���O�\������)
    private void showProgress() {
        // �_�C�A���O����
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(AppUtil.getString(this,
            R.string.picture_receive_progress_title));
        progressDialog.setMessage(AppUtil.getString(this,
            R.string.picture_receive_progress_waiting));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);

        // �_�C�A���O�ɃL�����Z�����X�i�[�ݒ�
        progressDialog.setOnCancelListener(new OnCancelListener() {
            // onCancel���\�b�h(�L�����Z�����C�x���g)
            @Override
            public void onCancel(DialogInterface dialog) {
                // �����I��
                finish();
            }
        });

        progressDialog.show();
    }

    // hideProgress���\�b�h(�i���_�C�A���O��\������)
    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    // updateProgressMessage���\�b�h(�i�����b�Z�[�W�X�V����)
    private void updateProgressMessage(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage(message);
        }
    }

    // updateProgressValue���\�b�h(�i���l�X�V����)
    private void updateProgressValue(int value) {
        if (progressDialog != null) {
            progressDialog.setProgress(value);
        }
    }
}
