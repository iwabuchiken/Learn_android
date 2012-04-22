package example.android.contentprovidermondai1;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
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

public class ContentProviderMondai1Activity extends Activity {
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.contentprovidermondai1);
        
        // �o�^�{�^���̃N���b�N���X�i�[�ݒ�
        Button insertBtn = (Button)findViewById(R.id.insert);
        insertBtn.setTag("insert");
        insertBtn.setOnClickListener(new ButtonClickListener());
        // �X�V�{�^���̃N���b�N���X�i�[�ݒ�
        Button updateBtn = (Button)findViewById(R.id.update);
        updateBtn.setTag("update");
        updateBtn.setOnClickListener(new ButtonClickListener());
        // �폜�{�^���̃N���b�N���X�i�[�ݒ�
        Button deleteBtn = (Button)findViewById(R.id.delete);
        deleteBtn.setTag("delete");
        deleteBtn.setOnClickListener(new ButtonClickListener());
        // �\���{�^���̃N���b�N���X�i�[�ݒ�
        Button selectBtn = (Button)findViewById(R.id.select); 
        selectBtn.setTag("select");
        selectBtn.setOnClickListener(new ButtonClickListener());
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
            
            // Uri�擾
            Uri uri = Uri.parse("content://example.android.contentprovidersample1");
            
            // �e�[�u�����C�A�E�g�I�u�W�F�N�g�擾
            TableLayout tablelayout = (TableLayout)findViewById(R.id.list);

            // �e�[�u�����C�A�E�g�̃N���A
            tablelayout.removeAllViews();
    		
    		// �o�^�{�^���������ꂽ�ꍇ
    		if(tag.equals("insert")){
                
                // �f�[�^�o�^
                try{
                    // �o�^�f�[�^�ݒ�
                    ContentValues val = new ContentValues();
                    val.put("productid", productid.getText().toString());
                    val.put("name", "***" + name.getText().toString() + "***");
                    val.put("price", price.getText().toString());
                    // �f�[�^�o�^
                    getContentResolver().insert(uri, val);
                	// ���b�Z�[�W�ݒ�
                    message += "�f�[�^��o�^���܂����I";

                }catch(Exception e){
                	message  = "�f�[�^�o�^�Ɏ��s���܂����I";
                	Log.e("ERROR",e.toString());
                }        
            // �X�V�{�^���������ꂽ�ꍇ
    		}else if(tag.equals("update")){
                
                // �f�[�^�X�V
                try{
                    // �X�V�f�[�^�ݒ�
                    ContentValues val = new ContentValues();
                    val.put("productid", productid.getText().toString());
                    val.put("name", "***" + name.getText().toString() + "***");
                    val.put("price", price.getText().toString());
                    // �X�V�����ݒ�
                    String condition = "productid = '" + productid.getText().toString() + "'";
                    // �f�[�^�X�V
                    getContentResolver().update(uri, val, condition, null);
                	// ���b�Z�[�W�ݒ�
                    message += "�f�[�^���X�V���܂����I";

                }catch(Exception e){
                	message  = "�f�[�^�X�V�Ɏ��s���܂����I";
                	Log.e("ERROR",e.toString());
                }        
            
            // �폜�{�^���������ꂽ�ꍇ
    		}else if(tag.equals("delete")){
                
                // �f�[�^�X�V
                try{
                    // �폜�����ݒ�
                    String condition = "productid = '" + productid.getText().toString() + "'";
                    // �f�[�^�X�V
                    getContentResolver().delete(uri, condition, null);
                	// ���b�Z�[�W�ݒ�
                    message += "�f�[�^���폜���܂����I";

                }catch(Exception e){
                	message  = "�f�[�^�폜�Ɏ��s���܂����I";
                	Log.e("ERROR",e.toString());
                } 
                
            // �\���{�^���������ꂽ�ꍇ
    		}else if(tag.equals("select")){
                
                // �f�[�^�擾
                try{                   
                    // �f�[�^�擾
                    Cursor cursor = managedQuery(uri, null, null, null, null);
                    
                    // �e�[�u�����C�A�E�g�̕\���͈͂�ݒ�
                    tablelayout.setStretchAllColumns(true);
                    
                    // �e�[�u���ꗗ�̃w�b�_���ݒ�
                    TableRow headrow = new TableRow(ContentProviderMondai1Activity.this);
                    TextView headtxt1 = new TextView(ContentProviderMondai1Activity.this);
                    headtxt1.setText("���iID");
                    headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt1.setWidth(60);
                    TextView headtxt2 = new TextView(ContentProviderMondai1Activity.this);
                    headtxt2.setText("���i��");
                    headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt2.setWidth(100);
                    TextView headtxt3 = new TextView(ContentProviderMondai1Activity.this);
                    headtxt3.setText("���i");
                    headtxt3.setGravity(Gravity.CENTER_HORIZONTAL);
                    headtxt3.setWidth(60);          
                    headrow.addView(headtxt1);
                    headrow.addView(headtxt2);
                    headrow.addView(headtxt3);
                    tablelayout.addView(headrow);
                    
                    // �擾�����f�[�^���e�[�u�����ו��ɐݒ�
                    while(cursor.moveToNext()){                	
                        
                    	TableRow row = new TableRow(ContentProviderMondai1Activity.this);
                        TextView productidtxt = new TextView(ContentProviderMondai1Activity.this);
                        productidtxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        productidtxt.setText(cursor.getString(1));
                        TextView nametxt = new TextView(ContentProviderMondai1Activity.this);
                        nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        nametxt.setText(cursor.getString(2));
                        TextView pricetxt = new TextView(ContentProviderMondai1Activity.this);
                        pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
                        pricetxt.setText(cursor.getString(3));
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
            // ���b�Z�[�W�\��  
            label.setText(message);
    	}
    }
}