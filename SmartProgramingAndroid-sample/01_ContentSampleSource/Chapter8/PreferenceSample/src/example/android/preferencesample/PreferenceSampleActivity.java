package example.android.preferencesample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class PreferenceSampleActivity extends Activity {
    
    // �v���t�@�����X�t�@�C����
    private static final String FILE_NAME = "PreferenceSampleFile";   

    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.preferencesample);
        
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
    		
    		// �v���t�@�����X�I�u�W�F�N�g�擾
    		SharedPreferences preference = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
    		
    		// TextView�擾
    		TextView message = (TextView)findViewById(R.id.message);
    		
    		// �ۑ��{�^���������ꂽ�ꍇ
    		if(tag.equals("save")){
    			// �v���t�@�����X�̕ҏW�p�I�u�W�F�N�g�擾
                SharedPreferences.Editor editor = preference.edit();
                
                // �I�����ꂽ�t�H���g�擾
                Spinner fontarray = (Spinner)findViewById(R.id.font);
                String font = (String)fontarray.getSelectedItem();
                
                // �I�����ꂽ�X�^�C���擾
                CheckBox italic = (CheckBox)findViewById(R.id.italic);
                CheckBox bold = (CheckBox)findViewById(R.id.bold);
                String check = "�ʏ�";
                if(italic.isChecked()){
                	check = "�Α�";
                }
                if(bold.isChecked()){
                	if(italic.isChecked()){
                		check += "|����";
                	}else{
                		check = "����";
                	}
                }
                
                // �v���t�@�����X�t�@�C���ɕۑ�
                editor.putString("FONT", font);
                editor.putString("STYLE", check);
                editor.commit();
                
                // ���b�Z�[�W�\��
                message.setText("�ۑ����܂����I");
    		
            // �\���{�^���������ꂽ�ꍇ
    		}else if(tag.equals("display")){
    			// �ۑ��f�[�^�擾
    			String font = preference.getString("FONT", "������܂���");
    			String style = preference.getString("STYLE", "������܂���");
    			
    			// ���b�Z�[�W�\���p�t�H���g�A�X�^�C���ݒ�
    			Typeface fonttype = Typeface.DEFAULT;
    			if(font.equals("������")){
    				fonttype = Typeface.SERIF;
    			}else if(font.equals("�S�V�b�N��")){
    				fonttype = Typeface.SANS_SERIF;
    			}else if(font.equals("�����t�H���g")){
    				fonttype = Typeface.MONOSPACE;
    			}
    			int styleflg = Typeface.NORMAL;
    			if(style.equals("�Α�")){
    				styleflg = Typeface.ITALIC;
    			}else if(style.equals("����")){
    				styleflg = Typeface.BOLD;
    			}else if(style.equals("�Α�|����")){
    				styleflg = Typeface.BOLD_ITALIC;
    			}
    			
    			// ���b�Z�[�W�\��
    			message.setText("Preference Sample\n" + "�t�H���g�F" + font + "\n�X�^�C���F" + style);
    			message.setTypeface(Typeface.create(fonttype, styleflg));

    			
    		// �폜�{�^���������ꂽ�ꍇ
    		}else if(tag.endsWith("delete")){
    			// �v���t�@�����X�̕ҏW�p�I�u�W�F�N�g�擾
                SharedPreferences.Editor editor = preference.edit();  
                
                // ���ׂĂ̕ۑ��f�[�^�폜
                editor.clear();
                editor.commit();
                
                // ���b�Z�[�W�\��
                message.setText("�폜���܂����I");
    		}
    	}
    }
}