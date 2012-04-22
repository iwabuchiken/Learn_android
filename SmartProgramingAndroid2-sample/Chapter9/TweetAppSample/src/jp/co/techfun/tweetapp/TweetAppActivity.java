package jp.co.techfun.tweetapp;

import jp.co.techfun.tweetapp.commons.AppConstants;
import oauth.signpost.OAuth;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

// �g�b�v���Activity
public class TweetAppActivity extends Activity {

    // OAuth�F�؃R���V���[�}�C���X�^���X
    private CommonsHttpOAuthConsumer oauthConsumer = null;
    // OAuth�F�؃v���o�C�_�C���X�^���X
    private CommonsHttpOAuthProvider oauthProvider = null;

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���w��
        setContentView(R.layout.tweet_app);
    }

    // onStart���\�b�h(�A�N�e�B�r�e�B�N���C�x���g)
    @Override
    protected void onStart() {
        super.onStart();

        // �R���V���[�}�I�u�W�F�N�g���ݒ肳��Ă��Ȃ��ꍇ
        if (oauthConsumer == null) {
            // Consumer Key��Consumer Secret��ݒ肵�ăR���V���[�}�I�u�W�F�N�g����
            oauthConsumer =
                new CommonsHttpOAuthConsumer(AppConstants.CONSUMER_KEY,
                    AppConstants.CONSUMER_SECRET);
        }

        // �v���o�C�_�I�u�W�F�N�g���ݒ肳��Ă��Ȃ��ꍇ
        if (oauthProvider == null) {
            // �v���o�C�_�I�u�W�F�N�g����
            oauthProvider =
                new CommonsHttpOAuthProvider(
                    AppConstants.REQUEST_TOKEN_ENDPOINT_URL,
                    AppConstants.ACCESS_TOKEN_ENDPOINT_URL,
                    AppConstants.AUTHORIZATION_WEBSITE_URL);
        }
    }

    // onClickBtnLogin���\�b�h(�u���O�C���v�{�^���N���b�N����)
    public void onClickBtnLogin(View v) {
        String authUrl = "";
        try {
            // Twitter�F�؉�ʂ̋N���p�ݒ�
            authUrl =
                oauthProvider.retrieveRequestToken(oauthConsumer,
                    AppConstants.CALLBACK_URL);
        } catch (OAuthMessageSignerException e) {
            Log.e(getClass().getSimpleName(), "Twitter OAuth Error", e);
        } catch (OAuthNotAuthorizedException e) {
            Log.e(getClass().getSimpleName(), "Twitter OAuth Error", e);
        } catch (OAuthExpectationFailedException e) {
            Log.e(getClass().getSimpleName(), "Twitter OAuth Error", e);
        } catch (OAuthCommunicationException e) {
            Log.e(getClass().getSimpleName(), "Twitter OAuth Error", e);
        }

        // Twitter�F�؉�ʋN��
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
    }

    // onResume���\�b�h(Twitter�F�؉�ʌ�̏���)
    @Override
    protected void onResume() {
        super.onResume();

        // �C���e���g�擾
        Intent data = getIntent();

        // �C���e���g���擾�ł��Ȃ������ꍇ�I��
        if (data == null) {
            return;
        }

        // URI�擾
        Uri uri = data.getData();

        // URI���擾�ł��Ȃ������ꍇ�I��
        if (uri == null) {
            return;
        }

        // Twitter�F�؉�ʂ���łȂ��ꍇ�I��
        if (!uri.toString().startsWith(AppConstants.CALLBACK_URL)) {
            return;
        }

        // Twitter�F�؉�ʂŋ����Ȃ������ꍇ�I��
        if (uri.getQueryParameter(AppConstants.REQUEST_TOKEN_DENIED) != null) {
            oauthConsumer.setTokenWithSecret(null, null);
            return;
        }

        // Access Token�擾
        String token = uri.getQueryParameter(OAuth.OAUTH_TOKEN);
        // Verifier�擾
        String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

        // Access Token Secret�擾
        try {
            oauthConsumer.setTokenWithSecret(token, "");
            oauthProvider.retrieveAccessToken(oauthConsumer, verifier);
        } catch (OAuthMessageSignerException e) {
            Log.e(getClass().getSimpleName(),
                "Twitter Access Token Secret Error", e);
        } catch (OAuthNotAuthorizedException e) {
            Log.e(getClass().getSimpleName(),
                "Twitter Access Token Secret Error", e);
        } catch (OAuthExpectationFailedException e) {
            Log.e(getClass().getSimpleName(),
                "Twitter Access Token Secret Error", e);
        } catch (OAuthCommunicationException e) {
            Log.e(getClass().getSimpleName(),
                "Twitter Access Token Secret Error", e);
        }

        // �C���e���g��URI���N���A
        getIntent().setData(null);
    }

    // onClickBtnTweet���\�b�h(�u�Ԃ₭�v�{�^���N���b�N����)
    public void onClickBtnTweet(View v) {
        // Access Token Secret���擾�ł����ꍇ
        if (oauthConsumer.getTokenSecret() != null) {
            // �Ԃ₫��ʂ֑J�ڗp�C���e���g����
            Intent intent =
                new Intent(TweetAppActivity.this, TweetUpdateActivity.class);
            // �R���V���[�}�I�u�W�F�N�g��ݒ�
            intent.putExtra(AppConstants.OAUTH_CONSUMER, oauthConsumer);
            // �Ԃ₫��ʂ֑J��
            startActivity(intent);
            // �擾�ł��Ȃ������ꍇ
        } else {
            // �g�[�X�g���b�Z�[�W�\��
            Toast.makeText(this,
                getResources().getText(R.string.tv_login_request),
                Toast.LENGTH_LONG).show();
        }
    }

    // onClickBtnTimeline���\�b�h(�^�C�����C����ʕ\������)
    public void onClickBtnTimeline(View v) {
        // Access Token Secret���擾�ł����ꍇ
        if (oauthConsumer.getTokenSecret() != null) {
            // �^�C�����C����ʂ֑J�ڗp�C���e���g����
            Intent intent =
                new Intent(TweetAppActivity.this, TimeLineActivity.class);
            // �R���V���[�}�I�u�W�F�N�g��ݒ�
            intent.putExtra(AppConstants.OAUTH_CONSUMER, oauthConsumer);
            // �^�C�����C����ʂ֑J��
            startActivity(intent);
            // �擾�ł��Ȃ������ꍇ
        } else {
            // �g�[�X�g���b�Z�[�W�\��
            Toast.makeText(this,
                getResources().getText(R.string.tv_login_request),
                Toast.LENGTH_LONG).show();
        }
    }
}
