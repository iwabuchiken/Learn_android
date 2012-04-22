package example.android.servicemondai1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ServiceMondai1Activity extends Activity {
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.servicemondai1);

        // �J�n�{�^���ɃN���b�N���X�i�[�ݒ�
        Button startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartButtonClickListener());    
        
        // �I���{�^���ɃN���b�N���X�i�[�ݒ�
        Button stopButton = (Button)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new StopButtonClickListener());     
    }
    // �J�n�{�^�����X�i�[�N���X
    class StartButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        public void onClick(View v) {
            // �C���e���g����
            Intent intent = new Intent(ServiceMondai1Activity.this, 
                                         ServiceMondai1Service.class);
            
            // �I�����Ԃ��C���e���g�ɐݒ�
            EditText stopcount = (EditText)findViewById(R.id.stopcount);
            intent.putExtra("STOPTIME",stopcount.getText().toString());
            
            // �T�[�r�X�J�n
            startService(intent);
        }
    }
    // �I���{�^�����X�i�[�N���X
    class StopButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        public void onClick(View v) {
            // �C���e���g����
            Intent intent = new Intent(ServiceMondai1Activity.this, 
                                         ServiceMondai1Service.class);
            
            // �T�[�r�X�I��
            stopService(intent);
        }
    }
}