package com.HardGame.Graphics;

/**
 * Created by 逢双 on 14-2-19.
 *
 * 原文作者： macro_xiao
 * 原文地址： http://my.eoe.cn/macro_xiao/archive/14886.html
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.ThumbnailUtils;
import android.util.Log;

/**
 * 图片处理工具类
 *
 * @author macro
 *
 */
public final class ImageUtil {
    static int posX = 2;
    static int posY = 5;
    private static ImageUtil instance;

    public ImageUtil() {

    }

    /**
     * 单例模式
     *
     * @return
     */
    public static ImageUtil getInstance() {
        if (null == instance) {
            instance = new ImageUtil();
        }
        return instance;
    }

    /**
     * 放大缩放图片
     *
     * @param bitmap
     *            要处理的图片
     * @param height
     *            最后的高度
     * @param width
     *            最后的宽度
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, Float height,
                                            Float width) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleW = width / (float) w;
        float scaleH = height / (float) h;
        matrix.postScale(scaleW, scaleH);
        Bitmap returnBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
                true);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(returnBitmap);
        return returnBitmap;
    }

    /**
     * 将drawable转换成bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        /*
         * if(drawable instanceof BitmapDrawable){ return
         * ((BitmapDrawable)drawable).getBitmap(); }
         */
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 圆角图片
     *
     * @param bitmap
     *            要处理的图片
     * @param corner
     *            角度
     * @return
     */
    public static Bitmap getRoundCornerBitmap(Bitmap bitmap, float corner) {
        final int height = bitmap.getHeight();
        final int width = bitmap.getWidth();
        Bitmap returnBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnBitmap);
        final int color = 0Xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, corner, corner, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return returnBitmap;
    }

    /**
     * 获得带倒影的图片方法
     *
     * @return Bitmap
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap originalBitmap,
                                                         int part) {
        // 图片与倒影之间的距离
        final int snap = 0;
        // 图片原来的大小
        int originalWidth = originalBitmap.getWidth();
        int originalHeight = originalBitmap.getHeight();
        // 反转图片
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        // 创建翻转后的图片的三分之一高度
        Bitmap halfBitmap = Bitmap.createBitmap(originalBitmap, 0,
                originalHeight / part, originalWidth, originalHeight / part,
                matrix, false);
        // 把原图 间隙 倒影 放在一张宽是原来宽度高是原来高度的1.5倍的图片
        Bitmap returnBitmap = Bitmap.createBitmap(originalWidth, originalHeight
                + originalHeight / part + snap, Config.ARGB_8888);
        // 构造一个新图片一样大小的画布 这样可以在上边画倒影
        Canvas canvas = new Canvas(returnBitmap);
        // int size = halfBitmap.getWidth() *halfBitmap.getHeight()*8;
        // 依次向上边画出原图 间隙 倒影
        canvas.drawBitmap(halfBitmap, 0, originalHeight + snap, null);
        canvas.drawBitmap(originalBitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawRect(0, originalHeight, originalWidth,
                originalHeight + snap, paint);
        canvas.drawBitmap(halfBitmap, 0, originalHeight + snap, null);

        // 实现倒影的效果
        LinearGradient lGradient = new LinearGradient(0, originalHeight, 0,
                returnBitmap.getHeight(), 0x70ffffff, 0x00ffffff,
                TileMode.MIRROR);
        paint.setShader(lGradient);
        paint.setAntiAlias(false);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, originalHeight, originalWidth,
                returnBitmap.getHeight(), paint);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(Bitmap.createBitmap(
                returnBitmap, 0, 0, originalWidth, originalHeight
                + originalHeight / 4));
        bitmapDrawable.setAntiAlias(true);
        return bitmapDrawable.getBitmap();
    }

    /**
     * 光晕效果
     *
     * @param bmp
     * @param x
     *            光晕中心点在bmp中的x坐标
     * @param y
     *            光晕中心点在bmp中的y坐标
     * @param r
     *            光晕的半径
     * @return
     */
    public static Bitmap haloEffect(Bitmap bmp, int x, int y, float r) {
        // 高斯矩阵
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 18; // 值越小图片会越亮，越大则越暗
        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                int distance = (int) (Math.pow(k - x, 2) + Math.pow(i - y, 2));
                // 不是中心区域的点做模糊处理
                if (distance > r * r) {
                    for (int m = -1; m <= 1; m++) {
                        for (int n = -1; n <= 1; n++) {
                            pixColor = pixels[(i + m) * width + k + n];
                            pixR = Color.red(pixColor);
                            pixG = Color.green(pixColor);
                            pixB = Color.blue(pixColor);
                            newR = newR + (int) (pixR * gauss[idx]);
                            newG = newG + (int) (pixG * gauss[idx]);
                            newB = newB + (int) (pixB * gauss[idx]);
                            idx++;
                        }
                    }

                    newR /= delta;
                    newG /= delta;
                    newB /= delta;
                    newR = Math.min(255, Math.max(0, newR));
                    newG = Math.min(255, Math.max(0, newG));
                    newB = Math.min(255, Math.max(0, newB));
                    pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                    newR = 0;
                    newG = 0;
                    newB = 0;
                }
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 添加边框
     *
     * @param bmp
     *            图片
     * @framePicture 边框图片
     * @return Bitmap
     */
    public static Bitmap addFrame(Bitmap bmp, Bitmap framePicture) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        int frameWidth = framePicture.getWidth();
        int frameHeight = framePicture.getHeight();
        float scaleX = width * 1F / frameWidth;
        float scaleY = height * 1F / frameHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        Bitmap frameCopy = Bitmap.createBitmap(framePicture, 0, 0, frameWidth,
                frameHeight, matrix, true);

        int pixColor = 0;
        int layColor = 0;
        int newColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixA = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int newA = 0;
        int layR = 0;
        int layG = 0;
        int layB = 0;
        int layA = 0;
        float alpha = 0.3F;
        float alphaR = 0F;
        float alphaG = 0F;
        float alphaB = 0F;

        for (int i = 0; i < width; i++) {
            for (int k = 0; k < height; k++) {
                pixColor = bmp.getPixel(i, k);
                layColor = frameCopy.getPixel(i, k);
                // 获取原图片的RGBA值
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                pixA = Color.alpha(pixColor);
                // 获取边框图片的RGBA值
                layR = Color.red(layColor);
                layG = Color.green(layColor);
                layB = Color.blue(layColor);
                layA = Color.alpha(layColor);
                // 颜色与纯黑色相近的点
                if (layR < 20 && layG < 20 && layB < 20) {
                    alpha = 1F;
                } else {
                    alpha = 0.3F;
                }
                alphaR = alpha;
                alphaG = alpha;
                alphaB = alpha;
                // 两种颜色叠加
                newR = (int) (pixR * alphaR + layR * (1 - alphaR));
                newG = (int) (pixG * alphaG + layG * (1 - alphaG));
                newB = (int) (pixB * alphaB + layB * (1 - alphaB));
                layA = (int) (pixA * alpha + layA * (1 - alpha));
                // 值在0~255之间
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                newA = Math.min(255, Math.max(0, layA));
                newColor = Color.argb(newA, newR, newG, newB);
                bitmap.setPixel(i, k, newColor);
            }
        }
        return bitmap;
    }

    /**
     * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处： 1.
     * 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。 2.
     * 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath
     *            图像的路径
     * @param width
     *            指定输出图像的宽度
     * @param height
     *            指定输出图像的高度
     * @return 生成的缩略图
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        Bitmap returnBitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        returnBitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        returnBitmap = ThumbnailUtils.extractThumbnail(returnBitmap, width,
                height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return returnBitmap;
    }

    public static Bitmap decodeFile(String imagePath, int[] s) {
        File f = new File(imagePath);
        int ws = 640;
        int hs = 640;
        ws = s[0];
        hs = s[1];

        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            // final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth;
            int height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
                if (width_tmp / 2 < ws || height_tmp / 2 < hs)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            Log.i("zwq", "__width_tmp__" + width_tmp + "__height_tmp__"
                    + height_tmp + "__ws__" + ws + "__hs__" + hs + "__scale__"
                    + scale);
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = 2;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath
     *            视频的路径
     * @param width
     *            指定输出视频缩略图的宽度
     * @param height
     *            指定输出视频缩略图的高度度
     * @param kind
     *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                    int kind) {
        // 参数例子：videoPath, 60, 60,MediaStore.Images.Thumbnails.MICRO_KIND
        // 获取视频的缩略图
        Bitmap returnBitmap = ThumbnailUtils.createVideoThumbnail(videoPath,
                kind);
        returnBitmap = ThumbnailUtils.extractThumbnail(returnBitmap, width,
                height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return returnBitmap;
    }

    @SuppressWarnings("unused")
    private static Bitmap drawImageDropShadow(Bitmap originalBitmap) {
        float radius = 8f;
        float readiusHalf = radius / 2;
        BlurMaskFilter filter = new BlurMaskFilter(radius,
                BlurMaskFilter.Blur.NORMAL);
        Paint shadowPaint = new Paint();
        shadowPaint.setAlpha(10);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setColor(Color.WHITE);
        shadowPaint.setMaskFilter(filter);
        int[] offsetXY = new int[2];
        Bitmap shadowBitmap = originalBitmap
                .extractAlpha(shadowPaint, offsetXY);
        Bitmap returnBitmap = shadowBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(returnBitmap);
        c.drawBitmap(originalBitmap, offsetXY[0], offsetXY[1], null);
        return returnBitmap;
    }

    public static void setCornerRadii(GradientDrawable drawable, float r0, float r1,
                               float r2, float r3) {
        drawable.setCornerRadii(new float[] { r0, r0, r1, r1, r2, r2, r3, r3 });
    }

    /**
     * 为图片创建特效,产生倒影等
     *
     * @param bitmap
     * @return
     */
    public static Bitmap BitmapElefct(Bitmap originalImage, int part) {
        // The gap we want between the reflection and the original image
        final int reflectionGap = 0;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        // This will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        // Create a Bitmap with the flip matrix applied to it.
        // We only want the bottom half of the image
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height
                / part, width, height / part, matrix, false);
        // Create a new bitmap with same width but taller to fit
        // reflection
        Bitmap returnBitmap = Bitmap.createBitmap(width, (height + height
                / part), Config.ARGB_8888);
        // Create a new Canvas with the bitmap that’s big enough for
        // the image plus gap plus reflection
        Canvas canvas = new Canvas(returnBitmap);
        // Draw in the original image
        canvas.drawBitmap(originalImage, 0, 0, null);
        // Draw in the gap
        Paint deafaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
        // Draw in the reflection
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        // Create a shader that is a linear gradient that covers the
        // reflection
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, returnBitmap.getHeight()
                + reflectionGap, 0x20ffffff, 0x00ffffff, TileMode.CLAMP);
        // Set the paint to use this shader (linear gradient)
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, returnBitmap.getHeight()
                + reflectionGap, paint);
        // 解决图片的锯齿现象
        BitmapDrawable bd = new BitmapDrawable(Bitmap.createBitmap(
                returnBitmap, 0, 0, width, height + height / 5));
        bd.setAntiAlias(true);
        return bd.getBitmap();
    }

    /***
     * 获取图片信息
     *
     * @param dst
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);
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
    }

    /**
     * 计算缩放比例
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

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

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

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

    /**
     * 创建倒影
     *
     * @param originalImage
     * @param part
     * @return
     */
    public static Bitmap createReflection(Bitmap originalImage, int part) {
        int reflectionGap = 2;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1); // 图片矩阵变换（从低部向顶部的倒影）
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false); // 截取原图下半部分
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888); // 创建倒影图片（高度为原图3/2）

        Canvas canvas = new Canvas(bitmapWithReflection); // 绘制倒影图（原图 + 间距 + 倒影）
        canvas.drawBitmap(originalImage, 0, 0, null); // 绘制原图
        Paint paint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, paint); // 绘制原图与倒影的间距
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null); // 绘制倒影图

        paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader); // 线性渐变效果
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); // 倒影遮罩效果
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint); // 绘制倒影的阴影效果
        // 解决图片的锯齿现象
        BitmapDrawable bitmapDrawable = new BitmapDrawable(Bitmap.createBitmap(
                bitmapWithReflection, 0, 0, width, height + height / part));
        bitmapDrawable.setAntiAlias(true);
        return bitmapDrawable.getBitmap();
    }

    /**
     * 以最省内存的方式获取图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(int resId, Context context) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}