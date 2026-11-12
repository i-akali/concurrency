import java.awt.Color;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Blur {
	
	public static Picture blur(Picture p, int boxSize) {

        ExecutorService es = Executors.newSingleThreadExecutor();

        Callable c = new Callable(){
            @Override
            public Boolean call(){
                return blurBottom(p,boxSize);
            }

        };

        Future<Boolean> f = es.submit(c);

        blurTop(p, boxSize);

        try{
            boolean done = f.get();
            return p;
        }
        catch(Exception e)
        {
            return null;
        }

	}

    public static boolean blurTop(Picture p, int boxSize) {
        int height = p.height();
        int width = p.width();
        Picture blurredPicture = new Picture(width, height);
        for (int y = 0; y < height/2; y++) {
            for (int x = 0; x < width; x++) {
                int red = 0, green = 0, blue = 0;
                int count = 0;
                for (int dx = -boxSize; dx <= boxSize; dx++) {
                    for (int dy = -boxSize; dy <= boxSize; dy++) {
                        if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                            red += p.get(x + dx, y + dy).getRed();
                            green += p.get(x + dx, y + dy).getGreen();
                            blue += p.get(x + dx, y + dy).getBlue();
                            count++;
                        }
                    }
                }
                red = (int) (red / count);
                green = (int) (green / count);
                blue = (int) (blue / count);
                p.set(x, y, new Color(red, green, blue));
            }
            System.out.println("Done processing row " + y + " of " + (height - 1));
        }
        return true;
    }

    public  static boolean blurBottom(Picture p, int boxSize){
        int height = p.height();
        int width = p.width();
        Picture blurredPicture = new Picture(width, height);
        for (int y = height/2; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = 0, green = 0, blue = 0;
                int count = 0;
                for (int dx = -boxSize; dx <= boxSize; dx++) {
                    for (int dy = -boxSize; dy <= boxSize; dy++) {
                        if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                            red += p.get(x + dx, y + dy).getRed();
                            green += p.get(x + dx, y + dy).getGreen();
                            blue += p.get(x + dx, y + dy).getBlue();
                            count++;
                        }
                    }
                }
                red = (int)(red / count);
                green = (int)(green / count);
                blue = (int)(blue / count);
                p.set(x, y, new Color(red, green, blue));
            }
            System.out.println("Done processing row " + y + " of " + (height-1));
        }
        return true;
    }

	
	public static void main(String[] args) {
		
		try {
			Picture p = new Picture("stitch.jpg");
			blur(p, 50).show();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
