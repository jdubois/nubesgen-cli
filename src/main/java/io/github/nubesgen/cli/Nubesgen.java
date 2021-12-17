package io.github.nubesgen.cli;

import io.github.nubesgen.cli.subcommand.ConfigureCommand;
import io.github.nubesgen.cli.subcommand.DownloadCommand;
import io.github.nubesgen.cli.subcommand.ScanCommand;
import io.github.nubesgen.cli.util.Output;

import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

@Command(name = "nubesgen", mixinStandardHelpOptions = true, version = "0.0.1",
        description = "CLI for NubesGen.com")
public class Nubesgen implements Callable<Integer> {

    @Option(names = {"-d", "--development"}, description = "Development mode, this requires a local REST server running on http://127.0.0.1:8080")
    public static boolean development;

    @Override
    public Integer call() throws Exception {
        int exitCode = ConfigureCommand.configure();
        if (exitCode == 0) {
            String getRequest = ScanCommand.scan();
            DownloadCommand.download(getRequest);
        }
        return exitCode;
    }

    public static void main(String... args) {
        AnsiConsole.systemInstall();
        Output.printTitle("NugesGen configuration starting");
        int exitCode = new CommandLine(new Nubesgen())
                .addSubcommand(new ConfigureCommand())
                .addSubcommand(new ScanCommand())
                .addSubcommand(new DownloadCommand())
                .execute(args);

        Output.printTitle("NugesGen configuration finished");
        AnsiConsole.systemUninstall();
        System.exit(exitCode);
    }
}

