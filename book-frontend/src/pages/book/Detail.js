import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Detail = (props) => {
  //const id = props.match.params.id; react dom 6버전에선  안돼서 아래와 같이 사용해야한다.... 버전...
  const { id } = useParams();
  const navigate = useNavigate();

  console.log(props);

  const [book, setBook] = useState({
    id: '',
    title: '',
    author: '',
  });

  useEffect(() => {
    fetch('http://localhost:8080/book/' + id)
      .then((res) => res.json())
      .then((res) => {
        setBook(res); //덮어씌우면 되기 때문에 ... 필요 없음
      });
  }, []);

  const deleteBook = () => {
    fetch('http://localhost:8080/book/' + id, {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          navigate('/');
        } else {
          alert('삭제실패');
        }
      });
  };

  const updateBook = () => {
    navigate('/updateForm/' + id);
  };

  return (
    <div>
      <h1>책 상세보기</h1>
      <Button variant="warning" onClick={updateBook}>
        수정
      </Button>{' '}
      <Button variant="danger" onClick={deleteBook}>
        삭제
      </Button>
      <hr />
      <h3>{book.author}</h3>
      <h1>{book.title}</h1>
    </div>
  );
};

export default Detail;
