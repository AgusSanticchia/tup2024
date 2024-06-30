package ar.edu.utn.frbb.tup.controller;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import java.time.LocalDate;


public class ClienteDTO extends PersonaDTO{
    private String TipoPersona;
    private String banco;
    private LocalDate fechaAlta;

    public ClienteDTO(String tipoPersona, String banco, LocalDate fechaAlta) {
        TipoPersona = tipoPersona;
        this.banco = banco;
        this.fechaAlta = fechaAlta;
    }

    public String getTipoPersona() {
        return TipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        TipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
        
}
