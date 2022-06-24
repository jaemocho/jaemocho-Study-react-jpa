package com.cos.book.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

//단위테스트(DB 관련된 Bean이 IoC에 등록되면 됨)

@Transactional
@AutoConfigureTestDatabase(replace= Replace.ANY) //가짜 디비로 테스트, Replace.NONE 는 실제 DB로 테스트 
@DataJpaTest // Repository들을 다 Ioc에 등록해둠 
public class BookRepositoryUnitTest {

	@Autowired  // Mock로 띄울 필요 없다 DataJpaTest 이 해준다. 
	private BookRepository bookRepository;
	
	@Test
	public void save_테스트() {
		// given
		Book book = new Book(null, "책제목1", "책저자1");
		
		// when
		Book bookEntity = bookRepository.save(book);
		
		// then
		assertEquals("책제목1", bookEntity.getTitle());
	}
}
