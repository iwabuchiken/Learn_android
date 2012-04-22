package example.android.startservicesample;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class StartServiceSampleService extends Service {
	// timer object
	private Timer timer	= null;
	// past time
	private int countTime;
	// end time
	private int stopTime;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}//public IBinder onBind(Intent arg0)
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// toast
		Toast.makeText(this, 
							"�T�[�r�X���N�����܂�", 
							Toast.LENGTH_SHORT).show();
		
		// generate a timer and initialize the time elapsed
		timer = new Timer();
		countTime = 0;
	}//onCreate()
	
	@Override
	public  void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		// toast
		Toast.makeText(this, "�T�[�r�X���J�n���܂�", Toast.LENGTH_SHORT).show();
		
		// set the timer
		timer.schedule(task, 10000, 10000);
		
		// get the end time
		Bundle bundle = intent.getExtras();
		stopTime = Integer.parseInt(bundle.getString("STOPTIME"));
	}//onStart()
	
	@Override
	public  void onDestroy() {
		super.onDestroy();
		
		// toast
		Toast.makeText(this, "�T�[�r�X���I��", Toast.LENGTH_SHORT).show();
		
		// let go of the timer
		timer.cancel();
		timer.purge();
	}//onDestroy()
	
	// gen a handler
	private Handler handler = new Handler(){
		// show message
		public  void handleMessage(Message msg) {
			// toast
			Toast.makeText(StartServiceSampleService.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
			
		}//handleMessage()
	};//private Handler handler = new Handler()
	
	// gen a TimerTask object
	private TimerTask task = new TimerTask(){

		@Override
		public void run() {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			// count up for every 10 sec
			countTime += 10;
			
			if (countTime / 60 == stopTime) {
				// stop the service
				stopSelf();
			} else {//if (countTime / 60 == stopTime)
				// send a message to the handler
				handler.sendMessage(
								Message.obtain(
										handler, 
										0, 
										countTime / 60 + 
											"��" + 
											countTime % 60 + "�b�o�߂��܂���"));
			}//if (countTime / 60 == stopTime)
			
			
		}//public void run()		
	};

}//public class StartServiceSampleService extends Service
