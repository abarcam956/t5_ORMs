package com.edu.domain;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link com.edu.domain.Centro}
 **/
@StaticMetamodel(Centro.class)
public abstract class Centro_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";
	
	/**
	 * @see #nombre
	 **/
	public static final String NOMBRE = "nombre";
	
	/**
	 * @see #titularidad
	 **/
	public static final String TITULARIDAD = "titularidad";
	
	/**
	 * @see #estudiantes
	 **/
	public static final String ESTUDIANTES = "estudiantes";

	
	/**
	 * Static metamodel type for {@link com.edu.domain.Centro}
	 **/
	public static volatile EntityType<Centro> class_;
	
	/**
	 * Static metamodel for attribute {@link com.edu.domain.Centro#id}
	 **/
	public static volatile SingularAttribute<Centro, Long> id;
	
	/**
	 * Static metamodel for attribute {@link com.edu.domain.Centro#nombre}
	 **/
	public static volatile SingularAttribute<Centro, String> nombre;
	
	/**
	 * Static metamodel for attribute {@link com.edu.domain.Centro#titularidad}
	 **/
	public static volatile SingularAttribute<Centro, Titularidad> titularidad;
	
	/**
	 * Static metamodel for attribute {@link com.edu.domain.Centro#estudiantes}
	 **/
	public static volatile ListAttribute<Centro, Estudiante> estudiantes;

}

