package jp.co.techfun.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

// �u���[�h�L���X�g���V�[�o�[
public class SampleBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // �C���e���g�̎擾
        Bundle bundle = intent.getExtras();
        // �I�����ꂽ�A���[�������擾
        Uri uri = (Uri) bundle.get(AlarmClockSampleActivity.URI);

        // �C���e���g�̐���
        Intent newIntent = new Intent(context, AlarmOnOffActivity.class);
        newIntent.putExtra(AlarmClockSampleActivity.URI, uri);

        // �A�N�e�B�r�e�B�ȊO����A�N�e�B�r�e�B���Ăяo�����߂̐ݒ�
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // �A�N�e�B�r�e�B�J�n
        context.startActivity(newIntent);
    }
}
