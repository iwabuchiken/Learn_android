package jp.co.techfun.lunchmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

// 店情報登録画面Activity
public class InputShopInfoActivity extends Activity {
    // インテントのデータ受け渡しキー定義
    // 店名
    static final String SHOP_NAME_KEY = "SHOP_NAME";
    // 評価
    static final String SHOP_RATE_KEY = "SHOP_RATE";
    // 平均予算
    static final String PRICE_KEY = "PRICE";
    // コメント
    static final String COMMENT_KEY = "COMMENT";

    // onCreateメソッド(画面初期表示イベント)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // レイアウトを設定
        setContentView(R.layout.input_shop_info);

        // 「確定」ボタンにクリックリスナー設定
        Button btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(btnOkListener);

        // 「戻る」ボタンにクリックリスナー設定
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(btnCancelListener);
    }

    // 確定ボタンクリックリスナー定義
    private OnClickListener btnOkListener = new OnClickListener() {
        // onClickメソッド(ボタンクリック時イベント)
        public void onClick(View v) {
            // インテントの取得
            Intent intent = getIntent();

            // 各テキストオブジェクト取得
            EditText etShopName = (EditText) findViewById(R.id.et_shop_name);
            RatingBar rbShopRate = (RatingBar) findViewById(R.id.rb_shop_rate);
            EditText etPrice = (EditText) findViewById(R.id.et_price);
            EditText etComment = (EditText) findViewById(R.id.et_comment);

            // 各テキストオブジェクトの値をインテントに設定
            intent.putExtra(SHOP_NAME_KEY, etShopName.getText().toString());
            intent.putExtra(SHOP_RATE_KEY, (int) rbShopRate.getRating());
            String priceText = etPrice.getText().toString();
            intent.putExtra(PRICE_KEY, priceText.length() > 0 ? Integer
                .parseInt(priceText) : 0);
            intent.putExtra(COMMENT_KEY, etComment.getText().toString());

            // 結果情報の設定
            setResult(RESULT_OK, intent);

            // 画面をクローズ
            finish();
        }
    };

    // 戻るボタンクリックリスナー定義
    private OnClickListener btnCancelListener = new OnClickListener() {
        // onClickメソッド(ボタンクリック時イベント)
        public void onClick(View v) {
            // 画面をクローズ
            finish();
        }
    };
}
