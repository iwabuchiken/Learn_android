package example.android.graphics.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

// �A�j���[�V������ʃA�N�e�B�r�e�B
public class AnimationActivity extends Activity {
	// ���s�[�g�t���O
	private boolean repeat = false;
	// �A�j���[�V�����`��p�N���X
	private AnimationDrawable drawable;

	// �I���{�b�N�X�̃C�x���g���X�i�[����
	private OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
		// onItemSelected���\�b�h(�A�C�e���I�����C�x���g)
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// �I�����ꂽ�A�C�e���擾
			Spinner spinner = (Spinner) parent;
			String item = (String) spinner.getSelectedItem();

			// �摜�I�u�W�F�N�g�擾
			ImageView image = (ImageView) findViewById(R.id.img_andy);

			// �I���A�C�e���𔻒肵�āA�Y���A�j���[�V������ݒ�
			Animation animation;
			if (item.equals("(����)")) {
				animation = null;
			} else if (item.equals("��]")) {
				animation = AnimationUtils.loadAnimation(
						AnimationActivity.this, R.anim.animation_rotate_1);
			} else if (item.equals("�g��k��")) {
				animation = AnimationUtils.loadAnimation(
						AnimationActivity.this, R.anim.animation_scale_1);
			} else if (item.equals("�ړ�")) {
				animation = AnimationUtils.loadAnimation(
						AnimationActivity.this, R.anim.animation_translate_1);
			} else if (item.equals("�S���I")) {
				animation = AnimationUtils.loadAnimation(
						AnimationActivity.this, R.anim.animation_multi_1);
			} else {
				animation = null;
			}

			// �A�j���[�V�����N��
			if (animation == null) {
				image.clearAnimation();
			} else {
				image.startAnimation(animation);
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	// onCreate���\�b�h(��ʏ����\���C�x���g)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		// ���C�A�E�g�ݒ�t�@�C����ݒ�
		setContentView(R.layout.animationsample);

		// �I���{�b�N�X�ɃC�x���g���X�i�[�ݒ�
		Spinner spinner = (Spinner) findViewById(R.id.spn_anim_control);
		spinner.setOnItemSelectedListener(listener);

		// �摜�������؂�ւ��\������A�j���[�V������`��I�u�W�F�N�g�ɐݒ�
		drawable = (AnimationDrawable) getResources().getDrawable(
				R.anim.animation_animation_1);
	}

	// onTouchEvent���\�b�h(�^�b�`�C�x���g)
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		// �^�b�`�C�x���g���^�b�`�����̏ꍇ
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// �A�j���[�V�����`��I�u�W�F�N�g���C���[�W�r���[�ɐݒ�
			ImageView image = (ImageView) findViewById(R.id.img_andy);
			image.setImageDrawable(drawable);

			// �A�j���[�V�����N��
			repeat = !repeat;
			if (repeat) {
				drawable.start();
			} else {
				drawable.stop();
			}
		}
		return result;
	}
}
