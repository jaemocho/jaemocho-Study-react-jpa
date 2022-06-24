package com.cos.book.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 통합테스트(모든Bean들을 똑같이 IoC에 올리고 테스트하는 것)
 * WebEnvironment.MOCK = 실제 톰캣을 올리는 게 아니라 다른톰캣으로 테스트
 * WebEnvironment.RANDOM_POR = 실제 톰캣으로 테스트
 * @AutoConfigureMockMvc MockMvc를 Ioc 에 등록해줌
 * @Transactional은 각 각의 테스트함수가 종료될 때마다 트랜잭션을 rollback해주는 어노테이션
 */

//
@Slf4j
@Transactional //를 붙여야 독립적으로 테스트를 할 수 있다, 끝나고 rollback
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) 
public class BookControllerIntegreTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	
	//@BeforeEach/@AfterEach 를 이용해서 초기 데이터 입력/삭제도 가능 
	
	@BeforeEach
	public void init() { //AUTO INCREMENT 번호 초기화 앞에서 테스트 하고나면 숫자가 올라간 상태라 초기화 필요 
		entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
		// MYSQL일 때 아래 쿼리 사용 
		//entityManager.createNativeQuery("ALTER TABLE book AUTO_INCREMENT = 1").executeUpdate();
//		
//		List<Book> books = new ArrayList<>();
//		books.add(new Book(1L, "스프링부트 따라하기", "코스"));
//		books.add(new Book(2L, "리엑트 따라하기", "코스"));
//		books.add(new Book(3L, "JUnit 따라하기", "코스"));
//		bookRepository.saveAll(books);
	}
	
//	@AfterEach
//	public void end() { //AUTO INCREMENT 번호 초기화 앞에서 테스트 하고나면 숫자가 올라간 상태라 초기화 필요 
//		bookRepository.deleteAll();
//	}
//	
	@Test
	public void test1() {
		
	}
	
	@Test
	public void test2() {
		
	}
	
	@Test
	public void save_테스트() throws Exception {
//		log.info("save_테스트() 시작 ==============================================================");
//		Book book = bookService.저장하기(new Book(1L, "제목", "코스"));
//		log.info("book: " + book);  
		
		//given (테스트를 하기 위한 준비)
		Book book = new Book(null, "스프링 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book);
//		log.info(content);
		
		// stub이 필요 없다 (given) 
		
		// when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then (검증) 
		resultAction
		  .andExpect(status().is(200))
		  .andExpect(jsonPath("$.title").value("스프링 따라하기"))
		  .andDo(MockMvcResultHandlers.print());
	}
	
	
	@Test
	public void findAll_테스트() throws Exception {
		
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "스프링부트 따라하기", "코스"));
		books.add(new Book(2L, "리엑트 따라하기", "코스"));
		books.add(new Book(3L, "JUnit 따라하기", "코스"));
		bookRepository.saveAll(books);
		
		//when 
		ResultActions resultAction = mockMvc.perform(get("/book")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		//then 
		resultAction
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", Matchers.hasSize(3)))
		.andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
		.andDo(MockMvcResultHandlers.print());
		

	}
	
	@Test
	public void findById_테스트() throws Exception{
		
		// given
		Long id = 1L;
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "스프링부트 따라하기", "코스"));
		books.add(new Book(2L, "리엑트 따라하기", "코스"));
		books.add(new Book(3L, "JUnit 따라하기", "코스"));
		bookRepository.saveAll(books);
	
		// when
		ResultActions resultAction = mockMvc.perform(get("/book/{id}", id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title").value("스프링부트 따라하기"))
		.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void update_테스트() throws Exception{
		
		// given
		Long id = 1L;
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "스프링부트 따라하기", "코스"));
		books.add(new Book(2L, "리엑트 따라하기", "코스"));
		books.add(new Book(3L, "JUnit 따라하기", "코스"));
		bookRepository.saveAll(books);
		
		
		Book book = new Book(null, "C++ 따라하기", "코스");
		String content = new ObjectMapper().writeValueAsString(book);
		
		
		// when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(put("/book/{id}",id)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// then
		resultAction
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title").value("C++ 따라하기"))
		.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void delete_테스트() throws Exception{
		
		// given
		// given
		Long id = 1L;
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "스프링부트 따라하기", "코스"));
		books.add(new Book(2L, "리엑트 따라하기", "코스"));
		books.add(new Book(3L, "JUnit 따라하기", "코스"));
		bookRepository.saveAll(books);
		
		
		// when (테스트 실행)
		ResultActions resultAction = mockMvc.perform(delete("/book/{id}",id)
				.accept(MediaType.TEXT_PLAIN));
		
		// then
		resultAction
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
		
		MvcResult requetResult = resultAction.andReturn();
		String result = requetResult.getResponse().getContentAsString();
		
		assertEquals("ok", result);
	}


}
