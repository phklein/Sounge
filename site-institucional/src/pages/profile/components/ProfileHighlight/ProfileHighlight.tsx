import { CircularProgress } from "@mui/material";
import React from "react";
import "./ProfileHighlight.style.css";

const ProfileHighlight = ({
  canEdit = false,
  bannerSrc = "https://i.pinimg.com/originals/70/c3/26/70c326bf1a4214a492a69d4a7f6d91b8.jpg",
  avatarSrc = "https://agenciauva.files.wordpress.com/2018/08/otakus-features-origin-and-types.jpg",
  userInfo = { name: "Elvis Presley", description: "Rei do Rock'n'Roll" },
  handleAvatarChange = () => {},
  handleBannerChange = () => {},
  loadingAvatar = false,
  loadingBanner = false,
}: {
  canEdit: boolean;
  bannerSrc: string;
  avatarSrc: string;
  userInfo: any;
  handleAvatarChange?: any;
  handleBannerChange?: any;
  loadingAvatar?: boolean;
  loadingBanner?: boolean;
}) => {
  return (
    <div className="profileHighlightWrapper">
      <div className="profileHighlightBannerWrapper">
        {loadingBanner ? (
          <CircularProgress />
        ) : (
          <>
            {canEdit ? (
              <>
                {" "}
                <input
                  id="banner-upload"
                  type="file"
                  accept="image/png, image/jpeg"
                  onChange={(event) => handleBannerChange(event.target)}
                />
                <label htmlFor="banner-upload"></label>
              </>
            ) : null}

            <img
              className="profileHighlightBanner"
              src={`data:image/png;base64,${bannerSrc}`}
            />
          </>
        )}
      </div>
      <div className="profileHighlightUser">
        <div className="profileHighlightUserImg">
          {loadingAvatar ? (
            <CircularProgress />
          ) : (
            <>
              {canEdit ? (
                <>
                  <input
                    id="avatar-upload"
                    type="file"
                    accept="image/png, image/jpeg"
                    onChange={(event) => handleAvatarChange(event.target)}
                  />
                  <label htmlFor="avatar-upload"></label>
                </>
              ) : null}

              <img src={`data:image/png;base64,${avatarSrc}`} />
            </>
          )}
        </div>
        <h3>{userInfo.name}</h3>
        <span>{userInfo.description}</span>
      </div>
    </div>
  );
};

export default ProfileHighlight;
