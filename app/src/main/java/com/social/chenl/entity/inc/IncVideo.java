package com.social.chenl.entity.inc;

import java.io.Serializable;
import java.util.List;

/**
 * author : happyc
 * e-mail : bafs.jy@live.com
 * time   : 2019/04/21
 * desc   :
 * version: 1.0
 */
public class IncVideo implements Serializable {
    /**
     * area : 大陆
     * note : 更新至48
     * last : 2019-04-20 10:20:54
     * year : 2018
     * director : 沈乐平
     * pic : https://pic.haku77.com/upload/vod/2018-12-25/15457470670.jpg
     * source : [{"flag":"hkzy","episode":[{"title":"第01集","url":"https://pan.jiningwanjun.com/v/mXi9aNbF"},{"title":"第02集","url":"https://pan.jiningwanjun.com/v/uJjWbviq"},{"title":"第03集","url":"https://pan.jiningwanjun.com/v/1DvCgivq"},{"title":"第04集","url":"https://pan.jiningwanjun.com/v/Bzk0kgIc"},{"title":"第05集","url":"https://pan.jiningwanjun.com/v/v2absT3n"},{"title":"第06集","url":"https://pan.jiningwanjun.com/v/K5uXhTel"},{"title":"第07集","url":"https://pan.jiningwanjun.com/v/MLsFlq5x"},{"title":"第08集","url":"https://pan.jiningwanjun.com/v/jz0pl03w"},{"title":"第09集","url":"https://pan.jiningwanjun.com/v/YTMpxfVL"},{"title":"第10集","url":"https://pan.jiningwanjun.com/v/VuBWnbW4"},{"title":"第11集","url":"https://pan.jiningwanjun.com/v/BuDPBhgl"},{"title":"第12集","url":"https://pan.jiningwanjun.com/v/BxvHNfSH"},{"title":"第13集","url":"https://pan.jiningwanjun.com/v/ftGwp3GU"},{"title":"第14集","url":"https://pan.jiningwanjun.com/v/knynUm4r"},{"title":"第15集","url":"https://pan.jiningwanjun.com/v/QPDdEpO8"},{"title":"第16集","url":"https://pan.jiningwanjun.com/v/vwSUi65u"},{"title":"第17集","url":"https://pan.jiningwanjun.com/v/TMMMwVvi"},{"title":"第18集","url":"https://pan.jiningwanjun.com/v/UrqKC3pg"},{"title":"第19集","url":"https://pan.jiningwanjun.com/v/bbZ5mFtf"},{"title":"第20集","url":"https://pan.jiningwanjun.com/v/n6eeNOdT"},{"title":"第21集","url":"https://pan.jiningwanjun.com/v/VLwLBrpJ"},{"title":"第22集","url":"https://pan.jiningwanjun.com/v/4rBfljSh"},{"title":"第23集","url":"https://pan.jiningwanjun.com/v/MddOzFys"},{"title":"第24集","url":"https://pan.jiningwanjun.com/v/q3DxqzG7"},{"title":"第25集","url":"https://pan.jiningwanjun.com/v/3tRHwxJU"},{"title":"第26集","url":"https://pan.jiningwanjun.com/v/og1ZBb1G"},{"title":"第27集","url":"https://pan.jiningwanjun.com/v/r1rMt5t8"},{"title":"第28集","url":"https://pan.jiningwanjun.com/v/4QK059af"},{"title":"第29集","url":"https://pan.jiningwanjun.com/v/R7idvdFE"},{"title":"第30集","url":"https://pan.jiningwanjun.com/v/emCX7DvO"},{"title":"第31集","url":"https://pan.jiningwanjun.com/v/PNeOQ9Ur"},{"title":"第32集","url":"https://pan.jiningwanjun.com/v/ag6FP1Ce"},{"title":"第33集","url":"https://pan.jiningwanjun.com/v/MXbw3FZL"},{"title":"第34集","url":"https://pan.jiningwanjun.com/v/mxZI3Pnj"},{"title":"第35集","url":"https://pan.jiningwanjun.com/v/ZLrPXoW5"},{"title":"第36集","url":"https://pan.jiningwanjun.com/v/Vt7SmfwO"},{"title":"第37集","url":"https://pan.jiningwanjun.com/v/Xwsk0HEe"},{"title":"第38集","url":"https://pan.jiningwanjun.com/v/Wudn4tA8"},{"title":"第39集","url":"https://pan.jiningwanjun.com/v/L8qHqfqT"},{"title":"第40集","url":"https://pan.jiningwanjun.com/v/o54W5Bab"},{"title":"第41集","url":"https://pan.jiningwanjun.com/v/68rar8Kg"},{"title":"第42集","url":"https://pan.jiningwanjun.com/v/3kQ7cJqG"},{"title":"第43集","url":"https://pan.jiningwanjun.com/v/6AbDpSxG"},{"title":"第44集","url":"https://pan.jiningwanjun.com/v/Wpa43nCH"},{"title":"第45集","url":"https://pan.jiningwanjun.com/v/sXc75xRm"},{"title":"第46集","url":"https://pan.jiningwanjun.com/v/ZsZq2w05"},{"title":"第47集","url":"https://pan.jiningwanjun.com/v/7NmlK2PH"},{"title":"第48集","url":"https://pan.jiningwanjun.com/v/Tlgms93T"}]},{"flag":"hkm3u8","episode":[{"title":"第01集","url":"https://cdn-2.haku99.com/hls/2018/12/25/mXi9aNbF/playlist.m3u8"},{"title":"第02集","url":"https://cdn-2.haku99.com/hls/2018/12/25/uJjWbviq/playlist.m3u8"},{"title":"第03集","url":"https://cdn-2.haku99.com/hls/2018/12/25/1DvCgivq/playlist.m3u8"},{"title":"第04集","url":"https://cdn-2.haku99.com/hls/2018/12/25/Bzk0kgIc/playlist.m3u8"},{"title":"第05集","url":"https://cdn-2.haku99.com/hls/2018/12/25/v2absT3n/playlist.m3u8"},{"title":"第06集","url":"https://cdn-2.haku99.com/hls/2018/12/25/K5uXhTel/playlist.m3u8"},{"title":"第07集","url":"https://cdn-2.haku99.com/hls/2018/12/25/MLsFlq5x/playlist.m3u8"},{"title":"第08集","url":"https://cdn-2.haku99.com/hls/2018/12/25/jz0pl03w/playlist.m3u8"},{"title":"第09集","url":"https://cdn-2.haku99.com/hls/2018/12/25/YTMpxfVL/playlist.m3u8"},{"title":"第10集","url":"https://cdn-2.haku99.com/hls/2018/12/25/VuBWnbW4/playlist.m3u8"},{"title":"第11集","url":"https://cdn-2.haku99.com/hls/2018/12/25/BuDPBhgl/playlist.m3u8"},{"title":"第12集","url":"https://cdn-2.haku99.com/hls/2018/12/25/BxvHNfSH/playlist.m3u8"},{"title":"第13集","url":"https://cdn-2.haku99.com/hls/2018/12/25/ftGwp3GU/playlist.m3u8"},{"title":"第14集","url":"https://cdn-2.haku99.com/hls/2018/12/25/knynUm4r/playlist.m3u8"},{"title":"第15集","url":"https://cdn-2.haku99.com/hls/2018/12/25/QPDdEpO8/playlist.m3u8"},{"title":"第16集","url":"https://cdn-2.haku99.com/hls/2018/12/25/vwSUi65u/playlist.m3u8"},{"title":"第17集","url":"https://cdn-2.haku99.com/hls/2018/12/25/TMMMwVvi/playlist.m3u8"},{"title":"第18集","url":"https://cdn-2.haku99.com/hls/2018/12/25/UrqKC3pg/playlist.m3u8"},{"title":"第19集","url":"https://cdn-2.haku99.com/hls/2018/12/25/bbZ5mFtf/playlist.m3u8"},{"title":"第20集","url":"https://cdn-2.haku99.com/hls/2018/12/25/n6eeNOdT/playlist.m3u8"},{"title":"第21集","url":"https://cdn-2.haku99.com/hls/2018/12/25/VLwLBrpJ/playlist.m3u8"},{"title":"第22集","url":"https://cdn-2.haku99.com/hls/2018/12/25/4rBfljSh/playlist.m3u8"},{"title":"第23集","url":"https://cdn-2.haku99.com/hls/2018/12/25/MddOzFys/playlist.m3u8"},{"title":"第24集","url":"https://cdn-2.haku99.com/hls/2018/12/25/q3DxqzG7/playlist.m3u8"},{"title":"第25集","url":"https://cdn-2.haku99.com/hls/2018/12/25/3tRHwxJU/playlist.m3u8"},{"title":"第26集","url":"https://cdn-2.haku99.com/hls/2018/12/25/og1ZBb1G/playlist.m3u8"},{"title":"第27集","url":"https://cdn-2.haku99.com/hls/2018/12/25/r1rMt5t8/playlist.m3u8"},{"title":"第28集","url":"https://cdn-2.haku99.com/hls/2018/12/25/4QK059af/playlist.m3u8"},{"title":"第29集","url":"https://cdn-2.haku99.com/hls/2018/12/25/R7idvdFE/playlist.m3u8"},{"title":"第30集","url":"https://cdn-2.haku99.com/hls/2018/12/25/emCX7DvO/playlist.m3u8"},{"title":"第31集","url":"https://cdn-2.haku99.com/hls/2018/12/25/PNeOQ9Ur/playlist.m3u8"},{"title":"第32集","url":"https://cdn-2.haku99.com/hls/2018/12/29/ag6FP1Ce/playlist.m3u8"},{"title":"第33集","url":"https://cdn-3.haku99.com/hls/2019/01/05/MXbw3FZL/playlist.m3u8"},{"title":"第34集","url":"https://cdn-3.haku99.com/hls/2019/01/13/mxZI3Pnj/playlist.m3u8"},{"title":"第35集","url":"https://cdn-3.haku99.com/hls/2019/01/19/ZLrPXoW5/playlist.m3u8"},{"title":"第36集","url":"https://cdn-3.haku99.com/hls/2019/01/26/Vt7SmfwO/playlist.m3u8"},{"title":"第37集","url":"https://cdn-3.haku99.com/hls/2019/02/02/Xwsk0HEe/playlist.m3u8"},{"title":"第38集","url":"https://cdn-2.haku99.com/hls/2019/02/09/Wudn4tA8/playlist.m3u8"},{"title":"第39集","url":"https://cdn-4.haku99.com/hls/2019/02/16/L8qHqfqT/playlist.m3u8"},{"title":"第40集","url":"https://cdn-4.haku99.com/hls/2019/02/23/o54W5Bab/playlist.m3u8"},{"title":"第41集","url":"https://cdn-4.haku99.com/hls/2019/03/02/68rar8Kg/playlist.m3u8"},{"title":"第42集","url":"https://cdn-4.haku99.com/hls/2019/03/09/3kQ7cJqG/playlist.m3u8"},{"title":"第43集","url":"https://cdn-4.haku99.com/hls/2019/03/16/6AbDpSxG/playlist.m3u8"},{"title":"第44集","url":"https://cdn-4.haku99.com/hls/2019/03/23/Wpa43nCH/playlist.m3u8"},{"title":"第45集","url":"https://cdn-4.haku99.com/hls/2019/03/30/sXc75xRm/playlist.m3u8"},{"title":"第46集","url":"https://cdn-5.haku99.com/hls/2019/04/06/ZsZq2w05/playlist.m3u8"},{"title":"第47集","url":"https://cdn-5.haku99.com/hls/2019/04/13/7NmlK2PH/playlist.m3u8"},{"title":"第48集","url":"https://cdn-5.haku99.com/hls/2019/04/20/Tlgms93T/playlist.m3u8"}]}]
     * type : 动漫
     * tid : 4
     * actor : 沈磊,程玉珠,黄翔宇,王肖兵,倪康,赵乾景,吴磊,张琦
     * des : 唐门外门弟子唐三，因偷学内门绝学为唐门所不容，跳崖明志时却发现没有死，反而以另外一个身份来到了另一个世界，一个属于武魂的世界，名叫斗罗大陆。这里没有魔法，没有斗气，没有武术，却有神奇的武魂。这里的每个人，在自己六岁的时候，都会在武魂殿中令武魂觉醒。武魂有动物，有植物，有器物，武魂可以辅助人们的日常生活。而其中一些特别出色的武魂却可以用来修炼并进行战斗，这个职业，是斗罗大陆上最为强大也是最荣耀的职业“魂师”。小小的唐三在圣魂村开始了他的魂师修炼之路，并萌生了振兴唐门的梦想。当唐门暗器来到斗罗大陆，当唐三武魂觉醒，他能否在这片武魂的世界再铸唐门的辉煌？
     * name : 斗罗大陆第二季
     * id : 2313
     * lang : 国语
     */
    private String area;
    private String note;
    private String last;
    private String year;
    private String director;
    private String pic;
    private String type;
    private String tid;
    private String actor;
    private String des;
    private String name;
    private String id;
    private String lang;
    private List<VideoSource> source;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<VideoSource> getSource() {
        return source;
    }

    public void setSource(List<VideoSource> source) {
        this.source = source;
    }
}
