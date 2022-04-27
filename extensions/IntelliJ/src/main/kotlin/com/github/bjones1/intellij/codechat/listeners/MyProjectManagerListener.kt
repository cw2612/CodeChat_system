// ************************************
// |docname| - Define plugin  listeners
// ************************************
// See the `docs <https://plugins.jetbrains.com/docs/intellij/plugin-listeners.html>`__.
//
// TODO: add a  `DocumentListener <https://upsource.jetbrains.com/idea-ce/file/idea-ce-4d741bc560dd19306d4624d7c8a88aea537f4e6f/platform/core-api/src/com/intellij/openapi/editor/event/DocumentListener.java?_ga=2.242772421.694060030.1650200348-2033576375.1648230492>`_.
package com.github.bjones1.intellij.codechat.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.github.bjones1.intellij.codechat.services.ProjectService

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<ProjectService>()
    }
}
