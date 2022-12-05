import React, {useState} from "react";

import { Button } from "../Button";
import Footer from "../Footer";
import Navbar from "../NavBarLog";
import useFetch from "../useFetch";
import "./Principal.css";


const PaginaInicial = () => {
    
  const [myArray, updateMyArray] = useState([]);
  const [myOdds, updateMyOdds] = useState([]);
  
  const {data, loading, error} = useFetch("http://ucras.di.uminho.pt/v1/games/");
    //const handleClick = () => setSelecao(!selecao);

const handlerCLick = (id, odd) => {
    //myOdds.push(e.target.odd);
    updateMyOdds(myOdds.concat(odd));
    updateMyArray(myArray.concat(id));
    console.log(myOdds);
    console.log(myArray);
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
        <div className="float-child1">
            <input type="input" className="form__field" placeholder="Procurar Jogos" name="name" id='name' required />
              <label for="name" className="form__label"></label>
            
            <div className="containerJogo">
              <div className="jogo2">
              <p>Sporting vs Varzim</p>
              <p>Hoje - 19:30H</p>
              </div>
              <div className="containerBigOdd">
                <div className="containerOdd">
                  <Button id="Sporting"  odd="1,10" handlerClick={handlerCLick} >Sporting 1,10</Button>
                </div>
                <div className="containerOdd">
                  <Button id="Empate" odd="3,10" handlerClick={handlerCLick} >Empate 3,10</Button>
                </div>
                <div className="containerOdd">
                  <Button id="Varzim" odd="5,00" handlerClick={handlerCLick} >Varzim 5,00</Button>
                </div>
              </div>
            </div>
            <div className="containerJogo">
              <div className="jogo2">
              <p>Braga vs Porto</p>
              <p>Hoje - 20:00H</p>
              </div>
              <div className="containerBigOdd">
                <div className="containerOdd">
                  <Button id="Braga" odd="2,60" handlerClick={handlerCLick} >Braga 2,60</Button>
                </div>
                <div className="containerOdd">
                  <Button id="Empate" odd="3,28" handlerClick={handlerCLick} >Empate 3,28</Button>
                </div>
                <div className="containerOdd">
                  <Button id="Porto" odd="1,2" handlerClick={handlerCLick} >Porto 1,20</Button>
                </div>
              </div>
            </div>
            <div className="containerJogo">
              <div className="jogo2">
              <p>Lomar vs Ferreiros</p>
              <p>20/11/2022 - 18:00H</p>
              </div>
              <div className="containerBigOdd">
                <div className="containerOdd">
                  <Button id="Lomar" odd="1.23" handlerClick={handlerCLick} >Lomar 1,23</Button>
                </div>
                <div className="containerOdd">
                  <Button id="Empate" odd="5,30" handlerClick={handlerCLick} >Empate 5,30</Button>
                </div>
                <div className="containerOdd">
                  <Button id="Ferreiros" odd="2.09" handlerClick={handlerCLick} >Ferreiros 2,09</Button>
                </div>
              </div>
            </div>
            <div className="containerJogo">
              <div className="jogo2">
              <p>Aveleda vs Benfica</p>
              <p>12/12/2022 - 17:00H</p>
              </div>
              <div className="containerBigOdd">
                <div className="containerOdd">
                  <Button id="Sporting" handlerClick={handlerCLick} odd="1.5">Aveleda 1,72</Button>
                </div>
                <div className="containerOdd">
                  <Button id="Empate" handlerClick={handlerCLick} odd="1.5">Empate 1,90</Button>
                </div>
                <div className="containerOdd">
                  <Button id="Porto" handlerClick={handlerCLick} odd="1.5">Benfica 1,47</Button>
                </div>
              </div>
            </div>
        </div>
        <div className="float-child">
          <div className="boletim">
              <p>Boletim de Apostas</p>
          </div>
          <div className="tipo">
            <div className="modo">
            <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}> Simples </Button> </div> 
            <div className="modo">
              <Button  className='btns' buttonStyle='btn--outline' buttonSize='btn--small' onClick={() => this.handleClick()}> Multipla</Button>
            </div>
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
            <div className="calculos">
              <div className="montante">  Montante apostado:</div>
              <div className="valor"> 
              <div className = "ganhos"> Cota final: {getOddtotal()} </div> 
              <div className = "ganhos"> Ganhos:</div>
              </div>
            </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default PaginaInicial;