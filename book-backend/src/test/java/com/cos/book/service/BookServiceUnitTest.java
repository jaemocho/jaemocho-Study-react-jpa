package com.cos.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;

/**
 * 단위테스트(Service 관련된 것들만 메모리에 )
 * BookRepository => 가짜 객체를 만들 수 있음.
 *
 */

@ExtendWith(SpringExtension.class)
public class BookServiceUnitTest {

	@InjectMocks // BookService 객체가 만들어질 때 BookServiceUnitTest 파일에 @Mock로 등록된 모든 것들을 주입받는다. 
	private BookService bookService;
	
	@Mock // mokito라는 memory 공간에 뜬다
	private BookRepository bookRepository;
	
	@Test
	public void 저장하기_테스트() {
		
		//BODMocikto 방식 
		//given
		Book book = new Book();
		book.setTitle("책제목1");
		book.setAuthor("책저자1");
		
		//stub - 동작 지정
		when(bookRepository.save(book)).thenReturn(book);
		
		// test execute
		Book bookEntity = bookService.저장하기(book);
		
		// then
		assertEquals(bookEntity, book);
	}
	
	
}
