package example.android.buttonclicksample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ButtonClickSample extends Activity {
	// onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		// ���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.buttonclicksample);
		// �{�^���I�u�W�F�N�g�擾
		Button button = (Button) findViewById(R.id.button1);
		// �{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
		button.setOnClickListener(new ButtonClickListener());
	}

	// �N���b�N���X�i�[��`
	class ButtonClickListener implements OnClickListener {
		// onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
		public void onClick(View v) {
			// �e�L�X�g�{�b�N�X�I�u�W�F�N�g�擾
			EditText input = (EditText) findViewById(R.id.nametext);
			// ���͏����g�[�X�g�@�\�ŉ�ʕ\��
			Toast.makeText(ButtonClickSample.this, input.getText(),
					Toast.LENGTH_SHORT).show();
		}
	}
}