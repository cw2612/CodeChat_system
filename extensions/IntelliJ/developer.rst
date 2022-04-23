***********************
Developer documentation
***********************
This plugin was created following the `IntelliJ docs <https://plugins.jetbrains.com/docs/intellij/getting-started.html>`_ using their `GitHub template <https://plugins.jetbrains.com/docs/intellij/github-template.html>`_.


Plan
====
-   Have an application service that starts the server and connects to Thrift. It has a "get Thrift client" function.
-   Have a project service that stores the client ID.
-   Have an action to enable/disable CodeChat.

Questions:

-   Is there some callback for when the Thrift connection dies?
-   How to report status/error messages?
-   What I really want to do is use the Markdown plugin's GUI, but none of its parsing/logic. How?


TODO
====
-   Get a basic plugin running.

    -   Send updates on edits, possibly using the `DocumentListener <https://upsource.jetbrains.com/idea-ce/file/idea-ce-4d741bc560dd19306d4624d7c8a88aea537f4e6f/platform/core-api/src/com/intellij/openapi/editor/event/DocumentListener.java?_ga=2.242772421.694060030.1650200348-2033576375.1648230492>`_.
    -   Send updates on focus changes (switching to another window). Not sure how -- the mention of focus in `FileEditorMananagerListener <https://upsource.jetbrains.com/idea-ce/file/idea-ce-4d741bc560dd19306d4624d7c8a88aea537f4e6f/platform/analysis-api/src/com/intellij/openapi/fileEditor/FileEditorManagerListener.java?_ga=2.28322171.694060030.1650200348-2033576375.1648230492>`_. Perhaps https://stackoverflow.com/questions/58627450/add-focus-blur-listener-in-intellij-plugin?
    -   See how to open a browser inside IntelliJ. The `Markdown plugin <https://www.jetbrains.com/help/idea/markdown.html>`_ provide a nice split-screen environment. See https://github.com/JetBrains/intellij-community/tree/master/plugins/markdown.


-   Integrate plugin plus standalone Thrift:

    -   Run the CodeChat server as a subprocess. Google seems to have decent info.


Notes
=====
To do plugin development, open the `Internal Actions Menu <https://plugins.jetbrains.com/docs/intellij/internal-actions-intro.html>`_.



Source
======
.. toctree::
    :maxdepth: 2

    .gitignore
    build.gradle.kts
    gradle.properties
    src/main/resources/META-INF/plugin.xml
    src/main/kotlin/com/github/bjones1/intellijaplugin/MyBundle.kt
    src/main/kotlin/com/github/bjones1/intellijaplugin/listeners/MyProjectManagerListener.kt
    src/main/kotlin/com/github/bjones1/intellijaplugin/services/MyApplicationService.kt
    src/main/kotlin/com/github/bjones1/intellijaplugin/services/MyProjectService.kt
