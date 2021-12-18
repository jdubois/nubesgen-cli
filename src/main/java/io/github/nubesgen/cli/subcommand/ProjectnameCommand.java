package io.github.nubesgen.cli.subcommand;

import picocli.CommandLine;

import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.concurrent.Callable;

import io.github.nubesgen.cli.util.Output;

@CommandLine.Command(name = "projectname", description = "Generate a name for the current project")
public class ProjectnameCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Output.printTitle("Project name: " + projectName());
        return 0;
    }

    public static String projectName() {
        Output.printTitle("Creating a name for the current project...");
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        Output.printMessage("Current directory: " + currentDir);
        String projectName = currentDir.substring(currentDir.lastIndexOf("/") + 1);
        if (projectName.length() > 8) {
            projectName = projectName.substring(0, 8);
        }
        if (projectName == null || projectName.isEmpty()) {
            projectName = "demo";
        }
        DecimalFormat formater = new DecimalFormat("0000");
        String random1 = formater.format(Math.random() * (10000));
        String random2 = formater.format(Math.random() * (10000));
        projectName += "-" + random1 + "-" + random2;
        Output.printInfo("Project name: " + projectName);
        return projectName;
    }
}
