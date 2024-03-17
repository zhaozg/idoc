package com.github.houbb.idoc.core.handler.impl.metadata;

import com.github.houbb.idoc.api.model.config.DocConfig;
import com.github.houbb.idoc.api.model.metadata.DocField;
import com.github.houbb.idoc.common.handler.AbstractHandler;
import com.github.houbb.idoc.common.util.ObjectUtil;
import com.github.houbb.idoc.core.constant.JavaTagConstant;
import com.github.houbb.idoc.core.util.JavaTypeAliasUtil;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaField;

import java.util.List;

/**
 * 处理字段
 * @author binbin.hou
 * @since 0.0.1
 */
public class MetadataDocFieldHandler extends AbstractHandler<JavaField, DocField> {

    /**
     * 配置信息
     */
    private final DocConfig docConfig;

    public MetadataDocFieldHandler(DocConfig docConfig) {
        this.docConfig = docConfig;
    }


    @Override
    protected DocField doHandle(JavaField javaField) {
        DocField docField = new DocField();
        docField.setName(javaField.getName());
        final String type = javaField.getType().getFullyQualifiedName();
        docField.setType(type);
        final String alias = JavaTypeAliasUtil.getAliasName(docConfig.getTypeAliases(), javaField.getType());
        docField.setTypeAlias(alias);
        docField.setComment(javaField.getComment());
        // 使用 doclet，缺点：严格的 java-doc 会报错
        // 使用判断的方式，会导致处理其他特别麻烦。
        DocletTag requireTag = javaField.getTagByName(JavaTagConstant.IDOC_REQUIRE);
        DocletTag remarkTag = javaField.getTagByName(JavaTagConstant.IDOC_REMARK);

        if (ObjectUtil.isNotNull(requireTag)) {
            docField.setRequire(requireTag.getValue());
        }
        if (ObjectUtil.isNotNull(remarkTag)) {
            docField.setRemark(remarkTag.getValue());
        }

        return docField;
    }

}
