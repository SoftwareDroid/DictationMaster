package com.softwaredroid.dictationmaster;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class HighlightOverlay {
    private final WindowManager windowManager;
    private final View overlayView;

    public HighlightOverlay(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        overlayView = LayoutInflater.from(context).inflate(R.layout.overlay, null);
    }

    public void showHighlight(Rect rect) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                rect.width(),
                rect.height(),
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Use TYPE_PHONE for older versions
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = rect.left;
        params.y = rect.top;

        // Set the size of the overlay to match the highlighted area
        overlayView.setLayoutParams(new FrameLayout.LayoutParams(rect.width(), rect.height()));
        overlayView.setBackgroundColor(Color.argb(128, 255, 0, 0)); // Semi-transparent red

        windowManager.addView(overlayView, params);
    }

    public void removeHighlight() {
        windowManager.removeView(overlayView);
    }
}
