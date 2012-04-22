package example.android.tabmondai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class TabMondaiActivity extends TabActivity implements OnTabChangeListener {
	// �t�@�C����
    private static final String FILE_NAME = "TabMondai";	
	
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        
        // TabHost�擾
        TabHost tabHost = getTabHost();
                
        // TabHost�ƃ��C�A�E�g�ƃr���[�̐ݒ�t�@�C�����֘A�t��
        LayoutInflater.from(this).inflate(R.layout.tabmondai,
                tabHost.getTabContentView(), true);
        
        // �^�u1�̐ݒ�
        TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setIndicator("�ݒ�",getResources().getDrawable(android.R.drawable.ic_menu_add));
        tab1.setContent(R.id.tablelayout1);
        // �^�u2�̐ݒ�
        TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setIndicator("�\��",getResources().getDrawable(android.R.drawable.ic_menu_info_details));
        tab2.setContent(R.id.tablelayout2);
        
        // �e�^�u��TabHost�ɐݒ�
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        
        // �����\���̃^�u�ݒ�
        tabHost.setCurrentTab(0);
        
        // �^�u���؂�ւ�����Ƃ��̃C�x���g�n���h����ݒ�   
        tabHost.setOnTabChangedListener(this);
        
        // �m�F�{�^���̃N���b�N���X�i�[�ݒ�
        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new ButtonClickListener());
    }
    
    // onTabChanged���\�b�h(�^�u���؂�ւ�������̃C�x���g�n���h��)
    public void onTabChanged(String tabId) {
    	// �؂�ւ�����^�u���\���̃^�u�̏ꍇ
    	if(tabId.equals("tab2")){
    		// �e�[�u�����C�A�E�g�I�u�W�F�N�g�擾
            TableLayout tablelayout = (TableLayout)findViewById(R.id.tablelayout2);

            // �e�[�u�����C�A�E�g�̃N���A
            tablelayout.removeAllViews();
            
            // �e�[�u�����C�A�E�g�̕\���͈͂�ݒ�
            tablelayout.setStretchAllColumns(true);
            
            // �e�[�u���ꗗ�̃w�b�_���ݒ�
            TextView headtxt1 = new TextView(TabMondaiActivity.this);
            headtxt1.setText("���i��");
            headtxt1.setGravity(Gravity.CENTER_HORIZONTAL);
            headtxt1.setWidth(100);
            TextView headtxt2 = new TextView(TabMondaiActivity.this);
            headtxt2.setText("���i");
            headtxt2.setGravity(Gravity.CENTER_HORIZONTAL);
            headtxt2.setWidth(60);
            TableRow headrow = new TableRow(TabMondaiActivity.this);
            headrow.addView(headtxt1);
            headrow.addView(headtxt2);
            tablelayout.addView(headrow);
    		
    		// �t�@�C������f�[�^�擾
            try{
                FileInputStream stream = openFileInput(FILE_NAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                
                String line = "";
                while((line = in.readLine())!=null){
                    String lineSplit[] = line.split(",");
                    
                    TextView nametxt = new TextView(TabMondaiActivity.this);
                    nametxt.setGravity(Gravity.CENTER_HORIZONTAL);
                    nametxt.setText(lineSplit[0]);
                    TextView pricetxt = new TextView(TabMondaiActivity.this);
                    pricetxt.setGravity(Gravity.CENTER_HORIZONTAL);
                    pricetxt.setText(lineSplit[1]);
                    
                    TableRow row = new TableRow(TabMondaiActivity.this);
                    row.addView(nametxt);
                    row.addView(pricetxt);
                    tablelayout.addView(row);
                }
                in.close();             
            }catch(Exception e){
                Log.e("ERROR", "file access error");
            }      
    	}
    }
    
    // �{�^���N���b�N���X�i�[��`
    class ButtonClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        @Override
        public void onClick(View v) {
        	// ���̓f�[�^�擾
        	String name = ((EditText)findViewById(R.id.name)).getText().toString();
        	String price = ((EditText)findViewById(R.id.price)).getText().toString();

			// �t�@�C���ɕۑ�
			try{
                FileOutputStream stream = openFileOutput(FILE_NAME, MODE_APPEND);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(stream));
                
                out.write(name + "," + price);
                out.newLine();
                out.close();
                
            }catch(Exception e){
                Log.e("FILE_ERROR", "�t�@�C���������݂Ɏ��s���܂���");
            }
        }
    }
}