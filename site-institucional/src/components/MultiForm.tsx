import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useForm, SubmitHandler } from "react-hook-form";
import Swal from "sweetalert2";
import { format } from "date-fns";
import withReactContent from "sweetalert2-react-content";

import UserService from "../routes/UserRoute";

import { Register } from "../pages/register/Register";
import { Register2 } from "../pages/register/Register2";
import { RegisterConcluid } from "../pages/register/RegisterConcluid";

import IUserResponseDto from "../dto/response/UserResponseDto";
import IUserRequestDto from "../dto/request/UserRequestDto";

import { StateEnum } from "../enums/StateEnum";
import { GenreNameEnum } from "../enums/GenreNameEnum";
import { RoleNameEnum } from "../enums/RoleNameEnum";
import { SkillLevelEnum } from "../enums/SkillLevelEnum";
import { SexEnum } from "../enums/SexEnum";
import IUserLoginRequestDto from "../dto/request/UserLoginRequestDto";

export interface IFormUserState {
  step: number;
  email: string;
  password: string;
  confirmPassword: string;
  name: string;
  sex: SexEnum;
  description: string;
  birthDate: string;
  state: StateEnum;
  city: string;
  likedGenres: GenreNameEnum[];
  roles: RoleNameEnum[];
  skillLevel: SkillLevelEnum;
}

export interface IListGenreName {
  size: number;
  genres: GenreNameEnum[];
}

export interface IListRoleName {
  size: number;
  roles: RoleNameEnum[];
}

const defaultUserValue: IFormUserState = {
  step: 1,
  email: "",
  password: "",
  confirmPassword: "",
  name: "",
  sex: SexEnum.NULL,
  description: "",
  birthDate: "",
  state: StateEnum.NULL,
  city: "",
  likedGenres: [],
  roles: [],
  skillLevel: SkillLevelEnum.NULL,
};

const defaultListGenreNameValue: IListGenreName = {
  size: 0,
  genres: [],
};

const defaultListRoleNameValue: IListRoleName = {
  size: 0,
  roles: [],
};

export function MultiForm() {
  const navigate = useNavigate();
  const MySwal = withReactContent(Swal);

  const [formUserState, setFormUserState] =
    useState<IFormUserState>(defaultUserValue);
  const [listGenreName, setListGenreName] = useState<IListGenreName>(
    defaultListGenreNameValue
  );
  const [listRoleName, setListRoleName] = useState<IListRoleName>(
    defaultListRoleNameValue
  );

  const handleAddFromListChangeGenre = (value: GenreNameEnum) => {
    const newListGenreName = {
      size: Object.values(listGenreName.genres).length + 1,
      genres: [...listGenreName.genres, value],
    };
    setListGenreName(newListGenreName);
  };

  const handleAddFromListChangeRole = (value: RoleNameEnum) => {
    const newListRoleName = {
      size: Object.values(listRoleName.roles).length + 1,
      roles: [...listRoleName.roles, value],
    };
    setListRoleName(newListRoleName);
  };

  const handleRemoveFromListChangeGenre = (value: GenreNameEnum) => {
    const newListGenreName = {
      size: Object.values(listGenreName.genres).length - 1,
      genres: listGenreName.genres.filter((genre) => genre !== value),
    };
    setListGenreName(newListGenreName);
  };

  const handleRemoveFromListChangeRole = (value: RoleNameEnum) => {
    const newListRoleName = {
      size: Object.values(listRoleName.roles).length - 1,
      roles: listRoleName.roles.filter((role) => role !== value),
    };
    setListRoleName(newListRoleName);
  };

  const handleSaveFromListChange = () => {
    setFormUserState({
      ...formUserState,
      likedGenres: listGenreName.genres,
      roles: listRoleName.roles,
    });
  };

  const handleFieldChange = (value: any, fieldName: string) => {
    setFormUserState({
      ...formUserState,
      [fieldName]: value,
    });
  };

  const handleNextStep = () => {
    setFormUserState({
      ...formUserState,
      step: formUserState.step + 1,
    });
  };

  const handlePreviousStep = () => {
    setFormUserState({
      ...formUserState,
      step: formUserState.step - 1,
    });
  };

  const submitForm = () => {
    if (formUserState.password == formUserState.confirmPassword) {
      const user: IUserRequestDto = {
        email: formUserState.email,
        password: formUserState.password,
        name: formUserState.name,
        sex: formUserState.sex,
        description: formUserState.description,
        birthDate: format(new Date(formUserState.birthDate), "yyyy-MM-dd"),
        state: formUserState.state,
        city: formUserState.city,
        likedGenres: formUserState.likedGenres,
        roles: formUserState.roles,
        skillLevel: formUserState.skillLevel,
      };

      UserService.saveAndLogin(user)
        .then((res) => {
          if (res.status == 201) {
            console.log(res.data);
            Swal.fire("Usuário cadastrado com sucesso");
            handleNextStep();
          }
        })
        .catch((err) => {
          console.log(err);
          Swal.fire("Erro ao cadastrar usuário");
        });
    } else {
      Swal.fire("Senhas não conferem");
    }
  };

  const signInUser = () => {
    const userLoginRequest: IUserLoginRequestDto = {
      email: formUserState.email,
      password: formUserState.password,
    };

    console.log(userLoginRequest);

    UserService.login(userLoginRequest)
      .then((res) => {
        if (res.status == 200) {
          localStorage.setItem("viewerId", `${res.data.id}`);
          navigate(`/profile/${res.data.id}`);
        }
      })
      .catch((err) => {
        console.log(err);

        Swal.fire("erro ao tentar entrar");
      });
  };

  useEffect(() => {
    handleSaveFromListChange();
  }, [listGenreName, listRoleName]);

  const renderForms = () => {
    if (formUserState.step === 1) {
      if (formUserState) console.log(formUserState.step);
      return (
        <Register
          nextStep={handleNextStep}
          handleChange={handleFieldChange}
          formState={formUserState}
        />
      );
    } else if (formUserState.step === 2) {
      console.log(formUserState.step);

      return (
        <Register2
          submit={submitForm}
          listGenre={listGenreName}
          listRole={listRoleName}
          removeFromListGenre={handleRemoveFromListChangeGenre}
          removeFromListRole={handleRemoveFromListChangeRole}
          addFromListGenre={handleAddFromListChangeGenre}
          addFromListRole={handleAddFromListChangeRole}
          saveList={handleSaveFromListChange}
          previousStep={handlePreviousStep}
          nextStep={handleNextStep}
          handleChange={handleFieldChange}
          formState={formUserState}
        />
      );
    } else if (formUserState.step === 3) {
      console.log(formUserState.step);

      return <RegisterConcluid signIn={signInUser} />;
    }

    return <></>;
  };

  return <>{renderForms()}</>;
}
