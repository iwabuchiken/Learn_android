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

// �E�B�W�F�b�g�X�V�T�[�r�X
public class FeedReaderIntentService extends IntentService {

	// �C���e���g�p�����[�^�p��`
	private static enum IntentParam {
		VIEW_FEED_PAGE, TITLE, LINK, DATE
	};

	// �O�y�[�W�{�^���A�N�V����
	private static final String ACTION_PREVBTN_CLICK = "jp.co.techfun.feedreader.ACTION_PREVBTN_CLICK";

	// ���y�[�W�{�^���A�N�V����
	private static final String ACTION_NEXTBTN_CLICK = "jp.co.techfun.feedreader.ACTION_NEXTBTN_CLICK";

	// �R���X�g���N�^(AppWidgetProvider�Ăяo���p)
	public FeedReaderIntentService() {
		super(FeedReaderIntentService.class.getSimpleName());
	}

	// onStart���\�b�h(��������)
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		// �����[�g�r���[���g�p���āATextView�֐ݒ�
		RemoteViews view = new RemoteViews(getPackageName(), R.layout.main);

		// �O/���y�[�W�{�^���ɂ��C���e���g�̏ꍇ
		if (ACTION_PREVBTN_CLICK.equals(intent.getAction())
				|| ACTION_NEXTBTN_CLICK.equals(intent.getAction())) {

			// �C���e���g�p�����[�^���擾
			Bundle extras = intent.getExtras();
			String title = extras.getString(IntentParam.TITLE.name());
			String date = extras.getString(IntentParam.DATE.name());
			String link = extras.getString(IntentParam.LINK.name());

			// ��ʕ\�����e��ݒ�
			setViewContents(view, title, date, link);

		} else {

			// �ǂݍ��ݒ���\��
			view.setTextViewText(R.id.tv_title,
					getString(R.string.tv_title_wait));
		}

		// �O/���y�[�W�{�^�����\��
		view.setViewVisibility(R.id.ibtn_prev, View.GONE);
		view.setViewVisibility(R.id.ibtn_next, View.GONE);

		// AppWidget���X�V
		updateAppWidget(view);
	}

	// onHandleIntent���\�b�h(�񓯊�����)
	@Override
	protected void onHandleIntent(Intent intent) {

		// �t�B�[�h���擾
		List<FeedItem> feedItems = FeedReader.getGoogleNewsItemList();

		// �����[�g�r���[���g�p���āATextView�֐ݒ�
		RemoteViews view = new RemoteViews(getPackageName(), R.layout.main);

		Integer viewFeedPage = 0;
		// �O/���{�^���ɂ��C���e���g�̏ꍇ
		if (ACTION_PREVBTN_CLICK.equals(intent.getAction())
				|| ACTION_NEXTBTN_CLICK.equals(intent.getAction())) {

			// �C���e���g�p�����[�^���擾
			Bundle extras = intent.getExtras();

			// �\���y�[�W�ԍ�
			viewFeedPage = extras.getInt(IntentParam.VIEW_FEED_PAGE.name());
		} else {

			// ��ʕ\�����e��ݒ�
			setViewContents(view, feedItems.get(viewFeedPage));
		}

		// �O/���y�[�W�{�^����\��
		view.setViewVisibility(R.id.ibtn_prev, View.VISIBLE);
		view.setViewVisibility(R.id.ibtn_next, View.VISIBLE);

		// �{�^���փC���e���g��ݒ�
		setBtnPendingIntent(view, viewFeedPage, feedItems);

		// AppWidget���X�V
		updateAppWidget(view);
	}

	// setViewContents���\�b�h(AppWidget�\�����e�ݒ菈��)
	private void setViewContents(RemoteViews view, String title, String date,
			String link) {
		// �L���̌��o����ݒ�
		view.setTextViewText(R.id.tv_title, title);
		view.setTextViewText(R.id.tv_date, date);

		// �L���̌��o�����������̃����N�ݒ�
		Intent onClickIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
		PendingIntent onClickPendingIntent = PendingIntent.getActivity(this, 0,
				onClickIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		view.setOnClickPendingIntent(R.id.tv_title, onClickPendingIntent);
	}

	// setViewContents���\�b�h(AppWidget�\�����e�ݒ菈��)
	private void setViewContents(RemoteViews view, FeedItem item) {
		setViewContents(view, item.getTitle(), item.getDate(), item.getLink());
	}

	// setBtnPendingIntent���\�b�h(�O/���y�[�W�{�^���ɃC���e���g�ݒ菈��)
	private void setBtnPendingIntent(RemoteViews view, Integer viewFeedPage,
			List<FeedItem> feedItems) {
		Intent prevBtnIntent = new Intent();
		Intent nextBtnIntent = new Intent();

		// �uPendingIntent�v���g����Intent�𔭐M������̂Ɏg�p
		prevBtnIntent.setAction(ACTION_PREVBTN_CLICK);
		nextBtnIntent.setAction(ACTION_NEXTBTN_CLICK);

		// �C���e���g�p�����[�^��ݒ�
		// �O�y�[�W���Ȃ��ꍇ
		Integer prevPage = viewFeedPage - 1;
		if (prevPage < 0) {
			// �Ō�̃t�B�[�h��\��
			prevPage = feedItems.size() - 1;
		}
		// ���y�[�W���Ȃ��ꍇ
		Integer nextPage = viewFeedPage + 1;
		if (feedItems.size() <= nextPage) {
			// 1���ڂ�\��
			nextPage = 0;
		}

		// �O�y�[�W�{�^���p�C���e���g�p�����[�^
		prevBtnIntent.putExtra(IntentParam.VIEW_FEED_PAGE.name(), prevPage);
		prevBtnIntent.putExtra(IntentParam.TITLE.name(), feedItems
				.get(prevPage).getTitle());
		prevBtnIntent.putExtra(IntentParam.DATE.name(), feedItems.get(prevPage)
				.getDate());
		prevBtnIntent.putExtra(IntentParam.LINK.name(), feedItems.get(prevPage)
				.getLink());
		// ���y�[�W�{�^���p�C���e���g�p�����[�^
		nextBtnIntent.putExtra(IntentParam.VIEW_FEED_PAGE.name(), nextPage);
		nextBtnIntent.putExtra(IntentParam.TITLE.name(), feedItems
				.get(nextPage).getTitle());
		nextBtnIntent.putExtra(IntentParam.DATE.name(), feedItems.get(nextPage)
				.getDate());
		nextBtnIntent.putExtra(IntentParam.LINK.name(), feedItems.get(nextPage)
				.getLink());

		// PendingIntent�C���X�^���X�擾
		PendingIntent prevBtnPendingIntent = PendingIntent.getService(this, 0,
				prevBtnIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent nextBtnPendingIntent = PendingIntent.getService(this, 0,
				nextBtnIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// �O/���y�[�W�{�^����PendingIntent��ݒ�
		view.setOnClickPendingIntent(R.id.ibtn_prev, prevBtnPendingIntent);
		view.setOnClickPendingIntent(R.id.ibtn_next, nextBtnPendingIntent);
	}

	// updateAppWidget���\�b�h(AppWidget�X�V����)
	private void updateAppWidget(RemoteViews view) {

		// AppWidgetManager���擾
		AppWidgetManager manager = AppWidgetManager.getInstance(this);

		// AppWidget���擾
		ComponentName widget = new ComponentName(this,
				FeedReaderSampleProvider.class);

		// AppWidgetManager��AppWidget���X�V
		manager.updateAppWidget(widget, view);
	}
}
