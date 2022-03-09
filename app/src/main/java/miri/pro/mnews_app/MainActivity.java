package miri.pro.mnews_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import miri.pro.mnews_app.data.ArticleModel;
import miri.pro.mnews_app.data.DataApiFetcher;
import miri.pro.mnews_app.data.NewsItemModel;
import miri.pro.mnews_app.data.RecycleViewCategoryAdapter;
import miri.pro.mnews_app.data.RecycleViewCategoryModel;
import miri.pro.mnews_app.data.RecycleViewNewsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecycleViewCategoryAdapter.CategoryClickEvent {
    private RecyclerView categoriesRecyclerView;
    private RecyclerView newsRecyclerView;
    private ProgressBar progressBare;

    private List<RecycleViewCategoryModel> categoryModelList;
    private List<ArticleModel> articleModelList ;

    private RecycleViewNewsAdapter recycleViewNewsAdapter;
    private RecycleViewCategoryAdapter recycleViewCategoryAdapter;


    private String API_KEY=  "fd8e9a2d5b2940cdbb5cc2f982c1f40d";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialising variables
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        progressBare = findViewById(R.id.progressBare);

        articleModelList = new ArrayList<>();
        categoryModelList = new ArrayList<>();

        recycleViewNewsAdapter = new RecycleViewNewsAdapter(articleModelList, this);
        recycleViewCategoryAdapter = new RecycleViewCategoryAdapter(categoryModelList, this, this::onCategoryItemClick);

        // init layouts
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setting adapters
        newsRecyclerView.setAdapter(recycleViewNewsAdapter);
        categoriesRecyclerView.setAdapter(recycleViewCategoryAdapter);

        loadCategoriesToList();

        loadNewsToList("all");

        recycleViewNewsAdapter.notifyDataSetChanged();




    }

    private  void loadCategoriesToList(){
        //business; entertainment; general; health; science; sports; technology
        String [] categoriesNames =
                {"all", "business", "entertainment", "general", "health", "science", "sports", "technology" };
        String [] categoriesURLs=
                {         "https://images.unsplash.com/photo-1646678471872-af73a8ef8ffe?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w={width}&q=80"
                        , "https://images.unsplash.com/photo-1541746972996-4e0b0f43e02a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w={width}&q=80"
                        , "https://images.unsplash.com/photo-1549342902-be005322599a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w={width}&q=80"
                        , "https://images.unsplash.com/photo-1451187580459-43490279c0fa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w={width}&q=80"
                        ,"https://images.unsplash.com/photo-1505751172876-fa1923c5c528?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w={width}&q=80"
                        , "https://images.unsplash.com/photo-1518152006812-edab29b069ac?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w={width}&q=80"
                        , "https://images.unsplash.com/photo-1484482340112-e1e2682b4856?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w={width}&q=80"
                        , "https://images.unsplash.com/photo-1518770660439-4636190af475?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w={width}&q=80"
                };
        for (int i=0; i<categoriesNames.length; i++){
            categoryModelList.add(new RecycleViewCategoryModel(categoriesNames[i], categoriesURLs[i].replace("{width}", String.valueOf(200))));
        }
        // notify adapter
        recycleViewCategoryAdapter.notifyDataSetChanged();
    }
    private void loadNewsToList(String category){
        // show progress bare
        progressBare.setVisibility(ProgressBar.VISIBLE);
        articleModelList.clear();
        String country = "us";
        //TODO: search with key word, with country, with date ...
        String baseURL = "https://newsapi.org";
        String byCategoryURL = "/v2/top-headlines?category={category}&country={country}&apiKey={apiKey}";
        String allURL = "/v2/top-headlines?country={country}&sortBy=publishAt&apiKey={apiKey}";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //initialising our class
        DataApiFetcher dataApiFetcher = retrofit.create(DataApiFetcher.class);
        Call<NewsItemModel> call= null;
        if(category.equals("all")){
            call = dataApiFetcher.getAllNews(baseURL+allURL.replace("{country}", country).replace("{apiKey}", API_KEY));
        }
        else{
            call = dataApiFetcher.getAllNews(baseURL+byCategoryURL.replace("{country}", country).replace("{apiKey}", API_KEY).replace("{category}", category));
        }

        call.enqueue(new Callback<NewsItemModel>() {
            @Override
            public void onResponse(Call<NewsItemModel> call, Response<NewsItemModel> response) {
                NewsItemModel newsItemModel = response.body();
                Log.d("Body:", ""+response.body().getTotalResults());
                Log.d("Body2:", ""+ (response.body().getArticles() == null));
                progressBare.setVisibility(ProgressBar.GONE);
                // TODO: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null object reference
                articleModelList.addAll(newsItemModel.getArticles());
                /*
                List<ArticleModel> localArticleModelList = newsItemModel.getArticlesList();
                for(ArticleModel articleModel1: localArticleModelList ){
                    //String title, String description, String url, String urlToImage, String publishedAt, String content) {
                    articleModelList.add(new ArticleModel(
                            articleModel1.getTitle(),
                            articleModel1.getDescription(),
                            articleModel1.getUrl(),
                            articleModel1.getUrlToImage(),
                            articleModel1.getPublishedAt(),
                            articleModel1.getContent()));
                }
                 */
                recycleViewNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsItemModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error when try to fetch data ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCategoryItemClick(int position) {
        String category = categoryModelList.get(position).getCategory();
        loadNewsToList(category);
    }
}