package example.android.optionmenusample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class OptionMenuSampleActivity extends Activity {
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.optionmenusample);        
    }
    
    // onCreateOptionsMenu���\�b�h(�I�v�V�������j���[����)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        // ���j���[�A�C�e��1�̒ǉ�
        @SuppressWarnings("unused")
		MenuItem item1=menu.add(0,0,0,"item1");
        
        // ���j���[�A�C�e��2�̒ǉ�
        MenuItem item2=menu.add(0,1,0,"item2");
        item2.setIcon(android.R.drawable.ic_menu_search);
        
        // ���j���[�A�C�e��3�̒ǉ�
        MenuItem item3=menu.add(0,2,0,"item3");
        item3.setIcon(android.R.drawable.ic_menu_save);
        
        //���j���[�A�C�e��4�̒ǉ� ���ǉ�
        MenuItem item4=menu.add(0,3,0,"item4");
        item4.setIcon(android.R.drawable.ic_menu_call);
        
        //���j���[�A�C�e��5�̒ǉ� ���ǉ�
        MenuItem item5=menu.add(0,4,0,"item5");
        item5.setIcon(android.R.drawable.ic_menu_camera);
        
        //���j���[�A�C�e��6�̒ǉ� ���ǉ�
        SubMenu item6=menu.addSubMenu(0,5,0,"���̑�");
        item6.setIcon(android.R.drawable.ic_menu_more);
        item6.add(0,10,0,"subitem1");
        item6.add(0,20,0,"subitem2");

        return true;
    }    

    // onOptionsItemSelected���\�b�h(���j���[�A�C�e���I������)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showDialog("���j���[�A�C�e��1��I�����܂����B");
                return true;
            case 1:
                showDialog("���j���[�A�C�e��2��I�����܂����B");
                return true;
            case 2:
                showDialog("���j���[�A�C�e��3��I�����܂����B");
                return true;
            case 3:
                showDialog("���j���[�A�C�e��4��I�����܂����B");
                return true;
            case 4:
                showDialog("���j���[�A�C�e��5��I�����܂����B");
                return true;
            case 10:
                showDialog("�T�u���j���[�A�C�e��1��I�����܂����B");
                return true;
            case 20:
                showDialog("�T�u���j���[�A�C�e��2��I�����܂����B");
                return true;            
        }
        return true;
    }    
    
    // showDialog���\�b�h(�_�C�A���O�\��)
    private void showDialog(String text) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(OptionMenuSampleActivity.this);
        dialog.setTitle("���j���[�A�C�e���I������");
        dialog.setMessage(text);
        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
            	OptionMenuSampleActivity.this.setResult(Activity.RESULT_OK);
            }
        });
        dialog.create();
        dialog.show();
    }
}