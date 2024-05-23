package weizberg.rijks;

import com.andrewoid.ApiKey;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import weizberg.rijks.json.ArtObject;
import weizberg.rijks.json.ArtObjectsCollection;

import javax.swing.*;
import java.awt.*;

public class RijksFrame extends JFrame {
    private JTextField searchField = new JTextField();
    private ArtObjectsCollection artObjectsCollection;
    private Button previousPageButton = new Button("Previous Page");
    private Button nextPageButton = new Button("Next Page");
    private ApiKey apiKey = new ApiKey();
    int pageNumber = 1;

    public RijksFrame() {

        setTitle("RijksFrame");
        setSize(300, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(previousPageButton, BorderLayout.WEST);
        topPanel.add(nextPageButton, BorderLayout.EAST);
        topPanel.add(searchField, BorderLayout.CENTER);

        north.add(topPanel, BorderLayout.CENTER);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.add(north, BorderLayout.NORTH);

        add(main);

        RijksService service = new RijksServiceFactory().getService();
    }

    private void updateSearch(RijksService service) {
        String query = searchField.getText();
        String keyString = apiKey.get();
        if(query.isEmpty())
        {
            Disposable disposablePageNumber = service.pageNumber(keyString, pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(SwingSchedulers.edt())
                    .subscribe(
                            (response) ->handleResponse(response),
                            Throwable::printStackTrace);
        }
        else
        {
            Disposable disposableWithQuery = service.queryAndPageNumber(keyString, query, pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(SwingSchedulers.edt())
                    .subscribe(
                            (response) -> handleResponse(response),
                            Throwable::printStackTrace
                    );

            if(service.queryAndPageNumber(keyString, query, 0) == null)
            {
                Disposable disposableWithArtist = service.artistAndPageNumber(keyString, query, pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(SwingSchedulers.edt())
                        .subscribe(
                                (response) -> handleResponse(response),
                                Throwable::printStackTrace
                        );
            }

        }
    }

    private void handleResponse(ArtObjectsCollection response) {
        artObjectsCollection = response;
        String[] titleAndArtist = new String[response.artObjects.length];
        String[] imageLink = new String[response.artObjects.length];
        for (int i = 0; i < response.artObjects.length; i++) {
            ArtObject artObject = response.artObjects[i];
            titleAndArtist[i] = artObject.title + " " + artObject.principalOrFirstMaker;
            imageLink[i] = artObject.webImage.url;
        }
    }

    public static void main(String[] args) {
        RijksFrame frame = new RijksFrame();
        frame.setVisible(true);
    }
}
