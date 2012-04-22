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

// タイムライン表示用アダプター
public class TimeLineAdapter extends ArrayAdapter<TweetEntity> {
    // LayoutInflater(レイアウトXMLからViewを生成)
    private LayoutInflater inflater;

    // コンストラクタ
    public TimeLineAdapter(Context context, int textViewResourceId,
        List<TweetEntity> list) {
        super(context, textViewResourceId, list);

        // ContextからLayoutInflaterを取得
        inflater =
            (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // getViewメソッド(一覧用ビュー取得処理)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        // ビューがnullの場合
        if (convertView == null) {
            // レイアウトXMLをビューオブジェクトとして取得
            view = inflater.inflate(R.layout.tweet, null);
        } else {
            view = convertView;
        }

        // リストからTweetEntityオブジェクト取得
        TweetEntity tweet = (TweetEntity) getItem(position);

        // スクリーンネーム設定
        TextView screenName = (TextView) view.findViewById(R.id.tv_screen_name);
        screenName.setText(tweet.getScreenName());

        // つぶやき本文設定
        TextView text = (TextView) view.findViewById(R.id.tv_text);
        text.setText(tweet.getText());

        // アイコン設定
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
