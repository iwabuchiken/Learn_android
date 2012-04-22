package jp.co.techfun.picturejump;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

// カメラ画面Activity
public class CameraActivity extends Activity {

    // onCreateメソッド(画面初期表示イベント)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ウィンドウタイトル非表示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // CameraViewインスタンスを画面に設定
        setContentView(new CameraView(this));
    }

    // カメラ画面Viewクラス
    private static class CameraView extends SurfaceView implements
        SurfaceHolder.Callback, Camera.PictureCallback,
        Camera.AutoFocusCallback {

        // サーフェイスホルダー
        private SurfaceHolder holder;
        // カメラ
        private Camera camera;

        // コンストラクタ
        public CameraView(Context context) {
            super(context);

            // サーフェイスホルダー生成
            holder = getHolder();
            holder.addCallback(this);

            // プッシュバッッファ設定
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        // Activity取得
        private Activity getActivity() {
            return (Activity) getContext();
        }

        // surfaceChangedメソッド(サーフェイス変更イベント)
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
            // カメラのプレビュー開始
            camera.startPreview();
        }

        // surfaceCreatedメソッド(サーフェイス作成イベント)
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            try {
                // カメラ起動
                camera = Camera.open();
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "camera open failed!", e);
                AppUtil.showToast(getContext(), R.string.camera_open_failed);
                getActivity().finish();
            }
        }

        // surfaceDestroyedメソッド(サーフェイス破棄イベント)
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // プレビュー停止、カメラ解放
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        // onTouchEventメソッド(タッチイベント)
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            // 画面タッチされた場合、オートフォーカス
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                camera.autoFocus(this);
            }

            return true;
        }

        // onAutoFocusメソッド(オートフォーカス実行時イベント)
        @Override
        public void onAutoFocus(boolean flag, Camera camera) {
            // オートフォーカスが完了したタイミングでカメラ撮影
            camera.takePicture(null, null, this);
        }

        // onPictureTakenメソッド(カメラ撮影イベント)
        @Override
        public void onPictureTaken(byte[] abyte0, Camera camera) {

            try {
                // 撮影画像を一時保存パスに保存
                AppUtil.savePicture(abyte0);
                // インテント呼び出し成功設定
                getActivity().setResult(Activity.RESULT_OK);
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "save picture failed!", e);
                AppUtil.showToast(getContext(), R.string.picture_save_failed);
            } finally {
                // アクティビティ終了
                getActivity().finish();
            }
        }
    }
}
