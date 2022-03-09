package miri.pro.mnews_app.data;

public class RecycleViewCategoryModel {
    private String Category;
    private String urlImageUrl;

    public RecycleViewCategoryModel(String category, String urlImageUrl) {
        Category = category;
        this.urlImageUrl = urlImageUrl;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getUrlImageUrl() {
        return urlImageUrl;
    }

    public void setUrlImageUrl(String urlImageUrl) {
        this.urlImageUrl = urlImageUrl;
    }
}
