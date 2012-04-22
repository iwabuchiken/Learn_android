package jp.co.techfun.picturejump.bluetooth;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

// �摜��M�����N���X
public class PictureReceiveJob implements Runnable {

    // �T�[�o�[�\�P�b�g
    private BluetoothServerSocket serverSocket;

    // ��M��~�t���O
    private volatile boolean stop;

    // stop���\�b�h(��M�I������)
    public void stop() {
        // �T�[�o�[�\�P�b�g��null�̏ꍇ
        if (serverSocket != null) {
            try {
                // ��M��~�t���O�ݒ�
                stop = true;
                // �T�[�o�[�\�P�b�g�N���[�Y
                serverSocket.close();
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "stop listening failed", e);
            }
        }
    }

    // run���\�b�h(�񓯊�����)
    @Override
    public void run() {
        try {

            // BluetoothAdapter�I�u�W�F�N�g�擾
            BluetoothAdapter bluetoothAdapter =
                BluetoothAdapter.getDefaultAdapter();

            // �ڑ��ҋ@�p�̃T�[�o�[�\�P�b�g�擾
            serverSocket =
                bluetoothAdapter.listenUsingRfcommWithServiceRecord(
                    BluetoothConstants.SERVICE_NAME,
                    BluetoothConstants.SERIAL_PORT_PROFILE);

            BluetoothSocket socket = null;

            try {

                // ���[������ڑ������܂őҋ@
                socket = serverSocket.accept();

                // �摜��M�J�n���̏���
                handleReceiveStarted(socket.getRemoteDevice());

                // �\�P�b�g���o�͗p�I�u�W�F�N�g����
                DataInputStream sockIn =
                    new DataInputStream(socket.getInputStream());
                DataOutputStream sockOut =
                    new DataOutputStream(socket.getOutputStream());

                // �摜�o�̓o�b�t�@����
                ByteArrayOutputStream imageOut = new ByteArrayOutputStream();

                // �摜�T�C�Y�擾
                int total = sockIn.readInt();
                int progress = 0;

                int len = -1;
                byte[] buff = new byte[1024];

                // �摜�T�C�Y�ɂȂ�܂ŁA�ǂݍ���
                while (total > progress) {
                    len = sockIn.read(buff);
                    imageOut.write(buff, 0, len);
                    progress += len;
                    handleProgress(total, progress);
                }

                imageOut.flush();

                // �摜��M�������o��
                sockOut.writeInt(BluetoothConstants.CONNECT_SUCCESS);

                // �r�b�g�}�b�v�ɕϊ�
                Bitmap picture =
                    BitmapFactory.decodeByteArray(imageOut.toByteArray(), 0,
                        imageOut.size());

                // �摜��M�I�����̏���
                handleReceiveFinished(socket.getRemoteDevice(), picture);

            } finally {
                // �\�P�b�g�N���[�Y
                if (socket != null) {
                    socket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            }
        } catch (IOException e) {
            // ��~���ɔ��������O�͖���
            if (!stop) {
                handleException(e);
            }
        }
    }

    // �摜��M�J�n���̏������T�u�N���X���ŋL�q���邽�߂̃e���v���[�g���\�b�h
    protected void handleReceiveStarted(BluetoothDevice device) {
    }

    // �摜��M�I�����̏������T�u�N���X���ŋL�q���邽�߂̃e���v���[�g���\�b�h
    protected void handleReceiveFinished(BluetoothDevice device, Bitmap picture) {
    }

    // �摜��M�r���̏������T�u�N���X���ŋL�q���邽�߂̃e���v���[�g���\�b�h
    protected void handleProgress(int total, int progress) {
    }

    // �摜�擾���s���̏������T�u�N���X���ŋL�q���邽�߂̃e���v���[�g���\�b�h
    protected void handleException(IOException e) {
    }
}
