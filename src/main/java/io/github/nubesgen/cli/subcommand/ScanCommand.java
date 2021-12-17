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
        String testFile = "";
        try {
            if (mavenFile.exists()) {
                Output.printInfo("Maven detected");
                testFile = Files.readString(mavenFile.toPath());
                if (testFile.contains("org.springframework.boot")) {
                    Output.printInfo("Runtime selected: Spring Boot + Maven");
                    getRequest += "&runtime=SPRING";
                } else if (testFile.contains("io.quarkus")) {
                    Output.printInfo("Runtime selected: Quarkus + Maven");
                    getRequest += "&runtime=QUARKUS";
                } else {
                    Output.printInfo("Runtime selected: Java + Maven");
                    getRequest += "&runtime=JAVA";
                }
            } else if (gradleFile.exists()) {
                Output.printInfo("Gradle project detected");
                testFile = Files.readString(gradleFile.toPath());
                if (testFile.contains("org.springframework.boot")) {
                    Output.printInfo("Runtime selected: Spring Boot + Gradle");
                    getRequest += "&runtime=SPRING_GRADLE";
                } else {
                    Output.printInfo("Runtime selected: Java + Gradle");
                    getRequest += "&runtime=JAVA_GRADLE";
                }
            } else {
                Output.printInfo("Runtime couldn't be detected, failing back to Docker");
            }

            if (testFile.contains("org.postgresql")) {
                Output.printInfo("Database selected: PostgreSQL");
                getRequest += "&database=POSTGRESQL";
            } else if (testFile.contains("mysql-connector-java")) {
                Output.printInfo("Database selected: MySQL");
                getRequest += "&database=MYSQL";
            } else if (testFile.contains("com.microsoft.sqlserver")) {
                Output.printInfo("Database selected: Azure SQL");
                getRequest += "&database=SQL_SERVER";
            } else {
                Output.printInfo("Database selected: None");
            }
        } catch (IOException e) {
            Output.printError("Error while reading files: " + e.getMessage());
            Output.printInfo("Project technology couldn't be detected, failing back to Docker");
        }
        return getRequest;
    }
}
