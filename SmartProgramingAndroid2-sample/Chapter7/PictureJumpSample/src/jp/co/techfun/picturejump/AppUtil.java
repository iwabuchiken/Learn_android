package jp.co.techfun.picturejump;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.Toast;

// �A�v���P�[�V�����p���[�e�B���e�B
public final class AppUtil {

    // �v���C�x�[�g�R���X�g���N�^
    private AppUtil() {
    }

    // �摜�ꎞ�ۑ��p�X
    private static String PICTURE_TEMP_PATH;

    // ����������
    public static void initialize(Context context) {
        
        // �A�v���ŗL�̃t�@�C���f�B���N�g�����擾
        File files = context.getFilesDir();
        files.mkdirs();
        
        // �摜�ꎞ�ۑ��p�X�̎擾
        File tempPath = new File(files, "temp.jpg");
        PICTURE_TEMP_PATH = tempPath.getAbsolutePath();
    }
    
    // �摜�ꎞ�ۑ��p�X�̎擾
    public static String getPictureTempPath() {
        return PICTURE_TEMP_PATH;
    }
    
    // savePicture���\�b�h(�摜�̈ꎞ�ۑ�����)
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

    // deletePicture���\�b�h(�摜�폜����)
    public static void deletePicture() {
        File file = new File(PICTURE_TEMP_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    // resizePicture���\�b�h(�摜�̃��T�C�Y����)
    public static Bitmap resizePicture(Bitmap picture, int maxWidth,
        int maxHeight) {

        if (picture == null) {
            return null;
        }

        // ���ƍ����ő傫���ق��ɍ��킹�ă��T�C�Y
        int width = 0;
        int height = 0;

        if (picture.getWidth() > picture.getHeight()) {
            width = maxWidth;
            height = width * picture.getHeight() / picture.getWidth();
        } else {
            height = maxHeight;
            width = height * picture.getWidth() / picture.getHeight();
        }

        // �Z�肵���T�C�Y�Ńr�b�g�}�b�v����
        Bitmap result = Bitmap.createScaledBitmap(picture, width, height, true);

        return result;
    }

    // rotateBitmap���\�b�h(�摜��90�x�E�ɉ�]����)
    public static Bitmap rotateBitmap(Bitmap bitmap) {

        Matrix matrix = new Matrix();
        matrix.postRotate(90f);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap
            .getHeight(), matrix, true);
    }

    // showToast���\�b�h(�g�[�X�g�Ń��b�Z�[�W�\�������j
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // showToast���\�b�h(�g�[�X�g�Ń��b�Z�[�W�\�������j
    public static void showToast(Context context, int resId) {
        String message = context.getResources().getString(resId);
        showToast(context, message);
    }

    // getString���\�b�h(���b�Z�[�W�擾����)
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }
}
