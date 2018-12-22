import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;



public class ColorChanger {

    public static void main(String[] args)throws IOException{
        System.out.println("Hello World!");

        BufferedImage raw, processed;
        raw = ImageIO.read(new File("flower.png"));

        int width = raw.getWidth();
        int height = raw.getHeight();

        processed = new BufferedImage(width, height, raw.getType());

        //hard coded hue value
        //90, 180, 270 all offer different hues
        float hue = 270/360.0f;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //this is how we grab the RGB value of a pixel at x, y coordinates in the image
                int rgb = raw.getRGB(x, y);

                //extract the red value
                int red = (rgb >> 16) & 0xFF;

                //extract the green value
                int green = (rgb >> 8) & 0xFF;

                //extract the blue value
                int blue = (rgb) & 0xFF;

                //use Color.RGBtoHSB() method to convert RGB value to HSB
                float hsb[] = Color.RGBtoHSB(red, green, blue, null);

                //can either set the hue with
                //hsb[0] = hue;
                //or we can offset the hue by adding the hardcoded hue to the existing hue
                //hsb[0] = hsb[0] + hue;
                //the latter will leave some of the original color in the image
                hsb[0] = hsb[0] + hue;

                //since hue should range from 0 to 1, if adding hue results in a number greater than 1,
                //resulting hue should loop back around the spectrum
                //so a simple -1 to resulting hue should work
                if(hsb[0] > 1){
                    hsb[0] = hsb[0] - 1;
                }

                //then use Color.HSBtoRGB() method to convert the HSB value to a new RGB value
                int newRGB = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

                //set the new RGB value to a pixel at x, y coordinates in the image
                processed.setRGB(x, y, newRGB);
            }
        }
        ImageIO.write(processed,"PNG", new File("processed.png"));
    }
}
