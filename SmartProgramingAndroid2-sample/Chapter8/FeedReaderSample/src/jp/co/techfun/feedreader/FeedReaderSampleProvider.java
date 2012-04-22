package jp.co.techfun.feedreader;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

// �E�B�W�F�b�g�N���X
public class FeedReaderSampleProvider extends AppWidgetProvider {

	// onUpdate���\�b�h(AppWidget�X�V���C�x���g)
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		// �T�[�r�X�Ăяo��
		startService(context);
	}

	// onReceive���\�b�h(AppWidget�ĕ\�����C�x���g)
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		// �T�[�r�X�Ăяo��
		startService(context);
	}

	// startService���\�b�h(�T�[�r�X�Ăяo������)
	private void startService(Context context) {

		// �T�[�r�X���Ăяo���C���e���g����
		Intent intentService = new Intent(context,
				FeedReaderIntentService.class);

		// �T�[�r�X�Ăяo��
		context.startService(intentService);
	}
}
