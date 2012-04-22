package example.android.contentprovidersample1;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ContentProviderSample1Activity extends Activity {
	CreateProductHelper helper = null;
    SQLiteDatabase db = null;	
	
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.contentprovidersample1);
        
        // �o�^�{�^���̃N���b�N���X�i�[�ݒ�
        Button insertBtn = (Button)findViewById(R.id.insert);
        insertBtn.setTag("insert");
        insertBtn.setOnClickListener(new ButtonClickListener());
        // �X�V�{�^���̃N���b�N���X�i�[�ݒ�
        Button updatetBtn = (Button)findViewById(R.id.update);
        updatetBtn.setTag("update");
        updatetBtn.setOnClickListener(new ButtonClickListener());
        // �폜�{�^���̃N���b�N���X�i�[�ݒ�
        Button delBtn = (Button)findViewById(R.id.delete);
        delBtn.setTag("delete");
        delBtn.setOnClickListener(new ButtonClickListener());
        // �\���{�^���̃N���b�N���X�i�[�ݒ�
        Button selectBtn = (Button)findViewById(R.id.select); 
        selectBtn.setTag("select");
        selectBtn.setOnClickListener(new ButtonClickListener());
        
        // DB�쐬
        helper = new CreateProductHelper(ContentProviderSample1Activity.this);
    }
    
    // �N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
    	// onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
    	public void onClick(View v){
    		// �^�O�̎擾
    		String tag = (String)v.getTag();
    		
    		// ���b�Z�[�W�\���p
            String message  = "";
            TextView label = (TextView)findViewById(R.id.message);
            
            // ���͏��擾
			EditText productid = (EditText)findViewById(R.id.id);
            EditText name = (EditText)findViewById(R.id.name);
            EditText price = (EditText)findViewById(R.id.price);
            
            // �e�[�u�����C�A�E�g�I�u�W�F�N�g�擾
            TableLayout tablelayout = (TableLayout)findViewById(R.id.list);

            // �e�[�u�����C�A�E�g�̃N���A
            tablelayout.removeAllViews();
            
            // �Y��DB�I�u�W�F�N�g�擾
            db = helper.getWritableDatabase();
    		
    		// �o�^�{�^���������ꂽ�ꍇ
    		if(tag.equals("insert")){
                
                // �e�[�u���쐬
                try{
                    
                	// SQL����`
                    String sql 
                         = "create table product(_id integer primary key autoincrement," +
                           "productid text not null," +
                           "name text not null," +
                           "price integer default 0)";
                    // SQL���s
                    db.execSQL(sql);
                    
                    // ���b�Z�[�W�ݒ�
                    message = "�e�[�u�����쐬���܂����I\n";
                    
                }catch(Exception e){
                	message  = "�e�[�u���͍쐬����Ă��܂��I\n";
                	Log.e("ERROR",e.toString());
                }
                
                // �f�[�^�o�^
                try{
                    
                    // �g�����U�N�V��������J�n
                    db.beginTransaction();
                    
                    // �o�^�f�[�^�ݒ�
                    ContentValues val = new ContentValues();
                    val.put("productid", productid.getText().toString());
                    val.put("name", name.getText().toString());
                    val.put("price", price.getText().toString());
                    // �f�[�^�o�^
                    db.insert("product", null, val);
                    
                    // �R�~�b�g
                    db.setTransactionSuccessful();
                    
                    // �g�����U�N�V��������I��
                    db.endTransaction();

                	// ���b�Z�[�W�ݒ�
                    message += "�f�[�^��o�^���܂����I";

                }catch(Exception e){
                	message  = "�f�[�^�o�^�Ɏ��s���܂����I";
                	Log.e("ERROR",e.toString());
                }
                
            // �X�V�{�^���������ꂽ�ꍇ
    		}else if(tag.endsWith("update")){    			
                
    			// �t�@�C���̃f�[�^�폜
    			try{
    				// �X�V����
    				String condition = null;
    				if(productid != null && !productid.equals("")){
    					condition = "productid = '" + productid.getText().toString() + "'";
    				}
    				
                    // �g�����U�N�V��������J�n
                    db.beginTransaction();
                    
                    // �X�V�f�[�^�ݒ�
                    ContentValues val = new ContentValues();
                    val.put("name", name.getText().toString());
                    val.put("price", price.getText().toString());
                    // �f�[�^�X�V
                    db.update("product", val, condition, null);

                    // �R�~�b�g
                    db.setTransactionSuccessful();
                    
                    // �g�����U�N�V��������I��
                    db.endTransaction();
                    
                    // ���b�Z�[�W�ݒ�
                    message = "�f�[�^���X�V���܂����I";
                }catch(Exception e){
                    // ���b�Z�[�W�ݒ�
                    message = "�f�[�^�X�V�Ɏ��s���܂����I";
                    Log.e("ERROR",e.toString());
                }  
    		
        	// �폜�{�^���������ꂽ�ꍇ
    		}else if(tag.endsWith("delete")){   			
                
    			// �t�@�C���̃f�[�^�폜
    			try{
    				// �폜����
    				String condition = null;
    				if(productid != null && !productid.equals("")){
    					condition = "productid = '" + productid.getText().toString() + "'";
    				}
    				
                    // �g�����U�N�V��������J�n
                    db.beginTransaction();
                    
                    // �f�[�^�폜
                    db.delete("product", condition, null);

                    // �R�~�b�g
                    db.setTransactionSuccessful();
                    
                    // �g�����U�N�V��������I��
                    db.endTransaction();
                    
                    // ���b�Z�[�W�ݒ�
                    message = "�f�[�^���폜���܂����I";
                }catch(Exception e){
                    // ���b�Z�[�W�ݒ�
                    message = "�f�[�^�폜�Ɏ��s���܂����I";
                    Log.e("ERROR",e.toString());
                }           
            
            // �\���{�^���������ꂽ�ꍇ
    		}else if(tag.equals("select")){
                
                // �f�[�^�擾
                try{
                	// �Y��DB�I�u�W�F�N�g�擾
                    db = helper.getReadableDatabase();
                
                    // �񖼒�`
                    String columns[] = {"productid","name","price"};
                    
                    // �f�[�^�擾
                    Cursor cursor = db.query("product", columns, null, null, null, null, "productid");
                    
                    // �e�[�u�����C�A�E�g�̕\���͈͂�ݒ�
                    tablelayout.setStretchAllColumns(true);
                    
                    // �e�[�u���ꗗ�̃w�b�_���ݒ�
                    TableRow headrow = new TableRow(ContentProviderSample1Activity.this);
                    TextView headtxt1 = new TextView(ContentProviderSample1Activity.this);
                    headtxt1.setText("���iID");
                    headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt1.setWidth(60);
                    TextView headtxt2 = new TextView(ContentProviderSample1Activity.this);
                    headtxt2.setText("���i��");
                    headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt2.setWidth(100);
                    TextView headtxt3 = new TextView(ContentProviderSample1Activity.this);
                    headtxt3.setText("���i");
                    headtxt3.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt3.setWidth(60);          
                    headrow.addView(headtxt1);
                    headrow.addView(headtxt2);
                    headrow.addView(headtxt3);
                    tablelayout.addView(headrow);
                    
                    // �擾�����f�[�^���e�[�u�����ו��ɐݒ�
                    while(cursor.moveToNext()){                	
                        
                        TableRow row = new TableRow(ContentProviderSample1Activity.this);
                        TextView productidtxt = new TextView(ContentProviderSample1Activity.this);
                        productidtxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        productidtxt.setText(cursor.getString(0));
                        TextView nametxt = new TextView(ContentProviderSample1Activity.this);
                        nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        nametxt.setText(cursor.getString(1));
                        TextView pricetxt = new TextView(ContentProviderSample1Activity.this);
                        pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        pricetxt.setText(cursor.getString(2));
                        row.addView(productidtxt);
                        row.addView(nametxt);
                        row.addView(pricetxt);
                        tablelayout.addView(row);
                        
                        // ���b�Z�[�W�ݒ�
                        message = "�f�[�^���擾���܂����I";
                    }
             
                }catch(Exception e){
                	// ���b�Z�[�W�ݒ�
                    message = "�f�[�^�擾�Ɏ��s���܂����I";
                    Log.e("ERROR",e.toString());
                }      
    		}
    		
            // DB�I�u�W�F�N�g�N���[�Y
            db.close(); 
            
            // ���b�Z�[�W�\��  
            label.setText(message);
    	}
    }
}