package com.eric.face.entity;

/**
 * Faace++人脸结果集
 * @author Chen 2018/1/26
 */
public class FaceSetEntity {
    private String faceset_token;//FaceSet 的标识
    private String outer_id;//用户提供的FaceSet标识，如果未提供为空 /AmsFaceSetEntity的部门ID
    private String display_name;//FaceSet的名字，如果未提供为空  //脸集名称
    private String tags;//FaceSet的标签，如果未提供为空

    public FaceSetEntity() {
    }

    public FaceSetEntity(String faceset_token, String outer_id, String display_name, String tags) {
        this.faceset_token = faceset_token;
        this.outer_id = outer_id;
        this.display_name = display_name;
        this.tags = tags;
    }

    public String getFaceset_token() {
        return faceset_token;
    }

    public void setFaceset_token(String faceset_token) {
        this.faceset_token = faceset_token;
    }

    public String getOuter_id() {
        return outer_id;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
