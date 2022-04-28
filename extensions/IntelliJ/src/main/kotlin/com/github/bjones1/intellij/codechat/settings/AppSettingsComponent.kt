// ***********************************************
// |docname| - Provide a GUI for CodeChat settings
// ***********************************************
// This code is based on the `IntelliJ SDK settings tutorial <https://plugins.jetbrains.com/docs/intellij/settings-tutorial.html#the-appsettingscomponent-class>`_.
package com.github.bjones1.intellij.codechat.settings

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class AppSettingsComponent {
    val panel: JPanel
    private val codeChatServerExecutableTextField = JBTextField()

    init {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Path to the CodeChat Server executable; for example, \"CodeChat_Server\"."), codeChatServerExecutableTextField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = codeChatServerExecutableTextField

    var codeChatServerExecutableText: String
        get() = codeChatServerExecutableTextField.text
        set(newText) {
            codeChatServerExecutableTextField.text = newText
        }
}
