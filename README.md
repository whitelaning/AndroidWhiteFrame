# AndroidWhiteFrame
这是我的结构框架
用于记录我的工具类和示例代码

###2015.05.05
####新增：
>1.whiteframe包中新增BaseActivity类，其它Activity继承该类，进行Activity的启动、退出动画的初始化。<br>
>2.whiteframe包中新增ActivityCollector类，在BaseActivity类中添加了该类的方法，便于对整体Activity的管理。<br>
>3.whiteframe包中新增ToastViewTool类，该类为自定义Toast方法，便于显示Toast信息。<br>
>4.whiteframe包中新增WhiteViewHolder类，该类是为了Adapter的复用布局做的封装，配合whiteframe包中WhiteAdapter一起使用，一劳永逸，不在需要写ViewHolder.<br>
>5.whiteframe包中新增WhiteAdapter类，该类为封装BaseAdapter，简化了代码量。<br>
>6.whiteframe包中新增WhiteFrame类，该类继承Application，为全局获取上下文提供getContext()方法。<br>
>7.新增WhiteAdapterDemoActivity，该Activity为WhiteViewHolder类和WhiteAdapter类封装使用的实例。<br>
