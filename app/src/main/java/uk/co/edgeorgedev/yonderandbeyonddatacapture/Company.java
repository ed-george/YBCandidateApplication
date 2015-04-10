package uk.co.edgeorgedev.yonderandbeyonddatacapture;

import java.util.List;

/**
 * Created by edgeorge on 09/02/15.
 */
public class Company {

    private String company;
    private String description;
    private String headline;
    private String main_tag;
    private String image;
    private List<String> tags;

    Company(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getImage() {
        if(image != null)
            return image;

        //Default - Launcher
        return "ic_launcher";
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMainTag() {
        return main_tag;
    }

    public void setMainTag(String main_tag) {
        this.main_tag = main_tag;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
