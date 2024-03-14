<#if class?? && class.commentFirstLine??>
## ${class.name}-${class.commentFirstLine!"暂无说明"}

${class.remark!class.comment!"暂无说明"}

* 包名: ${class.packageName!""}
* 类型: ${class.name}

<#if class.methods??>
<#list class.methods as method>
<#if method.commentFirstLine??>
### ${method.name}-${method.commentFirstLine!"暂无说明"}

${method.remark!method.comment!"暂无说明"}

> ${method.signature}

<#if method.params?? && (method.params?size > 0)>
#### 方法参数

| 字段 | 说明 | 字段类型 | 备注 |
|:---- |:---- |:-------- |:---- |
<#list method.params as param>
| ${param.name} | ${param.comment!""} | ${param.typeAlias!""} | ${param.remark!""} |
</#list>

<#if method.paramDetails?? && (method.paramDetails?size > 0)>
##### 参数详情

<#list method.paramDetails?keys as detailName>

- ${detailName} 入参详情

| 字段 | 说明 | 字段类型 | 是否必填 | 备注 |
|:---|:---|:---|:---|:----|
<#list method.paramDetails[detailName] as param>
| ${param.name} | ${param.comment!""} | ${param.typeAlias!""} | ${param.require!""} | ${param.remark!""} |
</#list>

</#list>
</#if>
</#if>
<#if method.return??>
#### 返回值

* 类型: ${method.return.typeAlias!"无"}
<#if method.return.comment??>
* 说明: ${method.return.comment!""}
</#if>
<#if method.return.remark??>
* 备注: ${method.return.remark!""}
</#if>
<#if method.exceptions?? && (method.exceptions?size > 0)>

#### 异常

<#list method.exceptions as except>
* ${except.name!except.fullName!""}: ${except.remark!except.comment!""}
</#list>
</#if>

</#if>
</#if>
</#list>
</#if>
</#if>
