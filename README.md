# TestView

### **自定义TextView添加描边效果：StrokeTextView**

> **实现方法**：继承TextView，在TextView里新建一个描边Textview，并重写TextView的onMeasure(),onLayout(),onDraw(),setLayoutParams()方法，使得描边TextView的属性一致。并设置描边颜色，描边宽度等XML属性。

> **注意问题**：调用setText()方法改变内容时会使得描边TextView与继承TextView内容不一致，需要在onMeasure()方法里事描边TextView重新setText(),并重写继承TextView的setText()方法，加一句requestLayout()方法重新调用onMeasure()。


