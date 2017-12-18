package io.jhonesdeveloper.loterias.cdi;

import javafx.fxml.FXMLLoader;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;

/**
 * @author Caique Jhones
 */
public class FXMLLoaderProducer {

	@Produces
	public FXMLLoader createLoader(Instance<Object> instance) {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(param -> instance.select(param).get());
		return loader;
	}
}
