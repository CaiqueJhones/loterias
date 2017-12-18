package io.jhonesdeveloper.loterias.cdi;

import javafx.application.Application.Parameters;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class ApplicationParametersProducer {
	
	private Parameters parameters;

	public void setParameters(Parameters p) {
		this.parameters = p;
	}
	
	@Produces 
	public Parameters getParameters() {
		return parameters;
	}
}
