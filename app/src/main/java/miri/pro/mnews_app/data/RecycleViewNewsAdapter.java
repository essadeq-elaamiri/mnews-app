package miri.pro.mnews_app.data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miri.pro.mnews_app.NewsItemDetailsActivity;
import miri.pro.mnews_app.R;

public class RecycleViewNewsAdapter extends RecyclerView.Adapter<RecycleViewNewsAdapter.ViewHolder>{
    private List<ArticleModel> articlesList;
    private Context context;

    public RecycleViewNewsAdapter(List<ArticleModel> articlesList, Context context) {
        this.articlesList = articlesList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // retrieve the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return  new RecycleViewNewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewNewsAdapter.ViewHolder holder, int position) {
        // bind data to layout components
        ArticleModel article = this.articlesList.get(position);
        holder.newsItemHeadingTextView.setText(article.getTitle());
        holder.newsItemSubHeadingTextView.setText(article.getDescription());
        Picasso.get().load(article.getUrlToImage()).into(holder.newsItemImageView);

        // open a new activity on click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //passing data to the other activity
                Intent intent = new Intent(context, NewsItemDetailsActivity.class);
                intent.putExtra("title", article.getTitle());
                intent.putExtra("description", article.getDescription());
                intent.putExtra("urlToImage", article.getUrlToImage());
                intent.putExtra("content", article.getContent());
                intent.putExtra("publishedAt", article.getPublishedAt());
                intent.putExtra("url", article.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.articlesList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsItemImageView;
        private TextView newsItemHeadingTextView, newsItemSubHeadingTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsItemImageView = itemView.findViewById(R.id.newsItemImageView);
            newsItemHeadingTextView = itemView.findViewById(R.id.newsItemHeadingTextView);
            newsItemSubHeadingTextView = itemView.findViewById(R.id.newsItemSubHeadingTextView);


        }
    }
}
