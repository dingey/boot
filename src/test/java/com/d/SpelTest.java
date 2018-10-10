package com.d;

import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.d.service.PetService;
import com.d.web.PetController.Pet;

public class SpelTest {
	@Test
	public void test() {
		// get();
		list();
	}

	void get() {
		PetService petService = new PetService();
		Method method;
		try {
			method = petService.getClass().getDeclaredMethod("get",
					Integer.class);
			Cacheable cacheable = method.getAnnotation(Cacheable.class);
			Integer id = 1;
			Object invoke = method.invoke(petService, id);
			System.out.println("get invoke : " + invoke);

			ExpressionParser parser = new SpelExpressionParser();
			StandardEvaluationContext context = new StandardEvaluationContext();
			context.setVariable("id", id);
			String value = parser.parseExpression(cacheable.key())
					.getValue(context, String.class);
			System.out.println("get spel : " + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	void list() {
		PetService petService = new PetService();
		Method method;
		try {
			method = petService.getClass().getDeclaredMethod("list", Pet.class);
			Cacheable cacheable = method.getAnnotation(Cacheable.class);
			Pet p = new Pet();
			p.setId(new Random().nextInt(100));
			p.setName("alice" + p.getId());
			Object invoke = method.invoke(petService, p);
			System.out.println("list invoke : " + invoke);

			ExpressionParser parser = new SpelExpressionParser();
			StandardEvaluationContext context = new StandardEvaluationContext();
			context.setVariable("id", p.getId());
			context.setVariable("name", p.getName());
			context.setVariable("pet", p);
			//context.setVariable("pet.name", p.getName());
			String value = parser
					.parseExpression("'id'+#pet.id+'name:'+#pet.name")
					.getValue(context, String.class);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
