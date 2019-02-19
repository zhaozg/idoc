package com.github.houbb.idoc.api.model.metadata;

import java.util.List;

/**
 * 方法参数信息
 * @author binbin.hou
 * @since 0.0.1
 */
public final class DocMethodParameter extends BaseDoc {

    /**
     * 参数类型
     */
    private String type;

    /**
     * 当前入参下面的字段信息
     */
    private List<DocField> docFieldList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DocField> getDocFieldList() {
        return docFieldList;
    }

    public void setDocFieldList(List<DocField> docFieldList) {
        this.docFieldList = docFieldList;
    }

    @Override
    public String toString() {
        return "DocMethodParameter{" +
                "type='" + type + '\'' +
                ", docFieldList=" + docFieldList +
                "} " + super.toString();
    }
}