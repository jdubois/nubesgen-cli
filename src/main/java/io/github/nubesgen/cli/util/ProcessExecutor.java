package io.github.nubesgen.cli.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessExecutor {

    /**
     * Execute a command, print the output and return the exit code.
     */
    public static Integer execute(String command) {
        Output.printMessage(command);
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

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Execute a command and return the result as a String.
     */
    public static String executeAndReturnString(String command) throws Exception {
        Output.printMessage(command);
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String result = "";
        String line;
        while ((line = reader.readLine()) != null) {
            if (result.length() > 0) {
                result += "\n" + line;
            } else {
                result += line;
            }
        }

        int exitVal = process.waitFor();
        if (exitVal != 0) {
            throw new Exception("Command failed with exit code: " + exitVal);
        }

        return result;
    }
}
