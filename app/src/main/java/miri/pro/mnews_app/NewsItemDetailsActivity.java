package miri.pro.mnews_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewsItemDetailsActivity extends AppCompatActivity {
    private String title;
    private String description;
    private String content;
    private String urlToImage;
    private String publishedAt;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item_details);
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        urlToImage = getIntent().getStringExtra("urlToImage");
        publishedAt = getIntent().getStringExtra("publishedAt");
        url = getIntent().getStringExtra("url");


    }
}