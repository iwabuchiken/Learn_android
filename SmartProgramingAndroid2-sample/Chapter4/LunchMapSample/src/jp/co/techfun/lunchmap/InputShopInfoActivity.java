package jp.co.techfun.lunchmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

// �X���o�^���Activity
public class InputShopInfoActivity extends Activity {
    // �C���e���g�̃f�[�^�󂯓n���L�[��`
    // �X��
    static final String SHOP_NAME_KEY = "SHOP_NAME";
    // �]��
    static final String SHOP_RATE_KEY = "SHOP_RATE";
    // ���ϗ\�Z
    static final String PRICE_KEY = "PRICE";
    // �R�����g
    static final String COMMENT_KEY = "COMMENT";

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g��ݒ�
        setContentView(R.layout.input_shop_info);

        // �u�m��v�{�^���ɃN���b�N���X�i�[�ݒ�
        Button btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(btnOkListener);

        // �u�߂�v�{�^���ɃN���b�N���X�i�[�ݒ�
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(btnCancelListener);
    }

    // �m��{�^���N���b�N���X�i�[��`
    private OnClickListener btnOkListener = new OnClickListener() {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        public void onClick(View v) {
            // �C���e���g�̎擾
            Intent intent = getIntent();

            // �e�e�L�X�g�I�u�W�F�N�g�擾
            EditText etShopName = (EditText) findViewById(R.id.et_shop_name);
            RatingBar rbShopRate = (RatingBar) findViewById(R.id.rb_shop_rate);
            EditText etPrice = (EditText) findViewById(R.id.et_price);
            EditText etComment = (EditText) findViewById(R.id.et_comment);

            // �e�e�L�X�g�I�u�W�F�N�g�̒l���C���e���g�ɐݒ�
            intent.putExtra(SHOP_NAME_KEY, etShopName.getText().toString());
            intent.putExtra(SHOP_RATE_KEY, (int) rbShopRate.getRating());
            String priceText = etPrice.getText().toString();
            intent.putExtra(PRICE_KEY, priceText.length() > 0 ? Integer
                .parseInt(priceText) : 0);
            intent.putExtra(COMMENT_KEY, etComment.getText().toString());

            // ���ʏ��̐ݒ�
            setResult(RESULT_OK, intent);

            // ��ʂ��N���[�Y
            finish();
        }
    };

    // �߂�{�^���N���b�N���X�i�[��`
    private OnClickListener btnCancelListener = new OnClickListener() {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g)
        public void onClick(View v) {
            // ��ʂ��N���[�Y
            finish();
        }
    };
}
