package jp.co.techfun.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

// ブロードキャストレシーバー
public class SampleBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // インテントの取得
        Bundle bundle = intent.getExtras();
        // 選択されたアラーム音情報取得
        Uri uri = (Uri) bundle.get(AlarmClockSampleActivity.URI);

        // インテントの生成
        Intent newIntent = new Intent(context, AlarmOnOffActivity.class);
        newIntent.putExtra(AlarmClockSampleActivity.URI, uri);

        // アクティビティ以外からアクティビティを呼び出すための設定
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // アクティビティ開始
        context.startActivity(newIntent);
    }
}
