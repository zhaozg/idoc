package com.github.houbb.idoc.api.model.metadata;

import java.util.List;

/**
 * 方法返回值信息
 * @author binbin.hou
 * @since 0.0.1
 */
public class DocMethodReturn extends BaseDoc {

    /**
     * 返回备注信息
     */
    private String returnComment;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 包名称
     */
    private String packageName;

    /*
     * 返回array
     */
    private boolean array;


    public String getReturnComment() {
        return returnComment;
    }

    public void setReturnComment(String returnComment) {
        this.returnComment = returnComment;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean getIsArray() {
        return array;
    }

    public void setIsArray(boolean array) {
        this.array = array;
    }

}
