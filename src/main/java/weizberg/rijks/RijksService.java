package weizberg.rijks;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weizberg.rijks.json.ArtObject;
import weizberg.rijks.json.ArtObjectsCollection;

public interface RijksService {

    @GET("/api/en/collection")
    Single<ArtObjectsCollection> pageNumber(
            @Query("key") String apiKey,
            @Query("p") int pageNumber);

    @GET("/api/en/collection")
    Single<ArtObjectsCollection> queryAndPageNumber(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("p") int pageNumber
    );

    @GET("/api/en/collection")
    Single<ArtObjectsCollection> artistAndPageNumber(
            @Query("key") String apiKey,
            @Query("involvedMaker") String artist,
            @Query("p") int pageNumber
    );
}
