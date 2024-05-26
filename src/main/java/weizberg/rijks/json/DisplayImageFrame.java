package weizberg.rijks.json;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DisplayImageFrame extends JFrame {

    public DisplayImageFrame(String imageUrl, String titleAndArtist) throws IOException {
        setTitle(titleAndArtist);
        setSize(1000, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        URL url = new URL(imageUrl);
        Image image = ImageIO.read(url);

        Image scaledImage = image.getScaledInstance(800, -1, Image.SCALE_DEFAULT);
        JLabel label = new JLabel();
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        label.setIcon(imageIcon);

        JScrollPane scrollPane = new JScrollPane(label);
        setPreferredSize(new Dimension(1000, 800));
        add(scrollPane, BorderLayout.CENTER);


        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.add(label, BorderLayout.CENTER);
        add(main);
    }
}
