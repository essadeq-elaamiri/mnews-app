package miri.pro.mnews_app.data;

import java.util.List;

public class NewsItemModel {
    private String status;
    private int totalResults;
    private List<ArticleModel> articlesList;

    public NewsItemModel(String status, int totalResults, List<ArticleModel> articlesList) {
        this.status = status;
        this.totalResults = totalResults;
        this.articlesList = articlesList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticleModel> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<ArticleModel> articlesList) {
        this.articlesList = articlesList;
    }
}
