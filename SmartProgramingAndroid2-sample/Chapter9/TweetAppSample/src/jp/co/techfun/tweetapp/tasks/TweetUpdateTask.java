package jp.co.techfun.tweetapp.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import jp.co.techfun.tweetapp.R;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

//�Ԃ₫���e�pAsyncTask
public class TweetUpdateTask extends AsyncTask<Object, Void, Void> {

    // Activity�擾�p
    private Activity activity;

    // �R���X�g���N�^
    public TweetUpdateTask(Activity activity) {
        super();
        this.activity = activity;
    }

    // doInBackground���\�b�h(�o�b�N�O���E���h����)
    @Override
    protected Void doInBackground(Object... params) {
        // POST�ʐM�p�I�u�W�F�N�g����(URL�ݒ�)
        HttpPost request = new HttpPost((String) params[0]);

        // ���M�p�����[�^�ݒ�(�Ԃ₫���ݒ�)
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("status", (String) params[1]));
        try {
            request.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            Log.e(getClass().getSimpleName(), "Encoding Error", e);
        }

        // Expect: 100-continue�̐ݒ����
        HttpParams httpparams = new BasicHttpParams();
        HttpProtocolParams.setUseExpectContinue(httpparams, false);
        // POST�ʐM�p�I�u�W�F�N�g�ɐݒ�
        request.setParams(httpparams);

        try {
            // POST�ʐM�p�I�u�W�F�N�g�ɏ���
            CommonsHttpOAuthConsumer consumer =
                (CommonsHttpOAuthConsumer) params[2];
            consumer.sign(request);
        } catch (OAuthMessageSignerException e) {
            Log.e(getClass().getSimpleName(), "Twitter OAuth Error", e);
        } catch (OAuthExpectationFailedException e) {
            Log.e(getClass().getSimpleName(), "Twitter OAuth Error", e);
        } catch (OAuthCommunicationException e) {
            Log.e(getClass().getSimpleName(), "Twitter OAuth Error", e);
        }

        // �N���C�A���g�I�u�W�F�N�g����
        HttpClient client = new DefaultHttpClient();

        // ���N�G�X�g���M���Ԃ₫���e
        try {
            client.execute(request);
        } catch (ClientProtocolException e) {
            Log.e(getClass().getSimpleName(),
                "Twitter TweetUpdateRequest Error", e);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(),
                "Twitter TweetUpdateRequest Error", e);
        }

        return null;
    }

    // onPostExecute���\�b�h(�o�b�N�O���E���h�I���㏈��)
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        // �Ԃ₫���͗pEditText���N���A
        EditText edittweet = (EditText) activity.findViewById(R.id.et_tweet);
        edittweet.setText("");

        // �Ԃ₫��ʏI��
        activity.finish();
    }
}
