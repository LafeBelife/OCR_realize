package com.baidu.ai.character.pojo;

/**
 * 百度ai token<><br/>
 * <p>
 * 2018年8月13日14:13:40
 *
 * @author 王保卫
 */
public class AiToken {
    // 登录秘钥
    private String apiKey;
    private String secretKey;
    private String appId;
    /**
     * 级别<br/>
     * 0 - 普通, 1 - 高精度(含位置版)
     * 默认为0
     */
    private Integer rank = 0;
    // 免费登录秘钥
    private String freeSecretKey;
    private boolean succeed = false;    // 主要字段是否获得 false 没有，true 有

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getFreeSecretKey() {
        return freeSecretKey;
    }

    public void setFreeSecretKey(String freeSecretKey) {
        this.freeSecretKey = freeSecretKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "AiToken{" +
                "apiKey='" + apiKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", appId='" + appId + '\'' +
                ", rank=" + rank +
                ", freeSecretKey='" + freeSecretKey + '\'' +
                '}';
    }
}
