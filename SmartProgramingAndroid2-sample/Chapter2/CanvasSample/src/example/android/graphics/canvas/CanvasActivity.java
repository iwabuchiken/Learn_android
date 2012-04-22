package example.android.graphics.canvas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

// �L�����o�X��ʃA�N�e�B�r�e�B
public class CanvasActivity extends Activity {
	// �L�����o�X�N���A�{�^���̃N���b�N���X�i�[��`
	private OnClickListener onClickListener = new View.OnClickListener() {
		public void onClick(View arg0) {
			CanvasView view = (CanvasView) findViewById(R.id.cv_canvas);
			view.clearDrawList();
		}
	};

	// onCreate���\�b�h(��ʏ����\���C�x���g)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		// �`��̈���L���邽�߂Ƀ^�C�g����\���ݒ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ���C�A�E�g�ݒ�t�@�C����ݒ�
		setContentView(R.layout.canvassample);

		// �L�����o�X�N���A�{�^���I�u�W�F�N�g����
		Button btnClear = (Button) findViewById(R.id.btn_clear);
		btnClear.setOnClickListener(onClickListener);
	}
}
