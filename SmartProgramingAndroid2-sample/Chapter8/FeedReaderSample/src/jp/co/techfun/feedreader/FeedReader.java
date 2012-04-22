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

// XML�f�[�^��̓N���X
final class FeedReader {

	// �����t�H�[�}�b�g(�L���z�M�����p)
	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	// �����R�[�h
	private static final String ENCODE = "UTF-8";

	// Google News Feed
	private static final String GOOGLE_NEWS_RSS_TOPIC = "http://news.google.com/news?hl=ja&ned=us&ie="
			+ ENCODE + "&oe=" + ENCODE + "&output=rss&topic=h&num=20";

	// �R���X�g���N�^
	private FeedReader() {
	}

	// getGoogleNewsItemList���\�b�h(�z�M�L����M����)
	static List<FeedItem> getGoogleNewsItemList() {
		List<FeedItem> feedItems = new ArrayList<FeedItem>();

		try {
			// URL�C���X�^���X����
			URL url = new URL(GOOGLE_NEWS_RSS_TOPIC);

			// XmlPullParserFactory�C���X�^���X����
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			// XmlPullParser�擾
			XmlPullParser xmlPullParser = factory.newPullParser();
			// XmlPullParser�ɉ�̓f�[�^�ݒ�
			xmlPullParser.setInput(url.openStream(), ENCODE);

			// �C�x���g�^�C�v�擾
			int eventType = xmlPullParser.next();

			// item�^�O�܂œǂݔ�΂����߂̃t���O
			boolean findItemTag = false;

			String title = null;
			String link = null;

			// ��M�f�[�^�̏I�[�܂ŉ��
			while (XmlPullParser.END_DOCUMENT != eventType) {

				switch (eventType) {

				// �^�O�J�n�ʒu�����������ꍇ
				case XmlPullParser.START_TAG:
					// �^�O�����擾
					String tagName = xmlPullParser.getName();

					// item�^�O��������܂ŃX�L�b�v
					if (!findItemTag) {
						findItemTag = "item".equals(tagName);
						break;
					}

					if ("title".equals(tagName)) {
						// �L���̌��o���擾
						title = xmlPullParser.nextText();

					} else if ("link".equals(tagName)) {
						// �����N�擾
						link = xmlPullParser.nextText();

					} else if ("pubDate".equals(tagName)) {

						// �����擾
						String pubDate = xmlPullParser.nextText();
						Date date = new Date(pubDate);

						// FeedItem�I�u�W�F�N�g����
						feedItems.add(new FeedItem(title, link, SDF
								.format(date)));
					}
					break;

				default:
					break;
				}

				// �ǂݔ�΂�
				eventType = xmlPullParser.next();
			}

		} catch (XmlPullParserException e) {
			Log.w(FeedReader.class.getSimpleName(), "XML�t�B�[�h�̉�͂Ɏ��s���܂����B", e);
		} catch (IOException e) {
			Log.w(FeedReader.class.getSimpleName(), "XML�t�B�[�h�̉�͂Ɏ��s���܂����B", e);
		}

		return feedItems;
	}
}
