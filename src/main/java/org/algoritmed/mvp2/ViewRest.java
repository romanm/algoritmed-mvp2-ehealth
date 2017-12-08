package org.algoritmed.mvp2;

import java.util.Map;

import org.algoritmed.mvp2.util.CommonUtil;
import org.algoritmed.mvp2.util.ReadJsonFromFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v")
public class ViewRest {
	@GetMapping("{page1}")
	public String home(@PathVariable String page1, Map<String, Object> model) {
		model.putAll(commonUtil.test());
		return "view-template";
	}
	@Autowired	CommonUtil commonUtil;
	@Autowired	ReadJsonFromFile readJsonFromFile;
}
