package ch.frankel.blog.vaadin

import com.vaadin.annotations.*
import com.vaadin.navigator.*
import com.vaadin.server.*
import com.vaadin.ui.*
import javax.servlet.annotation.*

class NavigatorUI : ViewDisplay, UI() {

    override fun showView(view: View) {
        content = view as Component
    }

    override fun init(vaadinRequest: VaadinRequest) {
        navigator = Navigator(this, CookieManager(), this)
        val viewNames = listOf("", "Another", "Third", "Last")
        val views = viewNames.map { GenericView(it, viewNames) }
        views.map { navigator.addView(it.name, it) }
        showView(views.first())
    }
}

class GenericView(val name: String, private val allNames: List<String>) : View, VerticalLayout() {

    init {
        val label = Label("$name View")
        val bar = HorizontalLayout()
        allNames.map {
            Button("Show $it View", Button.ClickListener { _ -> ui.navigator.navigateTo(it) })
        }.forEach { bar.addComponent(it) }
        addComponents(label, bar)
    }
}

@WebServlet(urlPatterns = arrayOf("/*"), asyncSupported = true)
@VaadinServletConfiguration(ui = NavigatorUI::class, productionMode = false)
class NavigatorServlet : VaadinServlet()
