package com.db.funnel_meterchartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


public class MeterChartView extends ConstraintLayout {

    private final Context mContext;
    private int secondaryColor = Color.YELLOW;
    private int progressColors = Color.GRAY;
    private int mColorHand = Color.RED;
    private int viewWidth = 100;
    private ImageView mImgDialBottom;
    private ImageView mImgDialFront;
    private ImageView mImgHand;
    private ImageView mImgDialCenter;
    private final int HAND_START_ANGLE = 270;
    private final int FRONT_START_ANGLE = 0;
    private int meterProgress;
    private int meterHandImageResource;
    private float meterWidthPercent = 10;


    public MeterChartView(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public MeterChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public MeterChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        if (set != null) {
            TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.MeterChartView);
            secondaryColor = ta.getColor(R.styleable.MeterChartView_secondaryColor, getResources().getColor(R.color.secondary_color));
            progressColors = ta.getColor(R.styleable.MeterChartView_progressColor, getResources().getColor(R.color.progress_color));
            meterProgress = ta.getInt(R.styleable.MeterChartView_progressMeter, 15);
            mColorHand = ta.getColor(R.styleable.MeterChartView_clockHandColor, getResources().getColor(R.color.hand_color));
            meterHandImageResource = ta.getResourceId(R.styleable.MeterChartView_meterHandImage, R.drawable.clock_hand);
            meterWidthPercent = ta.getFloat(R.styleable.MeterChartView_meterWidthPercent, 10);
            ta.recycle();
            rotateHand(meterProgress);
//            invalidate();
        }
    }

    private void initView() {
        if (mImgDialBottom == null) {
            int paddingVal = (int) ((viewWidth * meterWidthPercent) / 100);
            //Instance of view need to add
            mImgDialBottom = new ImageView(mContext);
            mImgDialFront = new ImageView(mContext);
            mImgDialCenter = new ImageView(mContext);
            mImgHand = new ImageView(mContext);
            ImageView tempView = new ImageView(mContext);

            // Need to set list of ids
            mImgDialBottom.setId(View.generateViewId());
            mImgDialFront.setId(View.generateViewId());
            mImgDialCenter.setId(View.generateViewId());
            mImgHand.setId(View.generateViewId());
            tempView.setId(View.generateViewId());

            //Set view height as half of width of view
            ViewGroup.LayoutParams params = this.getLayoutParams();
            params.height = (viewWidth / 2) + 50;

            this.setLayoutParams(params);

            //Set bitmap of half of circle
            mImgDialBottom.setImageResource(R.drawable.circle_half);
            mImgDialFront.setImageResource(R.drawable.circle_half);
            mImgDialCenter.setImageResource(R.drawable.white_circle);
            mImgHand.setImageResource(meterHandImageResource);

            mImgDialBottom.setRotation(180);
            mImgDialFront.setRotation(FRONT_START_ANGLE);
            mImgHand.setRotation(HAND_START_ANGLE);


            //Add View to constraint layout
            addView(mImgDialBottom);
            addView(mImgDialFront);
            addView(mImgDialCenter);
            addView(tempView);
            addView(mImgHand);

            ViewGroup.LayoutParams paramsBottom = mImgDialBottom.getLayoutParams();
            paramsBottom.width = viewWidth;
            paramsBottom.height = (viewWidth);
            mImgDialBottom.setLayoutParams(paramsBottom);
            mImgDialFront.setLayoutParams(paramsBottom);
            mImgHand.setLayoutParams(paramsBottom);
            mImgDialCenter.setPadding(paddingVal, paddingVal, paddingVal, paddingVal);
            mImgDialCenter.setLayoutParams(paramsBottom);

            ViewGroup.LayoutParams tempViewParams = tempView.getLayoutParams();
            tempViewParams.width = viewWidth;
            tempViewParams.height = 50;
            tempView.setBackgroundColor(Color.WHITE);
            tempView.setLayoutParams(tempViewParams);

            //Constraint layout properties
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            updateColorFilter();
            constraintSet.connect(mImgDialBottom.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            constraintSet.connect(mImgDialBottom.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            constraintSet.connect(mImgDialBottom.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);

            constraintSet.connect(mImgDialFront.getId(), ConstraintSet.START, mImgDialBottom.getId(), ConstraintSet.START, 0);
            constraintSet.connect(mImgDialFront.getId(), ConstraintSet.END, mImgDialBottom.getId(), ConstraintSet.END, 0);
            constraintSet.connect(mImgDialFront.getId(), ConstraintSet.TOP, mImgDialBottom.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(mImgDialFront.getId(), ConstraintSet.BOTTOM, mImgDialBottom.getId(), ConstraintSet.BOTTOM, 0);

            constraintSet.connect(mImgDialCenter.getId(), ConstraintSet.START, mImgDialBottom.getId(), ConstraintSet.START, 0);
            constraintSet.connect(mImgDialCenter.getId(), ConstraintSet.END, mImgDialBottom.getId(), ConstraintSet.END, 0);
            constraintSet.connect(mImgDialCenter.getId(), ConstraintSet.TOP, mImgDialBottom.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(mImgDialCenter.getId(), ConstraintSet.BOTTOM, mImgDialBottom.getId(), ConstraintSet.BOTTOM, 0);

            constraintSet.connect(mImgHand.getId(), ConstraintSet.START, mImgDialBottom.getId(), ConstraintSet.START, 0);
            constraintSet.connect(mImgHand.getId(), ConstraintSet.END, mImgDialBottom.getId(), ConstraintSet.END, 0);
            constraintSet.connect(mImgHand.getId(), ConstraintSet.TOP, mImgDialBottom.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(mImgHand.getId(), ConstraintSet.BOTTOM, mImgDialBottom.getId(), ConstraintSet.BOTTOM, 0);

            constraintSet.connect(tempView.getId(), ConstraintSet.START, mImgDialBottom.getId(), ConstraintSet.START, 0);
            constraintSet.connect(tempView.getId(), ConstraintSet.END, mImgDialBottom.getId(), ConstraintSet.END, 0);
            constraintSet.connect(tempView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
            constraintSet.applyTo(this);
            ////////////////////
            updateColorFilter();
            rotateHand(meterProgress);
        }
    }

    /**
     * Update color filter of chart view based on color
     */
    private void updateColorFilter() {
        if (mImgDialBottom != null) {
            mImgDialBottom.setColorFilter(secondaryColor);
            mImgDialFront.setColorFilter(progressColors);
            mImgHand.setColorFilter(mColorHand);
            mImgDialCenter.setColorFilter(Color.WHITE);
        } else {
            init(null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(viewWidth, viewHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        initView();
    }

    /**
     * Rotate meter based on first percent
     *
     * @param percentObtain value progress meter
     */
    public void rotateHand(float percentObtain) {
        if (mImgHand != null) {
            updateColorFilter();
            mImgHand.animate().rotation(HAND_START_ANGLE + ((percentObtain * 180) / 100)).start();
            mImgDialFront.animate().rotation(FRONT_START_ANGLE + ((percentObtain * 180) / 100)).start();
        }
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
        invalidate();
    }

    public int getProgressColors() {
        return progressColors;
    }

    public void setProgressColors(int progressColors) {
        this.progressColors = progressColors;
        invalidate();
    }

    public int getColorHand() {
        return mColorHand;
    }

    public void setColorHand(int mColorHand) {
        this.mColorHand = mColorHand;
        invalidate();
    }

    public ImageView getImgHand() {
        return mImgHand;
    }

    public void setImgHand(ImageView mImgHand) {
        this.mImgHand = mImgHand;
        invalidate();
    }

    public int getMeterProgress() {
        return meterProgress;
    }

    public void setMeterProgress(int meterProgress) {
        this.meterProgress = meterProgress;
        invalidate();
    }

    public int getMeterHandImageResource() {
        return meterHandImageResource;
    }

    public void setMeterHandImageResource(int meterHandImageResource) {
        this.meterHandImageResource = meterHandImageResource;
        invalidate();
    }

    public float getMeterWidthPercent() {
        return meterWidthPercent;

    }

    public void setMeterWidthPercent(float meterWidthPercent) {
        this.meterWidthPercent = meterWidthPercent;
        invalidate();
    }
}
