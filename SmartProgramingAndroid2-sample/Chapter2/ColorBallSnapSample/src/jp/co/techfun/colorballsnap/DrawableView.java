package jp.co.techfun.colorballsnap;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

// 画面の描画クラス
public class DrawableView extends View {
    // 円の直径
    private static final int DIAMETER = 50;

    // 円の半径
    private static final int RADIUS = DIAMETER / 2;

    // 画面上下のマージン
    private static final int OFFSET_Y = 100;

    // 画面左右のマージン
    private static final int OFFSET_X = 0;

    // 穴と描画オブジェクト用配列
    private ShapeDrawable[] drawables;

    // 穴と描画オブジェクト用配列要素数
    private static final int HOLES_SIZE = 5;

    // 赤玉を受ける穴の配列番号
    private static final int RED_OVAL = 0;

    // 緑玉を受ける穴の配列番号
    private static final int GREEN_OVAL = 1;

    // 青玉を受ける穴の配列番号
    private static final int BLUE_OVAL = 2;

    // 白玉を受ける穴の配列番号
    private static final int WHITE_OVAL = 3;

    // ボールの描画インスタンス
    private ShapeDrawable ballDrawables;

    // ボールの表示位置
    private int movableBallX;
    private int movableBallY;

    // ボールの現在の色
    private int currentColor;

    // ボール用グラデーション定義
    private static final RadialGradient RADIAL_GRADIENT_BLUE =
        new RadialGradient(10, 10, RADIUS, Color.CYAN, Color.BLUE,
            Shader.TileMode.MIRROR);
    private static final RadialGradient RADIAL_GRADIENT_RED =
        new RadialGradient(10, 10, RADIUS, Color.YELLOW, Color.RED,
            Shader.TileMode.MIRROR);
    private static final RadialGradient RADIAL_GRADIENT_GREEN =
        new RadialGradient(10, 10, RADIUS, Color.WHITE, Color.GREEN,
            Shader.TileMode.MIRROR);
    private static final RadialGradient RADIAL_GRADIENT_WHITE =
        new RadialGradient(10, 10, RADIUS, Color.WHITE, Color.DKGRAY,
            Shader.TileMode.MIRROR);

    // 落ちたボールの描画オブジェクト配列
    private ShapeDrawable[] fallBallDrawables;

    // 落ちたボールの数
    private int fallCount;

    // 落ちたボールをストックする数
    private static final int FALL_BALL_STOCKS_SIZE = 20;

    // コンストラクタ(描画オブジェクト生成)
    public DrawableView(Context context) {
        super(context);

        // ShapeDrawableのインスタンス生成
        drawables = new ShapeDrawable[HOLES_SIZE];
        fallBallDrawables = new ShapeDrawable[FALL_BALL_STOCKS_SIZE];

        // 穴の描画
        drawables[RED_OVAL] = new ShapeDrawable(new OvalShape());
        drawables[GREEN_OVAL] = new ShapeDrawable(new OvalShape());
        drawables[BLUE_OVAL] = new ShapeDrawable(new OvalShape());
        drawables[WHITE_OVAL] = new ShapeDrawable(new OvalShape());

        // 穴の色の設定
        drawables[RED_OVAL].getPaint().setShader(
            new RadialGradient(25, 25, 20, Color.BLACK, Color.RED,
                Shader.TileMode.MIRROR));
        drawables[GREEN_OVAL].getPaint().setShader(
            new RadialGradient(25, 25, 20, Color.BLACK, Color.GREEN,
                Shader.TileMode.MIRROR));
        drawables[BLUE_OVAL].getPaint().setShader(
            new RadialGradient(25, 25, 20, Color.BLACK, Color.BLUE,
                Shader.TileMode.MIRROR));
        drawables[WHITE_OVAL].getPaint().setShader(
            new RadialGradient(25, 25, 20, Color.BLACK, Color.WHITE,
                Shader.TileMode.MIRROR));

        // ボールの描画
        ballDrawables = new ShapeDrawable(new OvalShape());

        // ボールの色を設定(初期値)
        ballDrawables.getPaint().setShader(getRandomRadialGradient());

        // ボールの初期表示位置を画面外に設定
        movableBallX = -DIAMETER - 1;
        movableBallY = movableBallX;
    }

    // onDrawメソッド(画面描画処理)
    @Override
    protected void onDraw(Canvas canvas) {

        // 画面描画領域
        int width = canvas.getWidth() - OFFSET_X;
        int height = canvas.getHeight() - OFFSET_Y;

        // 画面中央に円を表示する座標
        int cX = width / 2 - RADIUS;
        int cY = height / 2 - RADIUS;

        // 4色の穴を画面に表示
        drawColorHole(canvas, width, height, cX, cY);

        // ボールと同じ色の穴に重なったか
        if (drawables[currentColor].getBounds().contains(movableBallX,
            movableBallY)) {
            // ストックに空きがない場合
            if (fallCount >= fallBallDrawables.length) {

                // 過去のストックを消去
                for (@SuppressWarnings("unused")
                Drawable dr : fallBallDrawables) {
                    dr = null;
                }

                // 穴に落ちた回数をリセット
                fallCount = 0;
            }

            // 落ちたボールをストック
            fallBallDrawables[fallCount] = ballDrawables;

            // 穴に落ちた回数を増加
            fallCount++;

            // 新しいボールを生成
            ballDrawables = new ShapeDrawable(new OvalShape());

            // 新しい色を設定
            ballDrawables.getPaint().setShader(getRandomRadialGradient());

            // ボールの表示位置を画面中央にセット
            movableBallX = cX;
            movableBallY = cY;
        }

        // ボールを画面に表示
        drawMovableBall(canvas, width, height, cX, cY);

        // 落ちたボールのストックを表示
        drawFallBall(canvas, width);
    }

    // drawMovableBallメソッド(ボールの画面描画処理)
    private void drawMovableBall(Canvas canvas, int width, int height, int cX,
        int cY) {

        // ボールが画面外に行った場合の処理
        if (movableBallX < -DIAMETER || width < movableBallX
            || movableBallY < -DIAMETER || height < movableBallY) {

            // 表示位置を画面中央にセット
            movableBallX = cX;
            movableBallY = cY;
        }

        // ボールを表示
        ballDrawables.setBounds(movableBallX, movableBallY, movableBallX
            + DIAMETER, movableBallY + DIAMETER);
        ballDrawables.draw(canvas);

    }

    // drawFallBallメソッド(落ちたボールのストック描画処理)
    private void drawFallBall(Canvas canvas, int width) {
        int x = 0;
        int y = 0;
        // ストックしたボールを画面左上から右方向に表示
        for (int i = 0; i < fallCount; i++) {
            // 画面幅まで達した場合
            if (x + RADIUS >= width) {
                // 1つ下の列の左端から表示
                x = 0;
                y += RADIUS;
            }

            // 落ちたボールを表示
            fallBallDrawables[i].setBounds(x, y, x + RADIUS, y + RADIUS);
            fallBallDrawables[i].draw(canvas);

            // 次の落ちたボールを表示する位置
            x += RADIUS;
        }
    }

    // drawColorHoleメソッド(ボールを受ける4色の穴の描画処理)
    private void drawColorHole(Canvas canvas, int width, int height, int cX,
        int cY) {

        // 画面上端に赤玉を受ける穴

        // 始点(左上)の座標
        int left = cX;
        int top = OFFSET_Y;

        // 終点(右下)の座標
        int right = cX + DIAMETER;
        int bottom = OFFSET_Y + DIAMETER;

        // 穴を表示
        drawables[RED_OVAL].setBounds(left, top, right, bottom);
        drawables[RED_OVAL].draw(canvas);

        // 画面右端に緑玉を受ける穴

        // 始点(左上)の座標
        left = width - DIAMETER;
        top = cY;

        // 終点(右下)の座標
        right = width;
        bottom = cY + DIAMETER;

        // 穴を表示
        drawables[GREEN_OVAL].setBounds(left, top, right, bottom);
        drawables[GREEN_OVAL].draw(canvas);

        // 画面下端に青玉を受ける穴

        // 始点(左上)の座標
        left = cX;
        top = height - DIAMETER;

        // 終点(右下)の座標
        right = cX + DIAMETER;
        bottom = height;

        // 穴を表示
        drawables[BLUE_OVAL].setBounds(left, top, right, bottom);
        drawables[BLUE_OVAL].draw(canvas);

        // 画面左端に白玉を受ける穴

        // 始点(左上)の座標
        left = OFFSET_X;
        top = cY;

        // 終点(右下)の座標
        right = OFFSET_X + DIAMETER;
        bottom = cY + DIAMETER;

        // 穴を表示
        drawables[WHITE_OVAL].setBounds(left, top, right, bottom);
        drawables[WHITE_OVAL].draw(canvas);
    }


    // getRandomRadialGradientメソッド(ランダムグラデーション処理)
    private Shader getRandomRadialGradient() {

        // グラデーションの種類
        final int type = 4;
        currentColor = new Random().nextInt(type);

        // ランダムにグラデーションの種類を選択
        switch (currentColor) {
        case RED_OVAL:
            return RADIAL_GRADIENT_RED;

        case GREEN_OVAL:
            return RADIAL_GRADIENT_GREEN;

        case BLUE_OVAL:
            return RADIAL_GRADIENT_BLUE;

        default:
            return RADIAL_GRADIENT_WHITE;
        }
    }

    // effectAccelarationメソッド(加速度の影響の反映処理)
    public void effectAccelaration(float x, float y, float z) {
        // 端末右側から受ける加速度を反映
        movableBallX -= x * 2;

        // 端末上側から受ける加速度を反映
        movableBallY += y * 2;
    }
}
