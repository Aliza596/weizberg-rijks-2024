package weizberg.rijks;

import com.andrewoid.ApiKey;
import org.junit.jupiter.api.Test;
import weizberg.rijks.json.ArtObject;
import weizberg.rijks.json.ArtObjectsCollection;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RijksServiceTest {

    @Test
    public void pageNumber() {
        //given
        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();
        RijksService service = new RijksServiceFactory().getService();

         //when
         ArtObjectsCollection artObjectsCollection = service.pageNumber(
                keyString,
                1
        ).blockingGet();

         //then
        assertNotEquals(null, artObjectsCollection.artObjects[0].title);
        assertNotEquals(null, artObjectsCollection.artObjects[0].longTitle);
        assertNotEquals(null, artObjectsCollection.artObjects[0].principalOrFirstMaker);
        assertNotEquals(null, artObjectsCollection.artObjects[0].webImage.url);
    }

    @Test
    public void queryAndPageNumber() {
        //given
        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();
        RijksService service = new RijksServiceFactory().getService();

        //when
        ArtObjectsCollection artObjectsCollection = service.queryAndPageNumber(
                keyString,
                "blue",
                1
        ).blockingGet();

        //then
        assertNotEquals(null, artObjectsCollection.artObjects[0].title);
        assertNotEquals(null, artObjectsCollection.artObjects[0].longTitle);
        assertNotEquals(null, artObjectsCollection.artObjects[0].principalOrFirstMaker);
        assertNotEquals(null, artObjectsCollection.artObjects[0].webImage.url);
    }

    @Test
    public void artistAndPageNumber() {
        //given
        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();
        RijksService service = new RijksServiceFactory().getService();

        //when
        ArtObjectsCollection artObjectsCollection = service.artistAndPageNumber(
                keyString,
                "Rembrandt van Rijn",
                1
        ).blockingGet();

        //then
        assertNotEquals(null, artObjectsCollection.artObjects[0].title);
        assertNotEquals(null, artObjectsCollection.artObjects[0].longTitle);
        assertNotEquals(null, artObjectsCollection.artObjects[0].principalOrFirstMaker);
        assertNotEquals(null, artObjectsCollection.artObjects[0].webImage.url);
    }
}
