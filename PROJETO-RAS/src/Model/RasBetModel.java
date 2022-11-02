package Model;

import java.util.*;
import Entidades.*;


public class RasBetModel implements IRasBetFacade{
    public IApostadorFacade apostadorFacade;
    public IAdministradorFacade administradorFacade;
    public ITecnicoFacade tecnicoFacade;
    public IEspecialistaFacade especialistaFacade;


    public RasBetModel() {
        apostadorFacade = new Apostador();
        administradorFacade = new Administrador();
        tecnicoFacade = new Tecnico();
        especialistaFacade = new Especialista();
    }



 


}