package org.algoritmed.mvp2.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("readJsonFromFile")
public class ReadJsonFromFile {

	private @Value("${config.webSites}") String configWebSites;
	public Map<String, Object> readConfigWebSite() {
		File file = new File(configWebSites);
		Map<String, Object> configWebSitesMap = readJsonFromFullFileName(file);
		return configWebSitesMap;
	}
	public Map<String, Object> readJsonFromFullFileName(File file) {
		Map<String, Object> readJsonFileToJavaObject = null;
		try {
			readJsonFileToJavaObject = objectMapper.readValue(file, Map.class);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return readJsonFileToJavaObject;
	}

	@Autowired	ObjectMapper objectMapper;

}
