package jp.co.techfun.picturejump;

import jp.co.techfun.picturejump.bluetooth.BluetoothDeviceAdapter;
import jp.co.techfun.picturejump.bluetooth.BluetoothDeviceDescoveryReceiver;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

// ���M�f�o�C�X�I�����Activity
public class DeviceSelectActivity extends ListActivity {

    // �f�o�C�X�ꗗ�\���p�A�_�v�^�[
    private BluetoothDeviceAdapter adapter;

    // ���o�f�o�C�X�擾�p���V�[�o�[
    private BluetoothDeviceDescoveryReceiver receiver;

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_select);

        // �f�o�C�X���X�g�\���p�A�_�v�^�[����
        adapter = new BluetoothDeviceAdapter(this);
        // ���o�f�o�C�X�擾�p���V�[�o�[����
        receiver = new BluetoothDeviceDescoveryReceiver(this, adapter);
        // �\���p�A�_�v�^�[�ݒ�
        setListAdapter(adapter);

        // �u�f�o�C�X�����v�{�^���Ƀ��X�i�[�ݒ�
        Button button = (Button) findViewById(R.id.btn_search_device);
        button.setOnClickListener(new OnClickListener() {
            // onClick���\�b�h(�N���b�N�C�x���g)
            @Override
            public void onClick(View v) {
                // BluetoothAdapter�I�u�W�F�N�g�擾
                BluetoothAdapter bluetoothAdapter =
                    BluetoothAdapter.getDefaultAdapter();

                // �f�o�C�X�������̏ꍇ
                if (bluetoothAdapter.isDiscovering()) {
                    // �������~
                    bluetoothAdapter.cancelDiscovery();
                }
                // �V�K�f�o�C�X�����J�n
                bluetoothAdapter.startDiscovery();
            }
        });
    }

    // getBluetoothDeviceAdapter���\�b�h(�f�o�C�X�ꗗ�\���p�A�_�v�^�[�擾�j
    private BluetoothDeviceAdapter getBluetoothDeviceAdapter() {
        return (BluetoothDeviceAdapter) getListAdapter();
    }

    // onStart���\�b�h(��ʕ\���C�x���g)
    @Override
    protected void onStart() {
        super.onStart();

        // BluetoothAdapter�I�u�W�F�N�g�擾
        BluetoothAdapter bluetoothAdapter =
            BluetoothAdapter.getDefaultAdapter();

        // ���Ƀy�A�����O����Ă���f�o�C�X�擾
        for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
            getBluetoothDeviceAdapter().add(device);
        }
    }

    // onResume���\�b�h(���͎�t�J�n�C�x���g)
    @Override
    protected void onResume() {
        super.onResume();
        // ���o�f�o�C�X�擾�p���V�[�o�[�o�^
        receiver.register();
    }

    // onPause���\�b�h(��ʔ�\���C�x���g)
    @Override
    protected void onPause() {
        super.onPause();
        // �f�o�C�X�擾�p���V�[�o�[�폜
        receiver.unregister();
    }

    // onListItemClick���\�b�h(���X�g�I��������)
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // �I�����ꂽ�f�o�C�X�擾
        BluetoothDevice device = adapter.getItem(position);
        // �I�����ꂽ�f�o�C�X���C���e���g�ɐݒ�
        getIntent().putExtra(BluetoothDevice.EXTRA_DEVICE, device);
        setResult(RESULT_OK, getIntent());
        // ���M�f�o�C�X��ʃN���[�Y
        finish();
    }
}
