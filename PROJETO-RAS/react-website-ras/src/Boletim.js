import React from "react"
import {Button} from './components/Button';


function Boletim() {
    return (
      
      <div className='boletim-container'>
          <h1> Boletim de apostas  </h1>
          
          <div className='boletim-btns'>        
              <Button className='btns' buttonStyle='btn--outline2' buttonSize='btn--large'>
                  Simples
              </Button>
              <Button className='btns' buttonStyle='btn--outline2' buttonSize='btn--large'>
                  Multipla
              </Button>
  
          </div>

           <h1> Montante Apostado: </h1>
          
  
  
  
      </div>
    )
  }
  
  export default Boletim