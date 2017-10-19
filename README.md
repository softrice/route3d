## Route3D 
![roting3d.gif](http://upload-images.jianshu.io/upload_images/2826549-9c039c36691867e9.gif?imageMogr2/auto-orient/strip)

### SplitLine描述图片状态
    
```
public class SplitLine {
    public float angle;
    public float leftFoldAngle;
    public float rightFoldAngle;

    public void set(float angle,float leftFoldAngle,float rightFoldAngle) {
        this.angle = angle;
        this.leftFoldAngle = leftFoldAngle;
        this.rightFoldAngle = rightFoldAngle;
    }

}
```
angle代表折叠线的角度
leftFoldAngle和rightFoldAngle分别代表左右面的夹角

> 度数的正负方向文字描述麻烦 可以在demo中滑动进度条来获知

### 画出左右面
* 旋转面到折叠面(左面)

```
canvas.translate(centerX, centerY);
camera.rotateZ(splitLine.angle);
camera.rotateX(splitLine.rightFoldAngle);
camera.applyToCanvas(canvas);
```

* 把图片旋转和位移后画入裁剪区域

```
matrix.preRotate(splitLine.angle);
matrix.preTranslate(-imgSize / 2,-imgSize / 2);
canvas.clipRect(-500, 0, 500, -500);
canvas.drawBitmap(bitmap,matrix,paint);
```
### 动画实现

ObjectAnimator和AnimatorSet配合使用
```
routeView.setSplitLine(-90,0,0);
ObjectAnimator animator1 = ObjectAnimator.ofFloat(route_view1,"leftFoldAngle",0,-45);
animator1.setInterpolator(new LinearInterpolator());
animator1.setDuration(1000);
ObjectAnimator animator2 = ObjectAnimator.ofFloat(route_view1,"angle",-90,180);
animator2.setInterpolator(new DecelerateInterpolator());
animator2.setDuration(1500);
ObjectAnimator animator3 = ObjectAnimator.ofFloat(route_view1,"rightFoldAngle",0,45);
animator3.setInterpolator(new LinearInterpolator());
animator3.setDuration(1000);

AnimatorSet animatorSet = new AnimatorSet();
animatorSet.playSequentially(animator1, animator2,animator3);
animatorSet.start();
```


