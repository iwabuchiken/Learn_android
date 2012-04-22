package jp.co.techfun.tweetapp.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

// �^�C�����C����JSON��̓N���X
public class TimeLineResponseHandler implements
    ResponseHandler<List<TweetEntity>> {

    // handleResponse���\�b�h(���X�|���X���擾����)
    @Override
    public List<TweetEntity> handleResponse(HttpResponse response)
        throws IOException {
        // �X�e�[�^�X�R�[�h�擾
        int statuscode = response.getStatusLine().getStatusCode();

        // �^�C�����C���擾�p���X�g
        List<TweetEntity> list = new ArrayList<TweetEntity>();

        // �X�e�[�^�X������łȂ��ꍇ
        if (statuscode != HttpStatus.SC_OK) {
            Log.e(getClass().getSimpleName(), "HTTP StatusCode Error");
            return list;
        }

        // ���X�|���X�I�u�W�F�N�g����JSON�f�[�^�擾
        String jsonStr = EntityUtils.toString(response.getEntity());

        try {
            // JSON�f�[�^����JSONTokener�擾
            JSONTokener jsonObj = new JSONTokener(jsonStr);
            // JSONTokener����JSONArray�擾
            JSONArray userArray = new JSONArray(jsonObj);

            // JSON�I�u�W�F�N�g1��������
            for (int i = 0; i < userArray.length(); i++) {
                // JSON�I�u�W�F�N�g�擾
                JSONObject jsonItem = userArray.getJSONObject(i);

                // "user"�v�f��JSON�I�u�W�F�N�g�擾
                JSONObject user = jsonItem.getJSONObject("user");

                // �Ԃ₫�{���擾
                String text = jsonItem.getString("text");

                // ���[�U���擾
                String screenName = user.getString("screen_name");

                // �A�C�R��URL�擾
                String profileImageUrlHttps =
                    user.getString("profile_image_url_https");

                // �Ԃ₫���ێ��p�I�u�W�F�N�g����
                TweetEntity entity =
                    new TweetEntity(screenName, text, profileImageUrlHttps);

                // �^�C�����C���擾�p���X�g�ɒǉ�
                list.add(entity);
            }
        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "JSON Error", e);
        }

        return list;
    }
}
