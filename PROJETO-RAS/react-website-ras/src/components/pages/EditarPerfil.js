import '../../App.css';
import React from 'react';

import { Button } from "../Button";
import Footer from "../Footer";
import Navbar from "../NavBarLog";
import "./EditarPerfil.css";




function EditarPerfil() {

    const handlerCLick = (e) => {
  
        //myOdds.push(e.target.odd);
        
       
     
        
        
    
        
    }
  return (
    <>
      <Navbar />
                  
      <div className="float-container">
        <div className="float-child">
          <div className="opcoes1">
          <Button  className='btns' buttonStyle='btn--info' buttonSize='btn--medium' onClick={() => this.handleClick()}> EDITAR PERFIL  </Button> 
          </div>

          <div className="opcoes">
          <Button  className='btns' buttonStyle='btn--info' buttonSize='btn--medium' onClick={() => this.handleClick()}> CONSULTAR HISTÓRICO DE APOSTAS  </Button>
          </div>

          <div className="opcoes">
          <Button  className='btns' buttonStyle='btn--info' buttonSize='btn--medium' onClick={() => this.handleClick()}> CONSULTAR HISTÓRICO DE TRANSAÇÕES  </Button> 
          </div>

          <div className="opcoes">
          <Button  className='btns' buttonStyle='btn--info' buttonSize='btn--medium' onClick={() => this.handleClick()}> DEPOSITAR/LEVANTAR DINHEIRO  </Button> 
          </div>




    
       
  
        </div>
        <div className="float-child">
          <div className="perfil"> 
          <i className="fa-solid fa-circle-user icon-large" />
          PERFIL
           <div className="teste">
            <div className="div1">

           <p>NOME</p>

          <p>NOME DE UTILIZADOR </p>

            <p>EMAIL</p>

            <p>NIF</p>

            <p>NIC</p>

            <p> PALAVRA-PASSE</p>


               
            


             </div>

             <div className="div2">


             <Button  className='btns' buttonStyle='button-12' buttonSize='btn--medium' onClick={() => this.handleClick()}> Diogo</Button>

             <Button  className='btns' buttonStyle='button-12' buttonSize='btn--medium' onClick={() => this.handleClick()}> purp</Button>

             <Button  className='btns' buttonStyle='button-12' buttonSize='btn--medium' onClick={() => this.handleClick()}> diogo172@gmail.com</Button>

             <Button  className='btns' buttonStyle='button-12' buttonSize='btn--medium' onClick={() => this.handleClick()}> 123456789</Button>

             <Button  className='btns' buttonStyle='button-12' buttonSize='btn--medium' onClick={() => this.handleClick()}> 123456789</Button>

             <Button  className='btns' buttonStyle='button-12' buttonSize='btn--medium' onClick={() => this.handleClick()}> ******** </Button>

            </div>
       </div>
   <div className='submeter'>
                
               <Button  className='btns1' buttonStyle='button-27' buttonSize='btn--medium' onClick={() => this.handleClick()}> Submeter</Button>
            

               </div>
        
       
        
        
        </div>
        </div>
        </div>
    
         
      <Footer />
    </>
  );
}


export default EditarPerfil;