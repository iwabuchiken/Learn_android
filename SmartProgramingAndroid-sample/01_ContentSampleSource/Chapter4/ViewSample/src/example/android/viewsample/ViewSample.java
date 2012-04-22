package example.android.viewsample;

import android.app.Activity;
import android.os.Bundle;

public class ViewSample extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウトとビューの設定ファイル「viewsample」を設定
        setContentView(R.layout.viewsample);
    }
}