package jp.co.techfun.feedreader;

// 記事1件の内容を保持するクラス
public class FeedItem {

	// 記事の見出し
	private String title;

	// リンク
	private String link;

	// 記事の配信日時
	private String date;

	// コンストラクタ
	public FeedItem(String title, String link, String pubDate) {
		super();
		this.title = title;
		this.link = link;
		this.date = pubDate;
	}

	// 記事の見出しを返すメソッド
	public String getTitle() {
		return title;
	}

	// リンクを返すメソッド
	public String getLink() {
		return link;
	}

	// 記事の配信日時を返すメソッド
	public String getDate() {
		return date;
	}

	// 文字列表現を返すメソッド
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FeedItem={");
		sb.append("title=" + title);
		sb.append(",link=" + link);
		sb.append(",pubDate=" + date);
		sb.append("}");
		return sb.toString();
	}
}
