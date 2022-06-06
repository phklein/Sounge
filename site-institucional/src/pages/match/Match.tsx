import React, { useState, useMemo, useRef, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import TinderCard from "react-tinder-card";

import "./../../styles/tindercards.css";

import UserRoute from "../../routes/UserRoute";
import GroupRoute from "../../routes/GroupRoute";

import { ContainerMessage } from "../../components/ContainerMessage";
import { UserMatchSideNav } from "../../components/UserMatchSideNav";

import { UserMatchResponseDto } from "../../dto/response/UserMatchResponseDto";
import UserProfileResponseDto from "../../dto/response/UserProfileResponseDto";
import UserSimpleResponseDto from "../../dto/response/UserSimpleResponseDto";
import GroupMatchResponseDto from "../../dto/response/GroupMatchResponseDto";
// import NotificationSimpleResponseDto from '../../dto/response/NotificationSimpleResponseDto'

import { GenreNameEnum } from "../../enums/GenreNameEnum";
import { RoleNameEnum } from "../../enums/RoleNameEnum";
import { SexEnum } from "../../enums/SexEnum";
import { SkillLevelEnum } from "../../enums/SkillLevelEnum";
// import { NotificationTypeEnum } from '../../enums/NotificationTypeEnum'

export function Match() {
  const navigate = useNavigate();
  const { id } = useParams();

  const [newNotifications, setNewNotifications] = useState<any[]>();

  setInterval(() => {
    console.log("Atualizando notificações");

    UserRoute.checkNewMatches(id)
      .then((res) => {
        console.log(res.status);
        if (res.status === 200) {
          console.log("Notificações atualizadas, novo match!");
          console.log(res.data);

          alert("Novo match");
          setNewNotifications(res.data);
          makeVisibleAndInvisible(true);
          setNewNotifications(undefined);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, 10000);

  const [userProfile, setUserProfile] = useState<UserProfileResponseDto>();

  const [matchList, setMatchList] = useState<
    UserMatchResponseDto[] | undefined
  >();
  const [matchGroupList, setMatchGroupList] = useState<
    GroupMatchResponseDto[] | undefined
  >();

  const [contactList, setContactList] = useState<
    UserSimpleResponseDto[] | undefined
  >();

  let matchListLength: any = 0;

  const [currentIndex, setCurrentIndex] = useState(matchListLength - 1);
  const [lastDirection, setLastDirection] = useState();

  const currentIndexRef = useRef(-1);

  const childRefs: any = useMemo(
    () =>
      Array(matchList?.length)
        .fill(0)
        .map((i) => React.createRef()),
    [matchList]
  );

  const updateCurrentIndex = (val: any) => {
    setCurrentIndex(val);
    currentIndexRef.current = val;
  };

  let canGoBack = currentIndex < matchListLength - 1;
  const canSwipe = currentIndex >= 0;

  const returnIdMatch = (indexSwiped: number) => {
    matchList?.map((match, index) => {
      if (indexSwiped === index) {
        console.log(match.id.valueOf());
        return match.id.valueOf();
      }
    });
  };

  const returnIdGroupMatch = (indexSwiped: number) => {
    matchGroupList?.map((match, index) => {
      if (indexSwiped === index) {
        console.log(match.id.valueOf());
        return match.id.valueOf();
      }
    });
  };

  const swiped = (
    direction: any,
    nameToDelete: any,
    indexSwiped: number,
    matchId: number
  ) => {
    if (direction === "right") {
      if (matchList != null) {
        UserRoute.likeUser(id, matchId)
          .then((res) => {
            if (res.status === 200) {
              console.log("liked user!");
            }
          })
          .catch((err) => console.log(err));
      }

      if (matchGroupList != null) {
        UserRoute.likeUser(id, matchId)
          .then((res) => {
            if (res.status === 200) {
              console.log("liked group!");
            }
          })
          .catch((err) => console.log(err));
      }
    }

    setLastDirection(direction);
    updateCurrentIndex(indexSwiped - 1);
  };

  const outOfFrame = (name: any, idx: any) => {
    console.log(`${name} (${idx}) left the screen!`, currentIndexRef.current);
    currentIndexRef.current >= idx && childRefs[idx].current.restoreCard();
  };

  const swipe = async (dir: any) => {
    if (canSwipe && currentIndex < matchList!.length) {
      await childRefs[currentIndex].current.swipe(dir);
    } else {
      console.log("nao funfou");
    }
  };

  const goBack = async () => {
    if (currentIndex != matchList!.length - 1) {
      const newIndex = currentIndex + 1;
      updateCurrentIndex(newIndex);
      await childRefs[newIndex].current.restoreCard();
    }
  };

  const goToBackPage = () => navigate(-1);

  const isAutomatic: boolean = true;

  const parametersMatchUsers: any = isAutomatic
    ? {
        id: id,
        maxDistance: 100,
      }
    : {
        id: id,
        maxDistance: 100,
        genreName: GenreNameEnum.CLASSICAL,
        minSize: 1,
        maxSize: 1,
        roleName: RoleNameEnum.ACCORDIONIST,
      };

  const parametersMatchGroups: any = isAutomatic
    ? {
        userId: id,
        maxDistance: 100,
      }
    : {
        id: id,
        maxDistance: 100,
        minAge: 18,
        maxAge: 50,
        genreName: GenreNameEnum.CLASSICAL,
        roleName: RoleNameEnum.ACCORDIONIST,
        sex: SexEnum.MALE,
        skillLevel: SkillLevelEnum.ADVANCED,
      };

  const [opacity, setOpacity] = useState(0);
  const [pointerEvents, setPointerEvents] = useState("");
  const [isVisible, setIsVisible] = useState(false);

  const makeVisibleAndInvisible = (visible: boolean) => {
    const containerModalMatchs = document.getElementsByClassName(
      "container-modal-match"
    );

    if (visible) {
      setOpacity(1);
      setIsVisible(true);
      setPointerEvents("auto");

      for (let i = 0; i < containerModalMatchs.length; i++) {
        const containerModalMatch = containerModalMatchs[i] as HTMLElement;
        containerModalMatch.style.opacity = `1`;
        containerModalMatch.style.pointerEvents = `auto`;
      }

      return;
    } else {
      setOpacity(0);
      setIsVisible(false);
      setPointerEvents("none");

      for (let i = 0; i < containerModalMatchs.length; i++) {
        const containerModalMatch = containerModalMatchs[i] as HTMLElement;
        containerModalMatch.style.opacity = `false`;
        containerModalMatch.style.pointerEvents = `none`;
      }
    }
  };

  useEffect(() => {
    onkeyup = (e: any) => {
      if (e.keyCode === 37) {
        console.log("key left");
        swipe("left");
      } else if (e.keyCode === 39) {
        console.log("key right");
        swipe("right");
      }
    };

    const getUserProfile = async () => {
      const queryString = window.location.search;
      const urlParams = new URLSearchParams(queryString);
      const viewerId = urlParams.get("viewerId");

      if (viewerId) {
        await UserRoute.getProfileForId(id, viewerId)
          .then((res) => {
            if (res.status === 200) {
              setUserProfile(res.data);
              getMatchList(res.data);
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
    };

    const getMatchList = async (user: UserProfileResponseDto) => {
      const buttons = document.getElementsByClassName("buttons");

      if (user?.group != null || user?.leader == true) {
        console.log("buscando usuários...");

        await UserRoute.getMatchList(parametersMatchUsers)
          .then((res) => {
            if (res.status === 200) {
              setMatchList(res.data);
              matchListLength = res.data.length;
              setCurrentIndex(res.data.length - 1);
              currentIndexRef.current = res.data.length - 1;
              canGoBack = currentIndex < matchListLength - 1;

              if (res.data.length > 0) {
                for (let i = 0; i < buttons.length; i++) {
                  const button = buttons[i] as HTMLElement;
                  button.style.opacity = "1";
                  button.style.pointerEvents = "auto";
                }
              }
            }
          })
          .catch((err) => {
            console.log(err);
          });
      } else if (user?.group == null) {
        console.log("buscando grupos...");

        await GroupRoute.getMatchList(parametersMatchGroups)
          .then((res) => {
            if (res.status === 200) {
              setMatchGroupList(res.data);
              matchListLength = res.data.length;
              setCurrentIndex(res.data.length);
              currentIndexRef.current = res.data.length;

              console.log(res.data.length);
              console.log(currentIndexRef.current);
              console.log(matchListLength);
              console.log(currentIndex);

              if (res.data.length > 0) {
                for (let i = 0; i < buttons.length; i++) {
                  const button = buttons[i] as HTMLElement;
                  button.style.opacity = "1";
                  button.style.pointerEvents = "auto";
                }
              }
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
    };

    const getContactList = async () => {
      await UserRoute.getContactList(id)
        .then((res) => {
          if (res.status === 200) {
            setContactList(res.data);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    };

    getUserProfile();
    getContactList();
  }, []);

  let isLoading = 0;

  useEffect(() => {
    isLoading++;

    if (isLoading != 0) {
    }
  }, [matchList]);

  let isContainerMatchesVisible: boolean = true;

  const replaceMatchContent = (name: string) =>
    name === "match"
      ? (isContainerMatchesVisible = true)
      : (isContainerMatchesVisible = false);

  const [clickedButton, setClickedButton] = useState("");

  const buttonHandler = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    const button: HTMLButtonElement = event.currentTarget;
    setClickedButton(button.name);
    console.log(button.name);
  };

  const returnContainerModalMatch = (notification: any) => {
    // if (notification.type === NotificationTypeEnum.MATCH) {
    console.log(notification.type);
    if (notification.type) {
      return (
        <div className="container-modal-match" id="modalNewMatchId">
          <div className="modal-match">
            <i
              className="bx bxs-x-circle btn-close-modal-match"
              onClick={(e) => makeVisibleAndInvisible(false)}
            ></i>
            <p>Sintonize-se!</p>
            <p>
              Parece que {notification.sender.name} atende a todos os seus
              requisitos.
            </p>
            <div className="modal-match-picture">
              <div
                className="img-modal-match"
                style={{
                  backgroundImage: `url(${userProfile?.profilePic})`,
                  backgroundSize: "cover",
                  backgroundPosition: "center",
                }}
              />
              <div
                className="img-modal-match"
                style={{
                  backgroundImage: `url(${notification.sender.profilePic})`,
                  backgroundSize: "cover",
                  backgroundPosition: "center",
                }}
              />
            </div>
            <button onClick={buttonHandler} name="init chat">
              Iniciar Conversa
            </button>
            <button onClick={buttonHandler} name="view profile">
              Visualizar Perfil
            </button>
          </div>
        </div>
      );
    } else {
      return null;
    }
  };

  const returnNoMoreCards = () => {
    return (
      <div className="no-more-cards">
        <h1>No more cards</h1>
      </div>
    );
  };

  const loadingProfileDetails = (idProfile: number) => {
    navigate("");
  };

  return (
    <>
      {newNotifications != null
        ? newNotifications.map((notification, index) => {
            returnContainerModalMatch(notification);
          })
        : null}
      <div className="container-match">
        <div className="item-match">
          <nav className="nav-match">
            <div className="nav-item">
              <div
                className="img-perfil"
                style={{
                  backgroundImage: `url(${userProfile?.profilePic})`,
                  backgroundSize: "cover",
                  backgroundPosition: "center",
                }}
              />
              <p>Meu Perfil</p>
            </div>
            <div className="nav-item">
              <i
                className="bx bxs-chevron-left"
                onClick={() => goToBackPage()}
              />
            </div>
          </nav>
          <div className="match-content">
            <div className="match-nav">
              <div
                className="match-li"
                onClick={() => replaceMatchContent("match")}
              >
                <p>Matches</p>
                <div className="number-match-nav">
                  {contactList?.length || 0}
                </div>
              </div>
            </div>
            {isContainerMatchesVisible ? (
              <div className="container-matchs">
                {contactList?.map((contact) => (
                  <UserMatchSideNav contactMatch={contact} />
                ))}
              </div>
            ) : (
              <ContainerMessage />
            )}
          </div>
        </div>
        <div className="item-match">
          {matchList != null
            ? matchList?.map((match, index) => {
                return (
                  <TinderCard
                    ref={childRefs[index]}
                    className="swipe"
                    key={match?.id}
                    preventSwipe={["up", "down"]}
                    onSwipe={(dir) => swiped(dir, match?.name, index, match.id)}
                    onCardLeftScreen={() => outOfFrame(match?.name, index)}
                  >
                    <div
                      style={{
                        backgroundImage: `url(${
                          match?.profilePic ||
                          "https://i.pinimg.com/736x/c9/e3/e8/c9e3e810a8066b885ca4e882460785fa.jpg"
                        })`,
                      }}
                      className="card"
                    >
                      <h3>{match?.name}</h3>
                    </div>
                  </TinderCard>
                );
              })
            : matchGroupList?.map((match, index) => {
                return (
                  <TinderCard
                    ref={childRefs[index]}
                    className="swipe"
                    key={match?.id}
                    preventSwipe={["up", "down"]}
                    onSwipe={(dir) => swiped(dir, match?.name, index, match.id)}
                    onCardLeftScreen={() => outOfFrame(match?.name, index)}
                  >
                    <div
                      style={{ backgroundImage: `url(${match?.profilePic})` }}
                      className="card"
                    >
                      <h3>{match?.name}</h3>
                    </div>
                  </TinderCard>
                );
              })}
          {currentIndexRef.current < 0 ? returnNoMoreCards() : null}
          <div className="buttons" id="buttonsMatchs">
            <button onClick={() => swipe("left")}>
              <i className="bx bx-x"></i>
            </button>
            <button onClick={() => goBack()}>
              <i className="bx bx-undo"></i>
            </button>
            <button onClick={() => swipe("right")}>
              <i className="bx bxs-heart"></i>
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
