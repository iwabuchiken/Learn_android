package example.android.intentmondai1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class FirstActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.firstlayout);
        
        // �{�^���I�u�W�F�N�g�擾
        Button button1=(Button)findViewById(R.id.nextbutton);        
        // �{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        button1.setOnClickListener(new ButtonClickListener()); 
    }
    // �N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        public void onClick(View v) {
            // ���O�擾
            EditText name = (EditText)findViewById(R.id.name);
            
            // �Z���擾
            EditText address = (EditText)findViewById(R.id.address);
            
            // ���N�����擾
            Spinner month = (Spinner)findViewById(R.id.month);
            Spinner day = (Spinner)findViewById(R.id.day);
            
            // ���ʎ擾
            RadioGroup radio = (RadioGroup)findViewById(R.id.gendar);
            RadioButton radiobutton = (RadioButton)findViewById(radio.getCheckedRadioButtonId());
            
            // ��]���i�擾
            CheckBox applecheck = (CheckBox)findViewById(R.id.applecheck);
            CheckBox orangecheck = (CheckBox)findViewById(R.id.orangecheck);
            CheckBox peachcheck = (CheckBox)findViewById(R.id.peachcheck);
            
            // �������ʎ擾
            EditText appleqty = (EditText)findViewById(R.id.appleqty); 
            EditText orangeqty = (EditText)findViewById(R.id.orangeqty); 
            EditText peachqty = (EditText)findViewById(R.id.peachqty); 
            
            // �C���e���g�̐���(�Ăяo���N���X�̎w��)
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            
            // ���̓f�[�^���C���e���g�ɐݒ� 
            intent.putExtra("NAME", name.getText().toString());
            intent.putExtra("ADDRESS", address.getText().toString());
            intent.putExtra("MONTH", month.getSelectedItem().toString());
            intent.putExtra("DAY", day.getSelectedItem().toString());
            intent.putExtra("GENDAR", radiobutton.getText().toString());
            if(applecheck.isChecked())intent.putExtra("APPLE", appleqty.getText().toString());
            if(orangecheck.isChecked())intent.putExtra("ORANGE", orangeqty.getText().toString());
            if(peachcheck.isChecked())intent.putExtra("PEACH", peachqty.getText().toString());
            
            // ���̃A�N�e�B�r�e�B�̋N��
            startActivity(intent);
        }

    }    
}