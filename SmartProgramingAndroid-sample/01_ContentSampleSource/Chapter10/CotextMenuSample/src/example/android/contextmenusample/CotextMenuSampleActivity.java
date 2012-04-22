package example.android.contextmenusample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CotextMenuSampleActivity extends Activity {
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.contextmenusample);
        
        // ���X�g�r���[�ɕ\�����郊�X�g�쐬
        List<String> list = new ArrayList<String>();
        list.add("�I��1");
        list.add("�I��2");
        list.add("�I��3");


        // ���X�g�A�_�v�^����
        ListAdapter adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1
                ,list);

        // ���X�g�r���[�ɃA�_�v�^��ݒ�
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        // �R���e�L�X�g���j���[�Ƀ��X�g�r���[��o�^
        registerForContextMenu(listView);
    }
    
    // onCreateContextMenu���\�b�h(�R���e�L�X�g���j���[����)
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo menuInfo) {
        // �R���e�L�X�g���j���[����
        menu.setHeaderTitle("�R���e�L�X�g���j���[");

        menu.add("���j���[1");
        menu.add("���j���[2");
        menu.add("���j���[3");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    // onContextItemSelected���\�b�h(�R���e�L�X�g���j���[�I��)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // �I�����ꂽ���j���[�A�C�e�����e�L�X�g�r���[�ɕ\��
        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText("�R���e�L�X�g���j���[�őI��: " + item.getTitle());

        return super.onContextItemSelected(item);

    }
}