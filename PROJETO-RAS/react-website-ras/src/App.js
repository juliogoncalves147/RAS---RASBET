
import './App.css';
import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Home from './components/pages/Home';
import Iniciarsessao from './components/pages/Iniciarsessao';
function App() {
  return (
    <>
    <Router>
    <Routes>
        <Route exact path='/' element = {<Home/>} />
        <Route exact path='/iniciarsessao' element = {<Iniciarsessao/>} />
    </Routes>
    </Router>
    </>
  );
}

export default App;
