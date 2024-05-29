package weizberg.rijks;

import com.andrewoid.ApiKey;
import hu.akarnokd.rxjava3.swing.SwingSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import weizberg.rijks.json.ArtObject;
import weizberg.rijks.json.ArtObjectsCollection;
import weizberg.rijks.json.DisplayImageFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RijksFrame extends JFrame {
    private JTextField searchField = new JTextField();
    private ArtObjectsCollection artObjectsCollection;
    private Button previousPageButton = new Button("Previous Page");
    private Button nextPageButton = new Button("Next Page");
    private RijksService service;
    private JPanel main = new JPanel();
    private JPanel imagePanel = new JPanel(new GridLayout(3, 4));
    private int pageNumber = 1;
    private ApiKey apiKey = new ApiKey();
    private String keyString = apiKey.get();

    public RijksFrame() {

        setTitle("RijksFrame");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(previousPageButton, BorderLayout.WEST);
        topPanel.add(nextPageButton, BorderLayout.EAST);
        topPanel.add(searchField, BorderLayout.CENTER);

        north.add(topPanel, BorderLayout.CENTER);

        main.setLayout(new BorderLayout());
        main.add(north, BorderLayout.NORTH);
        main.add(imagePanel, BorderLayout.CENTER);

        add(main);

        service = new RijksServiceFactory().getService();

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSearch(++pageNumber);
            }
        });

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pageNumber > 1) {
                    updateSearch(--pageNumber);
                }
            }
        });

        searchField.addActionListener(e -> updateSearch(1));
    }

    private void updateSearch(int pageNumber) {
        String query = searchField.getText();

        if (query.isEmpty())
        {
            Disposable disposablePageNumber = service.pageNumber(keyString, pageNumber)
                    .subscribeOn(Schedulers.io())
                    .observeOn(SwingSchedulers.edt())
                    .subscribe(
                            (response) -> handleResponse(response),
                            Throwable::printStackTrace);
        } else
        {
                Disposable disposableWithQuery = service.queryAndPageNumber(keyString, query, pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(SwingSchedulers.edt())
                        .subscribe(
                                (response) -> handleResponse(response),
                                Throwable::printStackTrace
                        );
        }
    }

    private void handleResponse(ArtObjectsCollection response) throws IOException {
        artObjectsCollection = response;
        openImages();
    }

    private void openImages() throws IOException {
        imagePanel.removeAll();
        for (ArtObject artObject : artObjectsCollection.artObjects) {
            String link = artObject.webImage.url;
            URL url = new URL(link);
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(200, -1, Image.SCALE_DEFAULT);
            JLabel label = new JLabel();
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            label.setIcon(imageIcon);

            String titleArtist = artObject.title
                    + ", " + artObject.principalOrFirstMaker;
            label.setToolTipText(titleArtist);

            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    try {
                        new DisplayImageFrame(link, titleArtist).setVisible(true);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            imagePanel.add(label);
        }

        imagePanel.revalidate();
        imagePanel.repaint();
    }

    public static void main(String[] args) {
        RijksFrame frame = new RijksFrame();
        frame.setVisible(true);
    }
}
