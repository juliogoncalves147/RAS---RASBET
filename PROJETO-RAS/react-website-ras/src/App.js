
import './App.css';
import React from 'react';

import "bootstrap/dist/css/bootstrap.min.css"
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Home from './components/pages/Home';
import Auth from "./Auth"
import Iniciarsessao from './components/pages/Iniciarsessao';
function App() {
  return (
    <>
    <Router>
    <Routes>
        <Route exact path='/' element = {<Home/>} />
        <Route exact path='/auth' element = {<Auth/>} />
    </Routes>
    </Router>
    </>
  );
}

export default App;
