import React from 'react'
import {Button} from './Button';
import './HeroSection.css';
import '../App.css';


function HeroSection() {
  return (
    <div className='hero-container'>
        <h1> RASBET </h1>
        <p> O seu site de apostas desportivas </p>
        <div className='hero-btns'>

          
            <Button className='btns' buttonStyle='btn--outline' buttonSize='btn--large'>
                INICIAR SESSÃO
            </Button>
            <Button className='btns' buttonStyle='btn--primary' buttonSize='btn--large'>
                REGISTAR
            </Button>

        </div>



    </div>
  )
}

export default HeroSection