import React, { useState } from "react";
import "./ProfileNavigationTabs.style.css";

const ProfileNavigatorTabs = ({
  canEdit = false,
  options = [],
}: {
  canEdit?: any;
  options?: any[];
}) => {
  const [currentTab, setCurrentTab] = useState(0);

  return (
    <div className="profileTabs">
      <ul className="profileTabsNavigation profileLists">
        {options.map((option, index) =>
          typeof option.CustomOption !== "function" ? (
            <li
              key={`${currentTab}-${option.label}`}
              className={
                currentTab === index ? "profileTabsOption--active" : ""
              }
            >
              <a
                onClick={() => {
                  setCurrentTab(index);
                  option.handleClick(index);
                }}
              >
                {option.label}
              </a>
            </li>
          ) : (
            <li
              key={`${currentTab}-${option.label}`}
              className={
                currentTab === index ? "profileTabsOption--active" : ""
              }
            >
              <option.CustomOption
                canEdit={canEdit}
                currentTab={index}
                handleChangeTab={setCurrentTab}
              />
            </li>
          )
        )}
      </ul>
    </div>
  );
};

export default ProfileNavigatorTabs;
