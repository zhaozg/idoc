package com.github.houbb.idoc.common.handler.impl.simplify;

import com.github.houbb.idoc.api.model.metadata.DocMethodReturn;
import com.github.houbb.idoc.common.handler.IHandler;
import com.github.houbb.idoc.common.model.SimplifyDocClass;
import com.github.houbb.idoc.common.util.CommentUtil;

/**
 * 通用类转换工具
 * @author binbin.hou
 * @since 0.0.1
 */
public class SimplifyMethodReturnHandler implements IHandler<DocMethodReturn, SimplifyDocClass> {
    @Override
    public SimplifyDocClass handle(DocMethodReturn docReturn) {
        SimplifyDocClass simplifyDocClass = new SimplifyDocClass();

        simplifyDocClass.setName(docReturn.getName());
        simplifyDocClass.setPackageName(docReturn.getPackageName());
        simplifyDocClass.setComment(docReturn.getReturnComment());
        simplifyDocClass.setRemark(docReturn.getRemark());
        if (docReturn.getIsArray()) {
            simplifyDocClass.setTypeAlias(docReturn.getName()+"[]");
        } else
            simplifyDocClass.setTypeAlias(docReturn.getName());

        //v0.2.0 添加第一行备注，避免过多，导致格式错乱
        String commentFirstLine = CommentUtil.getFirstLine(docReturn.getComment());
        simplifyDocClass.setCommentFirstLine(commentFirstLine);

        return simplifyDocClass;
    }

}
