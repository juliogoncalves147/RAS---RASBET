
import './App.css';
import React from 'react';

import "bootstrap/dist/css/bootstrap.min.css"
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Home from './components/pages/Home';
import Auth from "./Auth"
import AuthReg from './AuthReg';
import PaginaInicial from './PaginaInicial';
import PedidoAjuda from './components/pages/PedidoAjuda';
import Principal from './components/pages/Principal';

function App() {
  return (
    <>
    <Router>
    <Routes>
        <Route exact path='/' element = {<Home/>} />
        <Route exact path='/auth' element = {<Auth/>} />
        <Route exact path='/authReg' element = {<AuthReg/>} />
        <Route exact path='/paginainicial' element = {<PaginaInicial/>} />
        <Route exact path='/pedidoajuda' element = {<PedidoAjuda/>} />
        <Route exact path='/menuinicial' element = {<Principal/>} />
    </Routes>
    </Router>
    </>
  );
}

export default App;
