package jp.co.techfun.tweetapp.tasks;

// つぶやき情報を保持するクラス
public class TweetEntity {

    // ユーザ名
    private String screenName;
    // つぶやき
    private String text;
    // プロフィールアイコン画像のURL
    private String profileImageUrlHttps;

    // コンストラクタ
    public TweetEntity(String screenName, String text,
        String profileImageUrlHttps) {
        this.screenName = screenName;
        this.text = text;
        this.profileImageUrlHttps = profileImageUrlHttps;
    }

    // ユーザ名を返すメソッドです
    public String getScreenName() {
        return screenName;
    }

    // つぶやきを返すメソッドです
    public String getText() {
        return text;
    }

    // プロフィールアイコン画像のURLを返すメソッドです
    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

}
