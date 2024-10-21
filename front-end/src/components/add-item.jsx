import React, { useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import Axios from "axios";
import { useNavigate } from "react-router-dom";
import { savePost } from "../api/api-connector";

function CreatePostForm() {
    const [post, setPost] = useState({
        title: '',
        author: '',
        content: '',
    });
    const navigate = useNavigate();

    const handleChange = (event) => {
        console.log(event.target.value);
        setPost({
            ...post,
            [event.target.name]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault(); // Prevent default form submission
        console.log("Ebal" + post);
        savePost(post).then((r) => console.log(r));
        console.log('Новый пост:', post);
         navigate("/");
    };

    return (
        <Container style={{ width: '30%', marginTop: '10%' }}>
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="formBasicTitle">
                    <Form.Label className={'text-primary'} style={{ marginTop: '10px' }}>
                        Title
                    </Form.Label>
                    <Form.Control type="text" name="title" value={post.title} onChange={handleChange} />
                </Form.Group>

                <Form.Group controlId="formBasicAuthor" style={{ marginTop: '10px' }}>
                    <Form.Label className={'text-primary'}>Author</Form.Label>
                    <Form.Control type="text" name="author" value={post.author} onChange={handleChange} />
                </Form.Group>

                <Form.Group controlId="formBasicContent">
                    <Form.Label className={'text-primary'} style={{ marginTop: '10px' }}>
                        Content
                    </Form.Label>
                    <Form.Control as="textarea" rows={3} name="content" value={post.content} onChange={handleChange} />
                </Form.Group>

                <Button className={'btn-lg'} variant="primary" type="submit" style={{ marginTop: '10px' }}>
                    Save
                </Button>
            </Form>
        </Container>
    );
}

export default CreatePostForm;
