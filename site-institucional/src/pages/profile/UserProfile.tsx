import React, { useEffect, useMemo, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Box,
  Button,
  CircularProgress,
  ClickAwayListener,
} from "@mui/material";
import { ArrowDropDown, RestorePageRounded } from "@mui/icons-material";
import { ptBR } from "date-fns/locale";
import { useLocation, useNavigate } from "react-router-dom";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";

import UserRoute from "../../routes/UserRoute";
import { NavBar } from "../../components/Navbar";
import ProfilePost from "./components/ProfilePost/ProfilePost";
import ProfileHighlight from "./components/ProfileHighlight/ProfileHighlight";
import IUserProfileResponseDto from "../../dto/response/UserProfileResponseDto";
import ProfileNavigatorTabs from "./components/ProfileNavigationTabs/ProfileNavigationTabs";
import ProfileIntro, {
  PROFILE_TYPE,
} from "./components/ProfileIntro/ProfileIntro";
import "./profile.style.css";
import PictureUpdateRequestDto from "../../dto/request/PictureUpdateRequestDto";
import Swal from "sweetalert2";
import GroupService from "../../routes/GroupRoute";
import IGroupRequestDto from "../../dto/request/GroupRequestDto";

const toBase64 = (file: any) =>
  new Promise((resolve, reject) => {
    const reader: any = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = () => {
      resolve(
        reader.result && reader.result.replace("data:", "").replace(/^.+,/, "")
      );
    };
    reader.onerror = (error: any) => reject(error);
  });

interface IUserPageData {
  id: number;
  profileHighlight: {
    userInfo: { name: string; description: string };
    avatarSrc?: any;
    bannerSrc?: any;
  };
  profileIntroSkills: {
    id: number;
    label: string;
    skillLevel: string;
  }[];
  profileIntroBands?: {
    bandId?: number;
    name?: string;
    leader: boolean;
    role?: string;
    imageSrc?: string;
  };
}

// Dados mockados da Página
// const REOL_USER_PROFILE_PAGE_MOCK: IUserPageData = {
//   id: 1,
//   profileHighlight: {
//     userInfo: {
//       name: "Reol",
//       description: "RAINHA DEUSA PERFEITA NUNCA ERROU MELHOR DE TODAS",
//     },
//     avatarSrc:
//       "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
//     bannerSrc:
//       "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4CJ-_M93XzMWK2K27WvbxPWvNgA9PGtkr1suJhyrg7dvibiHSE9SHdBq4plRT1UMKcbs&usqp=CAU",
//   },
//   profileIntroSkills: [
//     {
//       id: 1,
//       label: "Expert",
//       skillLevel: "Expert",
//     },
//     {
//       id: 2,
//       label: "Cantor",
//       skillLevel: "Expert",
//     },
//     {
//       id: 3,
//       label: "Pianista",
//       skillLevel: "Expert",
//     },
//   ],
//   profileIntroBands: {
//     bandId: 1,
//     name: "Reol",
//     leader: true,
//     role: "Vocalista",
//     imageSrc:
//       "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
//   },
// };

// const SUMETAL_USER_PROFILE_PAGE_MOCK: IUserPageData = {
//   id: 2,
//   profileHighlight: {
//     userInfo: { name: "Suzuka Nakamoto", description: "😗🤘 Megitsune-🦊" },
//     avatarSrc: "https://images.moviefit.me/p/o/135051-suzuka-nakamoto.jpg",
//     bannerSrc:
//       "https://media.gettyimages.com/photos/sumetal-of-babymetal-performs-on-day-3-of-the-leeds-festival-at-park-picture-id486026350?s=612x612",
//   },
//   profileIntroSkills: [
//     {
//       id: 1,
//       label: "Expert",
//       skillLevel: "Expert",
//     },
//     {
//       id: 2,
//       label: "Cantor",
//       skillLevel: "Expert",
//     },
//     {
//       id: 3,
//       label: "Pianista",
//       skillLevel: "Expert",
//     },
//   ],
//   profileIntroBands: {
//     bandId: 2,
//     name: "BABYMETAL",
//     leader: true,
//     role: "Vocalista",
//     imageSrc:
//       "https://studiosol-a.akamaihd.net/uploadfile/letras/albuns/9/4/0/2/381521408793193.jpg",
//   },
// };

// const MOAMETAL_USER_PROFILE_PAGE_MOCK: IUserPageData = {
//   id: 3,
//   profileHighlight: {
//     userInfo: {
//       name: "Moa Kikuchi",
//       description: "🤘 Cadê minhas irmã gêmea mãe ?",
//     },
//     avatarSrc:
//       "https://i.pinimg.com/originals/ac/60/c4/ac60c4a8383950f04fefaf3d0900192c.jpg",
//     bannerSrc:
//       "https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Babymetal_-_2018152162203_2018-06-01_Rock_am_Ring_-_1D_X_MK_II_-_0347_-_B70I0418.jpg/1200px-Babymetal_-_2018152162203_2018-06-01_Rock_am_Ring_-_1D_X_MK_II_-_0347_-_B70I0418.jpg",
//   },
//   profileIntroSkills: [
//     {
//       id: 1,
//       label: "Expert",
//       skillLevel: "Expert",
//     },
//     {
//       id: 2,
//       label: "Cantor",
//       skillLevel: "Expert",
//     },
//     {
//       id: 3,
//       label: "Pianista",
//       skillLevel: "Expert",
//     },
//   ],
//   profileIntroBands: {
//     bandId: 2,
//     name: "BABYMETAL",
//     leader: false,
//     role: "Vocal",
//     imageSrc:
//       "https://studiosol-a.akamaihd.net/uploadfile/letras/albuns/9/4/0/2/381521408793193.jpg",
//   },
// };

// const YUIMETAL_USER_PROFILE_PAGE_MOCK: IUserPageData = {
//   id: 4,
//   profileHighlight: {
//     userInfo: { name: "Yui Mizuno", description: "Saiu da banda 😭" },
//     avatarSrc:
//       "https://4.bp.blogspot.com/-Bel3Lb_a1H8/W7fgAzWXPwI/AAAAAAAABD4/2QFXrSiDbmIndQv8mYAhd_uON5LeG5C_ACEwYBhgL/s1600/DoA81Y-XcAAiPa5.jpg",
//     bannerSrc:
//       "https://pm1.narvii.com/6348/c5994923f8975f312f51769f78779a6c0072dfb7_hq.jpg",
//   },
//   profileIntroSkills: [
//     {
//       id: 1,
//       label: "Expert",
//       skillLevel: "Expert",
//     },
//     {
//       id: 2,
//       label: "Cantor",
//       skillLevel: "Expert",
//     },
//     {
//       id: 3,
//       label: "Pianista",
//       skillLevel: "Expert",
//     },
//   ],
//   profileIntroBands: {
//     bandId: 2,
//     name: "BABYMETAL",
//     leader: false,
//     role: "Vocal",
//     imageSrc:
//       "https://studiosol-a.akamaihd.net/uploadfile/letras/albuns/9/4/0/2/381521408793193.jpg",
//   },
// };

// const MOCK_GET_PROFILE_USER_DATA = (userId: number) => {
//   const MOCK_DATABASE_USERS = [
//     {
//       id: 1,
//       data: REOL_USER_PROFILE_PAGE_MOCK,
//     },
//     {
//       id: 2,
//       data: SUMETAL_USER_PROFILE_PAGE_MOCK,
//     },
//     {
//       id: 3,
//       data: MOAMETAL_USER_PROFILE_PAGE_MOCK,
//     },
//     {
//       id: 4,
//       data: YUIMETAL_USER_PROFILE_PAGE_MOCK,
//     },
//   ];

//   return (
//     MOCK_DATABASE_USERS.find((user) => user.id === userId)?.data || undefined
//   );
// };
// MOCK ------------------

const Popover = ({
  open = false,
  children = <span></span>,
}: {
  open: boolean;
  children: any;
}) => {
  return (
    <div className={`popover-menu ${open ? "popover-menu--active" : ""}`}>
      {children}
    </div>
  );
};

const UserProfile = () => {
  const [currentUser, setCurrentUser] = useState(-1);
  const [canEdit, setCanEdit] = useState(false);
  const [currentShowcasePage, setCurrentShowcasePage] = useState(0);
  const [userProfileData, setUserProfileData] = useState<IUserPageData | any>(
    undefined
  );
  const [registerBandDialogVisibility, setRegisterBandDialogVisibility] =
    useState(false);
  const [registerBandForm, setRegisterBandForm] = useState<IGroupRequestDto>({
    name: "",
    creationDate: "",
    description: "",
    genres: ["ROCK"],
  });

  const [loadingAvatar, setLoadingAvatar] = useState(false);
  const [loadingBanner, setLoadingBanner] = useState(false);
  const [loadingImportGroup, setLoadingImportGroup] = useState(false);
  const [loadingProfielUserData, setLoadingProfielUserData] = useState(false);
  const [loadingRegisterBandConfirm, setLoadingRegisterBandConfirm] =
    useState(false);
  const location = useLocation();
  const navigate = useNavigate();

  const getProfileUserData = async (userId: number) => {
    try {
      setLoadingProfielUserData(true);
      const viewerId = localStorage.getItem("viewerId") || null;
      if (viewerId) {
        const response = await UserRoute.getProfileForId(userId, viewerId);
        if (response === undefined) navigate("/");
        if (response.status === 200) {
          const formatedIntroSkills = response.data.roles.map((role) => ({
            id: role.id,
            label: role.name,
            skillLevel: response.data.skillLevel,
          }));
          const formatResponse = {
            id: response.data.id,
            profileHighlight: {
              userInfo: {
                name: response.data.name,
                description: response.data.description,
              },
              avatarSrc: response.data.profilePic,
              bannerSrc: response.data.banner,
            },
            profileIntroBands: {
              bandId: response?.data?.group?.id,
              name: response?.data?.group?.name,
              imageSrc: response?.data?.group?.profilePic,
              leader: response?.data?.leader,
            },
            profileIntroSkills: formatedIntroSkills,
            profileShowcasePosts: response.data.postList,
          };
          setUserProfileData(formatResponse);
        }
      }
    } catch (err) {
      console.log(err);
    } finally {
      setLoadingProfielUserData(false);
    }
  };

  const registerNewBand = async () => {
    try {
      setLoadingRegisterBandConfirm(true);
      const viewerId = localStorage.getItem("viewerId") || null;
      if (viewerId) {
        const response = await GroupService.save({
          ...registerBandForm,
          leaderId: Number.parseInt(viewerId),
        });
        if (response.status === 201) {
          Swal.fire("Banda criada com sucesso!");
        }
      }
    } catch {
      Swal.fire("Erro ao criar a banda!");
    } finally {
      setRegisterBandDialogVisibility(false);
      setLoadingRegisterBandConfirm(false);
    }
  };

  const updateAvatar = async (input: any) => {
    setLoadingAvatar(true);
    const viewerId = localStorage.getItem("viewerId") || null;
    if (input.files && input.files.length) {
      const file = input.files[0];
      const formatedFile = await toBase64(file);
      const request: PictureUpdateRequestDto = {
        profilePic: formatedFile,
        banner: userProfileData.profileHighlight.bannerSrc,
      };
      try {
        const response = await UserRoute.updatePicture(viewerId, request);
        if (response.status === 200) {
          setUserProfileData({
            ...userProfileData,
            profileHighlight: {
              ...userProfileData.profileHighlight,
              avatarSrc: formatedFile,
            },
          });
        }
      } catch (err: any) {
        console.log(err);
      } finally {
        setLoadingAvatar(false);
      }
    }
  };

  const updateBanner = async (input: any) => {
    setLoadingBanner(true);
    const viewerId = localStorage.getItem("viewerId") || null;
    if (input.files && input.files.length) {
      const file = input.files[0];
      const formatedFile = await toBase64(file);
      const request: PictureUpdateRequestDto = {
        banner: formatedFile,
        profilePic: userProfileData.profileHighlight.avatarSrc,
      };
      try {
        const response = await UserRoute.updatePicture(viewerId, request);
        if (response.status === 200) {
          setUserProfileData({
            ...userProfileData,
            profileHighlight: {
              ...userProfileData.profileHighlight,
              bannerSrc: formatedFile,
            },
          });
        }
      } catch (err: any) {
        console.log(err);
      } finally {
        setLoadingBanner(false);
      }
    }
  };

  const importBandTxt = async (input: any) => {
    const viewerId = localStorage.getItem("viewerId") || null;
    if (input.files && input.files.length) {
      const file = input.files[0];
      const formatedFile = await toBase64(file);
      try {
        setLoadingImportGroup(true);
        const response = await UserRoute.importGroupText(
          viewerId,
          "Flork     BandaDaora ROCK"
        );
        if (response.status === 200) {
          Swal.fire("Importado com Sucesso!");
        }
      } catch (err: any) {
        console.log(err);
      } finally {
        setLoadingImportGroup(false);
      }
    }
  };

  const handleExport = async () => {
    const downloadTxtFile = (text: string) => {
      const element = document.createElement("a");
      const file = new Blob([text], {
        type: "text/plain",
      });
      element.href = URL.createObjectURL(file);
      element.download = "myFile.txt";
      document.body.appendChild(element);
      element.click();
    };

    const viewerId = localStorage.getItem("viewerId") || null;
    try {
      const response = await UserRoute.exportDownload(viewerId);
      if (response.status === 200) {
        downloadTxtFile(response.data);
      }
      console.log(response);
    } catch (err: any) {
      console.log(err);
    }
  };

  useEffect(() => {
    const viewerId = localStorage.getItem("viewerId") || "";
    const userPageId: string[] = location.pathname.match(/\d+$/) || ["-1"];
    if (viewerId) {
      setCurrentUser(Number.parseInt(viewerId));
      setCanEdit(Number.parseInt(viewerId) === Number.parseInt(userPageId[0]));
      getProfileUserData(Number.parseInt(userPageId[0]));
    } else {
      navigate("/");
    }
  }, []);

  const PROFILE_NAVIGATION_OPTIONS = useMemo(
    () => [
      {
        label: "Feed",
        handleClick: (tab: number) => {
          setCurrentShowcasePage(tab);
        },
      },
      {
        label: "Videos",
        handleClick: (tab: number) => {
          setCurrentShowcasePage(tab);
        },
      },
      {
        label: "Playlist",
        handleClick: (tab: number) => {
          setCurrentShowcasePage(tab);
        },
      },
      {
        CustomOption: ({
          canEdit,
          currentTab,
          handleChangeTab,
        }: {
          canEdit: boolean;
          currentTab: number;
          handleChangeTab: Function;
        }) => {
          const [menuVisibility, setMenuVisibility] = useState(false);

          return (
            <div>
              <ClickAwayListener onClickAway={() => setMenuVisibility(false)}>
                <div>
                  <a
                    onClick={() => {
                      handleChangeTab(currentTab);
                      setMenuVisibility(true);
                    }}
                  >
                    Mais
                    <ArrowDropDown />
                  </a>
                  <Popover open={menuVisibility}>
                    <ul className="popover-options">
                      <li>
                        <a>Chat</a>
                      </li>
                      <li>
                        <a onClick={() => handleExport()} download>
                          Exportar
                        </a>
                      </li>
                      {canEdit ? (
                        <li>
                          <a
                            onClick={() =>
                              setRegisterBandDialogVisibility(true)
                            }
                          >
                            Criar uma banda
                          </a>
                        </li>
                      ) : null}
                    </ul>
                  </Popover>
                </div>
              </ClickAwayListener>
            </div>
          );
        },
      },
    ],
    []
  );

  const PROFILE_SHOWCASE_PAGES = [
    {
      page: 0,
      renderPage: (canEdit: boolean) => (
        <>
          <ProfileIntro
            infos={{
              skills: userProfileData?.profileIntroSkills,
              bands: userProfileData?.profileIntroBands,
            }}
            type={PROFILE_TYPE.USER}
            handleClick={(id: number) => navigate("/band/" + id)}
          />
          <ProfilePost canEdit={canEdit} />
        </>
      ),
    },
  ];

  return (
    <>
      <Dialog
        fullWidth
        className="registerBandDialog"
        open={registerBandDialogVisibility}
        onClose={() => setRegisterBandDialogVisibility(false)}
      >
        <DialogTitle>Vamos preencher os dados da sua banda ?</DialogTitle>
        <DialogContent className="registerBandDialogContent">
          <input
            className="form-label"
            type="text"
            placeholder="Nome"
            value={registerBandForm.name}
            onChange={(event) =>
              setRegisterBandForm({
                ...registerBandForm,
                name: event.target.value,
              })
            }
          />
          <LocalizationProvider
            adapterLocale={ptBR}
            dateAdapter={AdapterDateFns}
          >
            <DatePicker
              value={registerBandForm.creationDate}
              onChange={(dateTime: any) =>
                setRegisterBandForm({
                  ...registerBandForm,
                  creationDate: dateTime,
                })
              }
              renderInput={({ inputRef, inputProps, InputProps }) => (
                <Box
                  className="form-label"
                  sx={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "space-between",
                  }}
                >
                  <input
                    {...inputProps}
                    disabled
                    type="text"
                    ref={inputRef}
                    placeholder="Data de Criação da Banda"
                    className="form-label-date"
                  />
                  {InputProps?.endAdornment}
                </Box>
              )}
            />
          </LocalizationProvider>
          <textarea
            className="form-label"
            placeholder="Descreva sobre usa Banda!"
            value={registerBandForm.description}
            onChange={(event) =>
              setRegisterBandForm({
                ...registerBandForm,
                description: event.target.value,
              })
            }
          />
          <input
            className="form-label"
            type="text"
            placeholder="Vamos categorizar o gênero da Banda?"
            value={registerBandForm.genres}
            onChange={(event) =>
              setRegisterBandForm({
                ...registerBandForm,
                genres: [...registerBandForm.genres, event.target.value],
              })
            }
          />
          <DialogActions
            sx={{
              display: "flex",
              justifyContent: "space-between",
              padding: "8px 0",
            }}
          >
            <Button
              variant="outlined"
              color="error"
              onClick={() => {
                setRegisterBandDialogVisibility(false);
                setRegisterBandForm({
                  name: "",
                  creationDate: "",
                  description: "",
                  genres: [],
                });
              }}
            >
              Cancelar
            </Button>
            <div>
              <Button
                variant="contained"
                sx={{
                  width: 100,
                  margin: "0 8px",
                  color: "var(--black)",
                  backgroundColor: "var(--white)",
                  "&:hover": { backgroundColor: "var(--gray)" },
                }}
                component="label"
              >
                {loadingImportGroup ? (
                  <CircularProgress size={24} />
                ) : (
                  <>
                    <input
                      type="file"
                      hidden
                      onChange={(event) => importBandTxt(event.target)}
                    />
                    Importar
                  </>
                )}
              </Button>
              <Button
                variant="contained"
                sx={{
                  width: 100,
                  backgroundColor: "var(--dark-purple)",
                  "&:hover": { backgroundColor: "var(--light-purple)" },
                }}
                onClick={() => registerNewBand()}
              >
                {loadingRegisterBandConfirm ? (
                  <CircularProgress size={24} />
                ) : (
                  "Próximo"
                )}
              </Button>
            </div>
          </DialogActions>
        </DialogContent>
      </Dialog>

      <NavBar isbtnRegisterOff />
      <div className="profilePageWrapper">
        {loadingProfielUserData ? (
          <CircularProgress />
        ) : (
          <>
            <div className="profileTopic">
              <ProfileHighlight
                canEdit={canEdit}
                bannerSrc={userProfileData?.profileHighlight.bannerSrc}
                avatarSrc={userProfileData?.profileHighlight.avatarSrc}
                userInfo={userProfileData?.profileHighlight.userInfo}
                handleAvatarChange={updateAvatar}
                handleBannerChange={updateBanner}
                loadingAvatar={loadingAvatar}
                loadingBanner={loadingBanner}
              />
              <div className="profileNavigation">
                <ProfileNavigatorTabs
                  canEdit={canEdit}
                  options={PROFILE_NAVIGATION_OPTIONS}
                />
                {canEdit ? (
                  <a href={`/match/${currentUser}`}>Sintonizar</a>
                ) : null}
              </div>
            </div>
            <div className="profileShowcaseWrapper">
              {PROFILE_SHOWCASE_PAGES.find(
                (showcase) => showcase.page === currentShowcasePage
              )?.renderPage(canEdit)}
            </div>
          </>
        )}
      </div>
    </>
  );
};

export default UserProfile;
