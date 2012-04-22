package example.android.preferencemondai;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {
	
	// �v���t�@�����X�t�@�C����
    private static final String FILE_NAME = "PreferenceMondai";
    
    // ���̓f�[�^
    String name = "";
    String address = "";
    String month = "";
    String day = "";
    String gendar = "";
    String apple = "";
    String orange = "";
    String peach = "";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondlayout);
        
        // �C���e���g�̕t�����擾
        Bundle extra = getIntent().getExtras();
        
        // �t����񂩂���̓f�[�^�擾
        name = extra.getString("NAME");
        address = extra.getString("ADDRESS");
        month = extra.getString("MONTH");
        day = extra.getString("DAY");
        gendar = extra.getString("GENDAR");
        apple = extra.getString("APPLE");
        orange = extra.getString("ORANGE");
        peach = extra.getString("PEACH");
        
        // �o�͗p�e�L�X�g�I�u�W�F�N�g�擾
        TextView inputName = (TextView)findViewById(R.id.name);
        TextView inputAddress = (TextView)findViewById(R.id.address);
        TextView inputMonth = (TextView)findViewById(R.id.month);
        TextView inputDay = (TextView)findViewById(R.id.day);
        TextView inputGendar = (TextView)findViewById(R.id.gender);
        TextView inputApple = (TextView)findViewById(R.id.apple);
        TextView inputOrange = (TextView)findViewById(R.id.orange);
        TextView inputPeach = (TextView)findViewById(R.id.peach);
        
        // �o�͗p�e�L�X�g�I�u�W�F�N�ɓ��̓f�[�^�ݒ�
        inputName.setText(name);
        inputAddress.setText(address);
        inputMonth.setText(month);
        inputDay.setText(day);
        inputGendar.setText(gendar);
        if(apple != null)inputApple.setText(apple);
        if(orange != null)inputOrange.setText(orange);
        if(peach != null)inputPeach.setText(peach);
        
        // �m�F�{�^���̃N���b�N���X�i�[�ݒ�
        Button confirmbutton = (Button)findViewById(R.id.confirmbutton);
        confirmbutton.setTag("confirm");
        confirmbutton.setOnClickListener(new ButtonClickListener());
        // �߂�{�^���̃N���b�N���X�i�[�ݒ�
        Button backbutton = (Button)findViewById(R.id.backbutton);
        backbutton.setTag("back");
        backbutton.setOnClickListener(new ButtonClickListener());
    }
    
    // �{�^���N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        @Override
        public void onClick(View v) { 
        	// �^�O�̎擾
    		String tag = (String)v.getTag();
    		
    		// �m�F�{�^���������ꂽ�ꍇ
    		if(tag.equals("confirm")){
    			// �v���t�@�����X�I�u�W�F�N�g�擾
        		SharedPreferences preference = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
    			
    			// �v���t�@�����X�̕ҏW�p�I�u�W�F�N�g�擾
                SharedPreferences.Editor editor = preference.edit();
                
                // �v���t�@�����X�t�@�C���ɕۑ�
                editor.putString("NAME", name);
                editor.putString("ADDRESS", address);
                editor.putString("MONTH", month);
                editor.putString("DAY", day);
                editor.putString("GENDAR", gendar);
                editor.putString("APPLE", apple);
                editor.putString("ORANGE", orange);
                editor.putString("PEACH", peach);
                editor.commit();
                
            	// �C���e���g�̐���(�Ăяo���N���X�̎w��)
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                
                // ���̃A�N�e�B�r�e�B�̋N��
                startActivity(intent);
    			
    		// �߂�{�^���������ꂽ�ꍇ
    		}else if(tag.endsWith("back")){   		
	            // ��ʂ��N���[�Y
	            finish();
	    	}
        }
    }
}
