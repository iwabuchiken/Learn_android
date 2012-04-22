package example.android.graphics.canvas;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

// キャンバスビュークラス
public class CanvasView extends View {
	// 描画する点格納用リスト
	private ArrayList<Point> points;
	// Paintインスタンス
	private Paint paint;

	// コンストラクタ
	public CanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);

		// 描画する点格納用リスト生成
		points = new ArrayList<Point>();

		// Paint(筆)の設定
		paint = new Paint();
		paint.setColor(0xFF4444FF);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(3);
	}

	// onMeasureメソッド(ビューのサイズ設定処理)
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	// onTouchEventメソッド(タッチイベント)
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			// 画面に指が付いたまたは動いている場合はその座標を設定
			points.add(new Point((int) event.getX(), (int) event.getY()));
			break;
		case MotionEvent.ACTION_UP:
			// 画面から指が離された場合はデリミタを設定
			points.add(new Point(-1, -1));
			break;
		default:
			break;
		}

		this.invalidate();

		return true;
	}

	// onDrawメソッド(描画処理)
	@Override
	protected void onDraw(Canvas canvas) {
		// キャンバスの背景を白に設定
		canvas.drawColor(Color.WHITE);

		// 描画処理
		Point sp = new Point(-1, -1);
		for (Point ep : points) {
			if (sp.x >= 0) {
				if (ep.x >= 0) {
					canvas.drawLine(sp.x, sp.y, ep.x, ep.y, paint);
				} else {
					canvas.drawPoint(sp.x, sp.y, paint);
				}
			}

			sp = ep;
		}
	}

	// clearDrawListメソッド(クリア処理)
	public void clearDrawList() {
		points.clear();
		this.invalidate();
	}
}
