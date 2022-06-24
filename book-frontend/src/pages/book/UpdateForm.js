import React, { useEffect, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';

const UpdateForm = () => {
  const navigate = useNavigate();

  const { id } = useParams();

  const [book, setBook] = useState({
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

  const changeValue = (e) => {
    setBook({
      ...book, // 기존값 지워지지 않게
      [e.target.name]: e.target.value,
    });

    console.log(e.target.name + ' ' + e.target.value);
  };

  const submitBook = (e) => {
    // e.prventDefault(); // submit이 action을 안타고 자기 할일을 그만함. ( 새로 고침 안함 )
    e.preventDefault();
    fetch('http://localhost:8080/book/' + id, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(book), // java script object를 json 으로 변경하여 송신
    })
      .then((res) => {
        console.log(1, res);
        if (res.status === 200) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        if (res != null) {
          console.log(2, res);
          navigate('/book/' + id);
        } else {
          alert('책 수정에 실패하였습니다');
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    // <div>
    //   <h1>책 등록하기</h1>
    // </div>
    // https://react-bootstrap.github.io/forms/overview/
    <Form onSubmit={submitBook}>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Title</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter title"
          onChange={changeValue}
          name="title"
          value={book.title}
        />
        <Form.Text className="text-muted">알림 문구가 필요 하면 작성</Form.Text>
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Author</Form.Label>
        <Form.Control
          type="text"
          placeholder="Enter auther"
          onChange={changeValue}
          name="author"
          value={book.author}
        />
        <Form.Text className="text-muted">알림 문구가 필요 하면 작성</Form.Text>
      </Form.Group>

      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
};

export default UpdateForm;
