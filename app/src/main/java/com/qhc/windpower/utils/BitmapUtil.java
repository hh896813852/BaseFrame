package com.qhc.windpower.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author huangyin
 *         Bitmap相关的工具类
 */
public class BitmapUtil {
    public enum Shape {
        CIRCLE, TRIANGLE
    }

    /**
     * 从大图标上截取小图标
     *
     * @param source       原图片
     * @param sourceRow    原图片包含小图标行数
     * @param sourceColumn 原图片包含小图标列数
     * @param index        需要截取第几个小图标（从一开始，往右数）
     * @return
     */
    public static Bitmap divideBitmapByIndex(Bitmap source, int sourceRow, int sourceColumn, int index) {
        // 得到每张小图片的宽与高
        int perWidth = source.getWidth() / sourceColumn;
        int perHeight = source.getHeight() / sourceRow;
        // 得到坐标
        int x = ((index - 1) % sourceColumn) * perWidth;
        int y = ((index - 1) / sourceColumn) * perHeight;

        return Bitmap.createBitmap(source, x, y, perWidth, perHeight);
    }

    /**
     * @param sourceImg 要传入的图片
     * @param number    设置的透明值 1~100之间的
     * @return 返回设置完成的图片
     */
    public static Bitmap setAlpha(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Config.ARGB_8888);

        return sourceImg;
    }

    public static void drawCircleToBitmap(Bitmap source, int x, int y, int r) {
        Canvas canvas = new Canvas(source);
        Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        /* 设置paint的颜色 */
        paint.setColor(Color.RED);
        /* 设置paint的　style　为STROKE：空心 */
        paint.setStyle(Paint.Style.STROKE);
		/* 设置paint的外框宽度 */
        paint.setStrokeWidth(3);
		/* 画一个空心圆形 */
        Log.v("x", x + "");
        Log.v("y", y + "");
        Log.v("r", r + "");
        canvas.drawCircle(x, y, r, paint);
    }

    /**
     * 图像转化成文件1
     */
    public static String bitmapToFile(Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "upload_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            OutputStream stream = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            LogUtil.e("BitmapUtil", e.getMessage());
        }
        return file.getPath();
    }

    /**
     * 图像转化成文件2
     */
    public static void bitmapToFile(Bitmap bmp, String filePath, CompressFormat format) {
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filePath);
            bmp.compress(format, 100, stream);
        } catch (Exception e) {
            LogUtil.e("bitmapToFile", e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    public static Bitmap fileToBitmap(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public static Bitmap fileToBitmap(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
//				opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
                opts.inSampleSize = calculateInSampleSize(opts, width, height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
//		final BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		BitmapFactory.decodeFile(dst.getPath(), options);
//
//		// Calculate inSampleSize
//		options.inSampleSize = calculateInSampleSize(options, width, height);
//
//		// Decode bitmap with inSampleSize set
//		options.inJustDecodeBounds = false;
//
//		return BitmapFactory.decodeFile(dst.getPath(), options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
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

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        @SuppressWarnings("deprecation")
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        return (Drawable) bitmapDrawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] bitmapToBytes(Bitmap bitmap, CompressFormat format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap bytesToBimap(byte[] b) {
        if (b.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * 图片缩放
     */
    public static Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        return Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
    }

    /**
     * 将一个view装换成bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
}