package com.edu;

import java.util.HashMap;
import java.util.Map;

import com.edu.domain.Centro;
import com.edu.domain.Centro.Titularidad;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        // Hashmap que usaremos más adelante
        Map<String, String> props = new HashMap<>();

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("InstitutoPersistente")) {
            
            // INSERTAMOS EL PRIMER CENTRO
            try (EntityManager em = emf.createEntityManager()) {
                EntityTransaction tr = em.getTransaction();
                try {
                    tr.begin();
                    Centro centro = new Centro(11004866, "IES Castillo de Luna", Titularidad.PUBLICA);
                    em.persist(centro);
                    tr.commit();
                } catch(Exception err) {
                    if(tr != null && tr.isActive()) tr.rollback();
                    throw new RuntimeException("Error al almacenar el centro", err);
                }  
            } catch (Exception e) {
                // TODO: handle exception
            }

            // ACTUALIZAMOS EL CENTRO INSERTADO CAMBIANDO SU NOMBRE
            try (EntityManager em = emf.createEntityManager()) {
                EntityTransaction tr = em.getTransaction();
                try {
                    tr.begin();
                    Centro centro = em.find(Centro.class, 11004866);
                    centro.setNombre("I.E.S. Castillo de Luna");
                    tr.commit();
                } catch(Exception err) {
                    if(tr != null && tr.isActive()) tr.rollback();
                    throw new RuntimeException("Error al obtener el centro", err);
                }  
            } catch (Exception e) {
                // TODO: handle exception
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}