package io.github.nubesgen.cli.subcommand;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

import io.github.nubesgen.cli.util.Output;

@CommandLine.Command(name = "scan", description = "Scan the current project to find the technologies it uses")
public class ScanCommand  implements Callable<Integer> {

    @Override public Integer call() {
        return scan();
    }

    public static Integer scan() {
        Output.printTitle("Scanning the current project...");
        File mavenFile = new File("pom.xml");
        File gradleFile = new File("build.gradle");
        if (mavenFile.exists()) {
            Output.printInfo("Maven project detected");
        } else if (gradleFile.exists()) {
            Output.printInfo("Gradle project detected");
        } else {
            Output.printInfo("Project technology couldn't be detected, failing back to Docker");
        }
        return 0;
    }
}
