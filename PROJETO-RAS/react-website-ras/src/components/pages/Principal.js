import React, {useState} from "react";

import { Button } from "../Button";
import Footer from "../Footer";
import Navbar from "../NavBarLog";
import "./Principal.css";


const PaginaInicial = () => {
    
  const [myArray, updateMyArray] = useState([]);
  const [myOdds, updateMyOdds] = useState([]);
  
    
    //const handleClick = () => setSelecao(!selecao);
   



const handlerCLick = (e) => {
  
    //myOdds.push(e.target.odd);
    
   
 
    
    

    updateMyArray(myArray.concat(e.target.id));
    updateMyOdds(myOdds.concat(e.target.odd));
    console.log(myArray);
    console.log(myOdds);
}

function update(){
  return 
}

const getOddtotal = () => {
  let oddTotal = 1;
  myOdds.forEach((odd) => {
    oddTotal *= odd;
  });
  return oddTotal;
};



    
  return (
    <>
      <Navbar />
      <div className="float-container">
        <div className="float-child">
          <div className="lista">

            Procurar Jogos: 
            <div className="listaJogos">
            <textarea id="w3review" name="w3review" rows="1" cols="40"></textarea>
            </div>
          </div>
          <div className="lista2">
          <div>
              Sporting vs Farense
            <div className="jogo">
              <div className="hipotese">
              <Button  className='btns' id='Sporting' odd='1.1' buttonStyle='btn--outline' buttonSize='btn--small'  onClick={handlerCLick} >
                Sporting 1,10
                </Button>
              </div>
             
              <div className="hipotese">
              <Button className='btns' id='Empate' odd='3.10' buttonStyle='btn--outline' buttonSize='btn--small' onClick={handlerCLick} >
                Empate 3,10
                </Button>
              </div>
              <div className="hipotese">
              <Button  className='btns' id='Varzim' odd='5.00' buttonStyle='btn--outline' buttonSize='btn--small' onClick={handlerCLick}>
                Varzim 5,00
                </Button>
              </div>
            </div>
          </div>
          </div>
          <div className="lista2">
          <div>
              Braga vs Porto
            <div className="jogo">
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Braga 2,60
                </Button>
              </div>
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Empate 3,28
                </Button>
              </div>
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Porto 5,00
                </Button>
              </div>
            </div>
          </div>
          </div>
          <div className="lista2">
            <div>
              Lomar vs Ferreiros
            <div className="jogo">
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                
                Lomar 1,23
                </Button>
              </div>
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Empate 5,30
                </Button>
              </div>
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Ferreiros 2,09
                </Button>
              </div>
            </div>
            </div>
          </div>
          <div className="lista2">
          <div>
              Aveleda vs Benfica 
            <div className="jogo">
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Aveleda 1,72
                </Button>
              </div>
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Empate 1,90
                </Button>
              </div>
              <div className="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Benfica 1,47
                </Button>
              </div>
            </div>
          </div>
          </div>
        </div>
        <div className="float-child">
          <div className="boletim">Boletim de Apostas
           <div>


            <div className="modo">
            <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}> Simples    </Button> </div> 

            <div className="modo">
               <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}> Multipla</Button>
               
               </div> 
            
              

            <div className="aposta">  
            
            
              {myArray.map(l => {
                return  (  <div> <Button  className='btns' buttonStyle='btn--primary' buttonSize='btn--small' onClick={() => {
                  updateMyArray(
                    myArray.filter(a =>
                      a.id !== myArray.id
                    )
                  );
                }}>  {l} </Button></div>  )
               })}

              
            </div>
            


            
           


            <div className="montante">  Montante apostado:</div>

          
            <div className="valor"> 
            <div className = "ganhos"> Cota final: {getOddtotal()} </div> 
            <div className = "ganhos"> Ganhos:</div>
            


            </div>



            </div>

            </div>

        </div>
        </div>
      <Footer />
    </>
  );
};

export default PaginaInicial;