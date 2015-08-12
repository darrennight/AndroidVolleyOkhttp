package com.breadtrio.sdk.common.utils;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class ViewUtils {

    private static final String TAG = "ViewUtils";

    /**
     * 测量view大小
     * 
     * @param child view
     */
    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public static void setVisibility(View view, int visibility) {
        if (view == null) {
            LogUtils.e(TAG, "dest view is null!!!");
            return;
        }

        int current = view.getVisibility();
        if (current != visibility) {
            view.setVisibility(visibility);
        }
    }

}
