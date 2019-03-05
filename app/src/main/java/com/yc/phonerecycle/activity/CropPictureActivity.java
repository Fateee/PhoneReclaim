package com.yc.phonerecycle.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yc.phonerecycle.R;
import com.yc.phonerecycle.utils.UriTools;
import com.yc.phonerecycle.widget.ClipImageLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yancongxian on 2017/7/12.
 */

public class CropPictureActivity extends Activity {
    Uri uri;
    String mUploadfilePath;
    String mUploadfileName;
    public static final String PIC_URI = "Pic_Uri";
    public static final String UPLOAD_FILE_PATH = "Upload_file_Path";
    public static final String UPLOAD_FILE_NAME = "Upload_file_Name";
    @BindView(R.id.sure)
    TextView btSure;
    @BindView(R.id.cancel)
    TextView btCancel;
    @BindView(R.id.id_clipImageLayout)
    ClipImageLayout mClipImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_picture);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        mUploadfilePath = intent.getStringExtra(UPLOAD_FILE_PATH);
        mUploadfileName = intent.getStringExtra(UPLOAD_FILE_NAME);

        try {
            uri = Uri.parse(intent.getStringExtra(PIC_URI));
            mClipImageLayout.setZoomImageDrawable(scaleBitmap(getRealPathFromURI(uri), 480, 800));
        } catch (Exception e) {
            e.printStackTrace();
        }

        btSure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = mClipImageLayout.clip();
                cacheFile(bitmap);
                setResult(RESULT_OK);
                CropPictureActivity.this.finish();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CropPictureActivity.this.finish();
            }
        });
    }

    /**
     * 由Uri获取真实路径
     */
    @Nullable
    public String getRealPathFromURI(@NonNull Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor == null) {  //证明是拍照，此时contentUri根本没有生成，直接就是真实路径
            return contentUri.getPath();
        } else {   //证明是选择照片,可以由Uri得到真实路径
            String authStr = contentUri.getAuthority();
            if (!TextUtils.isEmpty(authStr) && authStr.equals(getPackageName() + ".fileprovider")) {//拍照 fileprovider共享过来的图片
                res = mUploadfilePath+File.separator+mUploadfileName;
            } else if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }

            //加入未查询成功则通过此方式获取
            if (TextUtils.isEmpty(res)) {
                res = UriTools.getImageAbsolutePath(CropPictureActivity.this, contentUri);
            }
            cursor.close();
        }
        return res;
    }


    /**
     * 图片压缩
     */
    @NonNull
    public Drawable scaleBitmap(String path, int width, int height) {
        if (width <= 0)
            width = 50;
        if (height <= 0)
            height = 50;
        // 加载图片的尺寸而不是图片本身
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        //禁止为bitmap分配内存，返回值也不再是一个Bitmap对象，而是null
        bmpFactoryOptions.inJustDecodeBounds = true;
        //为BitmapFactory.Options的outWidth、outHeight和outMimeType属性赋值
        BitmapFactory.decodeFile(path, bmpFactoryOptions);
        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / width);

        if (heightRatio > 1 && widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }
        bmpFactoryOptions.inJustDecodeBounds = false;
        bmpFactoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeFile(path,
                bmpFactoryOptions);

        BitmapDrawable bd = new BitmapDrawable(getResources(), turnDegreeImg(path, bmp));
        return bd;
    }


    /**
     * 图片旋转角度
     */
    Bitmap turnDegreeImg(String path, @NonNull Bitmap bitmap) {
        // 得到图片的旋转角度
        int degree = getBitmapDegree(path);
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return returnBm;
    }


    /**
     * 获取原始图片的角度（解决三星手机拍照后图片是横着的问题）
     *
     * @param path 图片的绝对路径
     * @return 原始图片的角度
     */
    int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 一个缓存文件，用于上传图片
     */
    void cacheFile(@NonNull Bitmap bitmap) {
        File tempCacheFile = new File(mUploadfilePath, mUploadfileName);

        if (tempCacheFile.exists()) {
            tempCacheFile.delete();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempCacheFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

