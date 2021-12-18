package io.github.nubesgen.cli.subcommand;

import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.github.nubesgen.cli.Nubesgen;
import io.github.nubesgen.cli.util.Output;

@CommandLine.Command(name = "download", description = "Download the NubesGen configuration")
public class DownloadCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        return download("demo", "?application=APP_SERVICE.basic&runtime=DOCKER");
    }

    public static Integer download(String projectName, String getRequest) {
        Output.printTitle("Downloading the NubesGen configuration...");
        try {
            String server = "https://nubesgen.com";
            if (Nubesgen.development) {
                server = "http://localhost:8080";
            }
            Files.copy(
                    new URL(server + "/" + projectName + ".zip" + getRequest).openStream(),
                    Paths.get(projectName + ".zip"),
                    StandardCopyOption.REPLACE_EXISTING);

            Output.printInfo("NubesGen configuration downloaded");
            Path source = Paths.get(projectName + ".zip");
            Path target = Paths.get(System.getProperty("user.dir"));
            unzipFolder(source, target);
        } catch (IOException e) {
            Output.printError("Error: " + e.getMessage());
        }
        return 0;
    }

    public static void unzipFolder(Path source, Path target) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                boolean isDirectory = false;
                if (zipEntry.getName().endsWith(File.separator)) {
                    isDirectory = true;
                }
                Path newPath = zipSlipProtect(zipEntry, target);
                if (isDirectory) {
                    Files.createDirectories(newPath);
                } else {
                    if (newPath.getParent() != null) {
                        if (Files.notExists(newPath.getParent())) {
                            Files.createDirectories(newPath.getParent());
                        }
                    }
                    Output.printMessage(newPath.toString());
                    Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (Exception e) {
            Output.printError("Error: " + e.getMessage());
        }
    }

    // protect zip slip attack
    public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir)
        throws IOException {

        // test zip slip vulnerability
        // Path targetDirResolved = targetDir.resolve("../../" + zipEntry.getName());
        Path targetDirResolved = targetDir.resolve(zipEntry.getName());

        // make sure normalized file still has targetDir as its prefix
        // else throws exception
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }
        return normalizePath;
    }
}
