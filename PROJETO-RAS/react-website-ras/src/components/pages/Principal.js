import React, {useState} from "react";

import { Button } from "../Button";
import Footer from "../Footer";
import Navbar from "../NavBarLog";
import "./Principal.css";


const DesktopOneThreePage = () => {
    const[selecao, setSelecao] = useState(false);

    const handleClick = () => setSelecao(!selecao);


    
  return (
    <>
      <Navbar />
      <div class="float-container">
        <div class="float-child">
          <div class="lista">
            Procurar Jogos: 
            <div class="listaJogos">
            <textarea id="w3review" name="w3review" rows="1" cols="40"></textarea>
            </div>
          </div>
          <div class="lista2">
          <div>
              Sporting vs Farense
            <div class="jogo">
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small'>
                Sporting 1,10
                </Button>
              </div>
              <div class="hipotese">
              <Button className='btns' buttonStyle='btn--outline' buttonSize='btn--small'>
                Empate 3,10
                </Button>
              </div>
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Varzim 5,00
                </Button>
              </div>
            </div>
          </div>
          </div>
          <div class="lista2">
          <div>
              Braga vs Porto
            <div class="jogo">
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Braga 2,60
                </Button>
              </div>
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Empate 3,28
                </Button>
              </div>
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Porto 5,00
                </Button>
              </div>
            </div>
          </div>
          </div>
          <div class="lista2">
            <div>
              Lomar vs Ferreiros
            <div class="jogo">
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                
                Lomar 1,23
                </Button>
              </div>
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Empate 5,30
                </Button>
              </div>
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Ferreiros 2,09
                </Button>
              </div>
            </div>
            </div>
          </div>
          <div class="lista2">
          <div>
              Aveleda vs Benfica 
            <div class="jogo">
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Aveleda 1,72
                </Button>
              </div>
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Empate 1,90
                </Button>
              </div>
              <div class="hipotese">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}>
                Benfica 1,47
                </Button>
              </div>
            </div>
          </div>
          </div>
        </div>
        <div class="float-child">
          <div class="boletim">Boletim de Apostas
           <div>
            <div class="modo">
            <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}> Simples    </Button> </div> 

            <div class="modo"> <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}> Multipla</Button></div> 

            <div class="aposta">  Aposta</div>


            <div class="montante">  Montante apostado:</div>



            <div class="valor"> 
            <div class = "ganhos"> Cota final:</div>
            <div class = "ganhos"> Ganhos:</div>


            </div>



            </div>

            </div>

        </div>
        </div>
      <Footer />
    </>
  );
};

export default DesktopOneThreePage;