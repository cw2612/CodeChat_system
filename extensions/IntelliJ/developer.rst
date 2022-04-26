***********************
Developer documentation
***********************
This plugin was created following the `IntelliJ docs <https://plugins.jetbrains.com/docs/intellij/getting-started.html>`_ using their `GitHub template <https://plugins.jetbrains.com/docs/intellij/github-template.html>`_.


Plan
====
-   Create two application-wide `settings <https://plugins.jetbrains.com/docs/intellij/settings.html>`_: the path to CodeChat and where to display (browser or in IDE). To open a link in the browser, see `here <https://intellij-support.jetbrains.com/hc/en-us/community/posts/360000018690-Open-url-from-Idea-programaticly->`__.
-   Create a project-wide menu item (an `action <https://plugins.jetbrains.com/docs/intellij/plugin-actions.html>`_) named "Enable/disable CodeChat".
-   Create an application `service <https://plugins.jetbrains.com/docs/intellij/plugin-services.html>`_ that starts the server and connects to Thrift. It has a "get Thrift client" function.
-   Have a project service_ that stores the client ID for that project.
-   Use `editor hints <https://plugins.jetbrains.com/docs/intellij/notifications.html#editor-hints>`_ to report errors; use `balloons <https://plugins.jetbrains.com/docs/intellij/notifications.html#top-level-notifications-balloons>`_ for informational messages.
-   Need to use a `Java Timer <https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Timer.html>`_ to render at a good time.
-   Run the server; on error, display either an error message from the subprocess or the stdout/stderr from the subproces. A guess at how to do this in Java: see `SO <https://stackoverflow.com/a/57949752/16038919>`__. Slightly modified to wait until the process ends and to read all output on failure (untested):

    .. code:: Java
        :number-lines:

        public final class Processes
        {
            private static final String NEWLINE = System.getProperty("line.separator");

            /**
            * @param command the command to run
            * @return the output of the command
            * @throws IOException if an I/O error occurs
            */
            public static String run(String... command) throws IOException
            {
                ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
                Process process = pb.start();
                boolean finished = process.waitFor(5, TimeUnits.SECONDS);
                StringBuilder result = new StringBuilder(80);
                // TODO: read everything all at once as UTF-8. How?
                try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream())))
                {
                    while (true)
                    {
                        String line = in.readLine();
                        if (line == null)
                            break;
                        result.append(line).append(NEWLINE);
                    }
                }
                return result.toString();
            }

            /**
            * Prevent construction.
            */
            private Processes()
            {
            }
        }

Questions:

-   Is there some callback for when the Thrift connection dies?
-   The `Markdown plugin <https://www.jetbrains.com/help/idea/markdown.html>`_ provide a nice split-screen environment. See https://github.com/JetBrains/intellij-community/tree/master/plugins/markdown. How can I use the Markdown plugin's GUI, but none of its parsing/logic. If not, fall back to:

    -   `JCEF - Java Chromium Embedded Framework <https://plugins.jetbrains.com/docs/intellij/jcef.html>`_.
    -   Send updates on edits, possibly using the `DocumentListener <https://upsource.jetbrains.com/idea-ce/file/idea-ce-4d741bc560dd19306d4624d7c8a88aea537f4e6f/platform/core-api/src/com/intellij/openapi/editor/event/DocumentListener.java?_ga=2.242772421.694060030.1650200348-2033576375.1648230492>`_.
    -   Send updates on focus changes (switching to another window). Not sure how -- the mention of focus in `FileEditorMananagerListener <https://upsource.jetbrains.com/idea-ce/file/idea-ce-4d741bc560dd19306d4624d7c8a88aea537f4e6f/platform/analysis-api/src/com/intellij/openapi/fileEditor/FileEditorManagerListener.java?_ga=2.28322171.694060030.1650200348-2033576375.1648230492>`_. Perhaps https://stackoverflow.com/questions/58627450/add-focus-blur-listener-in-intellij-plugin?
    -   See how to open a browser inside IntelliJ.


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
