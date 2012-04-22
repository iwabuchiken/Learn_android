package example.android.servicemondai2;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ServiceMondai2Service extends Service{
    // Timerオブジェクト
    private Timer timer = null;
    // 経過時間
    private int countTime = 0;
    // コールバックオブジェクト
    ActivityAIDL callback = null;
    // 一時停止フラグ
    boolean flg = false;
    
    // onCreateメソッド(サービス初期起動イベントハンドラ)
    @Override
    public void onCreate() {
        super.onCreate();
        
        // タイマーと経過時間初期化
        timer = new Timer();
        // タイマー設定(1ミリ秒ごとにrunメソッド呼び出し)
        timer.schedule(task, 0, 1);
    }

    // onBindメソッド(バインドイベントハンドラ)
    @Override
    public IBinder onBind(Intent intent) {
        if (ServiceAIDL.class.getName().equals(intent.getAction())) {
            return serviceCallbackIf;
        }
        return null;
    }

    // onUnbindメソッド(アンバインドイベントハンドラ)
    @Override
    public boolean onUnbind(Intent intent) {
        // タイマー設定解除
        timer.cancel();
        timer.purge();

        return super.onUnbind(intent);
    }
       
    //  サービスに連携するインタフェースオブジェクト
    private ServiceAIDL.Stub serviceCallbackIf = new ServiceAIDL.Stub() {

        public void startService(ActivityAIDL callback) {
            // コールバックオブジェクト取得
        	ServiceMondai2Service.this.callback=callback;
        	// 一時停止フラグを変更
        	flg = false;
        }
        
        public void stopService() {           
        	// メッセージを送信
    		ServiceMondai2Service.this.sendDisplayTime(false);       	
    		// 一時停止フラグを変更
        	flg = true;

        }
        
        public void resetService(ActivityAIDL callback) {          
        	// メッセージを送信
    		ServiceMondai2Service.this.sendDisplayTime(true);        	
            // コールバックオブジェクト削除
        	ServiceMondai2Service.this.callback=null;
        	// 一時停止フラグを変更
        	flg = false;
        	// 経過時間初期化
        	countTime = 0;
        }
    };

    // TimerTaskオブジェクト生成
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
        	if(!flg){       	
	        	// メッセージを送信
        		ServiceMondai2Service.this.sendDisplayTime(false);
        	}			
        	// コンマ1秒ごとにカウントアップ
        	countTime += 1;
        }
    };
    
    // メッセージ送信
    private void sendDisplayTime(boolean stopFlg){
    	try {
    		String time = "00:00.00";
    		if(!stopFlg){
	    		String countSec = String.valueOf(countTime%(1000*60)/1000);
	    		if(countTime%(1000*60)/1000<10){
	    			countSec = "0" + countTime%(1000*60)/1000;
	    		}
	    		String countMin = String.valueOf(countTime/(1000*60));
	    		if(countTime/(1000*60)<10){
	    			countMin = "0" + countTime/(1000*60);
	    		}
	    		String countMSec = String.valueOf(countTime%1000/10);
	    		if(countTime%1000/10<10){
	    			countMSec = "0" + countTime%1000/10;  
	    		}
	    		time = countMin+":"+countSec+"."+countMSec;
    		}
    		
    		if(this.callback!=null){
    			this.callback.displayTime(time);
    		}
		} catch (RemoteException e) {
			Log.e("error", "displayTimeError");
		}
    }
}
