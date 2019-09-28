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
public class VideoSource implements Serializable {
    /**
     * flag : hkzy
     * episode : [{"title":"第01集","url":"https://pan.jiningwanjun.com/v/mXi9aNbF"},{"title":"第02集","url":"https://pan.jiningwanjun.com/v/uJjWbviq"},{"title":"第03集","url":"https://pan.jiningwanjun.com/v/1DvCgivq"},{"title":"第04集","url":"https://pan.jiningwanjun.com/v/Bzk0kgIc"},{"title":"第05集","url":"https://pan.jiningwanjun.com/v/v2absT3n"},{"title":"第06集","url":"https://pan.jiningwanjun.com/v/K5uXhTel"},{"title":"第07集","url":"https://pan.jiningwanjun.com/v/MLsFlq5x"},{"title":"第08集","url":"https://pan.jiningwanjun.com/v/jz0pl03w"},{"title":"第09集","url":"https://pan.jiningwanjun.com/v/YTMpxfVL"},{"title":"第10集","url":"https://pan.jiningwanjun.com/v/VuBWnbW4"},{"title":"第11集","url":"https://pan.jiningwanjun.com/v/BuDPBhgl"},{"title":"第12集","url":"https://pan.jiningwanjun.com/v/BxvHNfSH"},{"title":"第13集","url":"https://pan.jiningwanjun.com/v/ftGwp3GU"},{"title":"第14集","url":"https://pan.jiningwanjun.com/v/knynUm4r"},{"title":"第15集","url":"https://pan.jiningwanjun.com/v/QPDdEpO8"},{"title":"第16集","url":"https://pan.jiningwanjun.com/v/vwSUi65u"},{"title":"第17集","url":"https://pan.jiningwanjun.com/v/TMMMwVvi"},{"title":"第18集","url":"https://pan.jiningwanjun.com/v/UrqKC3pg"},{"title":"第19集","url":"https://pan.jiningwanjun.com/v/bbZ5mFtf"},{"title":"第20集","url":"https://pan.jiningwanjun.com/v/n6eeNOdT"},{"title":"第21集","url":"https://pan.jiningwanjun.com/v/VLwLBrpJ"},{"title":"第22集","url":"https://pan.jiningwanjun.com/v/4rBfljSh"},{"title":"第23集","url":"https://pan.jiningwanjun.com/v/MddOzFys"},{"title":"第24集","url":"https://pan.jiningwanjun.com/v/q3DxqzG7"},{"title":"第25集","url":"https://pan.jiningwanjun.com/v/3tRHwxJU"},{"title":"第26集","url":"https://pan.jiningwanjun.com/v/og1ZBb1G"},{"title":"第27集","url":"https://pan.jiningwanjun.com/v/r1rMt5t8"},{"title":"第28集","url":"https://pan.jiningwanjun.com/v/4QK059af"},{"title":"第29集","url":"https://pan.jiningwanjun.com/v/R7idvdFE"},{"title":"第30集","url":"https://pan.jiningwanjun.com/v/emCX7DvO"},{"title":"第31集","url":"https://pan.jiningwanjun.com/v/PNeOQ9Ur"},{"title":"第32集","url":"https://pan.jiningwanjun.com/v/ag6FP1Ce"},{"title":"第33集","url":"https://pan.jiningwanjun.com/v/MXbw3FZL"},{"title":"第34集","url":"https://pan.jiningwanjun.com/v/mxZI3Pnj"},{"title":"第35集","url":"https://pan.jiningwanjun.com/v/ZLrPXoW5"},{"title":"第36集","url":"https://pan.jiningwanjun.com/v/Vt7SmfwO"},{"title":"第37集","url":"https://pan.jiningwanjun.com/v/Xwsk0HEe"},{"title":"第38集","url":"https://pan.jiningwanjun.com/v/Wudn4tA8"},{"title":"第39集","url":"https://pan.jiningwanjun.com/v/L8qHqfqT"},{"title":"第40集","url":"https://pan.jiningwanjun.com/v/o54W5Bab"},{"title":"第41集","url":"https://pan.jiningwanjun.com/v/68rar8Kg"},{"title":"第42集","url":"https://pan.jiningwanjun.com/v/3kQ7cJqG"},{"title":"第43集","url":"https://pan.jiningwanjun.com/v/6AbDpSxG"},{"title":"第44集","url":"https://pan.jiningwanjun.com/v/Wpa43nCH"},{"title":"第45集","url":"https://pan.jiningwanjun.com/v/sXc75xRm"},{"title":"第46集","url":"https://pan.jiningwanjun.com/v/ZsZq2w05"},{"title":"第47集","url":"https://pan.jiningwanjun.com/v/7NmlK2PH"},{"title":"第48集","url":"https://pan.jiningwanjun.com/v/Tlgms93T"}]
     */
    private String flag;
    private List<Episode> episode;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<Episode> getEpisode() {
        return episode;
    }

    public void setEpisode(List<Episode> episode) {
        this.episode = episode;
    }
}
