package io.jhonesdeveloper.loterias.cdi


import javafx.application.Application
import javafx.stage.Stage
import org.jboss.weld.environment.se.Weld
import org.slf4j.LoggerFactory
import javax.enterprise.util.AnnotationLiteral

object Container {

    fun init(primaryStage: Stage, parameters: Application.Parameters) {
        try {
            val weld = Weld()
            val container = weld.initialize()

            container.select(ApplicationParametersProducer::class.java).get().parameters = parameters

            container.event().select(Stage::class.java, object : AnnotationLiteral<StartupScene>() {})
                    .fire(primaryStage)
        } catch (e: Exception) {
            LoggerFactory.getLogger(javaClass.name).error(e.message, e)
            throw IllegalStateException("Weld cdi does not initialized.", e)
        }
    }
}