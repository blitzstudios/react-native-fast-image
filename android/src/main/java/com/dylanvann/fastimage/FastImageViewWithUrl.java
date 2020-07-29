package com.dylanvann.fastimage;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.gif.GifDrawable;

class FastImageViewWithUrl extends AppCompatImageView {
    public GlideUrl glideUrl;
    public int loopCount = GifDrawable.LOOP_INTRINSIC;

    public FastImageViewWithUrl(Context context) {
        super(context);
    }

    public void setLoopCount(int loop) {
        loopCount = loop;
    }
}
