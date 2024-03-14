package com.github.houbb.idoc.common.handler.impl.simplify;

import com.github.houbb.idoc.api.model.metadata.DocClass;
import com.github.houbb.idoc.api.model.metadata.DocField;
import com.github.houbb.idoc.api.model.metadata.DocMethod;
import com.github.houbb.idoc.api.model.metadata.DocMethodParameter;
import com.github.houbb.idoc.api.model.metadata.DocMethodReturn;
import com.github.houbb.idoc.common.handler.IHandler;
import com.github.houbb.idoc.common.model.SimplifyDocField;
import com.github.houbb.idoc.common.model.SimplifyDocMethod;
import com.github.houbb.idoc.common.model.SimplifyDocClass;
import com.github.houbb.idoc.common.util.CollectionUtil;
import com.github.houbb.idoc.common.util.CommentUtil;
import com.github.houbb.idoc.common.util.ObjectUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 简化方法属性处理类
 * @author binbin.hou
 * date 2019/2/15
 * @since 0.0.1
 */
public class SimplifyMethodHandler implements IHandler<DocMethod, SimplifyDocMethod> {
    @Override
    public SimplifyDocMethod handle(DocMethod docMethod) {
        if(null == docMethod) {
            return null;
        }
        SimplifyDocMethod commonDocMethod = new SimplifyDocMethod();
        commonDocMethod.setComment(docMethod.getComment());
        commonDocMethod.setRemark(docMethod.getRemark());
        commonDocMethod.setName(docMethod.getName());
        commonDocMethod.setSignature(docMethod.getSignature());

        //v0.2.0 添加第一行备注，避免过多，导致格式错乱
        String commentFirstLine = CommentUtil.getFirstLine(commonDocMethod.getComment());
        commonDocMethod.setCommentFirstLine(commentFirstLine);

        // 处理入参
        final List<SimplifyDocField> params = buildParams(docMethod.getDocMethodParameterList());
        commonDocMethod.setParams(params);
        commonDocMethod.setParamDetails(buildFieldDetails(params));

        // 处理出参
        final SimplifyDocClass ret = buildRuturn(docMethod.getDocMethodReturn());
        commonDocMethod.setReturn(ret);

        // 处理异常
        final List<SimplifyDocClass> excetions = buildExceptions(docMethod.getExceptionList());
        commonDocMethod.setExceptions(excetions);

        return commonDocMethod;
    }

    /**
     * 构建入参信息
     * @param docMethodParameterList 原始参数列表
     * @return 构建后的参数结果
     */
    private List<SimplifyDocField> buildParams(final List<DocMethodParameter> docMethodParameterList) {
        return CollectionUtil.buildList(docMethodParameterList, new SimplifyParamFieldHandler());
    }

    /**
     * 构建返回值结果
     * @param docMethodReturn 返回结果
     * @return 构建后的参数列表
     */
    private SimplifyDocClass buildRuturn(final DocMethodReturn docMethodReturn) {
        SimplifyMethodReturnHandler handle = new SimplifyMethodReturnHandler();
        return handle.handle(docMethodReturn);
    }

    private List<SimplifyDocClass> buildExceptions(final List<DocClass> docMethodExceptionList) {
        if(CollectionUtil.isEmpty(docMethodExceptionList)) {
            return null;
        }
        return CollectionUtil.buildList(docMethodExceptionList, new SimplifyClassHandler());
    }

    /**
     * 构建出参/入参字段明细
     * @param fields 参数列表
     * @return 结果集合
     */
    private Map<String, List<SimplifyDocField>> buildFieldDetails(final List<SimplifyDocField> fields) {
        if(CollectionUtil.isEmpty(fields)) {
            return null;
        }

        final Map<String, List<SimplifyDocField>> map = new LinkedHashMap<>();

        for(SimplifyDocField docField : fields) {
            final String name = docField.getName();
            List<SimplifyDocField> entries = docField.getEntries();
            if(CollectionUtil.isEmpty(entries)) {
                continue;
            }
            map.put(name, entries);

            // 循环处理2层-可以优化
            for(SimplifyDocField entryDocField : entries) {
                final List<SimplifyDocField> entryFields = entryDocField.getEntries();
                if(CollectionUtil.isNotEmpty(entryFields)) {
                    final String entryName = entryDocField.getName();
                    map.put(entryName, entryFields);
                }
            }
        }
        return map;
    }

}
