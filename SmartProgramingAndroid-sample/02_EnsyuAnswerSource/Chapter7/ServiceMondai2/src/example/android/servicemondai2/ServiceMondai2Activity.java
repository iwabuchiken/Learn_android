package example.android.servicemondai2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ServiceMondai2Activity extends Activity {
    // �T�[�r�X�ɘA�g����C���^�[�t�F�[�X
    private ServiceAIDL bindserviceIf = null;
    // �ڑ��I�u�W�F�N�g
    private ServiceConnection conn = null;
    
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.servicemondai2);
        
        // �J�n�{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        Button startButton=(Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartButtonClickListener());
        
        // �I���{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        Button stopButton=(Button)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new StopButtonClickListener());
        
        // ���Z�b�g�{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        Button resetButton=(Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new ResetButtonClickListener());
    }
    
    // Start�{�^���N���b�N���X�i�[��`
    class StartButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        public void onClick(View v) {          	
            // �C���e���g����
            Intent intent = new Intent(ServiceAIDL.class.getName());

            // Service�ڑ��E�ؒf�p�I�u�W�F�N�g����
            conn = new SampleServiceConnection();

            // �T�[�r�X�Ƀo�C���h
            bindService(intent, conn, BIND_AUTO_CREATE);
        }
    }
    
    // Stop�{�^���N���b�N���X�i�[��`
    class StopButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        public void onClick(View v) {    
            try {
            	// �T�[�r�X�������ꎞ��~
            	bindserviceIf.stopService();
            }catch(RemoteException e) {
                Log.e("ERROR", e.getMessage());
            }
        }
    }
    
    // Reset�{�^���N���b�N���X�i�[��`
    class ResetButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        public void onClick(View v) {    
            try {
            	// �A�N�e�B�r�e�B�ɘA�g����C���^�[�t�F�[�X������
            	bindserviceIf.resetService(bindactivityIf);
            }catch(RemoteException e) {
                Log.e("ERROR", e.getMessage());
            }
            
            // �T�[�r�X���A���o�C���h
            unbindService(conn);
            // �C���e���g����
            Intent intent = new Intent(ServiceAIDL.class.getName());
            // �T�[�r�X���~
            stopService(intent);
        }
    }
    
    // �T�[�r�X�ڑ��E�ڑ��N���X��`
    class SampleServiceConnection implements ServiceConnection {
    	// onServiceConnected���\�b�h(�ڑ��C�x���g�n���h��)
        public void onServiceConnected(ComponentName compName, IBinder binder) {
            // �T�[�r�X�ƘA�g����C���^�[�t�F�[�X�擾
        	bindserviceIf = ServiceAIDL.Stub.asInterface(binder);
            try {
                // �A�N�e�B�r�e�B�ɘA�g����C���^�[�t�F�[�X��o�^
            	bindserviceIf.startService(bindactivityIf); 
            }catch (RemoteException e) {
                Log.e("ERROR", e.getMessage());
            }
        }
        // onServiceDisconnected���\�b�h(�ؒf�C�x���g�n���h��)
        public void onServiceDisconnected(ComponentName arg0) {
        	bindserviceIf = null;
        }
    }
    
    // �A�N�e�B�r�e�B�ɘA�g����C���^�[�t�F�[�X�I�u�W�F�N�g
    private ActivityAIDL bindactivityIf = new ActivityAIDL.Stub() {
        @Override
        public void displayTime(String time) throws RemoteException {
            // handler�Ƀ��b�Z�[�W�𑗐M
            handler.sendMessage(Message.obtain(handler, 0, time)); 
        }
    };     
    
    // �n���h���[����
    private Handler handler = new Handler(){ 
    	// ���b�Z�[�W�\��
        public void handleMessage(Message msg) {
    		// TextView�Ɏ��Ԃ�\��
    		TextView timetext = (TextView)findViewById(R.id.timetext);
    		timetext.setText((String)msg.obj);        	
        }
    };
}