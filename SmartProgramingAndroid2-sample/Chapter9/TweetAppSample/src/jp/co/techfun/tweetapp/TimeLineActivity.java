package jp.co.techfun.tweetapp;

import jp.co.techfun.tweetapp.commons.AppConstants;
import jp.co.techfun.tweetapp.tasks.TimeLineWithAuthTask;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;

// �^�C�����C�����Activity
public class TimeLineActivity extends ListActivity {
    // OAuth�F�؃R���V���[�}�C���X�^���X
    private CommonsHttpOAuthConsumer consumer = null;

    // �񓯊��Ń^�C�����C�����擾���邽�߂̃^�X�N�I�u�W�F�N�g����
    private final TimeLineWithAuthTask task = new TimeLineWithAuthTask(this);

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.timeline);

        // �R���V���[�}�I�u�W�F�N�g�擾
        consumer =
            (CommonsHttpOAuthConsumer) getIntent().getExtras().getSerializable(
                AppConstants.OAUTH_CONSUMER);
    }

    // onStart���\�b�h(�A�N�e�B�r�e�B�N���C�x���g)
    @Override
    protected void onStart() {
        super.onStart();

        // �^�C�����C���擾
        task.execute(AppConstants.TIMELINE_REQUEST_URL, consumer);
    }

    // onClickBtnBack���\�b�h(�u�߂�v�{�^���N���b�N����)
    public void onClickBtnBack(View v) {
        finish();
    }

}
