package example.android.viewsample;

import android.app.Activity;
import android.os.Bundle;

public class ViewSample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ƃr���[�̐ݒ�t�@�C���uviewsample�v��ݒ�
        setContentView(R.layout.viewsample);
    }
}