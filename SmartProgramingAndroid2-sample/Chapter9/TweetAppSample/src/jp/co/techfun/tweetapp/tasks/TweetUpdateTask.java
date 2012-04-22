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

//つぶやき投稿用AsyncTask
public class TweetUpdateTask extends AsyncTask<Object, Void, Void> {

    // Activity取得用
    private Activity activity;

    // コンストラクタ
    public TweetUpdateTask(Activity activity) {
        super();
        this.activity = activity;
    }

    // doInBackgroundメソッド(バックグラウンド処理)
    @Override
    protected Void doInBackground(Object... params) {
        // POST通信用オブジェクト生成(URL設定)
        HttpPost request = new HttpPost((String) params[0]);

        // 送信パラメータ設定(つぶやき情報設定)
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("status", (String) params[1]));
        try {
            request.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            Log.e(getClass().getSimpleName(), "Encoding Error", e);
        }

        // Expect: 100-continueの設定解除
        HttpParams httpparams = new BasicHttpParams();
        HttpProtocolParams.setUseExpectContinue(httpparams, false);
        // POST通信用オブジェクトに設定
        request.setParams(httpparams);

        try {
            // POST通信用オブジェクトに署名
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

        // クライアントオブジェクト生成
        HttpClient client = new DefaultHttpClient();

        // リクエスト送信しつぶやき投稿
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

    // onPostExecuteメソッド(バックグラウンド終了後処理)
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        // つぶやき入力用EditTextをクリア
        EditText edittweet = (EditText) activity.findViewById(R.id.et_tweet);
        edittweet.setText("");

        // つぶやき画面終了
        activity.finish();
    }
}
