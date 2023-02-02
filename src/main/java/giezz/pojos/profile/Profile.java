package giezz.pojos.profile;

public class Profile {
    private long id;
    private String nickname;
    private String url;
    private String name;
    private String sex;
    private String website;
    private String full_years;
    private String avatar;
    private Image image;

    public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        if (name != null) return name;
        return "None";
    }

    public String getSex() {
        return sex;
    }

    public String getWebsite() {
        return website;
    }

    public String getFullYears() {
        return full_years;
    }

    public String getAvatar() {
        return avatar;
    }

    public Image getImage() {
        return image;
    }
}
