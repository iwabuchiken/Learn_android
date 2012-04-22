package example.android.filesample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FileSampleActivity extends Activity {
    // �t�@�C����
    private static final String FILE_NAME = "FileSampleFile";   

    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.filesample);
        
        // �ۑ��{�^���̃N���b�N���X�i�[�ݒ�
        Button saveBtn = (Button)findViewById(R.id.save);
        saveBtn.setTag("save");
        saveBtn.setOnClickListener(new ButtonClickListener());
        // �\���{�^���̃N���b�N���X�i�[�ݒ�
        Button readBtn = (Button)findViewById(R.id.display); 
        readBtn.setTag("display");
        readBtn.setOnClickListener(new ButtonClickListener());   
        // �폜�{�^���̃N���b�N���X�i�[�ݒ�
        Button delBtn = (Button)findViewById(R.id.delete);
        delBtn.setTag("delete");
        delBtn.setOnClickListener(new ButtonClickListener());  
    }
    
    // �N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
    	// onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
    	public void onClick(View v){
    		// �^�O�̎擾
    		String tag = (String)v.getTag();
    		
    		// ���b�Z�[�W�\���p
            String str  = "";
            TextView label = (TextView)findViewById(R.id.message); 
    		
    		// �ۑ��{�^���������ꂽ�ꍇ
    		if(tag.equals("save")){
    			// ���͏��擾
                EditText name = (EditText)findViewById(R.id.name);
                EditText score = (EditText)findViewById(R.id.score);
                
                // �t�@�C���Ƀf�[�^�ۑ�
                try{
                    FileOutputStream stream = openFileOutput(FILE_NAME, MODE_APPEND);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(stream));
                    
                    String hantei = "�s���i";
                    if(Integer.parseInt(score.getText().toString()) >= 210){
                    	hantei = "���i";
                    }
                    
                    out.write(name.getText().toString() + "," + 
                    		  score.getText().toString() + "," + 
                    		  hantei);
                    out.newLine();
                    out.close();
                    str  = "�ۑ����܂����I";
                }catch(Exception e){
                    str  = "�f�[�^�ۑ��Ɏ��s���܂����I";
                }

    		
            // �\���{�^���������ꂽ�ꍇ
    		}else if(tag.equals("display")){
                
                // �t�@�C������f�[�^�擾
                try{
                    FileInputStream stream = openFileInput(FILE_NAME);
                    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                    
                    String line = "";
                    while((line = in.readLine())!=null){
                        str += line + "\n";
                    }
                    in.close();             
                }catch(Exception e){
                    str  = "�f�[�^�擾�Ɏ��s���܂����I";
                }      

    			
    		// �폜�{�^���������ꂽ�ꍇ
    		}else if(tag.endsWith("delete")){
                
    			// �t�@�C���̃f�[�^�폜
                try{
                    deleteFile(FILE_NAME);
                    str = "�폜���܂����I";                                
                }catch(Exception e){
                    str  = "�f�[�^�폜�Ɏ��s���܂����I";
                }
    		}            
            // ���b�Z�[�W�\��  
            label.setText(str);
    	}
    }
}