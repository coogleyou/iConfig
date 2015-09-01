package ren.wenchao.iconfig.core.pojo.model;

/**
 * @author rollenholt
 */
public enum ProfileType {
    LOCAL(1, "local", "本地环境"),
    DEV(2, "dev", "dev环境"),
    BETA(3, "beta", "beta环境"),
    PROD(4, "prod", "线上环境");

    private int id;
    private String code;
    private String description;

    ProfileType(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}