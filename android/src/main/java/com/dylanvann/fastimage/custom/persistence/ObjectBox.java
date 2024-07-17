package com.dylanvann.fastimage.custom.persistence;

import android.content.Context;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {
        if (boxStore == null) {
            boxStore = MyObjectBox.builder()
                    .androidContext(context.getApplicationContext())
                    .build();
        }
    }

    public static BoxStore get() {
        return boxStore;
    }

    public static Box<EntityEtagCache> getBoxEtagCache() {
        return get().boxFor(EntityEtagCache.class);
    }

    public static EntityEtagCache getEtagCacheEntityByUrl(String url) {
        Query<EntityEtagCache> query = getBoxEtagCache().query()
                                               .equal(EntityEtagCache_.url, url, QueryBuilder.StringOrder.CASE_SENSITIVE)
                                               .build();
        return query.findFirst();
    }

    public static String getEtagByUrl(String url) {
        EntityEtagCache entry = getEtagCacheEntityByUrl(url);
        if (entry != null) {
            return entry.getEtag(); // Ensure getter method for etag field
        }
        return null;
    }

    public static void removeAll() {
        getBoxEtagCache().removeAll();
    }

    public static void putOrUpdateEtag(String url, String etag) {
        EntityEtagCache entry = getEtagCacheEntityByUrl(url);
        if (entry != null) {
            entry.setEtag(etag); // Ensure setter method for etag field
        } else {
            entry = new EntityEtagCache();
            entry.setEtag(etag); // Ensure setter method for etag field
            entry.setUrl(url); // Ensure setter method for url field
        }
        getBoxEtagCache().put(entry);
    }
}