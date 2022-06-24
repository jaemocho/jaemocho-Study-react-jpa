package com.cos.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;

//@rEPOSITORY 적어야 스프링 IOC에 빈으로 들옥이 되는데 
// JpaRepository를 extends 하면 생략가능함.
// JpaRepository는 CRUD 가지고 있다. 
public interface BookRepository extends JpaRepository<Book, Long> {

}
