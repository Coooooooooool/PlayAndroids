package com.alex.playandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2018/6/14.
 */

public class Article implements Parcelable {


    /**
     * apkLink :
     * author : OCNYang
     * chapterId : 323
     * chapterName : 动画
     * collect : false
     * courseId : 13
     * desc :  View Animation ，Drawable Animation ，Property Animation ，Ripple Effect / Touch Feedback ，Reveal Effect ，Transition Animation ，Animate View State Changes ，AnimatedVectorDrawable。
     * envelopePic : http://www.wanandroid.com/blogimgs/e106cb44-572c-4f9f-b9ac-b71a889db1c3.png
     * fresh : false
     * id : 2795
     * link : http://www.wanandroid.com/blog/show/2103
     * niceDate : 2018-04-07
     * origin :
     * projectLink : https://github.com/OCNYang/Android-Animation-Set
     * publishTime : 1523109410000
     * superChapterId : 294
     * superChapterName : 开源项目主Tab
     * tags : [{"name":"项目","url":"/project/list/1?cid=323"}]
     * title : Android 动画详尽教程
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */

    private String apkLink;
    private String author;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String envelopePic;
    private boolean fresh;
    private int id;
    private String link;
    private String niceDate;
    private String origin;
    private String projectLink;
    private long publishTime;
    private int superChapterId;
    private String superChapterName;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;
    private List<TagsBean> tags;

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean {
        /**
         * name : 项目
         * url : /project/list/1?cid=323
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.apkLink);
        dest.writeString(this.author);
        dest.writeInt(this.chapterId);
        dest.writeString(this.chapterName);
        dest.writeByte(this.collect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.courseId);
        dest.writeString(this.desc);
        dest.writeString(this.envelopePic);
        dest.writeByte(this.fresh ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeString(this.link);
        dest.writeString(this.niceDate);
        dest.writeString(this.origin);
        dest.writeString(this.projectLink);
        dest.writeLong(this.publishTime);
        dest.writeInt(this.superChapterId);
        dest.writeString(this.superChapterName);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeInt(this.userId);
        dest.writeInt(this.visible);
        dest.writeInt(this.zan);
        dest.writeList(this.tags);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.apkLink = in.readString();
        this.author = in.readString();
        this.chapterId = in.readInt();
        this.chapterName = in.readString();
        this.collect = in.readByte() != 0;
        this.courseId = in.readInt();
        this.desc = in.readString();
        this.envelopePic = in.readString();
        this.fresh = in.readByte() != 0;
        this.id = in.readInt();
        this.link = in.readString();
        this.niceDate = in.readString();
        this.origin = in.readString();
        this.projectLink = in.readString();
        this.publishTime = in.readLong();
        this.superChapterId = in.readInt();
        this.superChapterName = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
        this.userId = in.readInt();
        this.visible = in.readInt();
        this.zan = in.readInt();
        this.tags = new ArrayList<TagsBean>();
        in.readList(this.tags, TagsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
