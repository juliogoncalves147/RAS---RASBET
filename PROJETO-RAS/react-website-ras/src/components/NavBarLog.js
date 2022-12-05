import React, {useState, useEffect} from 'react'
import { Link } from 'react-router-dom';
import {Button} from './Button';
import './Navbar.css';

function NavbarLog() {
  const [click, setClick] = useState(false);
  const [button, setButton] = useState(true);

  const handleClick = () => setClick(!click);

  const closeMobileMenu = () => setClick(false);

  const showButton = () => {
    if (window.innerWidth <= 960) {
      setButton(false);
    } else {
      setButton(true);
    }
  };

  useEffect(() => {
    showButton();
  }, []);

  window.addEventListener('resize', showButton);

  
  return (
    <>
        <nav className="navbar">
            <div className="navbar-container">
                <Link to='/' className="navbar-logo" onClick={closeMobileMenu}>
                    RasBet <i className="fa-solid fa-person-running" />
                </Link>
                <div className="menu-icon" onClick={handleClick}>
                    <i className={click ? 'fas fa-times' : 'fas fa-bars'} />
                </div>
                <ul className={click ? 'nav-menu active' : 'nav-menu'}>
                     <li className="nav-item">
                        <Link to='/todos' className="nav-links" onClick={closeMobileMenu}>
                            Todos
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link to='/editarPerfil' className="nav-links" onClick={closeMobileMenu}>
                            EditarPerfil
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link to='/futebol' className="nav-links" onClick={closeMobileMenu}>
                            Futebol
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link to='/' className="nav-links-mobile" onClick={closeMobileMenu}>
                            Sair
                        </Link>
                    </li>
                </ul>
                {button && <Button path='/' buttonStyle='btn--outline'>Sair</Button>}
            </div>
        </nav>

    </>
  )
}

export default NavbarLog