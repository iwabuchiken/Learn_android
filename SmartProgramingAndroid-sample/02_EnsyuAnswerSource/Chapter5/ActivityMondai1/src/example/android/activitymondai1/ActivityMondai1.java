package example.android.activitymondai1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityMondai1 extends Activity {
	// onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymondai1);
        
        // �{�^���I�u�W�F�N�g�擾
        Button button=(Button)findViewById(R.id.button1);
        
        // �{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        button.setOnClickListener(new ButtonClickListener());
    }
    // �N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        @Override
        public void onClick(View v) {
            // �e�L�X�g�{�b�N�X�̓��͏��擾
            EditText input = (EditText)findViewById(R.id.text_name);
            
            // �I���{�b�N�X�̓��͏��擾
            Spinner spinner = (Spinner)findViewById(R.id.spinner1);
            String str = (String)spinner.getSelectedItem();
            
            // ���͏����g�[�X�g�@�\�ŉ�ʕ\��
            Toast.makeText(ActivityMondai1.this, str+"\n"+input.getText()+"����",Toast.LENGTH_SHORT).show();
        }
    }
}