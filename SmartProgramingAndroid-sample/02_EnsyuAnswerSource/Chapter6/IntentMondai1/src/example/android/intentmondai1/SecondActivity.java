package example.android.intentmondai1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondlayout);
        
        // �C���e���g�̕t�����擾
        Bundle extra = getIntent().getExtras();
        
        // �t����񂩂���̓f�[�^�擾
        String name = extra.getString("NAME");
        String address = extra.getString("ADDRESS");
        String month = extra.getString("MONTH");
        String day = extra.getString("DAY");
        String gendar = extra.getString("GENDAR");
        String apple = extra.getString("APPLE");
        String orange = extra.getString("ORANGE");
        String peach = extra.getString("PEACH");
        
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
        
        // �{�^���I�u�W�F�N�g�擾
        Button button=(Button)findViewById(R.id.backbutton);
        
        // �{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        button.setOnClickListener(new ButtonBackClickListener());
        

    }
    // �߂�{�^���N���b�N���X�i�[��`
    class ButtonBackClickListener implements OnClickListener {
        // onClick���\�b�h(�{�^���N���b�N���C�x���g�n���h��)
        @Override
        public void onClick(View v) {           
            // ��ʂ��N���[�Y
            finish();
        }

    }
}
