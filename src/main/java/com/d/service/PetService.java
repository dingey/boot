package com.d.service;

import java.util.Arrays;
import java.util.List;

import com.d.annotation.LockMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.d.web.PetController.Pet;

@Service
public class PetService {
    Logger logger = LoggerFactory.getLogger(PetService.class);

    @LockMethod(key = "'id'+#id")
    public Pet get(Integer id) {
        logger.info("get pet  {} from db.", id);
        return new Pet(id, "cat-" + id);
    }

    @Cacheable(value = "list", key = "'id'+#pet.id+'name:'+#pet.name")
    public List<Pet> list(Pet pet) {
        logger.info("get pet  {} from db.", pet);
        return Arrays.asList(pet);
    }
}
