package jp.co.techfun.picturejump.bluetooth;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

// �摜���M�����N���X
public class PictureSendJob implements Runnable {

    // ���M�f�o�C�X�@
    private BluetoothDevice device;

    // ���M�摜
    private Bitmap picture;

    // �R���X�g���N�^
    public PictureSendJob(BluetoothDevice device, Bitmap picture) {
        this.device = device;
        this.picture = picture;
    }

    // run���\�b�h(�񓯊�����)
    @Override
    public void run() {
        try {
            BluetoothSocket socket = null;

            try {

                // BluetoothAdapter�I�u�W�F�N�g�擾
                BluetoothAdapter bluetoothAdapter =
                    BluetoothAdapter.getDefaultAdapter();
                // �f�o�C�X�������̏ꍇ
                if (bluetoothAdapter.isDiscovering()) {
                    // �������~
                    bluetoothAdapter.cancelDiscovery();
                }

                // ���M�p�̃\�P�b�g����
                socket =
                    device
                        .createRfcommSocketToServiceRecord(
                        		BluetoothConstants.SERIAL_PORT_PROFILE);

                // �f�o�C�X�ɐڑ�
                socket.connect();

                // �摜���M�J�n���̏���
                handleSendStarted(device);

                // �摜���o�͗p�I�u�W�F�N�g����
                ByteArrayOutputStream imageOutput = new ByteArrayOutputStream();
                picture.compress(CompressFormat.PNG, 100, imageOutput);
                ByteArrayInputStream imageIn =
                    new ByteArrayInputStream(imageOutput.toByteArray());

                // �\�P�b�g���o�͗p�I�u�W�F�N�g����
                DataOutputStream sockOut =
                    new DataOutputStream(socket.getOutputStream());
                DataInputStream sockIn =
                    new DataInputStream(socket.getInputStream());

                // �摜�T�C�Y�擾
                int total = imageOutput.size();
                int progress = 0;

                int len = -1;
                byte[] buff = new byte[1024];

                // �摜�T�C�Y��������
                sockOut.writeInt(total);

                // �摜��񏑂�����
                while ((len = imageIn.read(buff)) != -1) {
                    sockOut.write(buff, 0, len);
                    progress += len;
                    handleProgress(total, progress);
                }

                sockOut.flush();

                // ���M���ʎ擾
                int result = sockIn.readInt();
                if (result != BluetoothConstants.CONNECT_SUCCESS) {
                    throw new IOException("connect failed.");
                }

                // �摜���M�I�����̏���
                handleSendFinish();

            } finally {

                if (socket != null) {
                    socket.close();
                }
            }
        } catch (IOException e) {
            handleException(e);
        }
    }

    // �摜���M�J�n���̏������T�u�N���X���ŋL�q���邽�߂̃e���v���[�g���\�b�h
    protected void handleSendStarted(BluetoothDevice device) {
    }

    // �摜���M�I�����̏������T�u�N���X���ŋL�q���邽�߂̃e���v���[�g���\�b�h
    protected void handleSendFinish() {
    }

    // �摜���M���̏������T�u�N���X���ŋL�q���邽�߂̃e���v���[�g���\�b�h
    protected void handleProgress(int total, int progress) {
    }

    // �摜���M���s���̏������T�u�N���X���ŋL�q���邽�߂̃e���v���[�g���\�b�h
    protected void handleException(IOException e) {
    }
}
