package miri.pro.mnews_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private EditText searchFilterEditText;
    private Button datePickerButton;
    private Button searchButton; //searchWithFiltersFilters

    private List<RecycleViewCategoryModel> categoryModelList;
    private List<ArticleModel> articleModelList ;
    private DatePickerDialog datePickerDialog;

    private RecycleViewNewsAdapter recycleViewNewsAdapter;
    private RecycleViewCategoryAdapter recycleViewCategoryAdapter;





   // private String API_KEY=  "fd8e9a2d5b2940cdbb5cc2f982c1f40d";
    private String API_KEY=  "3cf8c4eb320946bf9d88200dd85311b3"; //TP
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialising variables
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        progressBare = findViewById(R.id.progressBare);
        searchFilterEditText = findViewById(R.id.searchFilterEditText);
        datePickerButton = findViewById(R.id.datePickerButton);
        searchButton = findViewById(R.id.searchButton);

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

        loadNewsToList(null, null, null);

        recycleViewNewsAdapter.notifyDataSetChanged();

        // init date picker
        initDatePicker();
        datePickerButton.setText(getTodaysDate());





    }

    public void searchWithFilters(View view){
        String kw = searchFilterEditText.getText().toString().trim().toLowerCase();
        String keyWords = (kw.equals("") || kw.isEmpty()) ? null: kw;
        String dateFrom = datePickerButton.getText().toString().trim().toLowerCase() == "" ? null: datePickerButton.getText().toString().trim().toLowerCase();
        loadNewsToList(null, keyWords, dateFrom);
        //Toast.makeText(this, "HH", Toast.LENGTH_SHORT).show();
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
    private void loadNewsToList(String category, String keyWord, String fromDate){
        //https://newsapi.org/v2/top-headlines?q=ma&from=2022-03-09&category=entertainment&apiKey=
        // show progress bare
        progressBare.setVisibility(ProgressBar.VISIBLE);
        articleModelList.clear();
        String country = null, qKeyParam, categoryParam, countryParam, fromParam;
        //TODO: search with key word, with country, with date ...
        String baseURL = "https://newsapi.org";
        //String byCategoryURL = "/v2/top-headlines?category={category}&country={country}&apiKey={apiKey}";
        String allParamURL = "/v2/top-headlines?{qKeyParam}{categoryParam}{countryParam}{fromParam}&sortBy=publishAt&apiKey={apiKey}";
        //String allURL = "/v2/top-headlines?country={country}&sortBy=publishAt&apiKey={apiKey}";


        if (keyWord == null){
            qKeyParam = "";
        }else{
            qKeyParam = "q="+keyWord;
        }
        if(category == null){
            categoryParam = "";
        }else if(keyWord != null){
            categoryParam = "&category="+category;
        }
        else{
            categoryParam = "category="+category;
        }

        // important  one of them to exist
        if (category == null && keyWord == null) country = "ma";

        if(country == null){
            countryParam = "";
        }else if(category != null || keyWord != null){
            countryParam = "&country="+country;
        }
        else{
            countryParam = "country="+country;
        }

        if(fromDate == null){
            fromParam = "";
        }else if(category != null || keyWord != null || country!=null){
            fromParam = "&from="+fromDate;
        }
        else{
            fromParam = "from="+fromDate;
        }



        String linkToFetchFrom =
                baseURL+ allParamURL
                        .replace("{qKeyParam}", qKeyParam)
                        .replace("{categoryParam}", categoryParam)
                        .replace("{countryParam}", countryParam)
                        .replace("{fromParam}", fromParam)
                        .replace("{apiKey}", API_KEY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //initialising our class
        DataApiFetcher dataApiFetcher = retrofit.create(DataApiFetcher.class);
        Call<NewsItemModel> call= null;
        /*
        if(category.equals("all")){
            call = dataApiFetcher.getAllNews(baseURL+allURL.replace("{country}", country).replace("{apiKey}", API_KEY));
        }
        else{
            call = dataApiFetcher.getAllNews(baseURL+byCategoryURL.replace("{country}", country).replace("{apiKey}", API_KEY).replace("{category}", category));
        }

         */
        call = dataApiFetcher.getAllNews(linkToFetchFrom);

        call.enqueue(new Callback<NewsItemModel>() {
            @Override
            public void onResponse(Call<NewsItemModel> call, Response<NewsItemModel> response) {
                NewsItemModel newsItemModel = response.body();
                //Log.d("Body:", ""+response.body().getTotalResults());
                //Log.d("Body2:", ""+ (response.body().getArticles() == null));
                progressBare.setVisibility(ProgressBar.GONE);
                // TODO: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null object reference
                if (newsItemModel == null ){
                    Toast.makeText(MainActivity.this, "No New Found ! ", Toast.LENGTH_SHORT).show();
                    Log.d("link::", linkToFetchFrom);
                    return;
                }
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
        String keyWords = searchFilterEditText.getText().toString().trim().toLowerCase() == "" ? null: searchFilterEditText.getText().toString().trim().toLowerCase();
        String dateFrom = datePickerButton.getText().toString().trim().toLowerCase() == "" ? null: datePickerButton.getText().toString().trim().toLowerCase();
        loadNewsToList(category, keyWords, dateFrom);
    }

    // manage date picker

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                datePickerButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return year+"-"+ (month>9?month:"0"+month) +"-" +(day>9?day:"0"+day);
    }



    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

}