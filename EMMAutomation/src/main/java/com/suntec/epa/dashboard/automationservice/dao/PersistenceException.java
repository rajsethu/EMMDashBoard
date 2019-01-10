package com.suntec.epa.dashboard.automationservice.dao;

/**
 * Throws when an error occurs in the persistence layer.  While this will generally be JDBC, it could be any type of persistence,
 * should the DAO interfaces receive other types of implementations (perhaps for unit tests).
 */
public class PersistenceException extends Exception {
	private static final long serialVersionUID = -7315188322675129719L;

	public PersistenceException() {
		super();
	}

	public PersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(Throwable cause) {
		super(cause);
	}
}
