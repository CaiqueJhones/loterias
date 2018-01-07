package io.jhonesdeveloper.loterias.mvp

import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.StackPane
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URL
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Executors.newCachedThreadPool
import java.util.function.Function
import java.util.function.Supplier

/**
 * Represents the View of the M**V**P. Example usage:
 *
 * ```
 * class MyView @Inject constructor(fxmlLoader: FXMLLoader) : FXMLView
 * val scene = Scene(myView.view, 800.0, 600.0)
 * ```
 *
 * This class it's based in the project [afterburner.fx](https://github.com/AdamBien/afterburner.fx/tree/v1.7.0)
 */
abstract class FXMLView(var fxmlLoader: FXMLLoader) : StackPane() {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.name)
    private val defaultEnding = "View"

    var fxmlName: String? = null
        get() {
            return if (field.isNullOrBlank()) conventionalName(".fxml")
            else field
        }

    var stylesheetName: String? = null
        get() {
            return if (field.isNullOrBlank()) conventionalName(".css")
            else field
        }

    var resourceBundleName: String? = null
        get() {
            return if (field.isNullOrBlank()) "${javaClass.`package`.name}.${classNameStripEnding(defaultEnding).toLowerCase()}"
            else field
        }

    val resource: URL
        get() {
            logger.debug("Load FXML $fxmlName")
            return javaClass.getResource(fxmlName)
        }


    val resourceBundle: ResourceBundle?
        get() = try {
            logger.debug("Load bundle $resourceBundleName")
            ResourceBundle.getBundle(resourceBundleName)
        } catch (e: MissingResourceException) {
            logger.info(e.message)
            null
        }

    val view: Parent
        get() {
            val p = loadSynchronously(fxmlLoader, resource, resourceBundle)
            addCSSIfAvailable(p)
            return p
        }

    /**
     * Initializes the view synchronously and invokes and passes the created
     * parent Node to the consumer within the FX UI thread.
     *
     * @param consumer - an object interested in received the Parent as callback
     */
    fun getView(consumer: (Parent) -> Unit) {
        loadSynchronously({ view }, consumer)
    }

    /**
     * Creates the view asynchronously using an internal thread pool and passes
     * the parent node within the UI Thread.
     *
     *
     * @param consumer - an object interested in received the Parent as callback
     */
    fun getViewAsync(consumer: (Parent) -> Unit) {
        loadAsynchronously(Supplier { view }, consumer)
    }

    private fun addCSSIfAvailable(parent: Parent) {
        javaClass.getResource(stylesheetName).also {
            parent.stylesheets.add(it?.toExternalForm())
        }
    }

    open protected fun conventionalName(ending: String): String =
            "${classNameStripEnding(this.defaultEnding)}$ending".toLowerCase()

    private fun classNameStripEnding(ending: String) = javaClass.simpleName.substringBefore(ending)

    companion object FXMLViewLoader {

        private val logger: Logger = LoggerFactory.getLogger(FXMLViewLoader::class.java.name)

        private val executorService: ExecutorService = newCachedThreadPool {
            val thread = Executors.defaultThreadFactory().newThread(it)
            thread.name = "afterburner.fx-$thread.name"
            thread.isDaemon = true
            thread
        }

        fun loadSynchronously(fxmlLoader: FXMLLoader, resource: URL, resourceBundle: ResourceBundle?): Parent {
            fxmlLoader.location = resource
            fxmlLoader.resources = resourceBundle
            return fxmlLoader.load<Parent>()
        }

        fun loadSynchronously(supplier: () -> Parent, consumer: (Parent) -> Unit,
                              exceptionally: Function<Throwable, Void?> = Function { logger.error(it.message); null }) {
            CompletableFuture
                    .supplyAsync(supplier, Platform::runLater)
                    .thenAccept(consumer)
                    .exceptionally(exceptionally)
        }

        fun loadAsynchronously(supplier: Supplier<Parent>, consumer: (Parent) -> Unit,
                               exceptionally: Function<Throwable, Void?> = Function { logger.error(it.message); null }) {
            CompletableFuture
                    .supplyAsync(supplier, FXMLViewLoader.executorService)
                    .thenAcceptAsync(consumer, Platform::runLater)
                    .exceptionally(exceptionally)
        }
    }
}