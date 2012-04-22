package jp.co.techfun.tweetapp.commons;

//アプリ全体用定数
public final class AppConstants {

    private AppConstants() {
    }

    // Request Token取得用URL
    public static final String REQUEST_TOKEN_ENDPOINT_URL =
        "https://api.twitter.com/oauth/request_token";

    // Access Token取得用URL
    public static final String ACCESS_TOKEN_ENDPOINT_URL =
        "https://api.twitter.com/oauth/access_token";

    // OAuth認証用URL
    public static final String AUTHORIZATION_WEBSITE_URL =
        "https://api.twitter.com/oauth/authorize";

    // Twitter認証後のコールバック用URL
    public static final String CALLBACK_URL = "tweetapp://techfun";

    // 認証用Consumerキー
    public static final String CONSUMER_KEY = "oQ9ri0QyuMr9FkR5EUN0Uw";

    // 認証用Consumer Secretキー
    public static final String CONSUMER_SECRET =
        "2TOsSnzHtiyTtvPKDkH4c9kLkXkAvHIi9O7muw";

    // CommonsHttpOAuthConsumer引き渡し用パラメータ名
    public static final String OAUTH_CONSUMER = "oauthConsumer";

    // タイムラインのURI
    public static final String TIMELINE_REQUEST_URL =
        "https://api.twitter.com/1/statuses/home_timeline.json";

    // つぶやきを投稿するURI
    public static final String TWEET_REQUEST_URL =
        "https://api.twitter.com/1/statuses/update.json";
    
    // 認証画面で許可しなかった場合の判定用
    public static final String REQUEST_TOKEN_DENIED = "denied";
}
