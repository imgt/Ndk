package com.lg.ndk;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lg.ndk.util.CompressImageUtil;
import com.lg.ndk.util.NativeUtil;
import com.lg.ndk.util.Util;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;

public class CaseActivity extends AppCompatActivity {
    public String sImagePath = null;

    private static final String IMAGE_FILE_NAME = "image.png";
    private static final String IMAGE_COMPRESS_NAME = "compress.png";
    private static final int REQUESTCODE_PICK = 0; // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1; // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2; // 图片裁切标记
    private File mOriginal;
    private File mCompress;
    private ImageView mCompressImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sImagePath = Util.getCacheDir(this,
                "image").getAbsolutePath();
        setContentView(R.layout.activity_auto);
        mCompressImage = (ImageView) findViewById(R.id.compress_image);
    }

    public void camera(View view) {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(sImagePath);
        // 下面这句指定调用相机拍照后的照片存储的路径
        Uri fileUri = FileProvider7.getUriForFile(this, new File(file, IMAGE_FILE_NAME));
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                fileUri);
        startActivityForResult(takeIntent, REQUESTCODE_TAKE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_TAKE:// 调用相机拍照
                if(resultCode == RESULT_OK)
                    mOriginal = new File(sImagePath, IMAGE_FILE_NAME);
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void compress(View view){
        if (mOriginal == null || !mOriginal.exists()) {
            Toast.makeText(this,"请先拍照，取图片", Toast.LENGTH_SHORT).show();
            return;
        }

        mCompress = new File(sImagePath, IMAGE_COMPRESS_NAME);
        int i = CompressImageUtil.compressBitmap(CompressImageUtil.decodeFile(mOriginal.getAbsolutePath()),
                CompressImageUtil.calculationQuality(mOriginal.getAbsolutePath()),
                mCompress.getAbsolutePath(), true, false);
      // NativeUtil.compressBitmap(mOriginal.getAbsolutePath(),  mCompress.getAbsolutePath());

        if (i == 1) {
            Toast.makeText(this,"压缩成功", Toast.LENGTH_SHORT).show();
            mCompressImage.setImageBitmap(BitmapFactory.decodeFile(mCompress.getAbsolutePath()));
        }else{
            Toast.makeText(this,"压缩失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void showOriginal(View view){
        if (mOriginal == null || !mOriginal.exists()) {
            Toast.makeText(this,"图片不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,mOriginal.getAbsolutePath(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,PhotoViewActivity.class);
        intent.putExtra("path",mOriginal.getAbsolutePath());
        startActivity(intent);
    }

    public void showCompress(View view){
        if (mCompress == null || !mCompress.exists()) {
            Toast.makeText(this,"图片不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,mCompress.getAbsolutePath(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,PhotoViewActivity.class);
        intent.putExtra("path",mCompress.getAbsolutePath());
        startActivity(intent);
    }
}
