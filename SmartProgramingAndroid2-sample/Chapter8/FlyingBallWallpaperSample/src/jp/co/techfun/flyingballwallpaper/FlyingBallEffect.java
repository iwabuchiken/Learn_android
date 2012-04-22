package jp.co.techfun.flyingballwallpaper;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

// ��щ��ʂ̃G�t�F�N�g��\������N���X
public class FlyingBallEffect {

	// ���������p
	private static final Random RDM = new Random();

	// ��ʂ̕�
	private int width;
	// ��ʂ̍���
	private int height;

	// ��ʂ̒��S���W
	private int centerX;
	private int centerY;

	// ��щ���
	private ShapeDrawable ball;

	// �ʂ�\������ʒu
	private float ballX;
	private float ballY;

	// �ʂ��ړ����鋗��
	private float movX;
	private float movY;

	// �ʂ̒��a
	private int ballDiameter;

	// �R���X�g���N�^
	public FlyingBallEffect() {
		// �����l��ݒ�
		ballDiameter = 150;

		ballX = -ballDiameter;
		ballY = -ballDiameter;

		// �ʃI�u�W�F�N�g�𐶐�
		ball = new ShapeDrawable(new OvalShape());
	}

	// draw���\�b�h(�ǎ��`�揈��)
	public void draw(Canvas canvas) {

		// ��ʂ̕����擾
		width = canvas.getWidth();
		// ��ʂ̍������擾
		height = canvas.getHeight();
		// ��ʂ̒��S���Čv�Z
		centerX = width / 2 - ballDiameter / 2;
		centerY = height / 2 - ballDiameter / 2;

		// �~���ړ�
		ballX += movX;
		ballY += movY;

		// ��ʂ��N���A
		canvas.drawColor(Color.BLACK);

		canvas.save();

		ball.setBounds((int) ballX, (int) ballY, (int) ballX + ballDiameter,
				(int) ballY + ballDiameter);
		ball.draw(canvas);

		canvas.restore();

		// ��ʊO�֋ʂ��o���ꍇ
		if (width < ballX || height < ballY || ballX + ballDiameter <= 0
				|| ballY + ballDiameter <= 0) {

			// �ʂ̕\���ʒu���Đݒ�
			resetPosition();
		}
	}

	// resetPosition���\�b�h(�ʂ̕\���ʒu�Đݒ菈��)
	private void resetPosition() {

		// �~�̒��a��ύX
		ballDiameter = 75 * (RDM.nextInt(4) + 1);

		// ��ʂ̒��S���Čv�Z
		centerX = width / 2 - ballDiameter / 2;
		centerY = height / 2 - ballDiameter / 2;

		// ���̏o���ʒu
		int rdmX = RDM.nextInt(width + ballDiameter) - ballDiameter;
		// �c�̏o���ʒu
		int rdmY = RDM.nextInt(height + ballDiameter) - ballDiameter;

		// �~�̏o������������
		switch (RDM.nextInt(4)) {
		case 0:
			// ��ʏ㕔���o��
			ballX = rdmX;
			ballY = -ballDiameter;
			break;

		case 1:
			// ��ʉE�����o��
			ballX = width;
			ballY = rdmY;
			break;

		case 2:
			// ��ʉ������o��
			ballX = rdmX;
			ballY = height;
			break;

		default:
			// ��ʍ��[���o��
			ballX = -ballDiameter;
			ballY = rdmY;
			break;
		}

		// 1�X�e�b�v�ňړ����鋗����ύX
		// �����Ő��������l(1�`3)����Z���A�Ⴂ�𖾊m�ɂ���
		int step = (RDM.nextInt(2) + 1) * 3;

		// ��ʒ����܂ł̋�������1�X�e�b�v�ňړ����鋗�����Z�o
		float difX = Math.abs(ballX - centerX);
		float difY = Math.abs(ballY - centerY);
		if (difX < difY) {
			movX = difX / difY * step;
			movY = step;
		} else {
			movX = step;
			movY = difY / difX * step;
		}

		// �ړ�����������
		// �E�[����o������ꍇ�A�������ֈړ�
		if (width / 2 < ballX) {
			movX = -movX;
		}
		// ��������o������ꍇ�A������ֈړ�
		if (height / 2 < ballY) {
			movY = -movY;
		}

		// �ʂ̐F������
		ball.getPaint().setShader(getRandomRadialGradient());
	}

	// getRandomRadialGradient���\�b�h(�����_���O���f�[�V�����擾����)
	private Shader getRandomRadialGradient() {

		// �����_���ɃO���f�[�V������I��
		switch (RDM.nextInt(4)) {
		case 0:
			// ��
			return new RadialGradient(15, 15, ballDiameter, Color.YELLOW,
					Color.RED, Shader.TileMode.MIRROR);

		case 1:
			// ��
			return new RadialGradient(15, 15, ballDiameter, Color.CYAN,
					Color.YELLOW, Shader.TileMode.MIRROR);

		case 2:
			// ��
			return new RadialGradient(15, 15, ballDiameter, Color.CYAN,
					Color.BLUE, Shader.TileMode.MIRROR);

		default:
			// ��
			return new RadialGradient(15, 15, ballDiameter, Color.CYAN,
					Color.GREEN, Shader.TileMode.MIRROR);
		}
	}
}
