import React, { useEffect, useState } from "react"

const ProfileNavigatorTabs = ({ currentTab = 0, handleChangeTab }: { currentTab: number, handleChangeTab: Function }) => {
	return (
		<div className='profileTabs'>
			<ul className='profileTabsNavigation'>
				<li className={currentTab === 0 ? "active" : ""}>
					<a onClick={() => handleChangeTab(0)}>Feed</a>
				</li>
				<li className={currentTab === 1 ? "active" : ""}>
					<a onClick={() => handleChangeTab(1)}>Sobre</a>
				</li>
				<li className={currentTab === 2 ? "active" : ""}>
					<a onClick={() => handleChangeTab(2)}>Videos</a>
				</li>
				<li className={currentTab === 3 ? "active" : ""}>
					<a onClick={() => handleChangeTab(3)}>Playlists</a>
				</li>
				<li className={currentTab === 4 ? "active" : ""}>
					<a onClick={() => handleChangeTab(4)}>Mais</a>
				</li>
			</ul>
		</div>
	)
}

export default ProfileNavigatorTabs
