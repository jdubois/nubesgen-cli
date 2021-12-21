# CLI for NubesGen

This CLI is part of the [NubesGen project](https://nubesgen.com).

It automates a project configuration: on simple projects,
running `nubesgen` on the command line should be enough
to have it running on Azure with *no manual step*.

More specifically, this tool:
- scans the project to find its NubesGen configuration
- sets up the GitOps workflow

## Running the binary on a Mac

To run the binary on a Mac, you need to:

- Download the latest release: `gh release download --repo jdubois/nubesgen-cli --pattern='*-macos'`
- Make the binary executable: `chmod +x nubesgen-cli-macos`
- Allow Mac OS X to execute it: `xattr -d com.apple.quarantine nubesgen-cli-macos`
- Run the binary: `./nubesgen-cli-macos`
