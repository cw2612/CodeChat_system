// *************************************************************
// |docname| - Define a ``Configurable`` for CodeChat's settings
// *************************************************************
// This code is based on the `IntelliJ SDK settings tutorial <https://plugins.jetbrains.com/docs/intellij/settings-tutorial.html#the-appsettingsconfigurable-class>`_.
package com.github.bjones1.intellij.codechat.settings

import com.github.bjones1.intellij.codechat.services.ApplicationService
import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nullable
import javax.swing.JComponent
import com.intellij.openapi.components.service

/**
 * Provides controller functionality for application settings.
 */
class AppSettingsConfigurable : Configurable {
    private var mySettingsComponent: AppSettingsComponent? = null

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP
    //@get:Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String
    {
        return "CodeChat Settings"
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return mySettingsComponent!!.preferredFocusedComponent
    }

    @Nullable
    override fun createComponent(): JComponent {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val applicationService = service<ApplicationService>()
        return mySettingsComponent!!.codeChatServerExecutableText != applicationService.codeChatServerExecutable
    }

    override fun apply() {
        val applicationService = service<ApplicationService>()
        applicationService.codeChatServerExecutable = mySettingsComponent!!.codeChatServerExecutableText
    }

    override fun reset() {
        val applicationService = service<ApplicationService>()
        mySettingsComponent!!.codeChatServerExecutableText = applicationService.codeChatServerExecutable
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}