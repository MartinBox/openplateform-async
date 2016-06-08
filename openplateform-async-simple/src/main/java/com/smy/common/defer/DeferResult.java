package com.smy.common.defer;

public class DeferResult {

	private boolean success;
	private Object result;
	private Exception exception;

	public DeferResult() {
		super();
	}

	public DeferResult(boolean success, Object result, Exception exception) {
		this.success = success;
		this.result = result;
		this.exception = exception;
	}

	public boolean isSuccess() {
		return success;
	}

	public Object getResult() {
		return result;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
