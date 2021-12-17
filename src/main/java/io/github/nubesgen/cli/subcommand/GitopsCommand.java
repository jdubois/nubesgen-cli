package io.github.nubesgen.cli.subcommand;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "gitops", description = "Setup GitOps on the current project")
public class GitopsCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        return gitops();
    }

    public Integer gitops() {
        
        return 0;
    }
}
