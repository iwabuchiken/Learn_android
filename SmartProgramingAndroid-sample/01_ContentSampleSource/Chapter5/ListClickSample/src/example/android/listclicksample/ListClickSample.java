package example.android.listclicksample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListClickSample extends Activity {
    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.listclicksample);
        
        // ListView�I�u�W�F�N�g�擾
        ListView listview = (ListView) findViewById(R.id.listView1);
        // ListView�I�u�W�F�N�g�ɃN���b�N���X�i�[���֘A�t��
        listview.setOnItemClickListener(new ListItemClickListener());
    }
    
    // �A�C�e���N���b�N���X�i�[��`
    class ListItemClickListener implements OnItemClickListener {
        // onItemClick���\�b�h(���X�g�̒l�N���b�N���C�x���g)
        public void onItemClick(AdapterView<?> parent, 
        							View view, 
        							int position, 
        							long id) {
            // �N���b�N����ListView�I�u�W�F�N�g�擾
            ListView listview = (ListView) parent;
            // �I�����ꂽ�l�擾
            String item = (String) listview.getItemAtPosition(position);
            
            // �I�����ꂽ�l���g�[�X�g�@�\�ŉ�ʕ\��
            Toast.makeText(ListClickSample.this, 
            				item,
            				Toast.LENGTH_SHORT).show();
        }
    }
}
