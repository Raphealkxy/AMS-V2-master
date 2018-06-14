package com.eric.face.entity;

/**
 * 人脸质量
 * @author Chen 2018/1/26
 */
public class FaceQuality {
    private float value;//值为人脸的质量判断的分数，是一个浮点数，范围 [0,100]，小数点后 3 位有效数字。
    private float threshold;//表示人脸质量基本合格的一个阈值，超过该阈值的人脸适合用于人脸比对。

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}
