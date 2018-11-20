import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据需要适配的最小宽度dp生成对应文件
 */
public class SmallWidthDpValueFiles {
    // 屏幕匹配的基础dp值。将屏幕切成[BASE_DP]部分，不建议修改，
	// 建议将 values-sw360dp 文件夹下的 dimens.xml 文件作为默认dimen放入values下，然后 values-sw360dp 文件夹可以不用复制到 res 下
    private static final double BASE_DP = 360;
    // 定义需要适配的屏幕最小宽度
    private static final String SUPPORT_DIMESION = "360,384,392,400,410,411,480,533,592,600,640,662,720,768,800,811,820,960,961,1024,1280,1365";
    // 定义取值
    private static List<Double> nameValue = new ArrayList<>();

    private String dirStr = "./res";

    private final static String dpTemplate = "<dimen name=\"dp_{0}\">{1}dp</dimen>\n";
    private final static String spTemplate = "<dimen name=\"sp_{0}\">{1}sp</dimen>\n";

    /**
     * {0}-最小宽度
     */
    private final static String VALUE_TEMPLATE = "values-sw{0}dp";

    private String supportStr = SUPPORT_DIMESION;

    public SmallWidthDpValueFiles() {
        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdir();
        }
        System.out.println(dir.getAbsoluteFile());

    }

    public void generate() {
        // 从 -60 取到 -5，间隔 1
        for (double i = -60; i < -5; i++) {
            nameValue.add(i);
        }
        // 从 -5 取到 -1，间隔 0.5
        for (double i = -5; i < -1; ) {
            nameValue.add(i);
            BigDecimal bigDecimal = new BigDecimal(i);
            BigDecimal bigDecimal1 = bigDecimal.add(new BigDecimal(0.5));
            i = bigDecimal1.setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
		// 从 -1 取到 1，间隔 0.2
        for (double i = -1; i < 1; ) {
			
            nameValue.add(i);
			if(i == 0.4)
				nameValue.add(0.5);

            BigDecimal bigDecimal = new BigDecimal(i);
            BigDecimal bigDecimal1 = bigDecimal.add(new BigDecimal(0.2));
            i = bigDecimal1.setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
		// 从 1 取到 5，间隔 0.5
        for (double i = 1; i <= 5; ) {
            nameValue.add(i);
            i += 0.5;
        }
        // 从 5 取到 720，间隔 1
        for (double i = 6; i <= 720; i++) {
            nameValue.add(i);
        }

        for (Double aDouble : nameValue) {
            System.out.println("nameValue => " + aDouble);
        }

        String[] vals = supportStr.split(",");
        for (String val : vals) {
            generateXmlFile(Integer.parseInt(val));
        }
    }

    private void generateXmlFile(int swDpValue) {
        double cellValue = swDpValue / BASE_DP;

        StringBuffer sbDpValue = new StringBuffer();
        sbDpValue.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbDpValue.append("<resources>\n");

        System.out.println("small width dp value : " + swDpValue + ", cellValue : " + cellValue);
        // dp
        sbDpValue.append("\n\t<!-- dp value -->\n");
        for (Double aDouble : nameValue) {
            sbDpValue.append("\t" + dpTemplate.replace("{0}", changeName(aDouble)).replace("{1}",
                    changeValue(cellValue, aDouble) + ""));
        }

        // sp
        sbDpValue.append("\n\t<!-- sp value -->\n");
		for (double i = 3; i < 12; ) {
			sbDpValue.append("\t" + spTemplate.replace("{0}", changeName(i)).replace("{1}",
                    changeValue(cellValue, i) + ""));
			BigDecimal bigDecimal = new BigDecimal(i);
            BigDecimal bigDecimal1 = bigDecimal.add(new BigDecimal(0.5));
            i = bigDecimal1.setScale(1, RoundingMode.HALF_UP).doubleValue();
		}
        for (int i = 12; i <= 48; i++) {
            sbDpValue.append("\t" + spTemplate.replace("{0}", changeName(i)).replace("{1}",
                    changeValue(cellValue, i) + ""));
        }

        sbDpValue.append("</resources>");

        File fileDir = new File(dirStr + File.separator + VALUE_TEMPLATE.replace("{0}", swDpValue + ""));
        fileDir.mkdir();

        File dimensFile = new File(fileDir.getAbsolutePath(), "dimens.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(dimensFile));
            pw.print(sbDpValue.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String changeName(double i) {
        DecimalFormat df = new DecimalFormat("#0.0");
        String value = df.format(i);
        String[] strings = value.split("\\.");
        if (i >= 0) {
			if ("0".equals(strings[1])) {
                return "" + strings[0];
            } else {
                return strings[0] + "_" + strings[1];
            }
        } else {
            if ("0".equals(strings[1])) {
                return "m_" + strings[0].replace("-","");
            } else {
                return "m_" + strings[0].replace("-","") + "_" + strings[1];
            }
        }
    }

    public static double changeValue(double cellValue, double i) {
        BigDecimal bigDecimal = new BigDecimal(cellValue * i);
        return bigDecimal.setScale(4, RoundingMode.HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        new SmallWidthDpValueFiles().generate();
    }
}