package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;

public class CuentaService {
    CuentaDao cuentaDao = new CuentaDao();

    @Autowired
    ClienteService clienteService;

    //Generar casos de test para darDeAltaCuenta
    //    1 - cuenta existente
    //    2 - cuenta no soportada
    //    3 - cliente ya tiene cuenta de ese tipo
    //    4 - cuenta creada exitosamente
    public void darDeAltaCuenta(Cuenta cuenta, long dniTitular) throws CuentaAlreadyExistsException{
        if(cuentaDao.find(cuenta.getNumeroCuenta()) == null) {
            throw new CuentaAlreadyExistsException("La cuenta " + cuenta.getNumeroCuenta() + " ya existe.");
        }
        cuentaDao.save(cuenta);
    }
    public boolean tipoCuentaEstaSoportada(Cuenta cuenta){

        if (cuenta.getMoneda() == TipoMoneda.DOLARES &&
                cuenta.getTipoCuenta() == TipoCuenta.CAJA_AHORRO){
            return true;
        }

        return cuenta.getMoneda() == TipoMoneda.PESOS &&
                (cuenta.getTipoCuenta() == TipoCuenta.CUENTA_CORRIENTE ||
                        cuenta.getTipoCuenta() == TipoCuenta.CAJA_AHORRO);
    }

    public Cuenta find(long id) {
        return cuentaDao.find(id);
    }
}
