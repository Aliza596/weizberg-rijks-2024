package weizberg.rijks;

import com.andrewoid.ApiKey;
import org.junit.jupiter.api.Test;
import weizberg.rijks.json.ArtObjects;
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
         ArtObjects artObjects = service.pageNumber(
                keyString,
                70
        ).blockingGet();

         //then
        assertNotEquals(0, artObjects.title);
        assertNotEquals(0, artObjects.longTitle);
        assertNotEquals(0, artObjects.principalOrFirstMaker);
//        assertNotEquals(0, artObjects.webImage.url);
    }

    @Test
    public void queryAndPageNumber() {
        //given
        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();
        RijksService service = new RijksServiceFactory().getService();

        //when
        ArtObjects artObjects = service.queryAndPageNumber(
                keyString,
                "blue",
                70
        ).blockingGet();

        //then
        assertNotEquals(0, artObjects.title);
        assertNotEquals(0, artObjects.longTitle);
        assertNotEquals(0, artObjects.principalOrFirstMaker);
//        assertNotEquals(0, artObjects.webImage.url);
    }

    @Test
    public void artistAndPageNumber() {
        //given
        ApiKey apiKey = new ApiKey();
        String keyString = apiKey.get();
        RijksService service = new RijksServiceFactory().getService();

        //when
        ArtObjects artObjects = service.artistAndPageNumber(
                keyString,
                "no one",
                70
        ).blockingGet();

        //then
        assertNotEquals(0, artObjects.title);
        assertNotEquals(0, artObjects.longTitle);
        assertNotEquals(0, artObjects.principalOrFirstMaker);
//        assertNotEquals(0, artObjects.webImage.url);
    }
}
