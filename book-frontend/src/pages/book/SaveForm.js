import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const SaveForm = () => {
  const navigate = useNavigate();

  const [book, setBook] = useState({
    title: '',
    author: '',
  });

  const changeValue = (e) => {
    setBook({
      ...book, // 기존값 지워지지 않게
      [e.target.name]: e.target.value,
    });

    // console.log(e.target.name + ' ' + e.target.value);
  };

  const submitBook = (e) => {
    // e.prventDefault(); // submit이 action을 안타고 자기 할일을 그만함. ( 새로 고침 안함 )
    e.preventDefault();
    fetch('http://localhost:8080/book', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(book), // java script object를 json 으로 변경하여 송신
    })
      .then((res) => {
        console.log(1, res);
        if (res.status === 201) {
          return res.json();
        } else {
          return null;
        }
      })
      .then((res) => {
        if (res != null) {
          navigate('/');
        } else {
          alert('책 등록에 실패하였습니다');
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
        />
        <Form.Text className="text-muted">알림 문구가 필요 하면 작성</Form.Text>
      </Form.Group>

      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
};

export default SaveForm;
