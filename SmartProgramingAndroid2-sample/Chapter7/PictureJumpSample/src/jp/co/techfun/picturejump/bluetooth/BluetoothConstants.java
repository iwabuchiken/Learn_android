package jp.co.techfun.picturejump.bluetooth;

import java.util.UUID;

// Bluetooth�ڑ��p�萔��`�N���X
public final class BluetoothConstants {

    // �R���X�g���N�^
    private BluetoothConstants() {
    }

    // �T�[�r�X��
    public static final String SERVICE_NAME = "Picture Jump";

    // UUID�i��ӂȎ��ʎq�j
    public static final UUID SERIAL_PORT_PROFILE =
        UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    // ��M�����ʒm�p�萔
    public static final int CONNECT_SUCCESS = 1;

    // ���g�̃f�o�C�X�𑼂̃f�o�C�X���甭���\�Ƃ������
    public static final int DURATION = 300;
}
