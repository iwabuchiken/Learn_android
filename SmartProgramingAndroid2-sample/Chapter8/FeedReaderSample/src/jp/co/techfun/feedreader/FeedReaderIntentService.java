package jp.co.techfun.feedreader;

import java.util.List;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

// ウィジェット更新サービス
public class FeedReaderIntentService extends IntentService {

	// インテントパラメータ用定義
	private static enum IntentParam {
		VIEW_FEED_PAGE, TITLE, LINK, DATE
	};

	// 前ページボタンアクション
	private static final String ACTION_PREVBTN_CLICK = "jp.co.techfun.feedreader.ACTION_PREVBTN_CLICK";

	// 次ページボタンアクション
	private static final String ACTION_NEXTBTN_CLICK = "jp.co.techfun.feedreader.ACTION_NEXTBTN_CLICK";

	// コンストラクタ(AppWidgetProvider呼び出し用)
	public FeedReaderIntentService() {
		super(FeedReaderIntentService.class.getSimpleName());
	}

	// onStartメソッド(同期処理)
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		// リモートビューを使用して、TextViewへ設定
		RemoteViews view = new RemoteViews(getPackageName(), R.layout.main);

		// 前/次ページボタンによるインテントの場合
		if (ACTION_PREVBTN_CLICK.equals(intent.getAction())
				|| ACTION_NEXTBTN_CLICK.equals(intent.getAction())) {

			// インテントパラメータを取得
			Bundle extras = intent.getExtras();
			String title = extras.getString(IntentParam.TITLE.name());
			String date = extras.getString(IntentParam.DATE.name());
			String link = extras.getString(IntentParam.LINK.name());

			// 画面表示内容を設定
			setViewContents(view, title, date, link);

		} else {

			// 読み込み中を表示
			view.setTextViewText(R.id.tv_title,
					getString(R.string.tv_title_wait));
		}

		// 前/次ページボタンを非表示
		view.setViewVisibility(R.id.ibtn_prev, View.GONE);
		view.setViewVisibility(R.id.ibtn_next, View.GONE);

		// AppWidgetを更新
		updateAppWidget(view);
	}

	// onHandleIntentメソッド(非同期処理)
	@Override
	protected void onHandleIntent(Intent intent) {

		// フィードを取得
		List<FeedItem> feedItems = FeedReader.getGoogleNewsItemList();

		// リモートビューを使用して、TextViewへ設定
		RemoteViews view = new RemoteViews(getPackageName(), R.layout.main);

		Integer viewFeedPage = 0;
		// 前/次ボタンによるインテントの場合
		if (ACTION_PREVBTN_CLICK.equals(intent.getAction())
				|| ACTION_NEXTBTN_CLICK.equals(intent.getAction())) {

			// インテントパラメータを取得
			Bundle extras = intent.getExtras();

			// 表示ページ番号
			viewFeedPage = extras.getInt(IntentParam.VIEW_FEED_PAGE.name());
		} else {

			// 画面表示内容を設定
			setViewContents(view, feedItems.get(viewFeedPage));
		}

		// 前/次ページボタンを表示
		view.setViewVisibility(R.id.ibtn_prev, View.VISIBLE);
		view.setViewVisibility(R.id.ibtn_next, View.VISIBLE);

		// ボタンへインテントを設定
		setBtnPendingIntent(view, viewFeedPage, feedItems);

		// AppWidgetを更新
		updateAppWidget(view);
	}

	// setViewContentsメソッド(AppWidget表示内容設定処理)
	private void setViewContents(RemoteViews view, String title, String date,
			String link) {
		// 記事の見出しを設定
		view.setTextViewText(R.id.tv_title, title);
		view.setTextViewText(R.id.tv_date, date);

		// 記事の見出しを押下時のリンク設定
		Intent onClickIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
		PendingIntent onClickPendingIntent = PendingIntent.getActivity(this, 0,
				onClickIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		view.setOnClickPendingIntent(R.id.tv_title, onClickPendingIntent);
	}

	// setViewContentsメソッド(AppWidget表示内容設定処理)
	private void setViewContents(RemoteViews view, FeedItem item) {
		setViewContents(view, item.getTitle(), item.getDate(), item.getLink());
	}

	// setBtnPendingIntentメソッド(前/次ページボタンにインテント設定処理)
	private void setBtnPendingIntent(RemoteViews view, Integer viewFeedPage,
			List<FeedItem> feedItems) {
		Intent prevBtnIntent = new Intent();
		Intent nextBtnIntent = new Intent();

		// 「PendingIntent」を使ってIntentを発信させるのに使用
		prevBtnIntent.setAction(ACTION_PREVBTN_CLICK);
		nextBtnIntent.setAction(ACTION_NEXTBTN_CLICK);

		// インテントパラメータを設定
		// 前ページがない場合
		Integer prevPage = viewFeedPage - 1;
		if (prevPage < 0) {
			// 最後のフィードを表示
			prevPage = feedItems.size() - 1;
		}
		// 次ページがない場合
		Integer nextPage = viewFeedPage + 1;
		if (feedItems.size() <= nextPage) {
			// 1件目を表示
			nextPage = 0;
		}

		// 前ページボタン用インテントパラメータ
		prevBtnIntent.putExtra(IntentParam.VIEW_FEED_PAGE.name(), prevPage);
		prevBtnIntent.putExtra(IntentParam.TITLE.name(), feedItems
				.get(prevPage).getTitle());
		prevBtnIntent.putExtra(IntentParam.DATE.name(), feedItems.get(prevPage)
				.getDate());
		prevBtnIntent.putExtra(IntentParam.LINK.name(), feedItems.get(prevPage)
				.getLink());
		// 次ページボタン用インテントパラメータ
		nextBtnIntent.putExtra(IntentParam.VIEW_FEED_PAGE.name(), nextPage);
		nextBtnIntent.putExtra(IntentParam.TITLE.name(), feedItems
				.get(nextPage).getTitle());
		nextBtnIntent.putExtra(IntentParam.DATE.name(), feedItems.get(nextPage)
				.getDate());
		nextBtnIntent.putExtra(IntentParam.LINK.name(), feedItems.get(nextPage)
				.getLink());

		// PendingIntentインスタンス取得
		PendingIntent prevBtnPendingIntent = PendingIntent.getService(this, 0,
				prevBtnIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent nextBtnPendingIntent = PendingIntent.getService(this, 0,
				nextBtnIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// 前/次ページボタンにPendingIntentを設定
		view.setOnClickPendingIntent(R.id.ibtn_prev, prevBtnPendingIntent);
		view.setOnClickPendingIntent(R.id.ibtn_next, nextBtnPendingIntent);
	}

	// updateAppWidgetメソッド(AppWidget更新処理)
	private void updateAppWidget(RemoteViews view) {

		// AppWidgetManagerを取得
		AppWidgetManager manager = AppWidgetManager.getInstance(this);

		// AppWidgetを取得
		ComponentName widget = new ComponentName(this,
				FeedReaderSampleProvider.class);

		// AppWidgetManagerでAppWidgetを更新
		manager.updateAppWidget(widget, view);
	}
}
