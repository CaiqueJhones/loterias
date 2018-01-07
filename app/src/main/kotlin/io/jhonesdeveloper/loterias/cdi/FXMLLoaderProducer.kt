package io.jhonesdeveloper.loterias.cdi

import javafx.fxml.FXMLLoader

import javax.enterprise.inject.Instance
import javax.enterprise.inject.Produces

/**
 * @author Caique Jhones
 */
class FXMLLoaderProducer {

    @Produces
    fun createLoader(instance: Instance<Any>): FXMLLoader {
        val loader = FXMLLoader()
        loader.setControllerFactory { param -> instance.select(param).get() }
        return loader
    }
}
