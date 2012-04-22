package @packageName@;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class @className@ extends @superClassName@ 
										implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.@layoutName@);
        
        
        // get a Button object
        Button button = (Button) findViewById(R.id.backbutton);
        // set a listener on the button
        button.setOnClickListener(this);
        
    }//public void onCreate(Bundle savedInstanceState)

	@Override
	public void onClick(View arg0) {
		// TODO 自動生成されたメソッド・スタブ
		// generate  an Intent
		Intent intent = new Intent(StartServiceSampleActivity.this, StartServiceSampleService.class);
		
		
		finish();
	}
}//public class AppleActivity extends Activity
