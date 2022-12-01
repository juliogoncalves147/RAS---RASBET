

import React, { useState } from "react"
import { Link, useHistory } from "react-router-dom"

export default function (props) {
  let [authMode, setAuthMode] = useState("signup")

  const changeAuthMode = () => {
    setAuthMode(authMode === "signin" ? "signup" : "signin")
  }

  if (authMode === "signin") {
    return (
      <div className="Auth-form-container">
        <form className="Auth-form">
          <div className="Auth-form-content">
            <h3 className="Auth-form-title">Iniciar sessão</h3>
            <div className="text-center">
              Ainda não estás registado?{" "}
              <span className="link-primary" onClick={changeAuthMode}>
                Registar
              </span>
            </div>
            <div className="form-group mt-3">
              <label>Email</label>
              <input
                type="email"
                className="form-control mt-1"
                placeholder="Insira o seu email"
              />
            </div>
            <div className="form-group mt-3">
              <label>Password</label>
              <input
                type="password"
                className="form-control mt-1"
                placeholder="Insira a sua password"
              />
            </div>
            <div className="d-grid gap-2 mt-3">
            <Link to="/menuinicial">
              <button type="Submit" className="btn btn-primary">
                Submeter
              </button>
            </Link> 
            </div>
            <p className="text-center mt-2">
              Esqueceu a sua <a href="#">password?</a>
            </p>
          </div>
        </form>
      </div>
    )
  }

  return (
    <div className="Auth-form-container">
      <form className="Auth-form">
        <div className="Auth-form-content">
          <h3 className="Auth-form-title">Registo</h3>
          <div className="text-center">
            Já estás registado?{" "}
            <span className="link-primary" onClick={changeAuthMode}>
              Iniciar sessão
            </span>
          </div>
          
          <div className="form-group mt-3">
            <label>Email</label>
            <input
              type="email"
              className="form-control mt-1"
              placeholder="Email"
            />
          </div>
          <div className="form-group mt-3">
            <label>Password</label>
            <input
              type="password"
              className="form-control mt-1"
              placeholder="Password"
            />
          </div><div className="form-group mt-3">
            <label>Nome completo</label>
            <input
              type="nomecompleto"
              className="form-control mt-1"
              placeholder="Nome completo"
            />
          </div>
          <div className="form-group mt-3">
            <label>Nome de Utilizador</label>
            <input
              type="nomeutilizador"
              className="form-control mt-1"
              placeholder="Nome de Utilizador"
            />
          </div>
          <div className="form-group mt-3">
            <label>Data de nascimento</label>
            <input
              type="data"
              className="form-control mt-1"
              placeholder="e.g. 01/01/2000"
            />
          </div>
          <div className="form-group mt-3">
            <label>Número de Identificação Fiscal</label>
            <input
              type="nif"
              className="form-control mt-1"
              placeholder="Número de Identificação Fiscal"
            />
          </div>
          <div className="form-group mt-3">
            <label>Número de Identificação Civil</label>
            <input
              type="nic"
              className="form-control mt-1"
              placeholder="Número de Identificação Civil"
            />
          </div>
     
          
          <div className="d-grid gap-2 mt-3">
            <Link to="/menuinicial">
            <button type="submit" className="btn btn-primary">
              Submeter
            </button>
            </Link>
          </div>
         
        </div>
      </form>
    </div>
  )
}