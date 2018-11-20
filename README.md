# Android 使用多个 values 适配

## 使用多个values适配的2种形式
1. 使用像素(px)，values 的文件名形式为 values-xxx(如：values-1080x1920，表示屏幕分辨率为 1080*1920)
2. 使用最小宽度限制(small width)， values 的文件名形式为 values-swxxxdp(如：values-sw360dp，表示屏幕最小的宽度为 360dp)

## 自动生成 像素(px) 适配的 values-xxx 文件
### 使用 GenerateValueFiles.java 类生成 values-xxx文件
* 1.修改代码，定义所需要的分辨率  
	![定义所需分辨率](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/all.png)
 
* 2.修改UI设计师定义的基本分辨率（如果不想在代码中修改，可以在运行Java类时进行修改）  
	![UI设计师定义的基本分辨率](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/base.png)

* 3.编译 ***GenerateValueFiles.java*** 类  
 	进入 ***GenerateValueFiles.java*** 类 所在目录，使用 `javac GenerateValueFiles.java` 编译生成 ***GenerateValueFiles.class*** 文件

* 4.运行***GenerateValueFiles.class*** 文件  
	 进入 ***GenerateValueFiles.class*** 所在目录
	* ① 使用代码中定义的基本分辨率  
	直接使用 `java GenerateValueFiles` 命令
	* ② 在运行中动态修改基本分辨率
	使用 `java GenerateValueFiles 1920,1080` 命令，将基本分辨率设置成 1920*1080  

	在当前目录下生成res文件夹，在res文件夹下生成如下内容：  
	![res文件内容](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/values.png)

* 5.将values-xxx复制到项目中的res文件夹下

### DimenUtils.java类说明
 ***DimenUtils.java*** 类用于生成 ***dimens_x.xml、dimens_y.xml*** 文件，默认生成基本准则是 1dp = 2px 形式。

* 1.修改默认基本准则  
	![修改默认基本准则](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/dimen.png)
* 2.修改生成的范围(既可以在代码中修改，也可以在运行时动态设置)  
	![修改生成的范围](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/dimen_base.png)
* 3.使用Java命令编译和运行生成 ***dimens_x.xml、dimens_y.xml*** 文件。
* 4.可以将生成的文件内容复制到 ***res/values文件夹下的 dimens.xml*** 文件中进行使用。  

> #### **当使用 像素(px) 适配 时，在项目中可以使用 “dimens.xml” 文件中的dimen值进行布局，方便统一修改。**


## 自动生成 宽度限制(small width) 适配的 values-swxxxdp 文件
1.修改代码  
① 修改基础dp值  
② 修改所需要适配的屏幕最下宽度值  
③ 修改每个 dimens.xml 文件中需要的数值

	// ① 屏幕匹配的基础dp值。将屏幕切成[BASE_DP]部分，不建议修改，
	// 建议将 values-sw360dp 文件夹下的 dimens.xml 文件作为默认dimen放入values下，然后 values-sw360dp 文件夹可以不用复制到 res 下
	// 注意：其实是将基础dp值[BASE_DP]对应的values values-sw[BASE_DP]dp 文件夹下的 dimens.xml 文件作为默认dimen放入values下
	// 其他的 values-swXXXdp 根据需要复制到res目录下
    private static final double BASE_DP = 360;
    // ② 定义需要适配的屏幕最小宽度
    private static final String SUPPORT_DIMESION = "360,384,392,400,410,411,480,533,592,600,640,662,720,768,800,811,820,960,961,1024,1280,1365";
    // 定义取值
    private static List<Double> nameValue = new ArrayList<>();

	public void generate() {
		// ③ 定义每个 dimens.xml 文件中需要的数值

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

* 2.编译 ***SmallWidthDpValueFiles.java*** 类  
 	进入 ***SmallWidthDpValueFiles.java*** 类 所在目录，使用 `javac SmallWidthDpValueFiles.java` 编译生成 ***SmallWidthDpValueFiles.class*** 文件

* 3.运行***SmallWidthDpValueFiles.class*** 文件  
	进入 ***SmallWidthDpValueFiles.class*** 所在目录， 使用 `java SmallWidthDpValueFiles` 命令执行代码，控制台会打印出相关信息，运行完成会在当前目录生成res文件夹，在res文件夹下生成如下内容：
 
	![SmallWidth](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/values_sw.png)

* 4.将基础dp值[BASE_DP]对应的values 即values-sw[BASE_DP]dp 文件夹下的 dimens.xml 文件作为默认dimen放入values下,其他的 values-swXXXdp 根据需要复制到res目录下
