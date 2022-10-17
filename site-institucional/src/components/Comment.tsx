import { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useForm, SubmitHandler } from "react-hook-form"
import { Button, Form, FormGroup } from 'react-bootstrap'
import CommentSaveRequestDto from "../dto/request/CommentSaveResquestDto";
import PostRoute from "../routes/PostRoute";
import withReactContent from "sweetalert2-react-content";
import Swal from 'sweetalert2'


export function Comment() {

   /* const navigate = useNavigate();

    const MySwal = withReactContent(Swal)

    const { register, handleSubmit } = useForm<CommentSaveRequestDto>()

    const onSubmit: SubmitHandler<CommentSaveRequestDto> = postId => {
        PostRoute.saveComment(any, CommentSaveRequestDto).then(res => {
            if (res.status == 200) {
                navigate(`/posts/${postId}/comments}`, body);
            }
        }).catch(err => {
            console.log(err)

            Swal.fire('erro ao comentar')
        })
    }

    return (
        <div className="comment-bar">
            <div className="comment-usuario">
                <h3 className="comment-usuarioName">Bruna Yumi Sato</h3>
            </div>
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Form.Group controlId="formGroupComment">
                    <Form.Control
                        {...register("text")}
                        type="text"
                        placeholder="Comentário"
                        className="comment"
                    />
                </Form.Group>
            </Form>

        </div>
    ) */
}