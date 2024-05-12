package weizberg.rijks;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weizberg.rijks.json.ArtObjects;

public interface RijksService {

    @GET("/api/en/collection")
    Single<ArtObjects> pageNumber(
            @Query("key") String apiKey,
            @Query("p") int pageNumber);

    @GET("/api/en/collection")
    Single<ArtObjects> queryAndPageNumber(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("p") int pageNumber
    );

    @GET("/api/en/collection")
    Single<ArtObjects> artistAndPageNumber(
            @Query("key") String apiKey,
            @Query("involvedMaker") String artist,
            @Query("p") int pageNumber
    );
}
