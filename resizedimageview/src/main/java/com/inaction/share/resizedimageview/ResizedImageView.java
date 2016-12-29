package com.inaction.share.resizedimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ResizedImageView extends ImageView {

    private int mAspectRatioWidth = 1;
    private int mAspectRatioHeight = 1;

    public ResizedImageView(Context context) {
        super(context);
    }

    public ResizedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialAttr(attrs, 0);
    }

    public ResizedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialAttr(attrs, defStyleAttr);
    }

    private void initialAttr(AttributeSet attrs, int defStyleAttr) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ResizedImageView,
                defStyleAttr,
                0);

        mAspectRatioWidth = typedArray.getInteger(R.styleable.ResizedImageView_aspectRatioWidth, 1);
        mAspectRatioHeight = typedArray.getInteger(R.styleable.ResizedImageView_aspectRatioHeight, 1);

        typedArray.recycle();
    }

    public void setAspectRatio(int width, int height) {
        mAspectRatioWidth = width;
        mAspectRatioHeight = height;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (!isInLayout()) {
                requestLayout();
            }
        } else {
            requestLayout();
        }
    }

    public int getAspectRatioWidth() {
        return mAspectRatioWidth;
    }

    public int getAspectRatioHeight() {
        return mAspectRatioHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // let ImageView do the measure first
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // get the result
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        // get padding
        int horizontalPadding = getPaddingLeft() + getPaddingRight();
        int verticalPadding = getPaddingTop() + getPaddingBottom();

        // get the dimensions which don't contain padding
        int measuredRawWidth = measuredWidth - horizontalPadding;
        int measuredRawHeight = measuredHeight - verticalPadding;

        if (measuredRawWidth * mAspectRatioHeight == measuredRawHeight * mAspectRatioWidth) {
            return;
        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (measuredRawWidth * mAspectRatioHeight >= measuredRawHeight * mAspectRatioWidth) {
            // need increase height or decrease width

            // try to increase height
            if (heightMode == MeasureSpec.AT_MOST) {
                int finalHeight = verticalPadding +
                        measuredRawWidth * mAspectRatioHeight / mAspectRatioWidth;
                if (finalHeight <= heightSize) {
                    setMeasuredDimension(measuredWidth, finalHeight);
                    return;
                }
            } else if (heightMode == MeasureSpec.UNSPECIFIED) {
                int finalHeight = verticalPadding +
                        measuredRawWidth * mAspectRatioHeight / mAspectRatioWidth;
                setMeasuredDimension(measuredWidth, finalHeight);
                return;
            }

            // try to decrease width
            if (widthMode == MeasureSpec.AT_MOST) {
                int finalWidth = horizontalPadding +
                        measuredRawHeight * mAspectRatioWidth / mAspectRatioHeight;
                if (finalWidth <= widthSize ) {
                    setMeasuredDimension(finalWidth, measuredHeight);
                    return;
                }
            } else if (widthMode == MeasureSpec.UNSPECIFIED) {
                int finalWidth = horizontalPadding +
                        measuredRawHeight * mAspectRatioWidth / mAspectRatioHeight;
                setMeasuredDimension(finalWidth, measuredHeight);
                return;
            }
        } else {
            // need increase width or decrease height

            // try to increase width
            if (widthMode == MeasureSpec.AT_MOST) {
                int finalWidth = horizontalPadding +
                        measuredRawHeight * mAspectRatioWidth / mAspectRatioHeight;
                setMeasuredDimension(Math.min(widthSize, finalWidth), measuredHeight);
            } else if (widthMode == MeasureSpec.UNSPECIFIED) {
                int finalWidth = horizontalPadding +
                        measuredRawHeight * mAspectRatioWidth / mAspectRatioHeight;
                setMeasuredDimension(finalWidth, measuredHeight);
            }

            // try to decrease height
            if (heightMode == MeasureSpec.AT_MOST) {
                int finalHeight = verticalPadding +
                        measuredRawWidth * mAspectRatioHeight / mAspectRatioWidth;
                if (finalHeight <= heightSize) {
                    setMeasuredDimension(measuredWidth, finalHeight);
                    return;
                }
            } else if (heightMode == MeasureSpec.UNSPECIFIED) {
                int finalHeight = verticalPadding +
                        measuredRawWidth * mAspectRatioHeight / mAspectRatioWidth;
                setMeasuredDimension(measuredWidth, finalHeight);
                return;
            }
        }
    }
}
