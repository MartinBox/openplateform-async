package com.smy.common.defer;

import java.util.List;

public interface AlwaysCallback extends Callback {

	public void callback(boolean success, List<DeferResult> results);
}
