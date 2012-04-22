package example.android.implicitintentmondai2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ImplicitIntentMondai2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.main);
        
        // �{�^���I�u�W�F�N�g�擾
        Button button1=(Button)findViewById(R.id.button1);        
        // �{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        button1.setOnClickListener(new ButtonClickListener()); 
    }
    // �N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        public void onClick(View v) {
            // �e�L�X�g�{�b�N�X�̓��͏��擾
            EditText content = (EditText) findViewById(R.id.content);
            String forwardstr = content.getText().toString();
            
            // �I�����X�g�̑I�����擾
            Spinner selectapli = (Spinner) findViewById(R.id.selectapli);
            String seleteditem = (String)selectapli.getSelectedItem();
            
            // �A�N�V�����̐ݒ�
            String action = null;
            if(seleteditem.equals("�d�b")){
                action = "android.intent.action.DIAL";
                forwardstr = "tel:"+content.getText().toString();
            }else if(seleteditem.equals("WEB�u���E�U")){
                action = "android.intent.action.VIEW";
            }else if(seleteditem.equals("�n�}")){
                action = "android.intent.action.VIEW";
                forwardstr = "geo:"+content.getText().toString();
            }else if(seleteditem.equals("���b�Z�[�W")){
            	action = "android.intent.action.VIEW";
            }
            
            // �C���e���g�̐���
            Intent intent = new Intent(action,Uri.parse(forwardstr));
            
            if(seleteditem.equals("���b�Z�[�W")){
            	intent.putExtra("sms_body",content.getText().toString()); 
            	intent.setType("vnd.android-dir/mms-sms"); 
            }
            
            // ���̃A�N�e�B�r�e�B�̋N��
            startActivity(intent);
        }
    }
}