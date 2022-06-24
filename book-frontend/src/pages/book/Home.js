import React, { useEffect, useState } from 'react';
import BookItem from '../../components/BookItem';

const Home = () => {
  const [books, setBooks] = useState([]);

  //함수 실행 시 최초 한번 실행되는 것
  useEffect(() => {
    // 첫번째 then에 데이터가 들어온

    //  CORS 오류 Backend에서 외부에서 오는 JAVASCRIPT 오는 것을 막는다  @CrossOrigin 추가 필요
    fetch('http://localhost:8080/book')
      .then((res) => res.json()) // res=> res.json 응답이 오면 java script object로 변경
      .then((res) => {
        // 위에서 받은 걸 넘겨 받는다
        console.log(1, res);
        setBooks(res); // 위에 setBooks 에 데이터 전달 key 가 없으면 추가/삭제 되었을 때 전체를 다시 그린다 key가 있으면 변경된 부분만 다시 그린다
      }); // 비동기 홤수
  }, []); // 뒤에 배열 []이 의미하는게 어디에도 의존하고 있지 않는다. 그래야 한번만 실행

  return (
    <div>
      {books.map((book) => (
        <BookItem key={book.id} book={book} />
      ))}
    </div>
  );
};

export default Home;
