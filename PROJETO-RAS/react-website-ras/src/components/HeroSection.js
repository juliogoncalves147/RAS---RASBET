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

          
            <Button path='/auth' className='btns' buttonStyle='btn--outline' buttonSize='btn--large'>
                ENTRAR
            </Button>
            <Button path='/consultarjogos' className='btns' buttonStyle='btn--outline' buttonSize='btn--large'>
                CONSULTAR JOGOS
            </Button>

        </div>



    </div>
  )
}

export default HeroSection