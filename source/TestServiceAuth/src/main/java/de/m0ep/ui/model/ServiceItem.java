package de.m0ep.ui.model;

import java.util.Objects;

import de.m0ep.sioc.service.auth.Service;

public class ServiceItem {
    private Service service;

    public ServiceItem(final Service service) {
	this.service = service;
    }

    public Service getService() {
	return service;
    }

    @Override
    public boolean equals(Object obj) {
	if (null == obj) {
	    return false;
	}

	if (this.getClass() != obj.getClass()) {
	    return false;
	}

	ServiceItem other = (ServiceItem) obj;

	return Objects.equals(
		this.getService().getResource(),
		other.getService().getResource());
    }

    @Override
    public String toString() {
	if (service.hasName()) {
	    return service.getName();
	}

	return service.getResource().toString();
    }
}
