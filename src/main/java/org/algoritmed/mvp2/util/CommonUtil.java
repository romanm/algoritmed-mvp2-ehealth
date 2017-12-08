package org.algoritmed.mvp2.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("commonUtil")
public class CommonUtil {

	SimpleDateFormat dateHHmm = new SimpleDateFormat("E, HH:mm");
	
	public Map<String, Object> test() {
		Map<String, Object> model = new HashMap<String, Object>();
		Date today = new Date();
		model.put("date", today);
		model.put("message", "Hello World");
		model.put("title", "_"+dateHHmm.format(today));
		return model;
	}

}
