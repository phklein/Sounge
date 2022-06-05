import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import { SubmitHandler, useForm } from "react-hook-form";
import Swal from "sweetalert2";
import { IListGenreName } from "../../../../components/MultiForm";
import PostSaveRequestDto from "../../../../dto/request/PostSaveRequestDto";
import { GenreNameEnum } from "../../../../enums/GenreNameEnum";
import PostRoute from "../../../../routes/PostRoute";
import "./ProfilePost.style.css";

const defaultListGenreNameValue: IListGenreName = {
  size: 0,
  genres: [],
};

let idPost: any = undefined;

const ProfilePost = () => {
  const { register, handleSubmit } = useForm<PostSaveRequestDto>();
  const [listGenreName, setListGenreName] = useState<IListGenreName>(
    defaultListGenreNameValue
  );

  const onSubmit: SubmitHandler<PostSaveRequestDto> = (data) => {
    PostRoute.save(data)
      .then((res) => {
        if (res.status == 201) {
          Swal.fire("post criado com sucesso");
          idPost = res.data.valueOf;
        }
      })
      .catch((err) => {
        console.log(err);
        Swal.fire("erro ao tentar realizar o post");
      });
  };

  const handleAddFromListChangeGenre = (value: GenreNameEnum) => {
    console.log(value);
    listGenreName.genres.push(value);
    setListGenreName(listGenreName);
    console.log(listGenreName);
  };
  return (
    <div className="profileShowcasePost">
      <div className="profileShowcaseWrite">
        <div className="post-container">
          <div className="form-post">
            <Form onSubmit={handleSubmit(onSubmit)}>
              <Form.Group controlId="formGroupText">
                <Form.Control
                  {...register("text")}
                  type="text"
                  placeholder="O que você está pensando?"
                  className="campo input-all"
                />
              </Form.Group>
              <Form.Group controlId="formGroupMediaUrl">
                <div className="image-post">
                  <label htmlFor="arquivo">adicionar imagem</label>
                  <input type="file" name="arquivo" id="arquivo" />
                </div>
                <Form.Control
                  {...register("mediaUrl")}
                  type="image"
                  className="image"
                />
              </Form.Group>
              <Form.Group controlId="formGroupGenres">
                <div className="dropdown">
                  <div className="genres">Adicionar Gêneros</div>
                  <div className="submenu">
                    <div
                      className="item"
                      onClick={(event) =>
                        handleAddFromListChangeGenre(GenreNameEnum.ROCK)
                      }
                    >
                      Rock
                    </div>
                    <div
                      className="item"
                      onClick={(event) =>
                        handleAddFromListChangeGenre(GenreNameEnum.POP)
                      }
                    >
                      Pop
                    </div>
                    <div
                      className="item"
                      onClick={(event) =>
                        handleAddFromListChangeGenre(GenreNameEnum.PAGODE)
                      }
                    >
                      Pagode
                    </div>
                    <div
                      className="item"
                      onClick={(event) =>
                        handleAddFromListChangeGenre(GenreNameEnum.SERTANEJO)
                      }
                    >
                      Sertanejo
                    </div>
                    <div
                      className="item"
                      onClick={(event) =>
                        handleAddFromListChangeGenre(GenreNameEnum.METAL)
                      }
                    >
                      Metal
                    </div>
                    <div
                      className="item"
                      onClick={(event) =>
                        handleAddFromListChangeGenre(GenreNameEnum.INDIE)
                      }
                    >
                      Indie
                    </div>
                  </div>
                </div>
                <Form.Control
                  {...register("genres")}
                  type="text"
                  placeholder="Gêneros"
                  className="campo input-all"
                />
              </Form.Group>

              <Button type="submit" className="button-post">
                Publicar
              </Button>
            </Form>
          </div>
        </div>
      </div>
      <div className="profileShowcasePosts"></div>
    </div>
  );
};

export default ProfilePost;
