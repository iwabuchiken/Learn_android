package example.android.startservicesample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartServiceSampleActivity extends Activity 
														implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startservicesample);
        
        // set a listener on the button
        // get a Button object
        Button button = (Button) findViewById(R.id.startButton);
        // set a listener on the button
        button.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		// generate  an Intent
		Intent intent = new Intent(StartServiceSampleActivity.this, StartServiceSampleService.class);
		
		// set the end time on the intent
		EditText stopcount = (EditText) findViewById(R.id.stopcount);
		intent.putExtra("STOPTIME", stopcount.getText().toString());
		
		// start service
		startService(intent);
	}//public void onClick(View v)
}