
import { RoleNameEnumDesc } from "../enums/RoleNameEnum";
import "../styles/pageintroduction.css"

interface Iprops {
  name: string
}

export function PageIntroduction(props: Iprops) {
  const {name} = props

  return (
    <>
        <div className="info-player">
          <div className="color-player img-guitar"></div>
          <h5 className="typograph-player">{RoleNameEnumDesc.get(name)}</h5>
        </div>
    </>
  );
}
