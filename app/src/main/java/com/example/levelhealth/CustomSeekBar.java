package com.example.levelhealth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSeekBar;

public class CustomSeekBar extends AppCompatSeekBar {

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Drawable mark = getResources().getDrawable(R.drawable.seekbar_item_2);

        int progress = getProgress();

        final int count = getMax();
        if (count > 1) {
            final int w = mark.getIntrinsicWidth();
            final int h = mark.getIntrinsicHeight();
            final int halfW = w >= 0 ? w / 2 : 1;
            final int halfH = h >= 0 ? h / 2 : 1;
            mark.setBounds(-halfW, -halfH, halfW, halfH);

            final float spacing = (getWidth() - getPaddingLeft() - getPaddingRight()) / (float) count;
            final int saveCount = canvas.save();
            canvas.translate(getPaddingLeft(), getHeight() / 2);
            for (int i = 0; i <= count; i++) {

                if (i == 0 || i == getMax()) mark.setTint(Color.parseColor("#00000000"));
                else {
                    if (i <= progress) {
                        mark.setTint(getResources().getColor(R.color.main_color_2));
                    } else {
                        mark.setTint(Color.parseColor("#D8D8D8"));
                    }
                }

                mark.draw(canvas);
                canvas.translate(spacing, 0);
            }
            canvas.restoreToCount(saveCount);
        }

    }
}
