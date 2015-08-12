package com.breadtrio.sdk.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class BitmapUtil {

    public static final String TAG = "BitmapUtil";

    public static final int UNCONSTRAINED = -1;

    public static Options getOptionsForResourceId(Resources res, int id) {
        Options options = new Options();
        options.inJustDecodeBounds = true;// 只描边，不读取数据
        BitmapFactory.decodeResource(res, id, options);
        return options;
    }

    public static Bitmap getBitmapByResource(Resources res, int id, Options options, int screenWidth, int screenHeight) {
        if (options != null) {
            Rect r = getScreenRegion(screenWidth, screenHeight);
            int w = r.width();
            int h = r.height();
            int maxSize = w > h ? w : h;
            int inSimpleSize = computeSampleSize(options, maxSize, w * h);
            options.inSampleSize = inSimpleSize; // 设置缩放比例
            options.inJustDecodeBounds = false;
        }
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeResource(res, id, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
            System.gc();
        }
        return b;
    }

    public static Bitmap getBitmapFromFile(String filePath) {
        return getBitmapFromFile(filePath, 0, 0);
    }

    public static Bitmap getBitmapFromFile(String filePath, int width, int height) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        Options options = new Options();
        options.inJustDecodeBounds = true;// 只描边，不读取数据
        BitmapFactory.decodeFile(filePath, options);

        Bitmap bitmap = null;
        int max = Math.max(width, height);
        try {
            int inSimpleSize = 1;
            if (max != 0) {
                inSimpleSize = computeSampleSize(options, max);
            }
            options.inSampleSize = inSimpleSize; // 设置缩放比例
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Config.RGB_565;
            bitmap = BitmapFactory.decodeFile(file.getPath(), options);
        } catch (OutOfMemoryError e) {
            LogUtils.e(TAG, "getBitmapFromFile OutOfMemoryError :", e);
        } catch (IllegalArgumentException e) {
            LogUtils.e(TAG, "getBitmapFromFile IllegalArgumentException :", e);
        }

        return bitmap;
    }

    /**
     * 计算缩放比例
     */
    private static int computeSampleSize(Options options, int target) {
        int w = options.outWidth;
        int h = options.outHeight;

        int length = Math.max(w, h);
        int candidate = length / target;

        if (candidate == 0)
            return 1;

        if (candidate > 1) {
            if ((length > target) && (length / candidate) < target)
                candidate -= 1;
        }

        return candidate;
    }

    public static Options getOptionsForByteArray(byte[] data) {
        Options options = new Options();
        options.inJustDecodeBounds = true;// 只描边，不读取数据
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return options;
    }

    public static Bitmap getBitmapByByteArray(byte[] data, Options options, int screenWidth, int screenHeight) {
        if (options != null) {
            Rect r = getScreenRegion(screenWidth, screenHeight);
            int w = r.width();
            int h = r.height();
            int maxSize = w > h ? w : h;
            int inSimpleSize = computeSampleSize(options, maxSize, w * h);
            options.inSampleSize = inSimpleSize; // 设置缩放比例
            options.inJustDecodeBounds = false;
        }
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
            System.gc();
        }
        return b;
    }

    /*
     * 获得设置信息
     */
    public static Options getOptionsForPath(String path) {
        Options options = new Options();
        options.inJustDecodeBounds = true;// 只描边，不读取数据
        BitmapFactory.decodeFile(path, options);
        return options;
    }

    /**
     * 获得图像
     *
     * @param path
     * @param options
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap getBitmapByPath(String path, Options options, int screenWidth, int screenHeight) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            return null;
        }
        if (options != null) {
            Rect r = getScreenRegion(screenWidth, screenHeight);
            int w = r.width();
            int h = r.height();
            int maxSize = w > h ? w : h;
            int inSimpleSize = computeSampleSize(options, maxSize, w * h);
            options.inSampleSize = inSimpleSize; // 设置缩放比例
            options.inJustDecodeBounds = false;
        }
        Bitmap b = null;
        try {
            b = BitmapFactory.decodeStream(in, null, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            b = null;
            System.gc();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    private static Rect getScreenRegion(int width, int height) {
        return new Rect(0, 0, width, height);
    }

    /**
     * 获取需要进行缩放的比例，即options.inSampleSize
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
                Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == UNCONSTRAINED) && (minSideLength == UNCONSTRAINED)) {
            return 1;
        } else if (minSideLength == UNCONSTRAINED) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
        }
        return bitmap;
    }

    /**
     * 保存Bitmap到本地文件
     *
     * @param bitmap
     * @param path
     */
    public static boolean saveBitmap(Bitmap bitmap, String path) {
        return saveBitmap(bitmap, path, 100);
    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param path
     * @param quality
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String path, int quality) {
        // Bitmap图片保存
        if (bitmap == null || path == null || path.equals("")) {
            return false;
        }
        BufferedOutputStream ostream = null;
        try {
            File file = FileUtils.makeDIRAndCreateFile(new File(path));
            ostream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(CompressFormat.JPEG, quality, ostream);
        } catch (FileNotFoundException e) {
            LogUtils.e(e.toString());
            return false;
        } catch (Exception e) {
            LogUtils.e(e.toString());
            return false;
        } finally {
            try {
                if (ostream != null) {
                    ostream.flush();
                    ostream.close();
                }
            } catch (IOException e) {
                LogUtils.e(e.toString());
                return false;
            }
        }
        return true;
    }

    /**
     * 获取中间的方形图片，超出时截取两边
     *
     * @param srcBitmap
     * @param dstWidth
     * @param dstHeight
     * @return
     * @throws OutOfMemoryError
     */
    public static Bitmap getCenterInBitmap(Bitmap srcBitmap, int dstWidth, int dstHeight) {
        Bitmap finalBm = null;
        try {

            if (srcBitmap == null || dstWidth <= 0 || dstHeight <= 0) {
                return null;
            }

            int srcWidth = srcBitmap.getWidth();
            int srcHeight = srcBitmap.getHeight();

            // 对于没有变化的图片不需要裁切缩放
            if (srcWidth == dstWidth && srcHeight == dstHeight) {
                return srcBitmap;
            }

            // 先判断比例相等的
            if (dstWidth * srcHeight == dstHeight * srcWidth) {
                if (dstWidth < srcWidth) {
                    // 仅往小尺寸缩放
                    Bitmap bm = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, true);
                    return bm;
                } else {
                    return srcBitmap;
                }
            }

            int tmpWidth = 0;
            int tmpHeight = 0;

            if (dstWidth * srcHeight > dstHeight * srcWidth) {
                // 截取上下两部分
                if (dstWidth < srcWidth) {
                    // 先对原图进行等比缩放
                    tmpWidth = dstWidth;
                    tmpHeight = tmpWidth * dstHeight / dstWidth;
                }
            } else {
                // 截取左右两部分
                if (dstHeight < srcHeight) {
                    // 先对原图进行等比缩放
                    tmpHeight = dstHeight;
                    tmpWidth = dstWidth * tmpHeight / dstHeight;
                }
            }

            Bitmap scaleBitmap = null;
            if (tmpWidth != 0 && tmpHeight != 0) {
                scaleBitmap = Bitmap.createScaledBitmap(srcBitmap, tmpWidth, tmpHeight, true);
            } else {
                scaleBitmap = srcBitmap;
            }

            if (scaleBitmap == null) {
                return null;
            }

            int x = 0;
            int y = 0;

            int sw = scaleBitmap.getWidth();
            int sh = scaleBitmap.getHeight();
            int imgWidth = 0;
            int imgHeight = 0;
            if (sw * dstHeight < sh * dstWidth) {
                imgWidth = sw;
                imgHeight = sw * dstHeight / dstWidth;
                // 切除上下部分
                x = 0;
                y = (sh - imgHeight) / 2;
            } else {
                imgHeight = sh;
                imgWidth = sh * dstWidth / dstHeight;
                y = 0;
                x = (sw - imgWidth) / 2;
            }

            if (x == 0 && y == 0) {
                // 没有缩放的图片
                return scaleBitmap;
            }

            finalBm = Bitmap.createBitmap(scaleBitmap, x, y, imgWidth, imgHeight);
        } catch (OutOfMemoryError e) {
            LogUtils.e(TAG, "getCenterInBitmap OutOfMemoryError :", e);
        }
        return finalBm;
    }

    public static Bitmap getRoundCornerBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = null;

        try {
            output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
            final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
            final RectF rectF = new RectF(dst);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, src, dst, paint);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            return null;
        } catch (Throwable t) {
            t.printStackTrace();
            System.gc();
            return null;
        }
        return output;

    }

}
