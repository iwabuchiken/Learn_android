package example.android.intentsample2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class SecondActivity extends Activity{
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.secondlayout);
        
        // Button�I�u�W�F�N�g�擾
        Button button = (Button)findViewById(R.id.backbutton);
        // �{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        button.setOnClickListener(new ButtonClickListener());
    }
    // �N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        @Override
        public void onClick(View v) {
            // �C���e���g�擾
            Intent intent = getIntent();
            
            // Spinner�I�u�W�F�N�g�擾
            Spinner spinner = (Spinner)findViewById(R.id.greeting);
            // �I�����ꂽ�������擾
            String greeting = (String)spinner.getSelectedItem();
            
            // ���͗p�e�L�X�g�I�u�W�F�N�g�̒l���C���e���g�ɐݒ�
            intent.putExtra("SELECTED_GREETING", greeting);
            
            // ���ʏ��̐ݒ�
            setResult(RESULT_OK, intent);        	
        	
        	// �A�N�e�B�r�e�B�I��(��ʃN���[�Y)
        	finish();
        }
    }
}
