import React from "react";


import Footer from "../Footer";
import Navbar from "../NavBarLog";
import "./PedidoAjuda.css";

const DesktopOneNineteenPage = () => {
  return (
    <>
    <Navbar />
      <div className="container">
        <div className="containerInterior">
          <div className = 'caixaPedido'>
            Pedido de Ajuda
          </div>
        <div className = 'caixaDescrição'>
        Descrição do Problema
        <textarea id="w3review" name="w3review" rows="10" cols="50"></textarea>
        </div>
        <div className = 'submeter'>
          <button className = 'submeterButton'>
            Submeter
          </button>
        </div>
        </div>
      </div>
    <Footer />
    </>
);
};

export default DesktopOneNineteenPage;
