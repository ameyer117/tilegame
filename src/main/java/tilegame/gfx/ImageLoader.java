package tilegame.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(ImageLoader.class.getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return image;
    }
}
