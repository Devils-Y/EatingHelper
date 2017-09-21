package com.hy.eatinghelper.quickopen.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class GlideBlurTFUtil extends BitmapTransformation {

     private RenderScript rs;
     float mRadius;

     public GlideBlurTFUtil(Context context, float radius) {
          super(context);
          this.mRadius = radius;
          rs = RenderScript.create(context);
     }

     @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
     @Override
     protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
          Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);

          // Allocate memory for Renderscript to work with
          Allocation input = Allocation.createFromBitmap(
                    rs,
                    blurredBitmap,
                    Allocation.MipmapControl.MIPMAP_FULL,
                    Allocation.USAGE_SHARED
          );
          Allocation output = Allocation.createTyped(rs, input.getType());

          // Load up an instance of the specific script that we want to use.
          ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
          script.setInput(input);

          // Set the blur radius
          script.setRadius(mRadius);

          // Start the ScriptIntrinisicBlur
          script.forEach(output);

          // Copy the output to the blurred bitmap
          output.copyTo(blurredBitmap);

          toTransform.recycle();

          return blurredBitmap;
     }

     @Override
     public String getId() {
          return "blur";
     }
}
