package example.android.filemondai;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {
	
	// �t�@�C����
    private static final String FILE_NAME = "FileMondai";
    
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
    			// �t�@�C���ɕۑ�
    			try{
                    FileOutputStream stream = openFileOutput(FILE_NAME, MODE_APPEND);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(stream));
                    
                    out.write(name + "," + 
                    		  address + "," + 
                    		  month + "/" + day + "," + 
                    		  gendar + "," + 
                    		  apple + "," + 
                    		  orange + "," + 
                    		  peach);
                    out.newLine();
                    out.close();
                    
                }catch(Exception e){
                    Log.e("FILE_ERROR", "�t�@�C���������݂Ɏ��s���܂���");
                }

                
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
