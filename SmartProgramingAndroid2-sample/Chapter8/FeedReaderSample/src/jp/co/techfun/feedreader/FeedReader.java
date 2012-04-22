package jp.co.techfun.feedreader;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

// XMLデータ解析クラス
final class FeedReader {

	// 日時フォーマット(記事配信日時用)
	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	// 文字コード
	private static final String ENCODE = "UTF-8";

	// Google News Feed
	private static final String GOOGLE_NEWS_RSS_TOPIC = "http://news.google.com/news?hl=ja&ned=us&ie="
			+ ENCODE + "&oe=" + ENCODE + "&output=rss&topic=h&num=20";

	// コンストラクタ
	private FeedReader() {
	}

	// getGoogleNewsItemListメソッド(配信記事受信処理)
	static List<FeedItem> getGoogleNewsItemList() {
		List<FeedItem> feedItems = new ArrayList<FeedItem>();

		try {
			// URLインスタンス生成
			URL url = new URL(GOOGLE_NEWS_RSS_TOPIC);

			// XmlPullParserFactoryインスタンス生成
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			// XmlPullParser取得
			XmlPullParser xmlPullParser = factory.newPullParser();
			// XmlPullParserに解析データ設定
			xmlPullParser.setInput(url.openStream(), ENCODE);

			// イベントタイプ取得
			int eventType = xmlPullParser.next();

			// itemタグまで読み飛ばすためのフラグ
			boolean findItemTag = false;

			String title = null;
			String link = null;

			// 受信データの終端まで解析
			while (XmlPullParser.END_DOCUMENT != eventType) {

				switch (eventType) {

				// タグ開始位置が見つかった場合
				case XmlPullParser.START_TAG:
					// タグ名を取得
					String tagName = xmlPullParser.getName();

					// itemタグが見つかるまでスキップ
					if (!findItemTag) {
						findItemTag = "item".equals(tagName);
						break;
					}

					if ("title".equals(tagName)) {
						// 記事の見出し取得
						title = xmlPullParser.nextText();

					} else if ("link".equals(tagName)) {
						// リンク取得
						link = xmlPullParser.nextText();

					} else if ("pubDate".equals(tagName)) {

						// 日時取得
						String pubDate = xmlPullParser.nextText();
						Date date = new Date(pubDate);

						// FeedItemオブジェクト生成
						feedItems.add(new FeedItem(title, link, SDF
								.format(date)));
					}
					break;

				default:
					break;
				}

				// 読み飛ばし
				eventType = xmlPullParser.next();
			}

		} catch (XmlPullParserException e) {
			Log.w(FeedReader.class.getSimpleName(), "XMLフィードの解析に失敗しました。", e);
		} catch (IOException e) {
			Log.w(FeedReader.class.getSimpleName(), "XMLフィードの解析に失敗しました。", e);
		}

		return feedItems;
	}
}
