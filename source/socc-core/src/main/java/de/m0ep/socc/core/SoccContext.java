package de.m0ep.socc.core;

import java.io.IOException;

import org.ontoware.rdf2go.model.Model;

import com.google.common.base.Preconditions;

import de.m0ep.socc.core.acl.IAccessControl;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class SoccContext implements ISoccContext {
	private final Model model;
	private IAccessControl accessControl;

	public SoccContext( final Model model ) throws AuthenticationException,
	        IOException {
		this.model = Preconditions.checkNotNull(
		        model,
		        "Required parameter model must be specified." );
		Preconditions.checkArgument(
		        model.isOpen(),
		        "Required paramater model is not open" );
	}

	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public IAccessControl getAccessControl() {
		return accessControl;
	}
}
