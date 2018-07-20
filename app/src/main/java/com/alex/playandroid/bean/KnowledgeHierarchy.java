package com.alex.playandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2018/6/15.
 */

public class KnowledgeHierarchy implements Parcelable {


    /**
     * courseId : 13
     * id : 60
     * name : Android Studio相关
     * order : 1000
     * parentChapterId : 150
     * visible : 1
     */

    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;
    private List<KnowledgeHierarchy> children;

    public List<KnowledgeHierarchy> getChildren() {
        return children;
    }

    public void setChildren(List<KnowledgeHierarchy> children) {
        this.children = children;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.courseId);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.order);
        dest.writeInt(this.parentChapterId);
        dest.writeInt(this.visible);
        dest.writeList(this.children);
    }

    public KnowledgeHierarchy() {
    }

    protected KnowledgeHierarchy(Parcel in) {
        this.courseId = in.readInt();
        this.id = in.readInt();
        this.name = in.readString();
        this.order = in.readInt();
        this.parentChapterId = in.readInt();
        this.visible = in.readInt();
        this.children = new ArrayList<KnowledgeHierarchy>();
        in.readList(this.children, KnowledgeHierarchy.class.getClassLoader());
    }

    public static final Parcelable.Creator<KnowledgeHierarchy> CREATOR = new Parcelable.Creator<KnowledgeHierarchy>() {
        @Override
        public KnowledgeHierarchy createFromParcel(Parcel source) {
            return new KnowledgeHierarchy(source);
        }

        @Override
        public KnowledgeHierarchy[] newArray(int size) {
            return new KnowledgeHierarchy[size];
        }
    };
}
