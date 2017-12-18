package io.jhonesdeveloper.loterias;

import io.jhonesdeveloper.loterias.cdi.ApplicationParametersProducer;
import io.jhonesdeveloper.loterias.cdi.StartupScene;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.util.AnnotationLiteral;

public class StartApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        ApplicationParametersProducer provider = container.select(ApplicationParametersProducer.class).get();
        provider.setParameters(getParameters());

        container.event().select(Stage.class, new AnnotationLiteral<StartupScene>() {}).fire(primaryStage);

        weld.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
