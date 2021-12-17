package io.github.nubesgen.cli.subcommand;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;

import io.github.nubesgen.cli.util.Output;

@CommandLine.Command(name = "scan", description = "Scan the current project to find the technologies it uses")
public class ScanCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Output.printTitle("GET request: " + scan());
        return 0;
    }

    public static String scan() {
        Output.printTitle("Scanning the current project...");
        String getRequest = "?application=APP_SERVICE.basic";
        File mavenFile = new File("pom.xml");
        File gradleFile = new File("build.gradle");
        try {
            if (mavenFile.exists()) {
                Output.printInfo("Maven detected");
                String testFile = Files.readString(mavenFile.toPath());
                if (testFile.contains("<groupId>org.springframework.boot</groupId>")) {
                    Output.printInfo("Spring Boot detected");
                    getRequest += "&runtime=SPRING";
                } else {
                    Output.printInfo("Default Java configuration");
                    getRequest += "&runtime=JAVA";
                }
            } else if (gradleFile.exists()) {
                Output.printInfo("Gradle project detected");
                String testFile = Files.readString(gradleFile.toPath());
                if (testFile.contains("org.springframework.boot")) {
                    Output.printInfo("Spring Boot detected");
                    getRequest += "&runtime=SPRING_GRADLE";
                } else {
                    Output.printInfo("Default Java configuration");
                    getRequest += "&runtime=JAVA_GRADLE";
                }
            } else {
                Output.printInfo("Project technology couldn't be detected, failing back to Docker");
            }
        } catch (IOException e) {
            Output.printError("Error while reading files: " + e.getMessage());
            Output.printInfo("Project technology couldn't be detected, failing back to Docker");
        }
        return getRequest;
    }
}
