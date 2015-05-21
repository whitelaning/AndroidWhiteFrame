Hi, I am Zack.<br>
Name: Zack White<br>
City: Guangdong, Guangzhou<br>
Pseudonym: Whitelaning / Newchapter<br>
Email:whitelaning@qq.com / whitelaning@gmail.com<br><br>
It's very easy to be different but very difficult to be better.

# AndroidWhiteFrame
这是我的结构框架
用于记录我的工具类和示例代码

####实例代码：<br>
1.WhiteAdapterDemoActivity ： 一个ViewHolder的封装示例，把每次BaseAdapater都要写的内部类ViewHolder进行的封装，之后基本不需要写ViewHolder。(201505071032）
2.MultithreadingBreakpointContinuinglyActivity ：
多文件多线程断点续传下载示例。(201505131718）
3.AsyncTaskActivity类 ： 一个AsyncTask异步下载的示例。（20105191659）

###2015.05.21
####新增：
>1. com.whitelaning.activity 中新增 AsyncTaskActivity类.该类是AsyncTask的示例。

###2015.05.13
####新增：
>1. com.whitelaning.activity 中新增 MultithreadingBreakpointContinuinglyActivity类.

###2015.05.07
####新增:
>1. com.whitelaning.whiteframe.tool 中新增 CrashHandlerTool类，该类用于获取报错信息。<br>
>2. com.whitelaning.whiteframe.tool 中新增 DensityTool类，该类用于px、dp、sp 转换。<br>
>3. com.whitelaning.whiteframe.tool 中新增 FolderTool类，该类用于对文件和文件夹的操作。<br>
>4. com.whitelaning.whiteframe.tool 中新增 LogTool类，该类用于对Log信息的打印进行统一管理。<br>
>5. com.whitelaning.whiteframe.control 中新增 DebugCollector类，该类用于统一控制当前框架的模式。<br>
>6. com.whitelaning.whiteframe.control 中新增 PathAddressCollector类，该类用于统一控制路径的设置。<br><br>

###2015.05.05
####新增：
>1.whiteframe包中新增BaseActivity类，其它Activity继承该类，进行Activity的启动、退出动画的初始化。<br>
>2.whiteframe包中新增ActivityCollector类，在BaseActivity类中添加了该类的方法，便于对整体Activity的管理。<br>
>3.whiteframe包中新增ToastViewTool类，该类为自定义Toast方法，便于显示Toast信息。<br>
>4.whiteframe包中新增WhiteViewHolder类，该类是为了Adapter的复用布局做的封装，配合whiteframe包中WhiteAdapter一起使用，一劳永逸，不在需要写ViewHolder.<br>
>5.whiteframe包中新增WhiteAdapter类，该类为封装BaseAdapter，简化了代码量。<br>
>6.whiteframe包中新增WhiteFrame类，该类继承Application，为全局获取上下文提供getContext()方法。<br>
>7.新增WhiteAdapterDemoActivity，该Activity为WhiteViewHolder类和WhiteAdapter类封装使用的实例。<br>
