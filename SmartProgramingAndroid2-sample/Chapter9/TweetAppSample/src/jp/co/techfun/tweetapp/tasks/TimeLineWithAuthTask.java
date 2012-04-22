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

// タイムライン取得用AsyncTask
public class TimeLineWithAuthTask extends
    AsyncTask<Object, String, List<TweetEntity>> {

    // ListActivity取得用
    private ListActivity activity;
    // プログレスダイアログ
    private ProgressDialog dialog;

    // コンストラクタ
    public TimeLineWithAuthTask(ListActivity activity) {
        super();
        this.activity = activity;
    }

    // onPreExecuteメソッド(バックグラウンド処理前処理)
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // プログレスダイアログ表示
        dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(activity.getResources().getText(
            R.string.dlg_get_timeline));
        dialog.setCancelable(true);
        dialog.show();
    }

    // doInBackgroundメソッド(バックグラウンド処理)
    @Override
    protected List<TweetEntity> doInBackground(Object... params) {
        // GET通信用オブジェクト生成
        HttpGet request = new HttpGet((String) params[0]);
        // Expect: 100-continueの設定解除
        HttpParams httpparams = new BasicHttpParams();
        HttpProtocolParams.setUseExpectContinue(httpparams, false);
        // GET通信用オブジェクトに設定
        request.setParams(httpparams);

        try {
            // GET通信用オブジェクトに署名
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

        // クライアントオブジェクト生成
        HttpClient client = new DefaultHttpClient();

        // タイムライン取得用リスト生成
        List<TweetEntity> result = new ArrayList<TweetEntity>();

        // リクエスト送信しタイムライン取得
        try {
            result = client.execute(request, new TimeLineResponseHandler());

        } catch (ClientProtocolException e) {
            Log.e(getClass().getSimpleName(), "Twitter TimeLineRequest Error",
                e);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Twitter TimeLineRequest Error",
                e);
        } finally {
            // 通信終了
            client.getConnectionManager().shutdown();
        }

        // タイムラインリストを戻す
        return result;
    }

    // onPostExecuteメソッド(バックグラウンド終了後処理)
    @Override
    protected void onPostExecute(List<TweetEntity> result) {
        super.onPostExecute(result);

        // ListViewにタイムライン情報を設定
        TimeLineAdapter adapter =
            new TimeLineAdapter(activity, R.layout.tweet, result);
        activity.setListAdapter(adapter);
        // プログレスダイアログ終了
        dialog.dismiss();
    }
}
