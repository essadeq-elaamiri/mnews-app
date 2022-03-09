package miri.pro.mnews_app.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DataApiFetcher {
    @GET
    Call<NewsItemModel> getAllNews(@Url String url);

    @GET
    Call<NewsItemModel> getNewsByCategory(@Url String url);
}
