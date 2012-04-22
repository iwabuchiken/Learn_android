package example.android.dialogsample;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class DialogSampleActivity extends Activity {
    TextView label = null;
    
    // onCreateメソッド(画面初期表示イベントハンドラ)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // スーパークラスのonCreateメソッド呼び出し
        super.onCreate(savedInstanceState);
        // レイアウト設定ファイルの指定
        setContentView(R.layout.dialogsample);
        
        // 表示ラベル取得
        label = (TextView)findViewById(R.id.label_dialogtext);
        
        // 通常ダイアログ表示ボタンのクリックリスナー設定
        Button dialogBtn = (Button)findViewById(R.id.dialogButton);   
        dialogBtn.setTag("dialog");
        dialogBtn.setOnClickListener(new ButtonClickListener()); 
        
        // テキストダイアログ表示ボタンのクリックリスナー設定
        Button txtDialogBtn = (Button)findViewById(R.id.textDialogButton);
        txtDialogBtn.setTag("textDialog");
        txtDialogBtn.setOnClickListener(new ButtonClickListener());   
        
        // 単一選択ダイアログ表示ボタンのクリックリスナー設定
        Button SingleSelectDialogBtn = (Button)findViewById(R.id.singleSelectDialogButton);
        SingleSelectDialogBtn.setTag("singleSelectDialog");
        SingleSelectDialogBtn.setOnClickListener(new ButtonClickListener()); 
       
        // 日付選択ダイアログ表示ボタンのクリックリスナー設定
        Button DatePickerDialogBtn = (Button)findViewById(R.id.datePickerDialogButton);
        DatePickerDialogBtn.setTag("datePickerDialogBtn");
        DatePickerDialogBtn.setOnClickListener(new ButtonClickListener());
        
        // 時間選択ダイアログ表示ボタンのクリックリスナー設定
        Button TimePickerDialogBtn = (Button)findViewById(R.id.timePickerDialogButton);
        TimePickerDialogBtn.setTag("timePickerDialogBtn");
        TimePickerDialogBtn.setOnClickListener(new ButtonClickListener());
        
        // プログレスバーダイアログ表示ボタンのクリックリスナー設定
        Button ProgressDialogBtn = (Button)findViewById(R.id.progressDialogButton);
        ProgressDialogBtn.setTag("progressDialogBtn");
        ProgressDialogBtn.setOnClickListener(new ButtonClickListener());
        
    }
    
    // クリックリスナー定義
    class ButtonClickListener implements OnClickListener{
        // onClickメソッド(ボタンクリック時イベントハンドラ)
        public void onClick(View v) {
            String tag = (String)v.getTag();
            if (tag.equals("dialog")) {
                showDialog();
            } else if (tag.equals("textDialog")) {
                showTextDialog();
            } else if (tag.equals("singleSelectDialog")) {
                showSingleSelectDialog();
            } else if (tag.equals("datePickerDialogBtn")) {
                showDatePickerDialog();
            } else if (tag.equals("timePickerDialogBtn")) {
                showTimePickerDialog();
            } else if (tag.equals("progressDialogBtn")) {
                showProgressDialog();
            } 
        }
    }
    
    // 通常ダイアログの表示
    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DialogSampleActivity.this);
        dialog.setTitle("通常ダイアログ");
        dialog.setMessage("選択してください。");
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                label.setText("通常ダイアログ：OKが選択されました。");
            }
        });
        dialog.setNegativeButton("NG", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                label.setText("通常ダイアログ：NGが選択されました。");
            }
        });
        dialog.show();
    }
    
    // テキストダイアログの表示
    public void showTextDialog() {  
        final EditText editText=new EditText(DialogSampleActivity.this);  
        AlertDialog.Builder dialog = new AlertDialog.Builder(DialogSampleActivity.this);
        dialog.setTitle("テキストダイアログ"); 
        dialog.setMessage("テキストを入力してください。");
        dialog.setView(editText);  
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                label.setText("テキストダイアログ：" + editText.getText().toString() + "が入力されました。");
            }
        });  
        dialog.show();  
    }  
    
    // 単一選択ダイアログの表示
    final String[] items = new String[]{"もも","うめ","さくら"};
    int which = 0;
    public void showSingleSelectDialog() {      
        AlertDialog.Builder dialog = new AlertDialog.Builder(DialogSampleActivity.this);  
        dialog.setTitle("単一選択ダイアログ");  
        dialog.setSingleChoiceItems(items
               ,0
               ,new DialogInterface.OnClickListener() {        
                    public void onClick(DialogInterface dialog, int whichButton) {
                        which = whichButton;
                    }
                }
        );
        
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                String selected = items[which];
                label.setText("単一選択ダイアログ：" + selected + "が入力されました。");
            }
        });  
        dialog.show(); 
    }
    
    // 日付選択ダイアログの表示
    public void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        
        DatePickerDialog dialog = new DatePickerDialog(DialogSampleActivity.this
                ,new DatePickerDialog.OnDateSetListener() { 
                    @Override
                    public void onDateSet(DatePicker picker, int year, int month, int day) {
                        label.setText("日付選択ダイアログ：" + year + "年" + (month+1) + "月" + day + "日");
                    }
                }
                ,cal.get(Calendar.YEAR)
                ,cal.get(Calendar.MONTH)
                ,cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show(); 
    }
    
    // 時間選択ダイアログの表示
    public void showTimePickerDialog() {
        TimePickerDialog dialog = new TimePickerDialog(DialogSampleActivity.this
                ,new TimePickerDialog.OnTimeSetListener() { 
                    @Override
                    public void onTimeSet(TimePicker picker, int hour, int min) {
                        label.setText("時間選択ダイアログ：" + hour + "時" + min + "分");
                    }
                }
                ,0
                ,0
                ,true
        );
        dialog.show();
    }
    
    // プログレスバーダイアログの表示
    ProgressDialog  dialog;
    public void showProgressDialog() {
        dialog = new ProgressDialog (DialogSampleActivity.this);
        dialog.setTitle("プログレスバーダイアログ");
        dialog.setMessage("しばらくお待ちください・・");
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMax(100);  
        dialog.setCancelable(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {   
              public void onCancel(DialogInterface dialog) {
              }
        });
 
        dialog.show(); 
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    for(int i=0; i< dialog.getMax(); i++){
                        dialog.setProgress(i);
                        Thread.sleep(100);
                    }
                }catch(Exception e){
                    
                }
                dialog.dismiss();
            }
        }).start();     
    }
}
