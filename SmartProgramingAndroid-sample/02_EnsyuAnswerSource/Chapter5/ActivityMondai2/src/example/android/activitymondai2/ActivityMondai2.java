package example.android.activitymondai2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ActivityMondai2 extends Activity {
	// onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymondai2);
        
        // Spinner�I�u�W�F�N�g���擾
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// Spinner�I�u�W�F�N�g�ɃC�x���g���X�i�[���֘A�t��
        spinner.setOnItemSelectedListener(new SelectItemSelectedListener());
        
    }
    // �C�x���g���X�i�[��`
	class SelectItemSelectedListener implements OnItemSelectedListener {
		// onItemSelected���\�b�h
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			
				// �N���b�N����Spinner�I�u�W�F�N�g���擾
				Spinner spinner = (Spinner)parent;
				// �I�����ꂽ���X�g�̒l�擾
				String item = (String) spinner.getItemAtPosition(position);
				
				// �e�L�X�g�{�b�N�X�̓��͏��擾
	    		EditText input = (EditText)findViewById(R.id.text_name);
	    		
				// �I�����ꂽ�l���g�[�X�g�@�\�ŉ�ʕ\��
	    		Toast.makeText(ActivityMondai2.this, item+"\n"+input.getText()+"����",Toast.LENGTH_SHORT).show();

		}
		// onNothingSelected���\�b�h
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	}
}