package Utils;

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;

public class ImageUtils {
    private static final String DEFAULT_IMG_PATH = "assets/images/Menu/default.png";

    public static ImageIcon getRoundedIcon(String imagePath, int w, int h, int cornerRadius) {
        ImageIcon rawIcon = new ImageIcon(imagePath);
        
        // Validación de ruta
        if (rawIcon.getIconWidth() == -1) { rawIcon = new ImageIcon(DEFAULT_IMG_PATH); }

        Image originalImage = rawIcon.getImage();

        // create a canvas with transparency
        BufferedImage roundedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = roundedImage.createGraphics();

        // quality settings (avoid poorly rounded edges)
        // Edge smoothing (Anti-aliasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // mask technique (COMPOSITING)
        // the rounded shape (the mask) is drawn. The color doesn't matter, 
        // only the shape and its opacity.
        g2.setColor(Color.WHITE); 
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // Set compositing mode to "SrcIn". This preserves the edge smoothing of the previous mask.
        g2.setComposite(AlphaComposite.SrcIn);

        // the original image is drawn, scaling it on the fly.
        g2.drawImage(originalImage, 0, 0, w, h, null);
        g2.dispose();
        return new ImageIcon(roundedImage);
    }
}
