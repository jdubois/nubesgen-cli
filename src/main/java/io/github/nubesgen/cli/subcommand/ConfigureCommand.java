package io.github.nubesgen.cli.subcommand;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import io.github.nubesgen.cli.util.Output;
import io.github.nubesgen.cli.util.ProcessExecutor;

@CommandLine.Command(name = "configure", description = "Configure GitOps on the current project")
public class ConfigureCommand  implements Callable<Integer> {

    @Override public Integer call() {
        return configure();
    }

    public static Integer configure() {
        Output.printTitle("Configuring GitOps on the current project");
        Output.printInfo("Checking Azure CLI status...");
        if (ProcessExecutor.execute("az > /dev/null") < 0) {
            Output.printError("Azure CLI is not installed. Please install it first.");
            return -1;
        } else {
            Output.printInfo("Azure CLI is installed.");
        }
        Output.printInfo("GitHub Azure CLI status...");
        if (ProcessExecutor.execute("gh version > /dev/null") < 0) {
            Output.printError("GitHub CLI is not installed. Please install it first.");
            return -1;
        } else {
            Output.printInfo("GitHub CLI is installed.");
        }
        return 0;
    }
}
