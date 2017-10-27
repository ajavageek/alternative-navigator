package ch.frankel.blog.vaadin

import com.vaadin.navigator.*
import com.vaadin.server.*
import javax.servlet.http.*

private const val STATE_KEY = "state"

class CookieManager : NavigationStateManager {

    private var navigator: Navigator? = null

    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
    }

    override fun getState(): String {
        val cookies = VaadinService.getCurrentRequest().cookies?.asList()
        val value = cookies?.find { it.name == STATE_KEY }?.value
        return when(value) {
            null -> ""
            else -> value
        }
    }

    override fun setState(state: String) {
        val cookie = Cookie(STATE_KEY, state)
        VaadinService.getCurrentResponse().addCookie(cookie)
    }
}