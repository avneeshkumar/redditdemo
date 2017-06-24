package com.atarashi.picreddit.utils;

import com.atarashi.picreddit.utils.redditjsonclass.Subredditpost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by avnee on 4/2/2017.
 */

public interface RedditService {
    @GET("r/{subredditname}.json?count=25")
    Call<Subredditpost> listPost(@Path("subredditname") String subredditname);

    @GET("r/{subredditname}.json?count=25")
    Call<Subredditpost> listnextPost(@Path("subredditname") String subredditname,@Query("after") String name);

}
