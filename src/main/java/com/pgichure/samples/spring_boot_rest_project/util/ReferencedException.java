package com.pgichure.samples.spring_boot_rest_project.util;


public class ReferencedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public ReferencedException() {
        super();
    }

    public ReferencedException(final ReferencedWarning referencedWarning) {
        super(referencedWarning.toMessage());
    }

}
