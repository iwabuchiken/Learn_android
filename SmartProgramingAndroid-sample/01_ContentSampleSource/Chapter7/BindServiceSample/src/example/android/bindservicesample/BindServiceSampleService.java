package example.android.bindservicesample;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class BindServiceSampleService extends Service {
    // �R�[���o�b�N�Ǘ����X�g
    private final RemoteCallbackList<BindActivityAIDL> callbackList 
                            = new RemoteCallbackList<BindActivityAIDL>();
    // Timer�I�u�W�F�N�g
    private Timer timer = null;
    // �o�ߎ���
    private int countTime;	
    
    // onCreate���\�b�h(�T�[�r�X�����N���C�x���g�n���h��)
    @Override
    public void onCreate() {
        super.onCreate();
        
        // �^�C�}�[�ƌo�ߎ��ԏ�����
        timer = new Timer();
        countTime = 0;
        
        // �^�C�}�[�ݒ�(10�b���Ƃ�run���\�b�h�Ăяo��)
        timer.schedule(task, 10000, 10000);
    }

    // onBind���\�b�h(�o�C���h�C�x���g�n���h��)
    @Override
    public IBinder onBind(Intent intent) {
        if (BindServiceAIDL.class.getName().equals(intent.getAction())) {
            return serviceCallbackIf;
        }
        return null;
    }

    // onUnbind���\�b�h(�A���o�C���h�C�x���g�n���h��)
    @Override
    public boolean onUnbind(Intent intent) {

        // �^�C�}�[�ݒ����
        timer.cancel();
        timer.purge();

        return super.onUnbind(intent);
    }
       
    //  �T�[�r�X�ɘA�g����C���^�[�t�F�[�X�I�u�W�F�N�g
    private BindServiceAIDL.Stub serviceCallbackIf = new BindServiceAIDL.Stub() {
        public void registerCallback(BindActivityAIDL callback) {
            // Callback���X�g�ɓo�^
            callbackList.register(callback);
        }
        public void unregisterCallback(BindActivityAIDL callback) {
            // Callback���X�g�������
            callbackList.unregister(callback);
        }
    };

    // TimerTask�I�u�W�F�N�g����
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
        	// 10�b���ƂɃJ�E���g�A�b�v
        	countTime += 10;
        	
            // Callback�̃��X�g�������J�n
            int n = callbackList.beginBroadcast();
            // �S�A�C�e�������[�v
            for (int i = 0; i < n; i++) {
                try {
                    // �A�N�e�B�r�e�B��displayTime���\�b�h���s
                    callbackList.getBroadcastItem(i).displayTime(
                    		countTime / 60 + 
                            "��"+ 
                            countTime % 60+"�b�o�߂��܂����I");
                } catch (RemoteException e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
            // Callback�̃��X�g�������I��
            callbackList.finishBroadcast();
        }
    };
}
