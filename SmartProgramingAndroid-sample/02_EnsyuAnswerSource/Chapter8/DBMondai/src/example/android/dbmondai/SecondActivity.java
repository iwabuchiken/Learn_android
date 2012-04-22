package example.android.dbmondai;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {
    
    // ���̓f�[�^
    String name = "";
    String address = "";
    String month = "";
    String day = "";
    String gendar = "";
    String apple = "";
    String orange = "";
    String peach = "";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondlayout);
        
        // �C���e���g�̕t�����擾
        Bundle extra = getIntent().getExtras();
        
        // �t����񂩂���̓f�[�^�擾
        name = extra.getString("NAME");
        address = extra.getString("ADDRESS");
        month = extra.getString("MONTH");
        day = extra.getString("DAY");
        gendar = extra.getString("GENDAR");
        apple = extra.getString("APPLE");
        orange = extra.getString("ORANGE");
        peach = extra.getString("PEACH");
        
        // �o�͗p�e�L�X�g�I�u�W�F�N�g�擾
        TextView inputName = (TextView)findViewById(R.id.name);
        TextView inputAddress = (TextView)findViewById(R.id.address);
        TextView inputMonth = (TextView)findViewById(R.id.month);
        TextView inputDay = (TextView)findViewById(R.id.day);
        TextView inputGendar = (TextView)findViewById(R.id.gender);
        TextView inputApple = (TextView)findViewById(R.id.apple);
        TextView inputOrange = (TextView)findViewById(R.id.orange);
        TextView inputPeach = (TextView)findViewById(R.id.peach);
        
        // �o�͗p�e�L�X�g�I�u�W�F�N�ɓ��̓f�[�^�ݒ�
        inputName.setText(name);
        inputAddress.setText(address);
        inputMonth.setText(month);
        inputDay.setText(day);
        inputGendar.setText(gendar);
        if(apple != null)inputApple.setText(apple);
        if(orange != null)inputOrange.setText(orange);
        if(peach != null)inputPeach.setText(peach);
        
        // �m�F�{�^���̃N���b�N���X�i�[�ݒ�
        Button confirmbutton = (Button)findViewById(R.id.confirmbutton);
        confirmbutton.setTag("confirm");
        confirmbutton.setOnClickListener(new ButtonClickListener());
        // �߂�{�^���̃N���b�N���X�i�[�ݒ�
        Button backbutton = (Button)findViewById(R.id.backbutton);
        backbutton.setTag("back");
        backbutton.setOnClickListener(new ButtonClickListener());
    }
    
    // �{�^���N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        @Override
        public void onClick(View v) { 
        	// �^�O�̎擾
    		String tag = (String)v.getTag();
    		
    		// �m�F�{�^���������ꂽ�ꍇ
    		if(tag.equals("confirm")){
    			// DB�A�N�Z�X
    			DBMondaiHelper helper = new DBMondaiHelper(SecondActivity.this);
    			
    			// DB�I�u�W�F�N�g�擾
    			SQLiteDatabase db = helper.getWritableDatabase();
    			
    			// �f�[�^�o�^
    			try{
    				// �e�[�u���쐬
                    String sql 
                         = "create table application(_id integer primary key autoincrement," +
                           "name text not null," +
                           "address text not null," +
                           "gendar text not null," +
                           "apple integer default 0," +
                           "orange integer default 0," +
                           "peach integer default 0)";
                    db.execSQL(sql);
                    
                    // �g�����U�N�V��������J�n
                    db.beginTransaction();
                    
                    // �f�[�^�o�^
                    ContentValues val = new ContentValues();
                    val.put("name", name);
                    val.put("address",address);
                    val.put("gendar", gendar);
                    val.put("apple", apple);
                    val.put("orange", orange);
                    val.put("peach", peach);
                    db.insert("application", null, val);
                    
                    // �R�~�b�g
                    db.setTransactionSuccessful();
                    
                    // �g�����U�N�V��������I��
                    db.endTransaction();
                    
                    // DB�N���[�Y
                    db.close();
                    
                }catch(Exception e){
                    Log.e("DB_ERROR", "�f�[�^�x�[�X�o�^�Ɏ��s���܂���");
                }
                
                
            	// �C���e���g�̐���(�Ăяo���N���X�̎w��)
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                
                // ���̃A�N�e�B�r�e�B�̋N��
                startActivity(intent);
    			
    		// �߂�{�^���������ꂽ�ꍇ
    		}else if(tag.endsWith("back")){   		
	            // ��ʂ��N���[�Y
	            finish();
	    	}
        }
    }
}
