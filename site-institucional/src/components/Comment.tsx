import { useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useForm, SubmitHandler } from "react-hook-form"
import { Button, Form } from 'react-bootstrap'
import CommentSaveRequestDto from "../dto/request/CommentSaveResquestDto";
import PostRoute from "../routes/PostRoute";

/*
export function Comment() {
    const { register, handleSubmit } = useForm<CommentSaveRequestDto>()

    const onSubmit: SubmitHandler<CommentSaveRequestDto> = body => {
        PostRoute.saveComment(body).then(res => {
            if (res.status == 200) {
                navigate(`/profile/${res.data.id}?viewerId=${res.data.id}`)
            }
        }).catch(err => {
            console.log(err)
            
            Swal.fire('erro ao tentar entrar')
        })
    }

    return (
        <div className="comment-bar">
            <div className="comment-usuario">
                <h3 className="comment-usuarioName">Bruna Yumi Sato</h3>
            </div>
            <input className="comment" type="text" />
        </div>
    )
} */