
import './App.css';
import React from 'react';

import "bootstrap/dist/css/bootstrap.min.css"
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Home from './components/pages/Home';
import Auth from "./Auth"
import AuthReg from './AuthReg';
import PaginaInicial from './PaginaInicial';


function App() {
  return (
    <>
    <Router>
    <Routes>
        <Route exact path='/' element = {<Home/>} />
        <Route exact path='/auth' element = {<Auth/>} />
        <Route exact path='/authReg' element = {<AuthReg/>} />
        <Route exact path='/paginainicial' element = {<PaginaInicial/>} />

    </Routes>
    </Router>
    </>
  );
}

export default App;
