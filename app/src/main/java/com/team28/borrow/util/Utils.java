package com.team28.borrow.util;

import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class Utils {
    public static String changeMyanmarNumber(String english_number, List<String> myanmarNumber) {
        String itemPrice = "";

        for (int i = 0; i <= 9; i++) {
            if (english_number.contains(String.valueOf(i))) {

                itemPrice = english_number.replaceAll(String.valueOf(i), myanmarNumber.get(i));

            }

        }
        return itemPrice;
    }


    public static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static boolean isPostOwner(String user_id) {
        return user_id.equals(FirebaseAuth.getInstance().getUid());
    }


}
