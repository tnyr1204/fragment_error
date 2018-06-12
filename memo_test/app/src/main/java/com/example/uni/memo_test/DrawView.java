package com.example.uni.memo_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.Stack;

public class DrawView extends View implements OnTouchListener{
	private Paint paint;
	private final Stack<Path> undoStack = new Stack<Path>();
	private final Stack<Path> redoStack = new Stack<Path>();
	private Path path;
	private Canvas canvas;

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(this);

		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(5);
		paint.setStrokeCap(Paint.Cap.ROUND);
		canvas = new Canvas();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (Path path : undoStack) {
			canvas.drawPath(path, paint);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// タッチしたとき
				path = new Path();
				path.moveTo(event.getX()-1, event.getY()-1);
				undoStack.push(path);
				redoStack.clear();
				break;
			case MotionEvent.ACTION_MOVE:
				// タッチしたまま動かしたとき
				path.lineTo(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				// 指を離したとき
				path.lineTo(event.getX(), event.getY());
				break;
			default:
		}
		invalidate();
		return true;
	}

	public Path undo() {
		if(!undoStack.isEmpty()) {
			path = undoStack.pop();
			redoStack.push(path);
			invalidate();
		}

		return path;
	}

	public Path redo() {
		if(!redoStack.isEmpty()) {
			path = redoStack.pop();
			undoStack.push(path);
			invalidate();
		}

		return path;
	}

	public void clear() {
		undoStack.clear();
		redoStack.clear();
		invalidate();
	}
}