package com.dylanvann.fastimage.custom.persistence;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class EntityEtagCache {
    @Id
    public long id;
    public String url;
    public String etag;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
