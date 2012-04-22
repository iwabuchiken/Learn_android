package jp.co.techfun.feedreader;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

// ウィジェットクラス
public class FeedReaderSampleProvider extends AppWidgetProvider {

	// onUpdateメソッド(AppWidget更新時イベント)
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		// サービス呼び出し
		startService(context);
	}

	// onReceiveメソッド(AppWidget再表示時イベント)
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		// サービス呼び出し
		startService(context);
	}

	// startServiceメソッド(サービス呼び出し処理)
	private void startService(Context context) {

		// サービスを呼び出すインテント生成
		Intent intentService = new Intent(context,
				FeedReaderIntentService.class);

		// サービス呼び出し
		context.startService(intentService);
	}
}
