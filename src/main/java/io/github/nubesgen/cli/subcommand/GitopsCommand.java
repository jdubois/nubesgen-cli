package io.github.nubesgen.cli.subcommand;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import io.github.nubesgen.cli.util.Output;

@CommandLine.Command(name = "gitops", description = "Setup GitOps on the current project")
public class GitopsCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        return gitops("demo");
    }

    public Integer gitops(String projectName) {
        Output.printTitle("Setting up GitOps...");
        // The resource group used by Terraform to store its remote state.
        String resourceGroup = "rg-terraform-001";
        // The location of the resource group.
        String location = "eastus";
        // The storage account (inside the resource group) used by Terraform to store its remote state.
        String tfStorageAccount = "terraform-storage-001";
        // The container name (inside the storage account) used by Terraform to store its remote state.
        String containerName = "tfstate";
        return 0;
    }
}
