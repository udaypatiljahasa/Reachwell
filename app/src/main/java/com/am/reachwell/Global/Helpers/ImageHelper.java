package com.am.reachwell.Global.Helpers;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Anand Davis on 01-02-2016.
 */
public class ImageHelper {

    private static ImageHelper imageHelper = new ImageHelper();
    private File MediaFile;
    private File MediaThumbnailFile;
    private ByteArrayOutputStream outStream;
    private byte[] mediaByteArray;
    private byte[] mediaThumbnailByteArray;
    private String mediaBase64;
    private String mediaThumbnailBase64;
    private Bitmap mediaThumbnailBitmap;
    private Bitmap mediaBitmap;
    private Bitmap resetBitmap;

    public void setResetBitmap(Bitmap bitmap) {

        this.resetBitmap = bitmap;
    }

    public Bitmap getResetBitmap() {
        return this.resetBitmap;
    }
    //private Bitmap mediaBitmap; not required. for future use.

    /* A private Constructor prevents any other
   * class from instantiating.
   */
    private ImageHelper() {
    }


    /* Static 'instance' method */
    public static ImageHelper getInstance() {
        return imageHelper;
    }

    // scale down image eg: Bitmap scaledBitmap = scaleDown(realImage, 960 , true);
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        if (realImage.getWidth() > maxImageSize) {
            float ratio = Math.min(
                    (float) maxImageSize / realImage.getWidth(),
                    (float) maxImageSize / realImage.getHeight());
            int width = Math.round((float) ratio * realImage.getWidth());
            int height = Math.round((float) ratio * realImage.getHeight());

            Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                    height, filter);
            return newBitmap;
        } else {
            return realImage;
        }
    }

    public String getMediaBase64() {
        return this.mediaBase64;
    }

    public String getMediaThumbnailBase64() {
        return this.mediaThumbnailBase64;
    }

    public Bitmap getMediaThumbnailBitmap() {
        return this.mediaThumbnailBitmap;
    }

    public Bitmap getMediaBitmap() {
        return this.mediaBitmap;
    }

    public File getMediaFile() {
        return this.MediaFile;
    }

    public void setMediaFile(File url) {
        this.MediaFile = url;
    }

    public File getMediaThumbnailFile() {
        return this.MediaThumbnailFile;
    }

    public void setMediaThumbnailFile(File url) {
        this.MediaThumbnailFile = url;
    }

    public void saveMediaBase64(Bitmap bitmap) {
        try {
            this.mediaBitmap = bitmap;
            outStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outStream);
            outStream.flush();
            outStream.close();
            this.mediaByteArray = outStream.toByteArray();
            this.mediaBase64 = Base64.encodeToString(mediaByteArray, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMediaThumbnailBase64(Bitmap bitmap) {
        try {
            this.mediaThumbnailBitmap = bitmap;
            outStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outStream);
            outStream.flush();
            outStream.close();
            this.mediaThumbnailByteArray = outStream.toByteArray();
            this.mediaThumbnailBase64 = Base64.encodeToString(mediaThumbnailByteArray, Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMediaBitmap(Bitmap mediaBitmap) {
        this.mediaBitmap = mediaBitmap;
    }

    public void setMediaThumbnailBitmap(Bitmap mediaThumbnailBitmap) {
        this.mediaThumbnailBitmap = mediaThumbnailBitmap;
    }
}
