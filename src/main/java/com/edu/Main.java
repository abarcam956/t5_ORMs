package com.edu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edu.domain.Centro;
import com.edu.domain.Estudiante;
import com.edu.domain.Titularidad;

import ch.qos.logback.classic.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;

public class Main {
        private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        ch.qos.logback.classic.Logger hibernateLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.WARN);

        String bd = "jdbc:sqlite:centro.db";
        
        Map<String, String> props = new HashMap<>();
        //props.put("jakarta.persistence.jdbc.url", bd);

        // Se crea un objeto para realizar transacciones
        int idx = JpaBackend.createEntityManagerFactory("InstitutoPersistente", props);

        // Transacción sin resultado para agregar un centro
        JpaBackend.transaction(idx, em -> {
            Centro[] centros = new Centro[] {
                new Centro(11004866, "IES Castillo de Luna",Titularidad.PUBLICA),
                new Centro(11700603, "IES Pintor Juan Lara",Titularidad.PUBLICA),
            };
            for(Centro c: centros) em.persist(c);
        });

        // Transacción con resultado (se aplica a la bbdd existente)
        Centro castillo = JpaBackend.transactionR(idx, em -> {
            // Busca el centro en la base datos
            Centro c = em.find(Centro.class, 11004866);
            c.setNombre("I.E.S. Castillo de Luna");
            return c;
        });
        
        Centro lara = JpaBackend.transactionR(idx, em -> {
            // Busca el centro en la base datos
            return em.find(Centro.class, 11700603);
        });

        // Transacción sin resultado para agregar una lista de estudiantes
        JpaBackend.transaction(idx, em -> {
            Estudiante[] estudiantes = new Estudiante[] {
                new Estudiante("Manolo", LocalDate.of(2000, 01, 01), castillo),
                new Estudiante("Marisa", LocalDate.of(2004, 10, 12), castillo),
                new Estudiante("Alberto", LocalDate.of(2003, 5, 22), castillo),
                new Estudiante("Lucía", LocalDate.of(2002, 7, 2), lara)
            };

            // Por cada estudiante de la lista, se añade a la base de datos (acción persist)
            for(Estudiante e: estudiantes) em.persist(e);
        });

        // Comprobar estudiantes
        List<Estudiante> estudiantes = JpaBackend.transactionR(em -> {
            TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e", Estudiante.class);
            return query.getResultList();
        });

        System.out.printf("-- Estudiantes de '%s' --\n ", castillo.getNombre() + ":");
        estudiantes.forEach(System.out::println);

        // Creamos una transacción nueva para obtener únicamente los nombres de los estudiantes
        JpaBackend.transaction(em -> {
            // Se establece una consulta a la tabla de Estudiante donde obtenemos solo su nombre
            TypedQuery<String> query = em.createQuery("SELECT e.nombre FROM Estudiante e", String.class);

            // Y se establece una lista con los nombres obtenidos
            List<String> nombres = query.getResultList();
            System.out.println("-- Listado de nombres de alumno --");
            nombres.forEach(System.out::println);

            // Creamos otra consulta CON TUPLAS para obtener nombre y fecha de nacimiento
            TypedQuery<Tuple>  query2 = em.createQuery("SELECT e.nombre AS nombre, e.nacimiento AS nacimiento FROM Estudiante e", Tuple.class);

            List<Tuple> datos_e = query2.getResultList();
            System.out.println("\n\n-- Listado de nombres y fechas de nacimiento de alumno --");
            for(Tuple t: datos_e) {
                String nombre = t.get("nombre", String.class);
                LocalDate nacimiento = t.get("nacimiento", LocalDate.class);

                System.out.printf("%s: %s.\n", nombre, nacimiento.format(df));
            }
        });

        JpaBackend.transaction(em -> {
            TypedQuery<Estudiante> query = em.createQuery("SELECT e FROM Estudiante e WHERE e.nombre LIKE :pattern", Estudiante.class);
            query.setParameter("pattern", "%o%");

            List<Estudiante> ee = query.getResultList();
            System.out.println("\n\n-- Estudiantes con 'o' en su nombre --");
            ee.forEach(System.out::println);

            LocalDate hace25anhos = LocalDate.now().minusYears(25);
            query = em.createQuery("SELECT e FROM Estudiante e WHERE e.nacimiento > :fecha", Estudiante.class);
            query.setParameter("fecha", hace25anhos);
            
            ee = query.getResultList();
            System.out.println("\n\n-- Estudiantes con menos de 25 años --");
            ee.forEach(System.out::println);
        });

        JpaBackend.transaction(em -> {
            /*
            TypedQuery<Tuple> query = em.createQuery("SELECT c.nombre, COUNT(c.estudiantes) AS cantidad FROM Centro c", Tuple.class);
            */

            TypedQuery<Tuple> query = em.createQuery("SELECT e.centro.nombre AS nombre, COUNT(e) AS cantidad FROM Estudiante e GROUP BY e.centro.nombre", Tuple.class);

            List<Tuple> resultados = query.getResultList();
            System.out.println("-- Cantidad de alumosnos por centro --");
            for(Tuple t: resultados) {
                String nombre = t.get("nombre", String.class);
                Long cantidad = t.get("cantidad", Long.class);

                System.out.printf("%s: %d estudiantes.\n", nombre, cantidad);
            }

        });

        // Resetea el hashmap de valores y objetos y cierra objetos abiertos
        JpaBackend.reset();
    }
}