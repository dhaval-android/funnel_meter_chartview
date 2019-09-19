package com.db.funnel_meterchartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class FunnelChartView extends View {

    private final Context mContext;
    private ArrayList<FunnelChartData> mDataSet;
    private int viewWidth;
    private float fontSize;
    private int fontColor;

    public FunnelChartView(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public FunnelChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public FunnelChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        if (set != null) {
            TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.FunnelChartView);
            fontSize = ta.getDimension(R.styleable.FunnelChartView_fontSizeF, 15f);
            fontColor = ta.getColor(R.styleable.FunnelChartView_fontColorF, Color.BLACK);
            ta.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(viewWidth, viewHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataSet != null && mDataSet.size() > 0) {
            drawShapeWithText(canvas);
        }

    }

    public void setmDataSet(ArrayList<FunnelChartData> mDataSet) {
        this.mDataSet = mDataSet;
        invalidate();
    }


    private void drawShapeWithText(Canvas canvas) {
        ViewGroup.LayoutParams tempParams = getLayoutParams();
        tempParams.height = viewWidth;
        tempParams.width = viewWidth;
        setLayoutParams(tempParams);

        int numberArray = mDataSet.size();
        int cornerSpace = viewWidth / (numberArray * 3);
//        int viewHeight = viewWidth / numberArray > 1 ? numberArray + 1 : numberArray;
        int viewHeight = viewWidth / (numberArray > 1 ? (numberArray + 1) : numberArray);
        int xPosition = 0; //Initial X position
        int yPosition = 0; //Initial Y Position
        int nextXPosition = cornerSpace; //
        int nextYPosition = viewHeight;

        for (int position = 0; position < numberArray; position++) {
            String textToPrint = mDataSet.get(position).getStrLabel();
            Paint paint = new Paint();
            paint.setColor(Color.parseColor(mDataSet.get(position).getColorCode()));
            //Draw Shape
            drawShape(canvas, paint, xPosition, nextXPosition, yPosition, nextYPosition);

            //Draw text on Shape
            drawCustomText(textToPrint, canvas, xPosition, yPosition, viewWidth - xPosition, nextYPosition);

            //Update axis & height width for next update
            if (position > 0 && position == numberArray - 2) {
                xPosition = xPosition + cornerSpace;
                nextXPosition = xPosition;
                yPosition = yPosition + viewHeight;
                nextYPosition = yPosition + (viewHeight * 2);
            } else {
                xPosition = xPosition + cornerSpace;
                yPosition = yPosition + viewHeight;
                nextXPosition = xPosition + cornerSpace;
                nextYPosition = yPosition + viewHeight;
            }
        }
    }

    private void drawCustomText(String textToPrint, Canvas canvas, int xPosition, int yPosition, int nextXPosition, int nextYPosition) {
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(fontSize);

        Rect areaRect = new Rect(xPosition, yPosition, nextXPosition, nextYPosition);
        mPaint.setColor(fontColor);


        RectF bounds = new RectF(areaRect);
        bounds.right = mPaint.measureText(textToPrint, 0, textToPrint.length());
        bounds.bottom = mPaint.descent() - mPaint.ascent();
        bounds.left += (areaRect.width() - bounds.right) / 2.0f;
        bounds.top += (areaRect.height() - bounds.bottom) / 2.0f;

        mPaint.setColor(Color.BLACK);
        canvas.drawText(textToPrint, bounds.left, bounds.top - mPaint.ascent(), mPaint);
    }

    private void drawShape(Canvas canvas, Paint paint, int xPosition, int nextXPosition, int yPosition, int nextYPosition) {
        Path path = new Path();
        path.moveTo(xPosition, yPosition); // Top
        path.lineTo(nextXPosition, nextYPosition); // Bottom left
        path.lineTo(viewWidth - nextXPosition, nextYPosition); // Bottom left
        path.lineTo(viewWidth - xPosition, yPosition); // Bottom left
        path.close();
        canvas.drawPath(path, paint);
    }

}
