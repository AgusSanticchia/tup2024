package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaSoportadaException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceTest {
    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private CuentaService cuentaService;
    @BeforeEach
    public void setUp() {}

    //Generar casos de test para darDeAltaCuenta
    //    1 - cuenta existente

    @Test
    public void testCuentaExistente() throws CuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setNombre("pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setDni(46637276);

        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setTitular(pepeRino);
        cuenta.setNumeroCuenta(65485545);

        when(cuentaDao.find(cuenta.getNumeroCuenta())).thenReturn(cuenta);

        assertThrows(CuentaAlreadyExistsException.class, () -> {
            cuentaService.darDeAltaCuenta(cuenta, pepeRino.getDni());
        });

        verify(cuentaDao, times(1)).find(cuenta.getNumeroCuenta());
    }
    //    2 - cuenta no soportada
    @Test
    public void testCuentaNoSoportada() throws TipoCuentaSoportadaException, CuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setNombre("pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setDni(46637276);

        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setMoneda(TipoMoneda.DOLARES);
        cuenta.setTitular(pepeRino);
        cuenta.setNumeroCuenta(65485545);

        assertThrows(TipoCuentaSoportadaException.class, () -> {
            cuentaService.darDeAltaCuenta(cuenta, pepeRino.getDni());
        });
    }
    //    3 - cliente ya tiene cuenta de ese tipo
    @Test
    public void testTipoDeCuentaRepetida() throws CuentaAlreadyExistsException, TipoCuentaSoportadaException, TipoCuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setNombre("pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setDni(46637276);

        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setTitular(pepeRino);
        cuenta.setNumeroCuenta(65485545);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta2.setMoneda(TipoMoneda.PESOS);
        cuenta2.setTitular(pepeRino);
        cuenta2.setNumeroCuenta(65485546);

        when(cuentaDao.find(cuenta.getNumeroCuenta())).thenReturn(null);

        //verifica que no se encontró ninguna cuenta el número de cuenta especificado
        Cuenta comprobacion = cuentaDao.find(cuenta.getNumeroCuenta());
        assertNull(comprobacion);

        cuentaService.darDeAltaCuenta(cuenta, pepeRino.getDni());
        doThrow(TipoCuentaAlreadyExistsException.class).when(clienteService).agregarCuenta(cuenta2, pepeRino.getDni());

        assertThrows(TipoCuentaAlreadyExistsException.class, () -> cuentaService.darDeAltaCuenta(cuenta2, pepeRino.getDni()));

        verify(clienteService, times(1)).agregarCuenta(cuenta2, pepeRino.getDni());
        verify(cuentaDao, times(1)).save(cuenta);
        verify(cuentaDao, times(2)).find(cuenta.getNumeroCuenta());
    }

    //    4 - cuenta creada exitosamente
    @Test
    public void testCuentaCreadaExitosamente() throws TipoCuentaSoportadaException, TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException {
        Cliente pepeRino = new Cliente();
        pepeRino.setNombre("pepe");
        pepeRino.setApellido("Rino");
        pepeRino.setDni(46637276);

        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setMoneda(TipoMoneda.PESOS);
        cuenta.setTitular(pepeRino);
        cuenta.setNumeroCuenta(65485545);

        when(cuentaDao.find(cuenta.getNumeroCuenta())).thenReturn(null);

        cuentaService.darDeAltaCuenta(cuenta, pepeRino.getDni());

        verify(clienteService, times(1)).agregarCuenta(cuenta, pepeRino.getDni());
        verify(cuentaDao, times(1)).save(cuenta);
    }
}