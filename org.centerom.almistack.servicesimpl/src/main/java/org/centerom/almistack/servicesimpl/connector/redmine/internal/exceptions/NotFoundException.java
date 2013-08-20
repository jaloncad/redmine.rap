package org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions;

public class NotFoundException extends RedmineException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String msg) {
        super(msg);
    }
}