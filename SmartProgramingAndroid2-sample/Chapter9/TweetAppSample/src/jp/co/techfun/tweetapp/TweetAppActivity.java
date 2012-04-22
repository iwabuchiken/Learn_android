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

// トップ画面Activity
public class TweetAppActivity extends Activity {

    // OAuth認証コンシューマインスタンス
    private CommonsHttpOAuthConsumer oauthConsumer = null;
    // OAuth認証プロバイダインスタンス
    private CommonsHttpOAuthProvider oauthProvider = null;

    // onCreateメソッド(画面初期表示イベント)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイル指定
        setContentView(R.layout.tweet_app);
    }

    // onStartメソッド(アクティビティ起動イベント)
    @Override
    protected void onStart() {
        super.onStart();

        // コンシューマオブジェクトが設定されていない場合
        if (oauthConsumer == null) {
            // Consumer KeyとConsumer Secretを設定してコンシューマオブジェクト生成
            oauthConsumer =
                new CommonsHttpOAuthConsumer(AppConstants.CONSUMER_KEY,
                    AppConstants.CONSUMER_SECRET);
        }

        // プロバイダオブジェクトが設定されていない場合
        if (oauthProvider == null) {
            // プロバイダオブジェクト生成
            oauthProvider =
                new CommonsHttpOAuthProvider(
                    AppConstants.REQUEST_TOKEN_ENDPOINT_URL,
                    AppConstants.ACCESS_TOKEN_ENDPOINT_URL,
                    AppConstants.AUTHORIZATION_WEBSITE_URL);
        }
    }

    // onClickBtnLoginメソッド(「ログイン」ボタンクリック処理)
    public void onClickBtnLogin(View v) {
        String authUrl = "";
        try {
            // Twitter認証画面の起動用設定
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

        // Twitter認証画面起動
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
    }

    // onResumeメソッド(Twitter認証画面後の処理)
    @Override
    protected void onResume() {
        super.onResume();

        // インテント取得
        Intent data = getIntent();

        // インテントが取得できなかった場合終了
        if (data == null) {
            return;
        }

        // URI取得
        Uri uri = data.getData();

        // URIが取得できなかった場合終了
        if (uri == null) {
            return;
        }

        // Twitter認証画面からでない場合終了
        if (!uri.toString().startsWith(AppConstants.CALLBACK_URL)) {
            return;
        }

        // Twitter認証画面で許可しなかった場合終了
        if (uri.getQueryParameter(AppConstants.REQUEST_TOKEN_DENIED) != null) {
            oauthConsumer.setTokenWithSecret(null, null);
            return;
        }

        // Access Token取得
        String token = uri.getQueryParameter(OAuth.OAUTH_TOKEN);
        // Verifier取得
        String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

        // Access Token Secret取得
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

        // インテントのURIをクリア
        getIntent().setData(null);
    }

    // onClickBtnTweetメソッド(「つぶやく」ボタンクリック処理)
    public void onClickBtnTweet(View v) {
        // Access Token Secretが取得できた場合
        if (oauthConsumer.getTokenSecret() != null) {
            // つぶやき画面へ遷移用インテント生成
            Intent intent =
                new Intent(TweetAppActivity.this, TweetUpdateActivity.class);
            // コンシューマオブジェクトを設定
            intent.putExtra(AppConstants.OAUTH_CONSUMER, oauthConsumer);
            // つぶやき画面へ遷移
            startActivity(intent);
            // 取得できなかった場合
        } else {
            // トーストメッセージ表示
            Toast.makeText(this,
                getResources().getText(R.string.tv_login_request),
                Toast.LENGTH_LONG).show();
        }
    }

    // onClickBtnTimelineメソッド(タイムライン画面表示処理)
    public void onClickBtnTimeline(View v) {
        // Access Token Secretが取得できた場合
        if (oauthConsumer.getTokenSecret() != null) {
            // タイムライン画面へ遷移用インテント生成
            Intent intent =
                new Intent(TweetAppActivity.this, TimeLineActivity.class);
            // コンシューマオブジェクトを設定
            intent.putExtra(AppConstants.OAUTH_CONSUMER, oauthConsumer);
            // タイムライン画面へ遷移
            startActivity(intent);
            // 取得できなかった場合
        } else {
            // トーストメッセージ表示
            Toast.makeText(this,
                getResources().getText(R.string.tv_login_request),
                Toast.LENGTH_LONG).show();
        }
    }
}
