package ar.edu.utn.frbb.tup.presentation.input;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.springframework.stereotype.Component;

@Component
public class ShowInfoCliente {
    private static ClienteService clienteService;

    public ShowInfoCliente(ClienteService clienteService) {
        ShowInfoCliente.clienteService = clienteService;
    }

    public static void mostrarInfoCliente(long dni) {
        try {
            Cliente cliente = clienteService.buscarClientePorDni(dni);

            System.out.println("Información del Cliente: ");
            System.out.println("Dni: " + cliente.getDni());
            System.out.println("Nombre y Apellido: " + cliente.getNombre() + " " + cliente.getApellido());
            System.out.println("Tipo de persona: " + cliente.getTipoPersona());
            System.out.println("Banco: " + cliente.getBanco());
            System.out.println("Fecha de nacimiento: " + cliente.getFechaNacimiento());
            System.out.println("Edad: " + cliente.getEdad());

            if (cliente.getListaCuentas().isEmpty()) {
                System.out.println("El cliente no tiene cuentas asociadas.");
            } else {
                System.out.println("Cuentas del Cliente: ");
                for (long cuentaId : cliente.getListaCuentas()) {
                    System.out.println("Número de cuenta: " + cuentaId);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}