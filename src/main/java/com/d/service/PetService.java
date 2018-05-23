package com.d.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.d.web.PetController.Pet;

@Service
public class PetService {
	Logger logger = LoggerFactory.getLogger(PetService.class);

	@Cacheable(value = "pet", key = "#id")
	public Pet get(Integer id) {
		logger.info("get pet  {} from db.", id);
		return new Pet(id, "cat-" + id);
	}
}
