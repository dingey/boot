package com.d.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author d
 */
@RestController
public class TableController {
	@GetMapping(path = "/table.data")
	public Object list() {
		List<Map<String, String>> m = new ArrayList<>();
		Map<String, String> a = new HashMap<>();
		a.put("id", "1");
		a.put("name", "alice");
		a.put("price", "15.2");
		m.add(a);
		Map<String, String> a1 = new HashMap<>();
		a1.put("id", "2");
		a1.put("name", "bob");
		a1.put("price", "19.2");
		m.add(a1);
		return m;
	}
}
