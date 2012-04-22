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

// つぶやき画面Activity
public class TweetUpdateActivity extends Activity {
    // OAuth認証コンシューマインスタンス
    private CommonsHttpOAuthConsumer consumer = null;

    // 非同期でつぶやきを投稿するためのタスクオブジェクト生成
    private final TweetUpdateTask task = new TweetUpdateTask(this);

    // つぶやき入力用EditText初期化
    private EditText edittweet = null;

    // onCreateメソッド(画面初期表示イベント)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイル指定
        setContentView(R.layout.tweet_update);

        // コンシューマオブジェクト取得
        consumer =
            (CommonsHttpOAuthConsumer) getIntent().getExtras().getSerializable(
                AppConstants.OAUTH_CONSUMER);

        // つぶやき入力用EditText取得
        edittweet = (EditText) findViewById(R.id.et_tweet);

        // つぶやき内容変更時用のリスナー登録
        edittweet.addTextChangedListener(new TextWatcher() {

            // onTextChangedメソッド(つぶやき内容変更中イベント)
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            }

            // beforeTextChangedメソッド(つぶやき内容変更前イベント)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
            }

            // afterTextChangedメソッド(つぶやき内容変更後イベント)
            @Override
            public void afterTextChanged(Editable s) {

                // 入力可能文字数表示用TextView取得
                TextView charscounts =
                    (TextView) findViewById(R.id.tv_charscount);
                // 入力可能文字数設定
                int count = 140 - s.length();
                charscounts.setText(String.valueOf(count));
                // 140文字以上入力された場合、トースト表示
                if (count < 0) {
                    Toast.makeText(getApplicationContext(),
                        getResources().getText(R.string.tv_charsmax),
                        Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // onClickBtnTweetUpdateメソッド(「つぶやき」ボタンクリック処理)
    public void onClickBtnTweetUpdate(View v) {
        // つぶやき投稿
        task.execute(AppConstants.TWEET_REQUEST_URL, edittweet.getText()
            .toString(), consumer);
    }

    // onClickBtnBackメソッド(「戻る」ボタンクリック処理)
    public void onClickBtnBack(View v) {
        // アクティビティ終了
        finish();
    }
}
