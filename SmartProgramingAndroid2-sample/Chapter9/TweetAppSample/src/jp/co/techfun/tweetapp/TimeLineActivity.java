package jp.co.techfun.tweetapp;

import jp.co.techfun.tweetapp.commons.AppConstants;
import jp.co.techfun.tweetapp.tasks.TimeLineWithAuthTask;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;

// タイムライン画面Activity
public class TimeLineActivity extends ListActivity {
    // OAuth認証コンシューマインスタンス
    private CommonsHttpOAuthConsumer consumer = null;

    // 非同期でタイムラインを取得するためのタスクオブジェクト生成
    private final TimeLineWithAuthTask task = new TimeLineWithAuthTask(this);

    // onCreateメソッド(画面初期表示イベント)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイル指定
        setContentView(R.layout.timeline);

        // コンシューマオブジェクト取得
        consumer =
            (CommonsHttpOAuthConsumer) getIntent().getExtras().getSerializable(
                AppConstants.OAUTH_CONSUMER);
    }

    // onStartメソッド(アクティビティ起動イベント)
    @Override
    protected void onStart() {
        super.onStart();

        // タイムライン取得
        task.execute(AppConstants.TIMELINE_REQUEST_URL, consumer);
    }

    // onClickBtnBackメソッド(「戻る」ボタンクリック処理)
    public void onClickBtnBack(View v) {
        finish();
    }

}
