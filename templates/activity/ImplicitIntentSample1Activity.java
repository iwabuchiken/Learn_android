package example.android.implicitintentsample1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ImplicitIntentSample1Activity extends Activity implements OnItemClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstlayout);
        
        // ListView object
        ListView listview = (ListView) findViewById(R.id.fruitlist);
        
        // set a listener
        listview.setOnItemClickListener(this);
    }

	@Override
	public void onItemClick(AdapterView<?> parent, 
									View view, int position, long id) {
		// TODO 自動生成されたメソッド・スタブ
		// get a ListView
		ListView listview = (ListView) parent;
		// get a chosen value
		String item = (String) listview.getItemAtPosition(position);
		
		// gen an intent
		Intent intent = new Intent(Intent.ACTION_VIEW);
		
		// set URI
		String uriStr = "";
		if (item.equals("Apple")) {
			uriStr += "intentsample://fruit/apple?selecteditem=" + item;
		} else if (item.equals("Banana")) {
			uriStr += "intentsample://fruit/banana?selecteditem=" + item;
		} else if (item.equals("Grape")) {
			uriStr += "intentsample://fruit/grape?selecteditem=" + item;
		} else {
			uriStr += "intentsample://fruitall?selecteditem=all";
		}//if (item.equals("Apple"))
		
		Uri uri = Uri.parse(uriStr);
		
		// set URI on the intent
		intent.setData(uri);
		
		// start the next activity
		startActivity(intent);
	}//public void onItemClick
}//public class ImplicitIntentSample1Activity
