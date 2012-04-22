package jp.co.techfun.tweetapp.tasks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import jp.co.techfun.tweetapp.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// �^�C�����C���\���p�A�_�v�^�[
public class TimeLineAdapter extends ArrayAdapter<TweetEntity> {
    // LayoutInflater(���C�A�E�gXML����View�𐶐�)
    private LayoutInflater inflater;

    // �R���X�g���N�^
    public TimeLineAdapter(Context context, int textViewResourceId,
        List<TweetEntity> list) {
        super(context, textViewResourceId, list);

        // Context����LayoutInflater���擾
        inflater =
            (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // getView���\�b�h(�ꗗ�p�r���[�擾����)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        // �r���[��null�̏ꍇ
        if (convertView == null) {
            // ���C�A�E�gXML���r���[�I�u�W�F�N�g�Ƃ��Ď擾
            view = inflater.inflate(R.layout.tweet, null);
        } else {
            view = convertView;
        }

        // ���X�g����TweetEntity�I�u�W�F�N�g�擾
        TweetEntity tweet = (TweetEntity) getItem(position);

        // �X�N���[���l�[���ݒ�
        TextView screenName = (TextView) view.findViewById(R.id.tv_screen_name);
        screenName.setText(tweet.getScreenName());

        // �Ԃ₫�{���ݒ�
        TextView text = (TextView) view.findViewById(R.id.tv_text);
        text.setText(tweet.getText());

        // �A�C�R���ݒ�
        ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);

        URL url = null;
        try {
            url = new URL(tweet.getProfileImageUrlHttps());
        } catch (MalformedURLException e) {
            Log.e(getClass().getSimpleName(), "Malformed URL Error", e);
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(url.openStream());
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Bitmap decodeStream Error", e);
        }
        icon.setImageBitmap(bitmap);

        return view;
    }
}
