import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class DimenUtils {
    private static void generateXmlFile(int vValueCount, int yValueCount) {

        StringBuffer sbForX = new StringBuffer();
        sbForX.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForX.append("<resources>");
        for (int i = 1; i <= vValueCount; i++) {
            sbForX.append("<dimen name=\"width_x_" + i + "\">@dimen/x" + (i * 2) + "</dimen>\n");
        }
        sbForX.append("</resources>");


        StringBuffer sbForY = new StringBuffer();
        sbForY.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForY.append("<resources>");
        for (int i = 1; i <= yValueCount; i++) {
            sbForY.append("<dimen name=\"height_y_" + i + "\">@dimen/y" + (i * 2) + "</dimen>\n");
        }
        sbForY.append("</resources>");

        File fileDir = new File("./values" + File.separator);
        fileDir.mkdir();

        File xCountFile = new File(fileDir.getAbsolutePath(), "dimens_x.xml");
        File yCountFile = new File(fileDir.getAbsolutePath(), "dimens_y.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(xCountFile));
            pw.print(sbForX.toString());
            pw.close();
            PrintWriter py = new PrintWriter(new FileOutputStream(yCountFile));
            py.print(sbForY.toString());
            py.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
		int xMaxValue = 600;  
        int yMaxValue = 800;  
        try {  
            if (args.length >= 2) {  
                xMaxValue = Integer.parseInt(args[0]);  
                yMaxValue = Integer.parseInt(args[1]);  
            } else if (args.length >= 1) {  
				xMaxValue = Integer.parseInt(args[0]);  
                yMaxValue = Integer.parseInt(args[0]); 
            }  
        } catch (NumberFormatException e) {  
  
            System.err  
                    .println("right input params : java -jar xxx.jar xMaxValue,yMaxValue;");  
            e.printStackTrace();  
            System.exit(-1);  
        }  

        generateXmlFile(xMaxValue,yMaxValue);
    }
}
