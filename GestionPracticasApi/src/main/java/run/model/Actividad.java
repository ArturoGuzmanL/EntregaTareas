package run.model;


import jakarta.persistence.Transient;

public class Actividad {
    private String fecha;
    private String actividadRealizada;
    private String observaciones;
    private String horas;
    private String tipo;

    public Actividad(String fecha, String actividadRealizada, String observaciones, String horas, String tipo) {
        this.fecha = fecha;
        this.actividadRealizada = actividadRealizada;
        this.observaciones = observaciones;
        this.horas = horas;
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getActividadRealizada() {
        return actividadRealizada;
    }

    public void setActividadRealizada(String actividadRealizada) {
        this.actividadRealizada = actividadRealizada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
