package io.github.nubesgen.cli.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessExecutor {

    /**
     * Execute a command, print the output and return the exit code.
     */
    public static Integer execute(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                Output.printMessage(line);
            }

            int exitVal = process.waitFor();
            return exitVal;

        } catch (IOException e) {
            Output.printError(e.getMessage());
            return -1;
        } catch (InterruptedException e) {
            Output.printError(e.getMessage());
            return -1;
        }
    }

    /**
     * Execute a command and return the result as a String.
     */
    public static String executeAndReturnString(String command) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String result = "";
        String line;
        while ((line = reader.readLine()) != null) {
            result += line;
        }

        int exitVal = process.waitFor();
        if (exitVal != 0) {
            throw new Exception("Command failed with exit code: " + exitVal);
        }

        return result;
    }
}
