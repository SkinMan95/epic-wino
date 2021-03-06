package edu.eci.pdsw.epicwino.logica.dao;

import edu.eci.pdsw.epicwino.logica.entidades.Materia;
import edu.eci.pdsw.epicwino.logica.entidades.Profesor;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public interface MateriaDAO {

    /**
     * guardar una materia en peristencia
     * @param materia la materia
     * @param idAsignatura id de la asignatura
     * @throws PersistenceException falla de persistencia
     */
    void saveMateria(Materia materia, int idAsignatura) throws PersistenceException;

    /**
     * agregar un nuevo cohorte en una materia de un programa
     * @param idPrograma id del programa
     * @param idMateria id de la materia
     * @param numCohorte numero del cohorte
     * @param periodo del cohorte
     * @throws PersistenceException falla de persistencia
     */
    void agregarCohorte(int idPrograma, String idMateria, int numCohorte, int periodo) throws PersistenceException;

    /**
     * consultar todos los cohorte registrados
     * @return lista no nula de enteros
     * @throws PersistenceException falla de persistencia
     */
    List<Integer> loadCohortes() throws PersistenceException;

    /**
     * consultar todos los prerrequisitos de una materia
     * @param idMateria id de la materia
     * @return lista no nula de materias que son prerrequisito de la materia
     * @throws PersistenceException falla de persistencia
     */
    List<Materia> loadPrerrequisitos(String idMateria) throws PersistenceException;

    /**
     * consultar todos los correquisitos de una materia
     * @param idMateria id de la materia
     * @return lista no nula de materias que son correquisito de la materia
     * @throws PersistenceException falla de persistencia
     */
    List<Materia> loadCorrequisitos(String idMateria) throws PersistenceException;

    /**
     * consultar todas las materias presentes
     * @return lista no nula de materias
     * @throws PersistenceException falla en persistencia
     */
    List<Materia> consultarMaterias() throws PersistenceException;

    /**
     * registrar un profesor
     * @param profesor a registrar
     * @throws PersistenceException falla en persistencia
     */
    void agregarProfesor(Profesor profesor) throws PersistenceException;

    /**
     *
     * @param idProf id del profesor
     * @param fecha fecha de disponibilidad
     * @param horaInicio hora de inicio en la que esta disponible
     * @param horaFin hora de finalizacion en la que esta disponible
     * @return el profesor esta disponible
     * @throws PersistenceException falla en persistencia
     */
    boolean consultarDisponibilidadProfesor(int idProf,
            Date fecha, Time horaInicio, Time horaFin) throws PersistenceException;

    /**
     * registrar requisito de una materia
     * @param idMateria id de la materia
     * @param idRequisito id de la materia requisito
     * @param prerrequisito es prerrequisito
     * @throws PersistenceException falla en persistencia
     */
    void registrarRequisito(String idMateria, int idRequisito, boolean prerrequisito) throws PersistenceException;
    
    /**
     * consulta un profesor que esta registrado en un periodo en una materia
     * @param idMateria id de la materia
     * @param periodo en el que esta presente la materia y profesor
     * @return profesor
     * @throws PersistenceException falla en persistencia
     */
    Profesor consultarProfesoresEnPeriodoYMateria(String idMateria, int periodo) throws PersistenceException;
    
    /**
     * consulta todos los profesores presentes en un periodo
     * @param periodo en donde se consultaran los profesores
     * @return lista no nula de profesores
     * @throws PersistenceException falla en persistencia
     */
    List<Profesor> consultarProfesoresEnPeriodo(int periodo) throws PersistenceException;
}
