
import { RoleNameEnumDesc } from "../enums/RoleNameEnum";
import "../styles/pageintroduction.css"

interface Iprops {
  id: number,
  name: string
}

export function PageIntroduction(props: Iprops) {
  const {id, name} = props

  return (
    <>
        <div className="info-player">
          <div className="color-player img-guitar"></div>
          <h5 className="typograph-player">{RoleNameEnumDesc.get(name)}</h5>
        </div>
    </>
  );
}
