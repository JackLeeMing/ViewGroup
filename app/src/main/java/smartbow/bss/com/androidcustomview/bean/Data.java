package smartbow.bss.com.androidcustomview.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 作者: lijiakui on 16/6/30 15:22.
 * 联系:JackLeeMing@outlook.com  AndroidCustomView
 */
public class Data extends MultiItemEntity {
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_MIX = 3;

    public String name;
    public int image;

    public Data(String name, int image,int type) {
        this.name = name;
        this.image = image;
        this.itemType = type;
    }
}