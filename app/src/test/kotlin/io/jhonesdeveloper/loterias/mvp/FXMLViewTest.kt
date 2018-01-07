package io.jhonesdeveloper.loterias.mvp

import javafx.embed.swing.JFXPanel
import javafx.fxml.FXMLLoader
import javafx.scene.layout.AnchorPane
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

private class CamelCaseFXMLView: FXMLView(FXMLLoader())
private class AViewWithoutFXML: FXMLView(FXMLLoader())
private class WithoutConvention : FXMLView(FXMLLoader())
private class WithViewName: FXMLView(FXMLLoader())

class FXMLViewTest {

    @Test
    fun loadBundleWithSuccess() {
        val view = WithoutConvention()
        assertThat(view.resourceBundle).isNotNull()
    }

    @Test
    fun loadNonExistingBundle() {
        val view = AViewWithoutFXML()
        view.resourceBundleName = "non-existing"
        assertThat(view.resourceBundle).isNull()
    }

    @Test
    fun loadViewWithCamelCaseFXML() {
        val view = CamelCaseFXMLView()
        assertThat(view.fxmlName).isEqualTo("CamelCaseFXML.fxml".toLowerCase())
    }

    @Test(expected = IllegalStateException::class)
    fun loadViewWithoutFXML() {
        val view = AViewWithoutFXML()
        view.view
    }

    @Test
    fun loadViewWithoutConvention() {
        val view = WithoutConvention()
        assertThat(view.view).isInstanceOf(AnchorPane::class.java)
    }

    @Test(expected = IllegalStateException::class)
    fun loadWithViewIntoName() {
        val view = WithViewName()
        view.view
    }

    @Test
    fun loadView() {
        val view = CamelCaseFXMLView()
        assertThat(view.view).isInstanceOf(AnchorPane::class.java)
    }

    @Test
    fun loadViewConsumer() {
        JFXPanel() //init FXML Toolkit
        val view = WithoutConvention()
        view.getView { assertThat(view.view).isInstanceOf(AnchorPane::class.java) }
    }

    @Test
    fun loadViewInBackground() {
        JFXPanel() //init FXML Toolkit
        val view = CamelCaseFXMLView()
        view.getViewAsync { assertThat(view.view).isInstanceOf(AnchorPane::class.java) }
    }
}
