package com.soooof.route3d.route3dview;

/**
 * 描述图片的状态 使用的是3d系的坐标系
 * Created by soooof on 17/10/17.
 */
public class SplitLine {
    public float angle;//折叠线的折叠角度 与x轴的夹角 y轴正方向为正
    public float leftFoldAngle;//折叠线 左面的折叠角度 与xy面的夹角 z轴正方向为正
    public float rightFoldAngle;//折叠线 右面的折叠角度 与xy面的夹角 z轴负方向为正

    public void set(float angle,float leftFoldAngle,float rightFoldAngle) {
        this.angle = angle;
        this.leftFoldAngle = leftFoldAngle;
        this.rightFoldAngle = rightFoldAngle;
    }

}
