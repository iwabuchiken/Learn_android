package jp.co.techfun.feedreader;

// �L��1���̓��e��ێ�����N���X
public class FeedItem {

	// �L���̌��o��
	private String title;

	// �����N
	private String link;

	// �L���̔z�M����
	private String date;

	// �R���X�g���N�^
	public FeedItem(String title, String link, String pubDate) {
		super();
		this.title = title;
		this.link = link;
		this.date = pubDate;
	}

	// �L���̌��o����Ԃ����\�b�h
	public String getTitle() {
		return title;
	}

	// �����N��Ԃ����\�b�h
	public String getLink() {
		return link;
	}

	// �L���̔z�M������Ԃ����\�b�h
	public String getDate() {
		return date;
	}

	// ������\����Ԃ����\�b�h
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
