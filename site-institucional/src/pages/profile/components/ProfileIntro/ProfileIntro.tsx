import React from "react";
import MusicNote from "@mui/icons-material/MusicNote";
import "./ProfileIntro.style.css";
import { RoleNameEnumDesc } from "../../../../enums/RoleNameEnum";
import { SkillLevelEnumDesc } from "../../../../enums/SkillLevelEnum";

export const PROFILE_TYPE = {
  USER: 0,
  BAND: 1,
};

const ProfileSkillInfo = ({ label = "" }: { label: string }) => {
  return (
    <li className="profileShowcaseIntroSkill">
      <MusicNote fontSize="large" />
      <span>{RoleNameEnumDesc.get(label)}</span>
    </li>
  );
};

const ProfileBandInfo = ({
  leader = false,
  imageSrc = "",
  name = "",
  role = "",
  handleClick = () => {},
}: {
  leader?: boolean;
  imageSrc: string;
  name: string;
  role: string;
  handleClick?: Function;
}) => {
  return (
    <li className="profileShowcaseIntroBandWrapper">
      <a className="profileShowcaseIntroBand" onClick={() => handleClick()}>
        <img src={imageSrc} />
        <div className="profileShowcaseIntroBandInfo">
          <span className="profileShowcaseIntroBandInfoName">
            {leader ? (
              <span>
                {name}{" "}
                <span className="profileShowcaseIntroBandInfoLeader">{`(Líder da Banda)⭐`}</span>
              </span>
            ) : (
              name
            )}
          </span>
          <span className="profileShowcaseIntroBandInfoRole">{role}</span>
        </div>
      </a>
    </li>
  );
};

const ProfileIntro = ({
  infos = {},
  type = PROFILE_TYPE.USER,
  handleClick = undefined,
}: {
  infos: { skills?: any[]; bands?: any; members?: any[] };
  type: number;
  handleClick?: any;
}) => {
  return (
    <div className="profileShowcaseIntro">
      {type === PROFILE_TYPE.USER ? (
        <>
          <h2>Intro</h2>
          <p>
            Nível -{" "}
            {SkillLevelEnumDesc.get(infos.skills && infos.skills[0].skillLevel)}
          </p>
          <ul className="profileLists">
            {infos?.skills?.map((info, index) => {
              return (
                <ProfileSkillInfo
                  key={`${info.label}-${index}`}
                  label={info.label}
                />
              );
            })}
          </ul>
          <h2>Banda</h2>
          <ul className="profileLists">
            <ProfileBandInfo
              key={`${infos.bands?.name}`}
              imageSrc={infos.bands?.imageSrc}
              name={infos.bands?.name}
              role={infos.bands?.role}
              leader={infos.bands?.leader}
              handleClick={() =>
                handleClick && handleClick(infos.bands?.bandId)
              }
            />
          </ul>
        </>
      ) : (
        <>
          <h2>Intro</h2>
          <ul className="profileLists">
            {infos?.members?.map((info) => {
              return (
                <ProfileBandInfo
                  key={`${info.name}-index`}
                  imageSrc={info.imageSrc}
                  name={info.name}
                  role={info.role}
                  leader={info.leader}
                  handleClick={() => handleClick && handleClick(info.userId)}
                />
              );
            })}
          </ul>
        </>
      )}
    </div>
  );
};

export default ProfileIntro;
