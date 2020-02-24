package com.example.dragonfly.customView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.dragonfly.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BubbleView extends View {


    private PointF size;
    private float maxDist;
    private Paint bbPaint;
    private Paint bandPaint;
    private int bubbleColor;
    private List<Bubble> bubbles = new ArrayList<>();
    private boolean[] states;//false远离；true接近
    private int bubbleNum;
    private static final int MAX_RADIUS = 120;
    private ValueAnimator anim;
    private float tg;

    class Bubble {
        PointF center;
        int radius;

        Bubble(PointF center, int radius) {
            this.center = center;
            this.radius = radius;
        }

        void setCenter(PointF center) {
            this.center = center;
        }
    }

    public BubbleView(Context context) {
        this(context, null);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BubbleView, defStyleAttr, 0);
        maxDist = 4 * array.getDimension(R.styleable.BubbleView_bubble_radius, 20);
        bubbleColor = array.getColor(R.styleable.BubbleView_bubble_color, Color.BLUE);
        bubbleNum = array.getInteger(R.styleable.BubbleView_bubble_num, 4);
        array.recycle();

        bandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bandPaint.setColor(bubbleColor);
        bandPaint.setAlpha(128);
        bandPaint.setStyle(Paint.Style.FILL);

        bbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bbPaint.setColor(bubbleColor);
        bbPaint.setStyle(Paint.Style.FILL);


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView(w, h);
    }


    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < bubbles.size(); i++) {
            for (int j = i + 1; j < bubbles.size(); j++) {
                if (isApproach(bubbles.get(i), bubbles.get(j))) {
//                    states[i] = true;
//                    states[j] = true;
                    Path path = getBezPathBetween2dots(bubbles.get(i), bubbles.get(j));
                    canvas.drawPath(path, bandPaint);

                }
            }
            if (!states[i]) {
                PointF b = getBezPathDot(tg, bubbles.get(i));
                canvas.drawCircle(b.x, b.y, bubbles.get(i).radius, bbPaint);
            } else {
                states[i] = false;
            }


        }


    }

    private void initView(int w, int h) {
        if (size == null) {
            size = new PointF(w, h);
        } else {
            size.set(w, h);
        }
        bubbles.clear();
        states = new boolean[bubbleNum];
        for (int i = 0; i < bubbleNum; i++) {
            states[i] = false;
            PointF center = getRandomDot(i);
            Bubble bubble = new Bubble(center, 20 + 8 * i);
            bubbles.add(bubble);

        }

        anim = ValueAnimator.ofFloat(0, 1);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(15000);
//        anim.setRepeatMode(ValueAnimator.RESTART);
//        anim.setRepeatCount(-1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //设置当前绘制的爆炸图片index
                //mCurDrawableIndex = (int) animation.getAnimatedValue();
                tg = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //修改动画执行标志
                for (Bubble bubble : bubbles) {
                    bubble.setCenter(getP2(bubble));
                }
                anim.start();
            }
        });
        anim.start();
    }

    private PointF getBezPathDot(float t, Bubble bubble) {

        PointF p0 = bubble.center;
        int r = bubble.radius;
        PointF p1 = getRandomDot((int) (p0.x * p0.y));
        PointF p2 = getRandomDot((int) (p0.x * p0.y * r));
        float bx = (1 - t) * (1 - t) * p0.x + 2 * (1 - t) * t * p1.x + t * t * p2.x;
        float by = (1 - t) * (1 - t) * p0.y + 2 * (1 - t) * t * p1.y + t * t * p2.y;
        return new PointF(bx, by);
    }


    private PointF getP2(Bubble bubble) {
        PointF p0 = bubble.center;
        int r = bubble.radius;
        return getRandomDot((int) (p0.x * p0.y * r));
    }


    private PointF getRandomDot(int seed) {

        Random random = new Random(seed);
        float width = random.nextInt((int) size.x - MAX_RADIUS) + MAX_RADIUS;
        float height = random.nextInt((int) size.y - MAX_RADIUS) + MAX_RADIUS;
        return new PointF(width, height);
    }


    private boolean isApproach(Bubble b1, Bubble b2) {

        PointF f1 = getBezPathDot(tg, b1);
        PointF f2 = getBezPathDot(tg, b2);
        float distance = (f1.x - f2.x) * (f1.x - f2.x) + (f1.y - f2.y) * (f1.y - f2.y);
        return distance < maxDist * maxDist;
    }


    private Path getBezPathBetween2dots(Bubble b1, Bubble b2) {
        PointF f1 = getBezPathDot(tg, b1);//圆心o1坐标
        PointF f2 = getBezPathDot(tg, b2);//圆心o2坐标
        float anchorX = (f1.x + f2.x) / 2;
        float anchorY = (f1.y + f2.y) / 2;
        float h = Math.abs(f2.x - f1.x);//水平边
        float v = Math.abs(f2.y - f1.y);//竖边
        float x = (float) Math.sqrt(h * h + v * v);//斜边。勾股定理
        float cos0 = h / x;
        float sin0 = v / x;
        float Ax = f1.x - b1.radius * sin0;
        float Ay = f1.y - b1.radius * cos0;

        float Dx = f1.x + b1.radius * sin0;
        float Dy = f1.y + b1.radius * cos0;

        float Bx = f2.x - b2.radius * sin0;
        float By = f2.y - b2.radius * cos0;

        float Cx = f2.x + b2.radius * sin0;
        float Cy = f2.y + b2.radius * cos0;

        RectF oval1 = new RectF(f1.x - b1.radius, f1.y - b1.radius, f1.x + b1.radius, f1.y + b1.radius);
        RectF oval2 = new RectF(f2.x - b1.radius, f2.y - b1.radius, f2.x + b1.radius, f2.y + b1.radius);

        float sita1 = (float) (Math.PI - Math.asin(sin0));

        Path path = new Path();
        path.reset();
        path.moveTo(Ax, Ay);
        path.quadTo(anchorX, anchorY, Bx, By);
//        path.addArc(oval2, sita1 + 180, 180);
        path.lineTo(Cx, Cy);
        path.quadTo(anchorX, anchorY, Dx, Dy);
//        path.addArc(oval1, 90 - sita1, 180);
        path.close();
        return path;
    }

}
