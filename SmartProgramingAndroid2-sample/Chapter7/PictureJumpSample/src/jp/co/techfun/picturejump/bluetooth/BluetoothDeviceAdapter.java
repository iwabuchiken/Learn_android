package jp.co.techfun.picturejump.bluetooth;

import jp.co.techfun.picturejump.R;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// 検出デバイス一覧表示用Adapter
public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {

    // コンストラクタ
    public BluetoothDeviceAdapter(Context context) {
        super(context, 0);
    }

    // getViewメソッド(一覧用ビュー取得)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        // convertViewがnullの場合
        if (convertView == null) {
            // レイアウトdevice_item.xmlをビューオブジェクトとして取得
            view =
                LayoutInflater.from(getContext()).inflate(R.layout.device_item,
                    null);
            // convertViewがnullでない場合
        } else {
            // 引き渡されたビューを設定
            view = convertView;
        }

        // リストからBluetoothDeviceオブジェクト取得
        BluetoothDevice device = getItem(position);

        // デバイス名表示用TextViewにデバイス名設定
        TextView deviceName = (TextView) view.findViewById(R.id.tv_device_name);
        deviceName.setText(device.getName());

        // MACアドレス表示用TextViewにMACアドレス設定
        TextView macAddress = (TextView) view.findViewById(R.id.tv_mac_address);
        macAddress.setText(device.getAddress());

        return view;
    }
}
