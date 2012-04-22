package jp.co.techfun.tweetapp;

import jp.co.techfun.tweetapp.commons.AppConstants;
import jp.co.techfun.tweetapp.tasks.TweetUpdateTask;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// �Ԃ₫���Activity
public class TweetUpdateActivity extends Activity {
    // OAuth�F�؃R���V���[�}�C���X�^���X
    private CommonsHttpOAuthConsumer consumer = null;

    // �񓯊��łԂ₫�𓊍e���邽�߂̃^�X�N�I�u�W�F�N�g����
    private final TweetUpdateTask task = new TweetUpdateTask(this);

    // �Ԃ₫���͗pEditText������
    private EditText edittweet = null;

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.tweet_update);

        // �R���V���[�}�I�u�W�F�N�g�擾
        consumer =
            (CommonsHttpOAuthConsumer) getIntent().getExtras().getSerializable(
                AppConstants.OAUTH_CONSUMER);

        // �Ԃ₫���͗pEditText�擾
        edittweet = (EditText) findViewById(R.id.et_tweet);

        // �Ԃ₫���e�ύX���p�̃��X�i�[�o�^
        edittweet.addTextChangedListener(new TextWatcher() {

            // onTextChanged���\�b�h(�Ԃ₫���e�ύX���C�x���g)
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            }

            // beforeTextChanged���\�b�h(�Ԃ₫���e�ύX�O�C�x���g)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
            }

            // afterTextChanged���\�b�h(�Ԃ₫���e�ύX��C�x���g)
            @Override
            public void afterTextChanged(Editable s) {

                // ���͉\�������\���pTextView�擾
                TextView charscounts =
                    (TextView) findViewById(R.id.tv_charscount);
                // ���͉\�������ݒ�
                int count = 140 - s.length();
                charscounts.setText(String.valueOf(count));
                // 140�����ȏ���͂��ꂽ�ꍇ�A�g�[�X�g�\��
                if (count < 0) {
                    Toast.makeText(getApplicationContext(),
                        getResources().getText(R.string.tv_charsmax),
                        Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // onClickBtnTweetUpdate���\�b�h(�u�Ԃ₫�v�{�^���N���b�N����)
    public void onClickBtnTweetUpdate(View v) {
        // �Ԃ₫���e
        task.execute(AppConstants.TWEET_REQUEST_URL, edittweet.getText()
            .toString(), consumer);
    }

    // onClickBtnBack���\�b�h(�u�߂�v�{�^���N���b�N����)
    public void onClickBtnBack(View v) {
        // �A�N�e�B�r�e�B�I��
        finish();
    }
}
