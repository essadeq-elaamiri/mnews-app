package miri.pro.mnews_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsItemDetailsActivity extends AppCompatActivity {
    private String title;
    private String description;
    private String content;
    private String urlToImage;
    private String publishedAt;
    private String url;

    //views
    private ImageView detailsActImageView;
    private TextView detailsActTitleTextView;
    private TextView detailsActSubTitleTextView;
    private TextView detailsActDateTextView;
    private TextView detailsActContentTextView;
    private Button readMoreButton;

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

        // get views
        detailsActImageView = findViewById(R.id.detailsActImageView);
        detailsActTitleTextView = findViewById(R.id.detailsActTitleTextView);
        detailsActSubTitleTextView = findViewById(R.id.detailsActSubTitleTextView);
        detailsActDateTextView = findViewById(R.id.detailsActDateTextView);
        detailsActContentTextView = findViewById(R.id.detailsActContentTextView);
        readMoreButton = findViewById(R.id.readMoreButton);

        Picasso.get().load(urlToImage).into(detailsActImageView);
        detailsActTitleTextView.setText(title);
        detailsActSubTitleTextView.setText(description);
        detailsActDateTextView.setText(publishedAt);
        detailsActContentTextView.setText(content);

        readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);


            }
        });



    }
}