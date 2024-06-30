package ar.edu.utn.frbb.tup.controller;

public class PersonaDTO {
    private long dni;
    private String persona;
    private String apellido;
    public PersonaDTO(long dni, String persona, String apellido) {
        this.dni = dni;
        this.persona = persona;
        this.apellido = apellido;
    }
    public long getDni() {
        return dni;
    }
    public void setDni(long dni) {
        this.dni = dni;
    }
    public String getPersona() {
        return persona;
    }
    public void setPersona(String persona) {
        this.persona = persona;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    
}
