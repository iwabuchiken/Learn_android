package example.android.menudialogmondai;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

public class MenuDialogMondaiActivity extends Activity {
	// �v���t�@�����X�t�@�C����
    private static final String FILE_NAME = "MenuDialogFile";   	
	
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.menudialogmondai);        
    }
    
    // onCreateOptionsMenu���\�b�h(�I�v�V�������j���[����)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        // ���j���[�A�C�e��1�̒ǉ�
        MenuItem item1=menu.add(0,0,0,"�ݒ�");
        item1.setIcon(android.R.drawable.ic_menu_add);
        
        // ���j���[�A�C�e��2�̒ǉ�
        MenuItem item2=menu.add(0,1,0,"�\��");
        item2.setIcon(android.R.drawable.ic_menu_info_details);

        return true;
    }    

    // onOptionsItemSelected���\�b�h(���j���[�A�C�e���I������)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
            	showDatePickerDialog();
                return true;
            case 1:
            	// �v���t�@�����X�I�u�W�F�N�g�擾
                SharedPreferences preference = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
     
                // �ۑ��f�[�^�擾
                String date = preference.getString("DATE", "����܂���");

                showDialog(date);
                return true;          
        }
        return true;
    }    
    
    // ���t�I���_�C�A���O�̕\��
    public void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        
        DatePickerDialog dialog = new DatePickerDialog(MenuDialogMondaiActivity.this
                ,new DatePickerDialog.OnDateSetListener() { 
                    @Override
                    public void onDateSet(DatePicker picker, int year, int month, int day) {
                    	// �v���t�@�����X�I�u�W�F�N�g�擾
                        SharedPreferences preference = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
             
                        // �v���t�@�����X�̕ҏW�p�I�u�W�F�N�g�擾
                        SharedPreferences.Editor editor = preference.edit();   
                        
                        // �擾������������v���t�@�����X�t�@�C���ɕۑ�
                        editor.putString("DATE",year + "�N" + (month + 1) + "��" + day + "��");  
                        editor.commit();
                    }
                }
                ,cal.get(Calendar.YEAR)
                ,cal.get(Calendar.MONTH)
                ,cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show(); 
    }
    
    // showDialog���\�b�h(�_�C�A���O�\��)
    private void showDialog(String text) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(MenuDialogMondaiActivity.this);
        dialog.setTitle("�ۑ�����");
        dialog.setMessage(text);
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
            	MenuDialogMondaiActivity.this.setResult(Activity.RESULT_OK);
            }
        });
        dialog.create();
        dialog.show();
    }
}