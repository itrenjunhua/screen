# Android适配生成对应的values文件(如：values-1080x1920)

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
	![UI设计师定义的基本分辨率](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/values.png)

* 5.将values-xxx复制到项目中的res文件夹下

### DimenUtils.java类说明
 ***DimenUtils.java*** 类用于生成 ***dimens_x.xml、dimens_y.xml*** 文件，默认生成基本准则是 1dp = 2px 形式。

* 1.修改默认基本准则  
	![修改默认基本准则](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/dimen.png)
* 2.修改生成的范围(既可以在代码中修改，也可以在运行时动态设置)  
	![修改生成的范围](https://raw.githubusercontent.com/itrenjunhua/screen/master/images/dimen_base.png)
* 3.使用Java命令编译和运行生成 ***dimens_x.xml、dimens_y.xml*** 文件。
* 4.可以将生成的文件内容复制到 ***res/values文件夹下的 dimens.xml*** 文件中进行使用。
