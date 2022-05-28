import React, { useEffect, useState } from "react"
import ArrowDropDown from "@mui/icons-material/ArrowDropDown"
import { ClickAwayListener } from "@mui/material"
import "./ProfileNavigationTabs.style.css"

const Popover = ({ open = false, children = <span></span> }: { open: boolean; children: any }) => {
	return <div className={`popover-menu ${open ? "popover-menu--active" : ""}`}>{children}</div>
}

const ProfileNavigatorTabs = ({
	currentTab = 0,
	handleChangeTab,
}: {
	currentTab: number
	handleChangeTab: Function
}) => {
	const [isMenuOpen, setIsMenuOpen] = useState(false)

	return (
		<div className='profileTabs'>
			<ul className='profileTabsNavigation profileLists'>
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
					<div>
						<ClickAwayListener onClickAway={() => setIsMenuOpen(false)}>
							<div>
								<a
									onClick={() => {
										handleChangeTab(4)
										setIsMenuOpen(true)
									}}
								>
									Mais
									<ArrowDropDown />
								</a>
								<Popover open={isMenuOpen}>
									<ul className='popover-options'>
										<li>
											<a>Chat</a>
										</li>
										<li>
											<a>Criar uma banda</a>
										</li>
									</ul>
								</Popover>
							</div>
						</ClickAwayListener>
					</div>
				</li>
			</ul>
		</div>
	)
}

export default ProfileNavigatorTabs
