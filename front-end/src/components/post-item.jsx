import {Card} from "react-bootstrap";
import './post.css'


const Post = ({ post }) => {
    return (
        <Card className="post-card" style={{backdropFilter: "blur(2px)"}}>
            <Card.Header className="post-card-header">
                <Card.Title className="post-card-title">Title: {post.title}</Card.Title>
                <Card.Text>Author: {post.author}</Card.Text>
            </Card.Header>
            <Card.Body>
                <Card.Text className="post-card-text">Content: {post.content}</Card.Text>
            </Card.Body>
        </Card>
    );
}

export default Post;