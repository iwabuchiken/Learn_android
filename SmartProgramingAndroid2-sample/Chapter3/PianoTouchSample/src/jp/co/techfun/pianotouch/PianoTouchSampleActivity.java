package jp.co.techfun.pianotouch;

import android.app.Activity;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

// ピアノ画面Activity
public class PianoTouchSampleActivity extends Activity {

    // 白鍵鍵盤数を定義する定数
    private static final int WHITE_KEY_NUMBER = 12;
    // 黒鍵鍵盤数を定義する定数
    private static final int BLACK_KEY_NUMBER = 8;

    // 白鍵・黒鍵音声再生用MediaPlayer配列の初期化
    private MediaPlayer[] whiteKeyPlayer;
    private MediaPlayer[] blackKeyPlayer;

    // onCreateメソッド(画面初期表示イベント)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // レイアウトXMLの設定
        setContentView(R.layout.pianotouchsample);

        // 白鍵・黒鍵音声再生用MediaPlayer配列の初期化
        whiteKeyPlayer = new MediaPlayer[WHITE_KEY_NUMBER];
        blackKeyPlayer = new MediaPlayer[BLACK_KEY_NUMBER];

        // リソースファイルから白鍵音声用midiファイル読み込み
        TypedArray mids =
            getResources().obtainTypedArray(R.array.mids_whiteKey);

        for (int i = 0; i < mids.length(); i++) {

            int mds = mids.getResourceId(i, -1);

            if (mds != -1) {

                whiteKeyPlayer[i] = MediaPlayer.create(this, mds);

            }
        }

        // リソースファイルから黒鍵音声用midiファイル読み込み
        mids = getResources().obtainTypedArray(R.array.mids_blackKey);

        for (int i = 0; i < mids.length(); i++) {

            int mds = mids.getResourceId(i, -1);

            if (mds != -1) {

                blackKeyPlayer[i] = MediaPlayer.create(this, mds);

            }
        }

        // レイアウトXMLより白鍵のレイアウトを取得し、タッチイベントを登録
        LinearLayout keyWhiteLayout =
            (LinearLayout) findViewById(R.id.layout_key_white);

        // 取得したレイアウトから白鍵キー(ボタン)に１つずつタッチイベントを登録
        for (int i = 0; i < keyWhiteLayout.getChildCount(); i++) {

            ImageButton keyWhiteBtn =
                (ImageButton) keyWhiteLayout.getChildAt(i);

            keyWhiteBtn.setOnTouchListener(new ButtonTouchListener(
                whiteKeyPlayer[i], R.drawable.k_w, R.drawable.k_w_p));

        }

        // レイアウトXMLより黒鍵のviewを取得し、タッチイベントを登録
        LinearLayout keyBlackLayout =
            (LinearLayout) findViewById(R.id.layout_key_black);

        // 取得したレイアウトから白鍵キー(ボタン)に１つずつタッチイベントを登録
        for (int i = 0; i < keyBlackLayout.getChildCount(); i++) {

            ImageButton keyBlackBtn =
                (ImageButton) keyBlackLayout.getChildAt(i);

            keyBlackBtn.setOnTouchListener(new ButtonTouchListener(
                blackKeyPlayer[i], R.drawable.k_b, R.drawable.k_b_p));

        }

        // ボリュームボタンでメディアの音量調整に設定
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    // onDestroyメソッド(アクティビティ破棄イベント)
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 白鍵音声再生用MediaPlayerの解放
        for (int i = 0; i < whiteKeyPlayer.length; i++) {
            if (whiteKeyPlayer[i].isPlaying()) {
                whiteKeyPlayer[i].stop();
            }
            whiteKeyPlayer[i].release();
        }

        // 黒鍵音声再生用MediaPlayerの解放
        for (int i = 0; i < blackKeyPlayer.length; i++) {
            if (blackKeyPlayer[i].isPlaying()) {
                blackKeyPlayer[i].stop();
            }
            blackKeyPlayer[i].release();
        }
    }
}
