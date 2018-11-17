package com.gg.fanapp.push_meizu;

import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import java.util.List;

/*
 * @author 工藤
 * @emil gougou@16fan.com
 * com.gg.fanapp.push_meizu
 * create at 2018/4/11  11:24
 * description:
 */
public class TagAndAliasManager {
    //别名
    private String alias = "";
    //标签
    private List<SubTagsStatus.Tag> tagList;

    public static TagAndAliasManager getInstance() {
        return SingleHolder.instance;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<SubTagsStatus.Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<SubTagsStatus.Tag> tagList) {
        this.tagList = tagList;
    }

    private static class SingleHolder {
        private static final TagAndAliasManager instance = new TagAndAliasManager();
    }
}
