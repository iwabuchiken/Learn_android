package jp.co.techfun.pianotouch;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

// タッチリスナークラス
public class ButtonTouchListener implements View.OnTouchListener {

    // タッチされたキーに対応する音声フィールド
    private MediaPlayer mp;

    // 初期状態の画像
    private int defaultPic;

    // タッチ時の画像
    private int touchPic;

    // コンストラクタ
    public ButtonTouchListener(MediaPlayer tMp, int tDefaultPic, int tTouchPic) {
        defaultPic = tDefaultPic;
        touchPic = tTouchPic;
        mp = tMp;
    }

    // onTouchメソッド(タッチイベント)
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        // リソースの取得
        Resources res = view.getResources();

        switch (event.getActionMasked()) {

        // タッチした場合
        case MotionEvent.ACTION_DOWN:
            // 鍵盤の画像をタッチ時画像に設定
            view.setBackgroundDrawable(res.getDrawable(touchPic));
            // 音声を再生
            startPlay();

            break;

        // タッチが離れた場合
        case MotionEvent.ACTION_UP:
            // 鍵盤の背景をデフォルト画像に設定
            view.setBackgroundDrawable(res.getDrawable(defaultPic));
            // 音声を停止
            stopPlay();

            break;

        // 上記以外
        default:

            break;
        }

        return true;
    }

    // startPlayメソッド(音声再生処理)
    private void startPlay() {
        mp.seekTo(0);
        mp.start();
    }

    // stopPlayメソッド(音声停止処理)
    private void stopPlay() {
        mp.pause();
    }
}
