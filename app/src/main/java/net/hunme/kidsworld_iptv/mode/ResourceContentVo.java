package net.hunme.kidsworld_iptv.mode;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/21
 * 描    述：资源实体类
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class ResourceContentVo {
    private String id;
    private String name;
    private String imgUrl;
    private String status;
    private String ordernumber;
    private String restypeid;
    private String description;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getRestypeid() {
        return restypeid;
    }

    public void setRestypeid(String restypeid) {
        this.restypeid = restypeid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
