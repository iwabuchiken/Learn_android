package jp.co.techfun.picturejump.bluetooth;

import jp.co.techfun.picturejump.R;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// ���o�f�o�C�X�ꗗ�\���pAdapter
public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {

    // �R���X�g���N�^
    public BluetoothDeviceAdapter(Context context) {
        super(context, 0);
    }

    // getView���\�b�h(�ꗗ�p�r���[�擾)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        // convertView��null�̏ꍇ
        if (convertView == null) {
            // ���C�A�E�gdevice_item.xml���r���[�I�u�W�F�N�g�Ƃ��Ď擾
            view =
                LayoutInflater.from(getContext()).inflate(R.layout.device_item,
                    null);
            // convertView��null�łȂ��ꍇ
        } else {
            // �����n���ꂽ�r���[��ݒ�
            view = convertView;
        }

        // ���X�g����BluetoothDevice�I�u�W�F�N�g�擾
        BluetoothDevice device = getItem(position);

        // �f�o�C�X���\���pTextView�Ƀf�o�C�X���ݒ�
        TextView deviceName = (TextView) view.findViewById(R.id.tv_device_name);
        deviceName.setText(device.getName());

        // MAC�A�h���X�\���pTextView��MAC�A�h���X�ݒ�
        TextView macAddress = (TextView) view.findViewById(R.id.tv_mac_address);
        macAddress.setText(device.getAddress());

        return view;
    }
}
