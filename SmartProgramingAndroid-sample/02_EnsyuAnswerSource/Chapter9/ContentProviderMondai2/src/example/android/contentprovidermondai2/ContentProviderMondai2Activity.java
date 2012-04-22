package example.android.contentprovidermondai2;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ContentProviderMondai2Activity extends Activity {
	TableLayout tablelayout = null;
	
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.contentprovidermondai2);
        
        // �e�[�u�����C�A�E�g�擾
        tablelayout = (TableLayout)findViewById(R.id.tablelayout);
        
        try {
            // �f�[�^�擾
            Cursor cur = managedQuery(CallLog.Calls.CONTENT_URI,
                    				   null,
                    				   null,
                    				   null,
                    				   null);
            
            // �f�[�^���擾�ł����ꍇ
            if(cur.getCount() != 0){
            	// �擾�����f�[�^�\��
                while (cur.moveToNext()) {
                	String name = cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER));
                    setItems(name);
                }
            }else{
            	TextView message = new TextView(this);
            	message.setText("�f�[�^���擾�ł��܂���ł����B");
            	LinearLayout linearlayout = (LinearLayout)findViewById(R.id.linearlayout);
            	linearlayout.addView(message);
            }            
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

    }
    // setItems���\�b�h(�e�[�u���\������)
    private void setItems(String name) {
    	// ���O��ݒ�
        TableRow row = new TableRow(this);
        TextView displayName = new TextView(this);
        displayName.setText(name);
        row.addView(displayName);
        // �e�[�u�����C�A�E�g�ɐݒ�
        tablelayout.addView(row);
    }
}