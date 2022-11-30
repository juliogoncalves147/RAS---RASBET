

import React from 'react';
import NavBarLog from './components/NavBarLog';
import Footer from './components/Footer'
import Boletim from './Boletim';


function PaginaInicial() {
  return (
    <>
      <NavBarLog />
      <Boletim  />
      <Footer />
    </>
  );
}


export default PaginaInicial;