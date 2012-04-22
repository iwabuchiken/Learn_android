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

// タイムラインのJSON解析クラス
public class TimeLineResponseHandler implements
    ResponseHandler<List<TweetEntity>> {

    // handleResponseメソッド(レスポンス情報取得処理)
    @Override
    public List<TweetEntity> handleResponse(HttpResponse response)
        throws IOException {
        // ステータスコード取得
        int statuscode = response.getStatusLine().getStatusCode();

        // タイムライン取得用リスト
        List<TweetEntity> list = new ArrayList<TweetEntity>();

        // ステータスが正常でない場合
        if (statuscode != HttpStatus.SC_OK) {
            Log.e(getClass().getSimpleName(), "HTTP StatusCode Error");
            return list;
        }

        // レスポンスオブジェクトからJSONデータ取得
        String jsonStr = EntityUtils.toString(response.getEntity());

        try {
            // JSONデータからJSONTokener取得
            JSONTokener jsonObj = new JSONTokener(jsonStr);
            // JSONTokenerからJSONArray取得
            JSONArray userArray = new JSONArray(jsonObj);

            // JSONオブジェクト1件ずつ処理
            for (int i = 0; i < userArray.length(); i++) {
                // JSONオブジェクト取得
                JSONObject jsonItem = userArray.getJSONObject(i);

                // "user"要素のJSONオブジェクト取得
                JSONObject user = jsonItem.getJSONObject("user");

                // つぶやき本文取得
                String text = jsonItem.getString("text");

                // ユーザ名取得
                String screenName = user.getString("screen_name");

                // アイコンURL取得
                String profileImageUrlHttps =
                    user.getString("profile_image_url_https");

                // つぶやき情報保持用オブジェクト生成
                TweetEntity entity =
                    new TweetEntity(screenName, text, profileImageUrlHttps);

                // タイムライン取得用リストに追加
                list.add(entity);
            }
        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), "JSON Error", e);
        }

        return list;
    }
}
