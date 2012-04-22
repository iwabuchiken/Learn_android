package jp.co.techfun.picturejump.bluetooth;

import jp.co.techfun.picturejump.AppUtil;
import jp.co.techfun.picturejump.R;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;

// �f�o�C�X�����ʒm���󂯂�u���[�h�L���X�g���V�[�o�[
public class BluetoothDeviceDescoveryReceiver extends BroadcastReceiver {

    // ���o�f�o�C�X�ꗗ�\���p�A�_�v�^�[
    private BluetoothDeviceAdapter adapter;

    // �Ăяo�����R���e�L�X�g
    private Context context;

    // �i���_�C�A���O
    private ProgressDialog progressDialog;

    // �R���X�g���N�^
    public BluetoothDeviceDescoveryReceiver(Context context,
        BluetoothDeviceAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    // onReceive���\�b�h(�f�o�C�X�����ʒm�C�x���g)
    @Override
    public void onReceive(Context context, Intent intent) {
        // �C���e���g�̃A�N�V�����擾
        String action = intent.getAction();

        // �f�o�C�X���o�J�n�̏ꍇ
        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
            // �i���_�C�A���O�\��
            showProgress();
        }

        // �f�o�C�X���o�I���̏ꍇ
        if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            // �i���_�C�A���O��\��
            hideProgress();
        }

        // �f�o�C�X�����̏ꍇ
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // �f�o�C�X��\���p�A�_�v�^�[�ɐݒ�
            addDevice(intent);
        }

        // �f�o�C�X���ύX�̏ꍇ
        if (BluetoothDevice.ACTION_NAME_CHANGED.equals(action)) {
            // �f�o�C�X��\���p�A�_�v�^�[�ɐݒ�f
            addDevice(intent);
        }
    }

    // addDevice���\�b�h(�f�o�C�X��\���p�A�_�v�^�[�ɐݒ菈��)
    private void addDevice(Intent intent) {
        // �f�o�C�X�I�u�W�F�N�g�擾
        BluetoothDevice device =
            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        // �f�o�C�X���������ȏꍇ
        if (device.getName() == null) {
            // �ݒ肹���ɏI��
            return;
        }

        // �f�o�C�X�����Ƀy�A�����O����Ă���ꍇ
        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            // �ݒ肹���ɏI��
            return;
        }

        // �f�o�C�X�����Ɉꗗ�ɕ\������Ă���ꍇ
        for (int i = 0; i < adapter.getCount(); i++) {
            if (device.equals(adapter.getItem(i))) {
                // �ݒ肹���ɏI��
                return;
            }
        }

        // �A�_�v�^�[�Ƀf�o�C�X�ݒ�
        adapter.add(device);
    }

    // register���\�b�h(���V�[�o�[�o�^����)
    public void register() {
        // �C���e���g�t�B���^����
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED);
        // �R���e�L�X�g�Ƀ��V�[�o�[�ݒ�
        context.registerReceiver(this, filter);
    }

    // unregister���\�b�h(���V�[�o�[�폜����)
    public void unregister() {
        // �R���e�L�X�g���烌�V�[�o�[�폜
        if (context != null) {
            context.unregisterReceiver(this);
        }
    }

    // showProgress���\�b�h(�i���_�C�A���O�\������)
    private void showProgress() {
        // �_�C�A���O����
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(AppUtil.getString(context,
            R.string.device_search_title));
        progressDialog.setMessage(AppUtil.getString(context,
            R.string.device_search_progress));
        progressDialog.setCancelable(true);

        // �_�C�A���O�ɃL�����Z�����X�i�[�ݒ�
        progressDialog.setOnCancelListener(new OnCancelListener() {
            // onCancel���\�b�h(�L�����Z���C�x���g)
            @Override
            public void onCancel(DialogInterface dialog) {
                // BluetoothAdapter�I�u�W�F�N�g�擾
                BluetoothAdapter bluetoothAdapter =
                    BluetoothAdapter.getDefaultAdapter();
                // �������~
                bluetoothAdapter.cancelDiscovery();
            }
        });
        // �_�C�A���O�\��
        progressDialog.show();
    }

    // hideProgress���\�b�h(�i���_�C�A���O��\������)
    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
