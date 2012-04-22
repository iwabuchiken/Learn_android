package jp.co.techfun.picturejump;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.Toast;

// アプリケーション用ユーティリティ
public final class AppUtil {

    // プライベートコンストラクタ
    private AppUtil() {
    }

    // 画像一時保存パス
    private static String PICTURE_TEMP_PATH;

    // 初期化処理
    public static void initialize(Context context) {
        
        // アプリ固有のファイルディレクトリを取得
        File files = context.getFilesDir();
        files.mkdirs();
        
        // 画像一時保存パスの取得
        File tempPath = new File(files, "temp.jpg");
        PICTURE_TEMP_PATH = tempPath.getAbsolutePath();
    }
    
    // 画像一時保存パスの取得
    public static String getPictureTempPath() {
        return PICTURE_TEMP_PATH;
    }
    
    // savePictureメソッド(画像の一時保存処理)
    public static void savePicture(byte[] picture) throws IOException {

        OutputStream out = null;
        try {
            out = new FileOutputStream(PICTURE_TEMP_PATH);
            out.write(picture);
            out.flush();

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    // deletePictureメソッド(画像削除処理)
    public static void deletePicture() {
        File file = new File(PICTURE_TEMP_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    // resizePictureメソッド(画像のリサイズ処理)
    public static Bitmap resizePicture(Bitmap picture, int maxWidth,
        int maxHeight) {

        if (picture == null) {
            return null;
        }

        // 幅と高さで大きいほうに合わせてリサイズ
        int width = 0;
        int height = 0;

        if (picture.getWidth() > picture.getHeight()) {
            width = maxWidth;
            height = width * picture.getHeight() / picture.getWidth();
        } else {
            height = maxHeight;
            width = height * picture.getWidth() / picture.getHeight();
        }

        // 算定したサイズでビットマップ生成
        Bitmap result = Bitmap.createScaledBitmap(picture, width, height, true);

        return result;
    }

    // rotateBitmapメソッド(画像を90度右に回転処理)
    public static Bitmap rotateBitmap(Bitmap bitmap) {

        Matrix matrix = new Matrix();
        matrix.postRotate(90f);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap
            .getHeight(), matrix, true);
    }

    // showToastメソッド(トーストでメッセージ表示処理）
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // showToastメソッド(トーストでメッセージ表示処理）
    public static void showToast(Context context, int resId) {
        String message = context.getResources().getString(resId);
        showToast(context, message);
    }

    // getStringメソッド(メッセージ取得処理)
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }
}
