package com.smy.common.defer;

import java.util.List;

public interface SuccessCallback extends Callback {

	public void callback(List<DeferResult> results);
}
