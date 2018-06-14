package com.eric.face.entity;

/**
 * 脸集实体类
 * @author Chen 2018/1/26
 */
public class FaceEntity {
    private String face_token;
    private FaceRectangle face_rectangle;
    private Attributes attributes;

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public FaceRectangle getFace_rectangle() {
        return face_rectangle;
    }

    public void setFace_rectangle(FaceRectangle face_rectangle) {
        this.face_rectangle = face_rectangle;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }
}
