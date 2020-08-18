package com.dylanvann.fastimage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.gif.GifDrawable;

class FastImageViewWithUrl extends ImageView {
    public GlideUrl glideUrl;
    private int _loopCount = GifDrawable.LOOP_INTRINSIC;
    private AnimationCallback _onAnimationComplete;

    // Lifecycle Methods
    public FastImageViewWithUrl(Context context) {
        super(context);
    }

    public void registerAnimationCallback(AnimationCallback animationCallback) {
        _onAnimationComplete = animationCallback;
    }

    public void clearAnimationCallbacks() {
        Drawable resource = this.getDrawable();

        if (resource instanceof GifDrawable) {
            GifDrawable drawable = (GifDrawable)resource;
            drawable.clearAnimationCallbacks();
        }
    }

    // Public API
    public void setLoopCount(int loop) {
        if (_loopCount == loop) {
            return;
        }
        
        _loopCount = loop;

        // If we have an active drawable, make sure to apply updates directly.
        // Otherwise, we defer setting loopCount until our resource is set.
        Drawable resource = this.getDrawable();
        if (resource instanceof GifDrawable) {
            GifDrawable drawable = (GifDrawable)resource;
            _applyLoopCount(drawable, _loopCount);
        }
    }

    public boolean shouldCustomLoopCount() {
        return _loopCount != GifDrawable.LOOP_INTRINSIC;
    }

    // Callback when our resource is set in FastImageViewTarget.
    public void onSetResource(GifDrawable drawable) {
        drawable.clearAnimationCallbacks();
        drawable.registerAnimationCallback(_onAnimationComplete);
        _applyLoopCount(drawable, _loopCount);
    }

    private void _applyLoopCount(GifDrawable drawable, int loopCount) {
        GifDrawableAccessor.setIsRunning(drawable, false);
        GifDrawableAccessor.setFrameLoaderIsRunning(drawable, false);

        drawable.setLoopCount(loopCount);
        drawable.stop();
        drawable.startFromFirstFrame();

        GifDrawableAccessor.setFrameLoaderIsRunning(drawable, true);
        GifDrawableAccessor.setIsRunning(drawable, true);
    }
}
