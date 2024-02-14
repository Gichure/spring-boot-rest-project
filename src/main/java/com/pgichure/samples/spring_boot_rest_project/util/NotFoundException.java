package com.pgichure.samples.spring_boot_rest_project.util;


public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }

}
