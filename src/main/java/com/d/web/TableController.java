package com.d.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author d
 */
@RestController
public class TableController {
	@GetMapping(path = "/table.data")
	public Object list(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, String sortName, String sortOrder) {
		List<Map<String, Object>> m = new ArrayList<>();
		for (int i = 0; i < pageSize; i++) {
			Map<String, Object> a = new HashMap<>();
			a.put("id", pageNum * pageSize + i);
			a.put("name", "alice"+i);
			a.put("price", 6+i);
			m.add(a);
		}
		Pager<Map<String, Object>> p = new Pager<Map<String, Object>>();
		p.setPageNumber(pageNum);
		p.setPageSize(pageSize);
		p.setRows(m);
		p.setTotal(100);
		return p;
	}

	class Pager<T> {
		int pageSize;
		int pageNumber;
		String sortName;
		String sortOrder;
		List<T> rows;
		int total;

		public List<T> getRows() {
			return rows;
		}

		public void setRows(List<T> rows) {
			this.rows = rows;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

		public String getSortName() {
			return sortName;
		}

		public void setSortName(String sortName) {
			this.sortName = sortName;
		}

		public String getSortOrder() {
			return sortOrder;
		}

		public void setSortOrder(String sortOrder) {
			this.sortOrder = sortOrder;
		}

	}
}
