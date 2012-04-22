package example.android.implicitintentsample1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FirstActivity extends Activity {
    // onCreate���\�b�h(��ʏ����\���C�x���g�n���h��)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // �X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
        super.onCreate(savedInstanceState);
        // ���C�A�E�g�ݒ�t�@�C���̎w��
        setContentView(R.layout.firstlayout);

        // ListView�I�u�W�F�N�g�擾
        ListView listview = (ListView) findViewById(R.id.fruitlist);
        // ListView�I�u�W�F�N�g�ɃN���b�N���X�i�[���֘A�t��
        listview.setOnItemClickListener(new ListItemClickListener());
    }

    // �A�C�e���N���b�N���X�i�[��`
    class ListItemClickListener implements OnItemClickListener {
        // onItemClick���\�b�h(�l�I�����C�x���g�n���h��)
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // ListView�I�u�W�F�N�g�擾
            ListView listview = (ListView) parent;
            // �I�����ꂽ�l�擾
            String item = (String) listview.getItemAtPosition(position);
            
            // �C���e���g�̐���(�f�[�^��\������A�N�V�����w��)
            Intent intent = new Intent(Intent.ACTION_VIEW);
            
            // URI�ݒ�
            String uriStr = "";
            if(item.equals("Apple")){
            	uriStr += "intentsample://fruit/apple?selecteditem="+item;
            }else if(item.equals("Banana")){
            	uriStr += "intentsample://fruit/banana?selecteditem="+item;
            }else if(item.equals("Grape")){
            	uriStr += "intentsample://fruit/grape?selecteditem="+item;
            }else{
            	uriStr += "intentsample://fruitall?selecteditem=all";
            }
            Uri uri = Uri.parse(uriStr);
            
            // URI���C���e���g�ɐݒ�
            intent.setData(uri);
            
            // ���̃A�N�e�B�r�e�B�̋N��
            startActivity(intent);
        }
    }
}