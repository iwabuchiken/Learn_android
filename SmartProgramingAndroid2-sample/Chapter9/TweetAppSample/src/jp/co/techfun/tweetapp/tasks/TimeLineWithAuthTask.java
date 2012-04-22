package jp.co.techfun.tweetapp.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.techfun.tweetapp.R;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

// �^�C�����C���擾�pAsyncTask
public class TimeLineWithAuthTask extends
    AsyncTask<Object, String, List<TweetEntity>> {

    // ListActivity�擾�p
    private ListActivity activity;
    // �v���O���X�_�C�A���O
    private ProgressDialog dialog;

    // �R���X�g���N�^
    public TimeLineWithAuthTask(ListActivity activity) {
        super();
        this.activity = activity;
    }

    // onPreExecute���\�b�h(�o�b�N�O���E���h�����O����)
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // �v���O���X�_�C�A���O�\��
        dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(activity.getResources().getText(
            R.string.dlg_get_timeline));
        dialog.setCancelable(true);
        dialog.show();
    }

    // doInBackground���\�b�h(�o�b�N�O���E���h����)
    @Override
    protected List<TweetEntity> doInBackground(Object... params) {
        // GET�ʐM�p�I�u�W�F�N�g����
        HttpGet request = new HttpGet((String) params[0]);
        // Expect: 100-continue�̐ݒ����
        HttpParams httpparams = new BasicHttpParams();
        HttpProtocolParams.setUseExpectContinue(httpparams, false);
        // GET�ʐM�p�I�u�W�F�N�g�ɐݒ�
        request.setParams(httpparams);

        try {
            // GET�ʐM�p�I�u�W�F�N�g�ɏ���
            CommonsHttpOAuthConsumer consumer =
                (CommonsHttpOAuthConsumer) params[1];
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

        // �^�C�����C���擾�p���X�g����
        List<TweetEntity> result = new ArrayList<TweetEntity>();

        // ���N�G�X�g���M���^�C�����C���擾
        try {
            result = client.execute(request, new TimeLineResponseHandler());

        } catch (ClientProtocolException e) {
            Log.e(getClass().getSimpleName(), "Twitter TimeLineRequest Error",
                e);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Twitter TimeLineRequest Error",
                e);
        } finally {
            // �ʐM�I��
            client.getConnectionManager().shutdown();
        }

        // �^�C�����C�����X�g��߂�
        return result;
    }

    // onPostExecute���\�b�h(�o�b�N�O���E���h�I���㏈��)
    @Override
    protected void onPostExecute(List<TweetEntity> result) {
        super.onPostExecute(result);

        // ListView�Ƀ^�C�����C������ݒ�
        TimeLineAdapter adapter =
            new TimeLineAdapter(activity, R.layout.tweet, result);
        activity.setListAdapter(adapter);
        // �v���O���X�_�C�A���O�I��
        dialog.dismiss();
    }
}
