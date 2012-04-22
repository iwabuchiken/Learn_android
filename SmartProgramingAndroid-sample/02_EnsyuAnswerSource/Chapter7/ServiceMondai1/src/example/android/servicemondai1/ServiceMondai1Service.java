package example.android.servicemondai1;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class ServiceMondai1Service extends Service{
    // Timer�I�u�W�F�N�g
    private Timer timer = null;
    // �o�ߎ���
    private int countTime;
    // �I������
    private int stopTime;
	
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    // onCreate���\�b�h(�T�[�r�X�����N���C�x���g�n���h��)
    @Override
    public void onCreate() {
		super.onCreate();
		
		// �g�[�X�g�\��
        Toast.makeText(this, 
        		       "�T�[�r�X���N�����܂��B", 
        		       Toast.LENGTH_SHORT).show();
        
        // �^�C�}�[�ƌo�ߎ��ԏ�����
        timer = new Timer();
        countTime = 0;
    }

    // onStart���\�b�h(�T�[�r�X�J�n�C�x���g�n���h��)
    @Override
    public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

        // �g�[�X�g�\��
        Toast.makeText(this, 
        		        "�T�[�r�X���J�n���܂��B", 
        		        Toast.LENGTH_SHORT).show();
        
        // �^�C�}�[�ݒ�(10�b���Ƃ�run���\�b�h�Ăяo��)
        timer.schedule(task, 10000, 10000);
        
        // �I�����Ԏ擾
        Bundle bundle = intent.getExtras();
        stopTime = Integer.parseInt(bundle.getString("STOPTIME"));
    }

    // onDestroy���\�b�h(�T�[�r�X�I���C�x���g�n���h��)
    @Override
    public void onDestroy() {
		super.onDestroy();
		
		// �^�C�}�[�ݒ����
        timer.cancel();
        timer.purge();
        handler.removeMessages(0);
        
        // SMS�N��
		Intent intent=new Intent(Intent.ACTION_VIEW);
		intent.putExtra("address", "0901234XXXX");
		intent.putExtra("sms_body",
				countTime / 60 + "��"+ 
                countTime % 60+"�b�o�߂��āA�T�[�r�X���I�����܂����I");
		intent.setType("vnd.android-dir/mms-sms"); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(intent);
    }
    
    // �n���h���[����
    private Handler handler = new Handler(){ 
    	// ���b�Z�[�W�\��
    	public void handleMessage(Message msg) {
    		Toast.makeText(ServiceMondai1Service.this,
    				       (String)msg.obj, 
    				       Toast.LENGTH_SHORT).show();  
    	}
    };

    // TimerTask�I�u�W�F�N�g����
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // 10�b���ƂɃJ�E���g�A�b�v
        	countTime += 10;
        	
        	if(countTime / 60 == stopTime){
        		// �T�[�r�X�I��
        		stopSelf();
        	}else{
        		// handler�Ƀ��b�Z�[�W�𑗐M
                handler.sendMessage(
                		Message.obtain(handler, 
    		                            0, 
    		                            countTime / 60 + 
    		                            "��"+ 
    		                            countTime % 60+"�b�o�߂��܂����I")); 
        	}        	
        }
    };    
}
