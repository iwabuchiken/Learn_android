package example.android.intentsample1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SecondActivity extends Activity {
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.secondlayout);
        
        // �C���e���g�擾
        Intent data = getIntent();
        // �C���e���g�̕t�����擾
        Bundle extras = data.getExtras();
        // �t����񂩂�I�����ꂽ�l�擾
        String disp_pict = extras != null ? extras.getString("SELECTED_PICT") : "";

        // ImageView�I�u�W�F�N�g�擾
        ImageView image = (ImageView) findViewById(R.id.fruitimage);
        
        // �\������摜�̐ݒ�
        if ( disp_pict.equals("Apple") ) {
            image.setImageResource(R.drawable.apple);
        }
        else if ( disp_pict.equals("Banana") ) {
            image.setImageResource(R.drawable.banana);
        }
        else if ( disp_pict.equals("Grape") ) {
            image.setImageResource(R.drawable.grape);
        }
        
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
        	// �A�N�e�B�r�e�B�I��(��ʃN���[�Y)
        	finish();
        }
    }
}
