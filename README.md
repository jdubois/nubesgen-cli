# CLI for NubesGen

This CLI is part of the [NubesGen project](https://nubesgen.com).

It automates a project configuration: on simple projects,
running this command line should be enough
to have your project configured with GitOps, and automate its deployment to Azure.

More specifically, this tool:
- scans the project to find its NubesGen configuration
- sets up the GitOps workflow
- creates the Infrastructure as Code (IaC) configuration

## Installation

## Prerequisites

__Tip:__ You can go to [https://shell.azure.com](https://shell.azure.com) and login with the Azure subscription you want to use. This will provide you with the 
mandatory prerequisites below (Bash, Azure CLI, and GitHub CLI).

For the CLI to work, you need to have installed and configured the following tools:

- [Bash](https://fr.wikipedia.org/wiki/Bourne-Again_shell), which is installed by default on most Linux distributions and on Mac OS X. If you're using Windows, one solution is to use [WSL](https://aka.ms/nubesgen-install-wsl).
- [Azure CLI](https://aka.ms/nubesgen-install-az-cli). To login, use `az login`.
- (optional) [GitHub CLI](https://cli.github.com/). To login, use `gh auth login`. This will automate creating the GitHub secrets for you, otherwise you will need to do it manually.

## Downloading and installing the CLI

This CLI is available as a native image on major platforms (Linux, Mac OS, Windows), and as a Java archive. The Java archive will run everywhere, but
requires a Java Virtual Machine.

Releases are available on GitHub at [https://github.com/jdubois/nubesgen-cli/releases](https://github.com/jdubois/nubesgen-cli/releases) and in the following examples we will use the GitHub CLI to download them.

<details>
<summary>Downloading and installing the CLI with Java</summary>

To run the Java archive, you need to have a Java Virtual Machine (version 11 or higher) installed.

- Download the latest release: `gh release download --repo jdubois/nubesgen-cli --pattern='*.jar'`
- Run the binary: `java -jar nubesgen-*.jar -h`
</details>

<details>
<summary>Downloading and installing the CLI on Linux</summary>

To run the binary on Linux, you need to:

- Download the latest release: `gh release download --repo jdubois/nubesgen-cli --pattern='*-linux'`
- Make the binary executable: `chmod +x nubesgen-cli-linux`
- Run the binary: `./nubesgen-cli-linux -h`

</details>
<details>
<summary>Downloading and installing the CLI on a Mac OS</summary>

To run the binary on a Mac OS, you need to:

- Download the latest release: `gh release download --repo jdubois/nubesgen-cli --pattern='*-macos'`
- Make the binary executable: `chmod +x nubesgen-cli-macos`
- Allow Mac OS X to execute it: `xattr -d com.apple.quarantine nubesgen-cli-macos`
- Run the binary: `./nubesgen-cli-macos -h`

</details>
<details>
<summary>Downloading and installing the CLI on Windows</summary>

To run the binary on Windows, you need to:

- Download the latest release: `gh release download --repo jdubois/nubesgen-cli --pattern='*-windows.exe'`
- Run the binary: `nubesgen-cli-windows -h`

</details>

## Using the CLI