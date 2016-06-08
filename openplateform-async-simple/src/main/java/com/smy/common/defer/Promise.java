package com.smy.common.defer;

import java.util.ArrayList;
import java.util.List;

public class Promise {

	private boolean success = true;
	private List<DeferResult> results;

	public Promise() {
		//增加一个默认值，避免使用时出现空指针
		this.results = new ArrayList<DeferResult>(4);
	}

	public Promise(boolean success, List<DeferResult> results) {
		this.success = success;
		this.results = results;
	}

	public Promise then(SuccessCallback successCallback, FailCallback failCallback) {
		if (success) {
			if (successCallback != null) {
				successCallback.callback(results);
			}
		} else {
			if (failCallback != null) {
				failCallback.callback(results);
			}
		}
		return this;
	}

	public Promise success(SuccessCallback successCallback) {
		if (success) {
			if (successCallback != null) {
				successCallback.callback(results);
			}
		}
		return this;
	}

	public Promise fail(FailCallback failCallback) {
		if (!success) {
			if (failCallback != null) {
				failCallback.callback(results);
			}
		}
		return this;
	}

	public void always(AlwaysCallback alwaysCallback) {
		if (alwaysCallback != null) {
			alwaysCallback.callback(success, results);
		}
	}

}
