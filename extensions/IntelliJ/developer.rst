***********************
Developer documentation
***********************
This plugin was created following the `IntelliJ docs <https://plugins.jetbrains.com/docs/intellij/getting-started.html>`_ using their `GitHub template <https://plugins.jetbrains.com/docs/intellij/github-template.html>`_.

Build steps
===========

Thrift library
--------------
It seems like we can just pull the `compiled library <https://search.maven.org/artifact/org.apache.thrift/libthrift>`_ from the `central Maven repository <https://search.maven.org/>`_. So the following is probably not necessary...

To create this extension, first compile the Thrift Java .jar file. These directions implement the `Thrift Java build procedure <https://github.com/apache/thrift/blob/master/lib/java/README.md#building-thrift-with-gradle-without-cmakeautoconf>`_:

-   In a terminal, clone Thrift and check out the version to build::

        git clone https://github.com/apache/thrift.git
        cd thrift
        # List all tags.
        git tags
        # Pick a tag to build -- here, the 0.16.0 release.
        git checkout tags/v0.16.0 -b temp-branch

-   Open ``thift/lib/java`` in the IntelliJ IDE.
-   Open the file ``./gradle.properties``.
-   Change the line ``release=false`` to ``release=true``.
-   Optionally, add the line ``thrift.compiler=../../../CodeChat_system/CodeChat_Services/thrift-0.16.0``, replacing the path with the path to the Thrift binary.
-   Open the `Gradle tool window <https://www.jetbrains.com/help/idea/jetgradle-tool-window.html>`_ in the IntelliJ IDE then double-click ``build`` in the ``libthrift/Tasks/build`` folder if you provided a path to the Thrift binary or ``assemble`` if you didn't.
-   In the Gradle pane, navigate to ``libthrift/Tasks/other`` and double-click on ``install``.


TODO
====
-   Get a basic plugin running.

    -   Send updates on edits, possibly using the `DocumentListener <https://upsource.jetbrains.com/idea-ce/file/idea-ce-4d741bc560dd19306d4624d7c8a88aea537f4e6f/platform/core-api/src/com/intellij/openapi/editor/event/DocumentListener.java?_ga=2.242772421.694060030.1650200348-2033576375.1648230492>`_.
    -   Send updates on focus changes (switching to another window). Not sure how -- the mention of focus in `FileEditorMananagerListener <https://upsource.jetbrains.com/idea-ce/file/idea-ce-4d741bc560dd19306d4624d7c8a88aea537f4e6f/platform/analysis-api/src/com/intellij/openapi/fileEditor/FileEditorManagerListener.java?_ga=2.28322171.694060030.1650200348-2033576375.1648230492>`_.
    -   See how to open a browser inside IntelliJ. It seems `supported <https://www.jetbrains.com/help/idea/settings-tools-web-browsers.html>`_.


-   Get Thrift running in Java.

    -   See https://thrift.apache.org/tutorial/java.html and https://github.com/apache/thrift/tree/master/lib/java.
    -   Need to build the Thrift Jar file using Gradle

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
