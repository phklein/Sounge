import React, { useEffect, useMemo, useState } from "react";
import { withStyles } from "@mui/styles";
import { ArrowDropDown } from "@mui/icons-material";
import { useLocation, useNavigate } from "react-router-dom";
import {
  Autocomplete,
  Box,
  Button,
  CircularProgress,
  ClickAwayListener,
  TextField,
} from "@mui/material";
import useDebounce from "./useDebounce";
import { NavBar } from "../../components/Navbar";
import ProfilePost from "./components/ProfilePost/ProfilePost";
import ProfileHighlight from "./components/ProfileHighlight/ProfileHighlight";
import ProfileIntro, {
  PROFILE_TYPE,
} from "./components/ProfileIntro/ProfileIntro";
import ProfileNavigatorTabs from "./components/ProfileNavigationTabs/ProfileNavigationTabs";
import "./profile.style.css";
import GroupService from "../../routes/GroupRoute";
import UserRoute from "../../routes/UserRoute";

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
interface IBandPageData {
  groupId: number;
  profileHighlight: {
    bannerSrc: string;
    avatarSrc: string;
    userInfo: { name: string; description: string };
  };
  profileIntroMembers: {
    imageSrc: string;
    name: string;
    role: string;
    leader: boolean;
    userId: number;
  }[];
}

interface ISearchUsers {
  users: {
    imageSrc: string;
    userId: number;
    name: string;
    role: string;
    leader: boolean;
  }[];
}

// Dados Mockados da Página
// const REOL_BAND_PROFILE_PAGE_MOCK: IBandPageData = {
//   profileHighlight: {
//     bannerSrc: "https://www.otaquest.com/wp-content/uploads/2019/08/reol.png",
//     avatarSrc:
//       "https://i.pinimg.com/736x/5d/ba/bd/5dbabdc399dcd3c377bf28bf5b80ad80.jpg",
//     userInfo: {
//       name: "Reol",
//       description: "Ainda é uma Banda de certa forma ¯\\_(ツ)_/¯",
//     },
//   },
//   profileIntroMembers: [
//     {
//       imageSrc:
//         "https://i.pinimg.com/736x/82/92/b8/8292b855a223d714cf1975e50f3b0ed5.jpg",
//       userId: 1,
//       name: "Reol",
//       role: "Vocalista",
//       leader: true,
//     },
//     // {
//     // 	imageSrc: "https://lastfm.freetls.fastly.net/i/u/ar0/e63de329c7db8ddccd1a1b0ee9ba5d67.jpg",
//     // 	userId: 2,
//     // 	name: "Giga",
//     // 	role: "Produtor",
//     // 	leader: false,
//     // },
//     // {
//     // 	imageSrc: "https://pm1.narvii.com/6314/ea5666b0d0583fef6c5ed8a06207e0b9bf1dfb7a_hq.jpg",
//     // 	userId: 3,
//     // 	name: "Okiku",
//     // 	role: "Designer",
//     // 	leader: false,
//     // },
//   ],
// };

// const BABYMETAL_BAND_PROFILE_PAGE_MOCK: IBandPageData = {
//   profileHighlight: {
//     bannerSrc: "https://www.jame-world.com/media/image/2014-07/5737.jpg",
//     avatarSrc:
//       "https://studiosol-a.akamaihd.net/uploadfile/letras/albuns/9/4/0/2/381521408793193.jpg",
//     userInfo: {
//       name: "BABYMETAL",
//       description: "NÃO! Não é metal de bebe, seu burro 🐋",
//     },
//   },
//   profileIntroMembers: [
//     {
//       imageSrc: "https://images.moviefit.me/p/o/135051-suzuka-nakamoto.jpg",
//       userId: 2,
//       name: "Suzuka Nakamoto",
//       role: "Vocal",
//       leader: true,
//     },
//     {
//       imageSrc:
//         "https://i.pinimg.com/originals/ac/60/c4/ac60c4a8383950f04fefaf3d0900192c.jpg",
//       userId: 3,
//       name: "Moa Kikuchi",
//       role: "Vocal",
//       leader: false,
//     },
//     {
//       imageSrc:
//         "https://4.bp.blogspot.com/-Bel3Lb_a1H8/W7fgAzWXPwI/AAAAAAAABD4/2QFXrSiDbmIndQv8mYAhd_uON5LeG5C_ACEwYBhgL/s1600/DoA81Y-XcAAiPa5.jpg",
//       userId: 4,
//       name: "Yui Mizuno",
//       role: "Ex-Integrante",
//       leader: false,
//     },
//   ],
// };

// const USERS_MOCK: ISearchUsers = {
//   users: [
//     {
//       imageSrc:
//         "http://pm1.narvii.com/7022/62ae42858fe3836a2f0a5e9c1b822346eb8cadber1-1516-1080v2_uhq.jpg",
//       userId: 4,
//       name: "Kradness",
//       role: "Vocalista",
//       leader: false,
//     },
//     {
//       imageSrc:
//         "https://cdns-images.dzcdn.net/images/artist/3c7e82a847df0e25fea009504ca45664/500x500.jpg",
//       userId: 5,
//       name: "DECO*27",
//       role: "Produtor",
//       leader: false,
//     },
//     {
//       imageSrc:
//         "https://i.discogs.com/GEa-goBX-6Z5Owq7qw6Knv0QFnkQTZu6aSgBS7GFgcU/rs:fit/g:sm/q:40/h:300/w:300/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTgxNzkx/MzktMTYwMTY1NTAy/Mi0zNjYyLmpwZWc.jpeg",
//       userId: 6,
//       name: "Eve",
//       role: "Vocalista/Produtor",
//       leader: false,
//     },
//   ],
// };

// const MOCK_GET_PROFILE_USER_DATA = (bandId: number) => {
//   const MOCK_DATABASE_USERS = [
//     {
//       id: 1,
//       data: REOL_BAND_PROFILE_PAGE_MOCK,
//     },
//     {
//       id: 2,
//       data: BABYMETAL_BAND_PROFILE_PAGE_MOCK,
//     },
//   ];

//   return (
//     MOCK_DATABASE_USERS.find((band) => band.id === bandId)?.data || undefined
//   );
// };

const CURRENT_USER = 1;
// MOCK -------------------

const ProfileBandMemberFormInfo = ({
  leader = false,
  name = "",
  role = "",
  imageSrc = "",
  disabled = false,
  isCurrentUser = false,
  handleClick = () => {},
}: {
  leader?: boolean;
  imageSrc: string;
  name: string;
  role: string;
  disabled: boolean;
  isCurrentUser?: boolean;
  handleClick?: any;
}) => {
  return (
    <li
      className={`profileBandWrapper ${
        isCurrentUser
          ? "profileBandWrapper--currentUser"
          : "profileBandWrapper--otherMember"
      }`}
    >
      <a className="profileBand">
        <img src={imageSrc} />
        <div className="profileBandInfo">
          <span className="profileBandInfoName">
            {leader ? (
              <span>
                {name}{" "}
                <span className="profileBandInfoLeader">{`(Líder da Banda)⭐`}</span>
              </span>
            ) : (
              name
            )}
          </span>
          <span className="profileBandInfoRole">{role}</span>
        </div>
      </a>
      <Button
        disabled={disabled}
        variant="outlined"
        onClick={handleClick}
        color={isCurrentUser ? "error" : undefined}
      >
        {isCurrentUser ? "Sair" : "Excluir"}
      </Button>
    </li>
  );
};

const CustomTextField = withStyles({
  root: {
    "& .MuiInputBase-root": {
      color: "var(--white)",
    },
    "& label": {
      color: "var(--gray)",
    },
    "& label.Mui-focused": {
      color: "var(--white)",
    },
    "& .MuiInput-underline:after": {
      borderBottomColor: "var(--light-purple)",
    },
    "& .MuiOutlinedInput-root": {
      "& fieldset": {
        borderColor: "var(--gray)",
      },
      "&:hover fieldset": {
        borderColor: "var(--gray)",
      },
      "&.Mui-focused fieldset": {
        borderColor: "var(--light-purple)",
      },
    },
  },
})(TextField);

const CustomButton = withStyles({
  disabled: {
    "&$disabled": {
      color: "var(--gray)",
    },
  },
})(Button);

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

const BandProfile = () => {
  const [bandMemberSearch, setBandMemberSearch] = useState("");
  const [currentShowcasePage, setCurrentShowcasePage] = useState(0);
  const [bandMemberForm, setBandMemberForm] = useState<{
    member: any;
    role: string;
  }>({
    member: { name: "" },
    role: "",
  });
  const [searchedUsers, setSearchedUsers] = useState<any>([]);
  const [profileBandData, setProfileBandData] = useState<IBandPageData | any>(
    undefined
  );
  const [loadingProfileBandData, setLoadingProfileBandData] = useState(false);
  const [loadingProfileBandMember, setLoadingProfileBandMember] =
    useState(false);
  const [loadingProfileBandMemberSave, setLoadingProfileBandMemberSave] =
    useState(false);
  const [loadingProfileBandMemberDelete, setLoadingProfileBandMemberDelete] =
    useState(false);
  const [loadingAvatar, setLoadingAvatar] = useState(false);
  const [loadingBanner, setLoadingBanner] = useState(false);
  const autocompleteBandMemberValue = useDebounce(bandMemberSearch, 500);
  const location = useLocation();
  const navigate = useNavigate();

  const getProfileBandData = async (bandId: number) => {
    try {
      setLoadingProfileBandData(true);
      // Trocar pela request API puxando os dados do Perfil da Banda
      const response = await GroupService.getGroupPageById(bandId);
      // const response = MOCK_GET_PROFILE_USER_DATA(bandId);
      // if (response === undefined) navigate("/")
      const formatedProfileIntroMembers = response.data.users.map(
        (user: any) => ({
          imageSrc: user.profilePic,
          name: user.name,
          role: "",
          leader: user.leader,
          userId: user.id,
        })
      );
      setProfileBandData({
        profileHighlight: {
          bannerSrc: response.data.banner,
          avatarSrc: response.data.pictureUrl,
          userInfo: {
            name: response.data.name,
            description: response.data.description,
          },
        },
        profileIntroMembers: formatedProfileIntroMembers,
        groupId: response.data.id,
      });
    } catch {
      // Validar erros da request
    } finally {
      // Remover Timeout
      setLoadingProfileBandData(false);
    }
  };

  const getSearchedUsers = async (searchUser: string) => {
    try {
      setLoadingProfileBandMember(true);
      // const search = new RegExp(searchUser, "gi");
      const viewerId = localStorage.getItem("viewerId") || null;
      if (viewerId) {
        const response = await UserRoute.getUsersByName(
          Number.parseInt(viewerId),
          searchUser
        );
        // Trocar pela request API de pesquisa pelos usuários
        // const response = USERS_MOCK.users?.filter((user) => {
        //   return user.name.match(search);
        // });
        const uniqueUsers = response.data.filter(
          (value: any, index: any, self: any) =>
            index ===
            self.findIndex(
              (t: any) => t.place === value.place && t.name === value.name
            )
        );
        setSearchedUsers([...uniqueUsers]);
      }
    } catch {
      // Validar erros da request
    } finally {
      // Remover Timeout
      setLoadingProfileBandMember(false);
    }
  };

  const saveNewMember = async (selectedMember: any) => {
    try {
      setLoadingProfileBandMemberSave(true);
      const response = await UserRoute.joinGroup(
        selectedMember.member.id,
        profileBandData.groupId
      );
      console.log(response);
      setBandMemberForm({ member: "", role: "" });
    } catch {
      // Validar erros da request
    } finally {
      // Remover Timeout
      setTimeout(() => {
        setLoadingProfileBandMemberSave(false);
      }, 500);
    }
  };

  const deleteMemberFromBand = async (selectedMember: any) => {
    try {
      setLoadingProfileBandMemberDelete(true);
      // Trocar pela request API de remover um membro da banda
      // Remover Timeout
      setTimeout(() => {
        setProfileBandData({
          ...profileBandData,
          profileIntroMembers: profileBandData.profileIntroMembers.filter(
            (member: any) => member.userId !== selectedMember.userId
          ),
        });
      }, 500);
    } catch {
      // Validar erros da request
    } finally {
      // Remover Timeout
      setTimeout(() => {
        setProfileBandData({
          ...profileBandData,
          profileIntroMembers: profileBandData.profileIntroMembers.filter(
            (member: any) => member.userId !== selectedMember.userId
          ),
        });
        setLoadingProfileBandMemberDelete(false);
      }, 500);
    }
  };

  const updateAvatar = async (input: any) => {
    setLoadingAvatar(true);
    if (input.files && input.files.length) {
      const file = input.files[0];
      const formatedFile = await toBase64(file);
      console.log(formatedFile);

      const request = {
        profilePic: formatedFile,
        banner: profileBandData.profileHighlight.bannerSrc,
      };

      console.log(request);

      try {
        const response = await GroupService.changeGroupImages(
          profileBandData.groupId,
          request
        );
        if (response.status === 200) {
          setProfileBandData({
            ...profileBandData,
            profileHighlight: {
              ...profileBandData.profileHighlight,
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
    if (input.files && input.files.length) {
      const file = input.files[0];
      const formatedFile = await toBase64(file);
      console.log(formatedFile);

      const request = {
        banner: formatedFile,
        profilePic: profileBandData.profileHighlight.avatarSrc,
      };

      console.log(request);

      try {
        const response = await GroupService.changeGroupImages(
          profileBandData.groupId,
          request
        );
        if (response.status === 200) {
          setProfileBandData({
            ...profileBandData,
            profileHighlight: {
              ...profileBandData.profileHighlight,
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

  useEffect(() => {
    getSearchedUsers(autocompleteBandMemberValue);
  }, [autocompleteBandMemberValue]);

  useEffect(() => {
    const groupPageId: string[] = location.pathname.match(/\d+$/) || ["-1"];
    getProfileBandData(Number.parseInt(groupPageId[0]));
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
        label: "Sobre",
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
          currentTab,
          handleChangeTab,
        }: {
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
                        <a
                          onClick={() => {
                            setCurrentShowcasePage(currentTab);
                            setMenuVisibility(false);
                          }}
                        >
                          Integrantes da Banda
                        </a>
                      </li>
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
      renderPage: () => (
        <>
          <ProfileIntro
            infos={{ members: profileBandData?.profileIntroMembers }}
            type={PROFILE_TYPE.BAND}
            handleClick={(userProfileId: number) =>
              navigate("/profile/" + userProfileId)
            }
          />
          {/* <ProfilePost /> */}
        </>
      ),
    },
    {
      page: 4,
      renderPage: () => (
        <div className="registerBandMemberFormWrapper">
          <div className="registerBandMemberForm">
            <Autocomplete
              autoSelect
              id="profileBandMemberFormInputMember"
              className="profileBandMemberFormInput profileBandMemberFormInputMember"
              options={searchedUsers}
              value={bandMemberForm.member}
              loading={loadingProfileBandMember}
              loadingText="Buscando usuário"
              noOptionsText="Não encontramos o usuário com esse nome"
              filterOptions={(x) => x}
              getOptionLabel={(option: any) => option.name || ""}
              isOptionEqualToValue={(option: any, value: any) =>
                option.userId === value.userId || option.userId !== undefined
              }
              onInputChange={(_, name) => {
                setBandMemberSearch(name);
              }}
              onChange={(_, member) => {
                setBandMemberForm({ ...bandMemberForm, member });
              }}
              renderInput={(params) => (
                <CustomTextField
                  {...params}
                  placeholder="Procure por um integrante"
                  label="Nome"
                  size="small"
                  InputProps={{
                    ...params.InputProps,
                    endAdornment: (
                      <React.Fragment>
                        {loadingProfileBandMember ? (
                          <CircularProgress color="inherit" size={20} />
                        ) : null}
                        {params.InputProps.endAdornment}
                      </React.Fragment>
                    ),
                  }}
                />
              )}
            />
            <CustomTextField
              id="profileBandMemberFormInputRole"
              className="profileBandMemberFormInput profileBandMemberFormInputRole"
              size="small"
              label="Função"
              value={bandMemberForm.role}
              placeholder="Qual a função dele na banda ?"
              onChange={(event) =>
                setBandMemberForm({
                  ...bandMemberForm,
                  role: event.target.value,
                })
              }
            />
            <CustomButton
              color="success"
              variant="contained"
              sx={{
                width: "100px",
                "& .MuiCircularProgress-root": { color: "var(--green)" },
              }}
              onClick={() => saveNewMember(bandMemberForm)}
              disabled={
                !(bandMemberForm.member !== "" && bandMemberForm.role !== "")
              }
            >
              {loadingProfileBandMemberSave ? (
                <CircularProgress size={24} />
              ) : (
                "Adicionar"
              )}
            </CustomButton>
          </div>
          <div className="profileBandMembersList">
            <h2>Integrantes</h2>
            <ul className="profileLists">
              {profileBandData?.profileIntroMembers.map(
                (member: any, index: number) => (
                  <ProfileBandMemberFormInfo
                    key={`${member.name}-${index}`}
                    isCurrentUser={member?.userId === CURRENT_USER}
                    disabled={loadingProfileBandMemberDelete}
                    name={member?.name}
                    role={member?.role}
                    leader={member?.leader}
                    imageSrc={member?.imageSrc}
                    handleClick={() => deleteMemberFromBand(member)}
                  />
                )
              )}
              {loadingProfileBandMemberDelete ? (
                <Box
                  sx={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    alignSelf: "center",
                    "& span": { color: "var(--red)" },
                    "& div": { marginBottom: "4px", fontSize: "16px" },
                  }}
                >
                  <div>Excluindo</div>
                  <CircularProgress size={32} />
                </Box>
              ) : null}
            </ul>
          </div>
        </div>
      ),
    },
  ];

  return (
    <div>
      <NavBar isbtnRegisterOff />
      <div className="profilePageWrapper">
        {loadingProfileBandData ? (
          <CircularProgress />
        ) : (
          <>
            <div className="profileTopic">
              <ProfileHighlight
                userInfo={profileBandData?.profileHighlight.userInfo}
                avatarSrc={profileBandData?.profileHighlight.avatarSrc}
                bannerSrc={profileBandData?.profileHighlight.bannerSrc}
                handleAvatarChange={updateAvatar}
                handleBannerChange={updateBanner}
                loadingAvatar={loadingAvatar}
                loadingBanner={loadingBanner}
              />
              <ProfileNavigatorTabs options={PROFILE_NAVIGATION_OPTIONS} />
            </div>
            <div className="profileShowcaseWrapper">
              {PROFILE_SHOWCASE_PAGES.find(
                (showcase) => showcase.page === currentShowcasePage
              )?.renderPage()}
            </div>
          </>
        )}
      </div>
    </div>
  );
};
export default BandProfile;
