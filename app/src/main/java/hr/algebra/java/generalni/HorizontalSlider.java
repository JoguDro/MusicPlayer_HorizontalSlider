package hr.algebra.java.generalni;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class HorizontalSlider extends ProgressBar {

    private List<OnProgressChangeListener> listeners = new ArrayList<>();

    public HorizontalSlider(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.progressBarStyleHorizontal);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {

            float position = event.getX();
            int progress = Math.round(getMax() * position / getWidth());

            setProgress(progress);
            notifyListeners(progress);
        }

        return true;
    }

    private void notifyListeners(int progress) {
        for (OnProgressChangeListener listener : listeners) {
            listener.onProgressChange(this, progress);
        }
    }
}
