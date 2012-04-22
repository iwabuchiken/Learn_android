package example.android.bindservicesample;

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
import android.widget.Toast;

public class BindServiceSampleActivity extends Activity {
    // �T�[�r�X�ɘA�g����C���^�[�t�F�[�X
    private BindServiceAIDL bindserviceIf = null;
    // �ڑ��I�u�W�F�N�g
    private ServiceConnection conn = null;
    
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.bindservicesample);
        
        // �J�n�{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        Button startButton=(Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartButtonClickListener());
        
        // �I���{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        Button stopButton=(Button)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new StopButtonClickListener());
    }
    
    // �J�n�{�^���N���b�N���X�i�[��`
    class StartButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        public void onClick(View v) {   
            // ���b�Z�[�W�\��
            Toast.makeText(BindServiceSampleActivity.this,
			            "�T�[�r�X���J�n���܂�", 
			            Toast.LENGTH_SHORT).show();
        	
            // �C���e���g����
            Intent intent = new Intent(BindServiceAIDL.class.getName());

            // Service�ڑ��E�ؒf�p�I�u�W�F�N�g����
            conn = new SampleServiceConnection();

            // �T�[�r�X�Ƀo�C���h
            bindService(intent, conn, BIND_AUTO_CREATE);
        }
    }
    
    // �I���{�^���N���b�N���X�i�[��`
    class StopButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        public void onClick(View v) {    
            try {
            	// �A�N�e�B�r�e�B�ɘA�g����C���^�[�t�F�[�X������
            	bindserviceIf.unregisterCallback(bindactivityIf);
            }catch(RemoteException e) {
                Log.e("ERROR", e.getMessage());
            }
            
            // ���b�Z�[�W�\��
            Toast.makeText(BindServiceSampleActivity.this,
			            "�T�[�r�X���I�����܂�", 
			            Toast.LENGTH_SHORT).show();
            
            // �T�[�r�X���A���o�C���h
            unbindService(conn);
            // �C���e���g����
            Intent intent = new Intent(BindServiceAIDL.class.getName());
            // �T�[�r�X���~
            stopService(intent);
        }
    }
    
    // �T�[�r�X�ڑ��E�ڑ��N���X��`
    class SampleServiceConnection implements ServiceConnection {
    	// onServiceConnected���\�b�h(�ڑ��C�x���g�n���h��)
        public void onServiceConnected(ComponentName compName, IBinder binder) {
            // �T�[�r�X�ƘA�g����C���^�[�t�F�[�X�擾
        	bindserviceIf = BindServiceAIDL.Stub.asInterface(binder);
            try {
                // �A�N�e�B�r�e�B�ɘA�g����C���^�[�t�F�[�X��o�^
            	bindserviceIf.registerCallback(bindactivityIf); 
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
    private BindActivityAIDL bindactivityIf = new BindActivityAIDL.Stub() {
        @Override
        public void displayTime(String msg) throws RemoteException {
            // handler�Ƀ��b�Z�[�W�𑗐M
            handler.sendMessage(Message.obtain(handler, 0, msg)); 
        }
    };     
    
    // �n���h���[����
    private Handler handler = new Handler(){ 
    	// ���b�Z�[�W�\��
        public void handleMessage(Message msg) {
        	Toast.makeText(BindServiceSampleActivity.this,
				           (String)msg.obj, 
				            Toast.LENGTH_SHORT).show();   
        }
    };
}