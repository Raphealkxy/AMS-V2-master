package com.eric.face.entity;

/**
 * 人脸矩形框的位置
 * @author Chen 2018/1/26
 */
public class FaceRectangle {
    private int top;//矩形框左上角像素点的纵坐标
    private int left;//矩形框左上角像素点的横坐标
    private int width;  //矩形框的宽度
    private int height; //矩形框的高度

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
