import React from 'react';
import './Button.css';
import { Link } from 'react-router-dom';

const STYLES = ['btn--primary', 'btn--outline', 'btn--info','button-12','button-27'];

const SIZES = ['btn--medium', 'btn--large','btn--small'];

export const Button = ({
    path,
    children,
    type,
    id,
    odd,
    handlerClick,
    buttonStyle,
    buttonSize}) => {
    const checkButtonStyle = STYLES.includes(buttonStyle) ? buttonStyle : STYLES[0];
    const checkButtonSize = SIZES.includes(buttonSize) ? buttonSize : SIZES[0];

    return (
        <Link to={path} className='btn-mobile'>
            <button
                className={`btn ${checkButtonStyle} ${checkButtonSize}`}
                id={id}
                odd={odd}
                onClick={()=>handlerClick(id,odd)}
                type={type}>
                {children}
            </button>
        </Link>
    );
    };
