package example.android.intentsample2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends Activity {
	// ���N�G�X�g�R�[�h��`
    private static final int SHOSW_CALC = 0;

    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.firstlayout);
        
        // �{�^���I�u�W�F�N�g�擾
        Button button = (Button) findViewById(R.id.nextbutton);       
        // �{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        button.setOnClickListener(new ButtonClickListener());
    }
    
    // �N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        public void onClick(View v) {
            // �C���e���g�̐���(�Ăяo���N���X�̎w��)
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            
            // ���̃A�N�e�B�r�e�B�̋N��
            startActivityForResult(intent, SHOSW_CALC);
        }

    }

    // onActivityResult���\�b�h(���C����ʍĕ\�����C�x���g�n���h��)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SHOSW_CALC) {
            if (resultCode == RESULT_OK) {
                // �C���e���g����t�����擾
                Bundle extra = data.getExtras();
                // �I�����ꂽ�������擾
                String selectedGreeting = extra.getString("SELECTED_GREETING");

                // �e�L�X�g�{�b�N�X�̓��͏��擾
                EditText input = (EditText) findViewById(R.id.nametext);

                // �g�[�X�g�@�\�ŉ�ʕ\��
                Toast.makeText(FirstActivity.this, 
                		        selectedGreeting+"\n"+input.getText()+"����",
                		        Toast.LENGTH_SHORT).show();
            }
        }
    }
}