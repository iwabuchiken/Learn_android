package example.android.servicemondai2;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ServiceMondai2Service extends Service{
    // Timer�I�u�W�F�N�g
    private Timer timer = null;
    // �o�ߎ���
    private int countTime = 0;
    // �R�[���o�b�N�I�u�W�F�N�g
    ActivityAIDL callback = null;
    // �ꎞ��~�t���O
    boolean flg = false;
    
    // onCreate���\�b�h(�T�[�r�X�����N���C�x���g�n���h��)
    @Override
    public void onCreate() {
        super.onCreate();
        
        // �^�C�}�[�ƌo�ߎ��ԏ�����
        timer = new Timer();
        // �^�C�}�[�ݒ�(1�~���b���Ƃ�run���\�b�h�Ăяo��)
        timer.schedule(task, 0, 1);
    }

    // onBind���\�b�h(�o�C���h�C�x���g�n���h��)
    @Override
    public IBinder onBind(Intent intent) {
        if (ServiceAIDL.class.getName().equals(intent.getAction())) {
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
       
    //  �T�[�r�X�ɘA�g����C���^�t�F�[�X�I�u�W�F�N�g
    private ServiceAIDL.Stub serviceCallbackIf = new ServiceAIDL.Stub() {

        public void startService(ActivityAIDL callback) {
            // �R�[���o�b�N�I�u�W�F�N�g�擾
        	ServiceMondai2Service.this.callback=callback;
        	// �ꎞ��~�t���O��ύX
        	flg = false;
        }
        
        public void stopService() {           
        	// ���b�Z�[�W�𑗐M
    		ServiceMondai2Service.this.sendDisplayTime(false);       	
    		// �ꎞ��~�t���O��ύX
        	flg = true;

        }
        
        public void resetService(ActivityAIDL callback) {          
        	// ���b�Z�[�W�𑗐M
    		ServiceMondai2Service.this.sendDisplayTime(true);        	
            // �R�[���o�b�N�I�u�W�F�N�g�폜
        	ServiceMondai2Service.this.callback=null;
        	// �ꎞ��~�t���O��ύX
        	flg = false;
        	// �o�ߎ��ԏ�����
        	countTime = 0;
        }
    };

    // TimerTask�I�u�W�F�N�g����
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
        	if(!flg){       	
	        	// ���b�Z�[�W�𑗐M
        		ServiceMondai2Service.this.sendDisplayTime(false);
        	}			
        	// �R���}1�b���ƂɃJ�E���g�A�b�v
        	countTime += 1;
        }
    };
    
    // ���b�Z�[�W���M
    private void sendDisplayTime(boolean stopFlg){
    	try {
    		String time = "00:00.00";
    		if(!stopFlg){
	    		String countSec = String.valueOf(countTime%(1000*60)/1000);
	    		if(countTime%(1000*60)/1000<10){
	    			countSec = "0" + countTime%(1000*60)/1000;
	    		}
	    		String countMin = String.valueOf(countTime/(1000*60));
	    		if(countTime/(1000*60)<10){
	    			countMin = "0" + countTime/(1000*60);
	    		}
	    		String countMSec = String.valueOf(countTime%1000/10);
	    		if(countTime%1000/10<10){
	    			countMSec = "0" + countTime%1000/10;  
	    		}
	    		time = countMin+":"+countSec+"."+countMSec;
    		}
    		
    		if(this.callback!=null){
    			this.callback.displayTime(time);
    		}
		} catch (RemoteException e) {
			Log.e("error", "displayTimeError");
		}
    }
}
