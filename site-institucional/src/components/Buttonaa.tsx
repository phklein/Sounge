import { ButtonHTMLAttributes } from 'react'

type ButtonaaProps = ButtonHTMLAttributes<HTMLButtonElement>

export function Buttonaa(props: ButtonaaProps) {
    return (
        <button className="button" {...props} />
    )
}