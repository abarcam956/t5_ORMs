package com.edu.domain;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

/**
 * Static metamodel for {@link com.edu.domain.Estudiante}
 **/
@StaticMetamodel(Estudiante.class)
public abstract class Estudiante_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";
	
	/**
	 * @see #nombre
	 **/
	public static final String NOMBRE = "nombre";
	
	/**
	 * @see #nacimiento
	 **/
	public static final String NACIMIENTO = "nacimiento";
	
	/**
	 * @see #centro
	 **/
	public static final String CENTRO = "centro";

	
	/**
	 * Static metamodel type for {@link com.edu.domain.Estudiante}
	 **/
	public static volatile EntityType<Estudiante> class_;
	
	/**
	 * Static metamodel for attribute {@link com.edu.domain.Estudiante#id}
	 **/
	public static volatile SingularAttribute<Estudiante, Long> id;
	
	/**
	 * Static metamodel for attribute {@link com.edu.domain.Estudiante#nombre}
	 **/
	public static volatile SingularAttribute<Estudiante, String> nombre;
	
	/**
	 * Static metamodel for attribute {@link com.edu.domain.Estudiante#nacimiento}
	 **/
	public static volatile SingularAttribute<Estudiante, LocalDate> nacimiento;
	
	/**
	 * Static metamodel for attribute {@link com.edu.domain.Estudiante#centro}
	 **/
	public static volatile SingularAttribute<Estudiante, Centro> centro;

}

