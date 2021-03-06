/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.epicwino.logica.managedbeans;

import edu.eci.pdsw.epicwino.logica.entidades.Asignatura;
import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Programa;
import edu.eci.pdsw.epicwino.logica.servicios.ExcepcionServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPost;
import edu.eci.pdsw.epicwino.logica.servicios.ServiciosProgmsPostFactory;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author MariaCamila
 */
@ManagedBean(name = "programarMateriaBean")
@SessionScoped
public class ProgramarMateriaBean implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(MateriasRegistradasBean.class);
    private static final long serialVersionUID = 1L;
    
    private final ServiciosProgmsPost servProg = ServiciosProgmsPostFactory.getInstance().getServiciosProgmsPost();
    
    private String programa;
    private int anio;
    private int semestre;
    private String asignatura;
    private int asignatura_id = 0;
    private String materia;
    private int cohorte;
    private String profesor; 
    private int programa_id;
    private String nivel;
    private String materia_id;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
     public Map<String, String> getNiveles() {
        LOGGER.debug(MessageFormat.format("Se intenta obtener los niveles (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<String> r = null;
        Map<String, String> niveles = new HashMap<>();
        r = servProg.consultarNiveles();
        for (String p : r) {
            niveles.put(p, p);
        }

        return niveles;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
        Programa_id();
    }
    
    public Map<String, String> getProgramas() {
        LOGGER.debug(MessageFormat.format("Se intenta obtener los programas (anio: {0}, "
                + "semestre: {1})", anio, semestre));
        List<Programa> r;
        Map<String, String> programs = new HashMap<>();
        r = servProg.consultarProgramas();
        for (Programa p : r) {
            String n = p.getNombre();
            programs.put(n, n);
        }
        
        return programs;
    }
    
     public void Programa_id() {
        List<Programa> r;
        r = servProg.consultarProgramas();
        for (Programa p : r) {
            if (p.getNombre().equals(programa) && p.getNivel().equals(nivel)) {
                programa_id = p.getId();
            }
        }
     }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
    
     public Map<Integer,Integer> getAnios(){
        LOGGER.debug("Se obtienen los anios");
        Map<Integer,Integer> periodos  = new HashMap<>();
        List<Integer> periods = servProg.consultarPeriodos();
        for (Integer i : periods) {
            i /= 10;
            periodos.put(i, i);
        }
        return periodos;
    }
     
    public int convertirSemestre(String sem){
        LOGGER.debug("Se convierte el semestre");
        int s; 
        if("1".equals(sem)){
            s=1;
        } else if("2".equals(sem)){
            s=2;
        } else {
            s=3;
        }
        return s;
    }

    public Map<String, String> getSemestres() {
        LOGGER.debug("Se obtienen los semestres");
        Map<String, String> m = new HashMap<>();
        m.put("1", "1");
        m.put("2", "2");
        m.put("I", "I");
        return m;
    }

    public String getSemestre() {
        String s = "";
        switch(semestre) {
            case 1:
                s = "1";
                break;
            case 2:
                s = "2";
                break;
            case 3:
                s = "I";
        }
        return s;
    }

    public void setSemestre(String semestre) {
        this.semestre = convertirSemestre(semestre);
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;

        idAsignatura();
    }
    
    public Map<String, String> getAsignaturas(){
        LOGGER.debug("Se obtienen los anios");
        Map<String, String> asig  = new HashMap<>();
        List<Asignatura> a;
        Programa_id();
        try {
            LOGGER.debug("Se intentan obtener las asignaturas del programa "+programa+" con ID: "+programa_id);
            a=servProg.consultarAsignaturasPorPrograma(programa_id);
            for (Asignatura as:a){
                String n=as.getNombre();
                asig.put(n,n);
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error al consultar asignaturas", ex);
        }
        return asig; 
    } 
    
    public void Asignatura_id() {
        LOGGER.debug("Se intentan obtener id de la asignatura");
        Programa_id();
        List<Asignatura> asignaturas;
        try {
            asignaturas = servProg.consultarAsignaturasPorPrograma(programa_id);
            for (Asignatura a : asignaturas) {
                if (a.getNombre().equals(asignatura)) {
                    asignatura_id = a.getId();
                }
            }
            LOGGER.debug(MessageFormat.format("Se obtiene el id de asignatura)", asignatura_id));
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error consultando id de asignatura", ex);
        }
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
        idMateria();
    }

    public int getCohorte() {
        return cohorte;
    }

    public void setCohorte(int cohorte) {
        this.cohorte = cohorte;
    }
    
    public Map<Integer,Integer> getCohortes(){
        LOGGER.debug("Se intentan obtener los cohortes");
        Map<Integer,Integer> co= new HashMap<>();
        co.put(25, 25);
        co.put(24,24);
        return co;

    }
    
    public void idMateria(){
        LOGGER.debug("Se intentan obtener id de materia");
        idAsignatura();
        try {
            LOGGER.debug("Se esta obteniendo id con id de asignatura numero" + asignatura_id + "con año" + anio + "con semestre" + semestre);
            List<Materia> mate=servProg.consultarMaterias((anio * 10) + semestre, asignatura_id);
            for(Materia m:mate){
                if(m.getNombre().equals(materia)){
                    materia_id=m.getId();
                }
            }
            LOGGER.debug("Se obtiene id de materia" + materia + "su ID es" + materia_id);
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error al consultar id de la materia", ex);
        }
        
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
    
    public void idAsignatura(){
        LOGGER.debug("Se intentan obtener id de asignatura");
        Programa_id();
        try {
            List<Asignatura> asign=servProg.consultarAsignaturasPorPrograma(programa_id);
            for (Asignatura a:asign){
                if(a.getNombre().equals(asignatura)){
                    asignatura_id=a.getId();
                }
            }
            LOGGER.debug("Se  obtiene id de asignatura"+ asignatura + "con ID" + asignatura_id);
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error al consultar id de asignatura", ex);
        }
    }
    
    public Map<String, String> getMaterias(){
        LOGGER.debug("Se intentan obtener las materias");
        idAsignatura();
        Map<String, String> mat = new HashMap<>();
        try {
            List<Materia> m= servProg.consultarMaterias((anio * 10) + semestre, asignatura_id);
            for (Materia ma:m){
                String n=ma.getNombre();
                mat.put(n, n);
            }
        } catch (ExcepcionServiciosProgmsPost ex) {
            LOGGER.error("Error al consultar materias", ex);
        }
        return mat;
    }
    
    
}
