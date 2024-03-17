package com.github.houbb.idoc.api.model.metadata;

import java.util.Map;

import com.thoughtworks.qdox.model.expression.AnnotationValue;
/**
 * 注解信息
 * @author binbin.hou
 * @since 0.0.1
 */
public final class DocAnnotation extends BaseDoc {

    /**
     * 属性配置
     * @since 0.0.2
     */
    private Map<String, AnnotationValue> properties;

    /**
     * 名称参数信息
     * @since 0.0.2
     */
    private Map<String, Object> namedParameters;

    public Map<String, AnnotationValue> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, AnnotationValue> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getNamedParameters() {
        return namedParameters;
    }

    public void setNamedParameters(Map<String, Object> namedParameters) {
        this.namedParameters = namedParameters;
    }

}
