package com.github.houbb.idoc.common.model;

import java.util.List;
import java.util.Map;

/**
 * 简化方法信息
 * @author binbin.hou
 * @since 0.0.1
 */
public class SimplifyDocMethod extends SimplifyDocBase {

    /**
     * 方法入参
     */
    private List<SimplifyDocField> params;

    /**
     * 方法出参
     */
    private SimplifyDocClass returns;

    /**
     * 存放参数详情
     */
    private Map<String, List<SimplifyDocField>> paramDetails;


    private String signature;

    public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
     * 存放异常详情
     */
    private List<SimplifyDocClass> exceptions;


    public List<SimplifyDocField> getParams() {
        return params;
    }

    public void setParams(List<SimplifyDocField> params) {
        this.params = params;
    }

    public SimplifyDocClass getReturn() {
        return returns;
    }

    public void setReturn(SimplifyDocClass returns) {
        this.returns = returns;
    }

    public Map<String, List<SimplifyDocField>> getParamDetails() {
        return paramDetails;
    }

    public void setParamDetails(Map<String, List<SimplifyDocField>> paramDetails) {
        this.paramDetails = paramDetails;
    }

    public List<SimplifyDocClass> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<SimplifyDocClass> exceptions) {
        this.exceptions = exceptions;
    }

}
