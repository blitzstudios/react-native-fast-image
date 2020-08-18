package com.dylanvann.fastimage;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GifDrawableAccessor {
    public static void setIsRunning(GifDrawable gifDrawable, boolean isRunning) {
        try {
            Method method = gifDrawable.getClass().getDeclaredMethod("setIsRunning", boolean.class);
            method.setAccessible(true);
            method.invoke(gifDrawable, isRunning);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void setFrameLoaderIsRunning(GifDrawable gifDrawable, boolean isRunning) {
        Drawable.ConstantState gifState = gifDrawable.getConstantState();
        try {
            Field field = gifState.getClass().getDeclaredField("frameLoader");
            field.setAccessible(true);
            Object gifFrameLoader = field.get(gifState);
            Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader");

            if(isRunning) {
                Method start = gifFrameLoaderClass.getDeclaredMethod("start");
                start.setAccessible(true);
                start.invoke(gifFrameLoader);
            } else {
                Method stop = gifFrameLoaderClass.getDeclaredMethod("stop");
                stop.setAccessible(true);
                stop.invoke(gifFrameLoader);
            }
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}