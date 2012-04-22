package example.android.servicemondai2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ServiceMondai2Activity extends Activity {
    // サービスに連携するインターフェース
    private ServiceAIDL bindserviceIf = null;
    // 接続オブジェクト
    private ServiceConnection conn = null;
    
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.servicemondai2);
        
        // 開始ボタンオブジェクトにクリックリスナー設定
        Button startButton=(Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartButtonClickListener());
        
        // 終了ボタンオブジェクトにクリックリスナー設定
        Button stopButton=(Button)findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new StopButtonClickListener());
        
        // リセットボタンオブジェクトにクリックリスナー設定
        Button resetButton=(Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new ResetButtonClickListener());
    }
    
    // Startボタンクリックリスナー定義
    class StartButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベント)
        public void onClick(View v) {          	
            // インテント生成
            Intent intent = new Intent(ServiceAIDL.class.getName());

            // Service接続・切断用オブジェクト生成
            conn = new SampleServiceConnection();

            // サービスにバインド
            bindService(intent, conn, BIND_AUTO_CREATE);
        }
    }
    
    // Stopボタンクリックリスナー定義
    class StopButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベント)
        public void onClick(View v) {    
            try {
            	// サービス処理を一時停止
            	bindserviceIf.stopService();
            }catch(RemoteException e) {
                Log.e("ERROR", e.getMessage());
            }
        }
    }
    
    // Resetボタンクリックリスナー定義
    class ResetButtonClickListener implements OnClickListener {
        // onClickメソッド(ボタンクリック時イベント)
        public void onClick(View v) {    
            try {
            	// アクティビティに連携するインターフェースを解除
            	bindserviceIf.resetService(bindactivityIf);
            }catch(RemoteException e) {
                Log.e("ERROR", e.getMessage());
            }
            
            // サービスをアンバインド
            unbindService(conn);
            // インテント生成
            Intent intent = new Intent(ServiceAIDL.class.getName());
            // サービスを停止
            stopService(intent);
        }
    }
    
    // サービス接続・接続クラス定義
    class SampleServiceConnection implements ServiceConnection {
    	// onServiceConnectedメソッド(接続イベントハンドラ)
        public void onServiceConnected(ComponentName compName, IBinder binder) {
            // サービスと連携するインターフェース取得
        	bindserviceIf = ServiceAIDL.Stub.asInterface(binder);
            try {
                // アクティビティに連携するインターフェースを登録
            	bindserviceIf.startService(bindactivityIf); 
            }catch (RemoteException e) {
                Log.e("ERROR", e.getMessage());
            }
        }
        // onServiceDisconnectedメソッド(切断イベントハンドラ)
        public void onServiceDisconnected(ComponentName arg0) {
        	bindserviceIf = null;
        }
    }
    
    // アクティビティに連携するインターフェースオブジェクト
    private ActivityAIDL bindactivityIf = new ActivityAIDL.Stub() {
        @Override
        public void displayTime(String time) throws RemoteException {
            // handlerにメッセージを送信
            handler.sendMessage(Message.obtain(handler, 0, time)); 
        }
    };     
    
    // ハンドラー生成
    private Handler handler = new Handler(){ 
    	// メッセージ表示
        public void handleMessage(Message msg) {
    		// TextViewに時間を表示
    		TextView timetext = (TextView)findViewById(R.id.timetext);
    		timetext.setText((String)msg.obj);        	
        }
    };
}