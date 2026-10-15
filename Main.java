import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // Map menu options to the folder and class that implement each module.
    private static final Map<String, ModuleInfo> MODULES = new LinkedHashMap<>();

    static {
        MODULES.put("1", new ModuleInfo(
                "Bank Account Management System",
                "Bank Assignment",
                "BankDemo",
                new String[]{"Account.java", "CurrentAccount.java", "SavingsAccount.java", "BankDemo.java"}
        ));
        MODULES.put("2", new ModuleInfo(
                "Abstraction, Abstract Classes & Interfaces",
                "Abstraction, Abstract Classes and Interfaces",
                "AbstractionExamples",
                new String[]{"AbstractionExamples.java"}
        ));
        MODULES.put("3", new ModuleInfo(
                "Classes and Objects (Practical OOP)",
                "classes and objects",
                "PracticalOOPExamples",
                new String[]{"PracticalOOPExamples.java"}
        ));
        MODULES.put("4", new ModuleInfo(
                "Constructors & Object Initialization",
                "constructors",
                "ConstructorsExamples",
                new String[]{"ConstructorsExamples.java"}
        ));
        MODULES.put("5", new ModuleInfo(
                "Encapsulation & Access Modifiers",
                "Encapsulation and Access Modifiers",
                "EncapsulationExamples",
                new String[]{"EncapsulationExamples.java"}
        ));
        MODULES.put("6", new ModuleInfo(
                "Exception Handling",
                "Exception Handling",
                "ExceptionHandlingExamples",
                new String[]{"ExceptionHandlingExamples.java"}
        ));
        MODULES.put("7", new ModuleInfo(
                "File I/O, Lambdas and Streams",
                "File IO, Lambdas and Streams",
                "FileIOLambdasStreamsExamples",
                new String[]{"FileIOLambdasStreamsExamples.java"}
        ));
        MODULES.put("8", new ModuleInfo(
                "Generics & Collections Framework",
                "Generics and the Collections Framework",
                "CollectionsGenericsExamples",
                new String[]{"CollectionsGenericsExamples.java"}
        ));
        MODULES.put("9", new ModuleInfo(
                "Inheritance & Composition",
                "Inheritance",
                "InheritanceExamples",
                new String[]{"InheritanceExamples.java"}
        ));
        MODULES.put("10", new ModuleInfo(
                "Nested Types, Enums, Records & Object Contract",
                "Nested Types, Enums, Records and the Object Contract",
                "NestedTypesExamples",
                new String[]{"NestedTypesExamples.java"}
        ));
        MODULES.put("11", new ModuleInfo(
                "Polymorphism (Overloading, Overriding, Dispatch)",
                "Polymorphism",
                "PolymorphismExamples",
                new String[]{"PolymorphismExamples.java"}
        ));
    }

    public static void main(String[] args) {
        // Command-line arguments bypass the interactive menu.
        if (args.length > 0) {
            handleArgs(args);
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printBanner();
                printMenu();
                System.out.print("Select an option (0-11, A=All, Q=Quit): ");
                if (!scanner.hasNextLine()) {
                    break;
                }
                String choice = scanner.nextLine().trim();

                if (choice.equalsIgnoreCase("Q") || choice.equals("0")) {
                    System.out.println("\nExiting. Thank you!");
                    break;
                } else if (choice.equalsIgnoreCase("A")) {
                    runAllModules();
                } else if (MODULES.containsKey(choice)) {
                    runModule(choice, MODULES.get(choice), false);
                } else {
                    System.out.println("\n[!] Invalid selection. Please choose a valid option from the menu.\n");
                }

                System.out.println("\nPress Enter to return to the main menu...");
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
        }
    }

    private static void handleArgs(String[] args) {
        String target = args[0].toLowerCase();
        if (target.equals("all") || target.equals("a")) {
            runAllModules();
        } else if (target.equals("bank") || target.equals("1")) {
            runModule("1", MODULES.get("1"), false);
        } else if (MODULES.containsKey(target)) {
            runModule(target, MODULES.get(target), false);
        } else {
            System.out.println("Unknown module argument: " + target);
            System.out.println("Usage: java Main [all | bank | 1..11]");
        }
    }

    private static void printBanner() {
        System.out.println("================================================================================");
        System.out.println("           BANK ACCOUNT MANAGEMENT SYSTEM & OOP LAB ASSIGNMENT                 ");
        System.out.println("================================================================================");
        System.out.println(" Student: Gary Munamba            Reg No: H250094f                             ");
        System.out.println(" Department: School of Information and Sciences                                ");
        System.out.println("================================================================================");
    }

    private static void printMenu() {
        System.out.println("\nAvailable Modules:\n");
        for (Map.Entry<String, ModuleInfo> entry : MODULES.entrySet()) {
            System.out.printf("  [%2s] %s%n", entry.getKey(), entry.getValue().title);
        }
        System.out.println("  [ A] Run ALL Modules Sequentially (Automated)");
        System.out.println("  [ 0] Exit\n");
    }

    private static void runAllModules() {
        System.out.println("\n>>> Running all modules sequentially (automated test suite)...\n");
        // Run modules in menu order and count successful completions.
        int total = MODULES.size();
        int passed = 0;
        for (Map.Entry<String, ModuleInfo> entry : MODULES.entrySet()) {
            boolean success = runModule(entry.getKey(), entry.getValue(), true);
            if (success) passed++;
        }
        System.out.println("================================================================================");
        System.out.printf(" All modules executed. Result: %d / %d completed successfully.%n", passed, total);
        System.out.println("================================================================================");
    }

    private static boolean runModule(String id, ModuleInfo module, boolean automated) {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.printf(" [%s] Running: %s%n", id, module.title);
        System.out.printf(" Folder: %s | Class: %s%n", module.directory, module.mainClass);
        System.out.println("--------------------------------------------------------------------------------");

        File workingDir = new File(module.directory);
        if (!workingDir.exists()) {
            System.err.println("[ERROR] Directory does not exist: " + workingDir.getAbsolutePath());
            return false;
        }

        // Compile the selected module before starting its main class.
        String[] compileCmd = new String[module.sourceFiles.length + 3];
        compileCmd[0] = "javac";
        compileCmd[1] = "--release";
        compileCmd[2] = "21";
        System.arraycopy(module.sourceFiles, 0, compileCmd, 3, module.sourceFiles.length);

        try {
            ProcessBuilder compileProcessBuilder = new ProcessBuilder(compileCmd);
            compileProcessBuilder.directory(workingDir);
            compileProcessBuilder.inheritIO();
            Process compileProcess = compileProcessBuilder.start();
            int compileExit = compileProcess.waitFor();
            if (compileExit != 0) {
                System.err.printf("[ERROR] Compilation failed for %s with exit code %d%n", module.title, compileExit);
                return false;
            }

            ProcessBuilder runProcessBuilder = new ProcessBuilder("java", "-cp", ".", module.mainClass);
            runProcessBuilder.directory(workingDir);
            if (!automated) {
                runProcessBuilder.inheritIO();
            } else {
                runProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                runProcessBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
            }
            Process runProcess = runProcessBuilder.start();
            if (automated) {
                try (java.io.OutputStream os = runProcess.getOutputStream()) {
                    os.write("10 2\nLine 1\nLine 2\nLine 3\nLine 4\nLine 5\nJava is an object oriented language\n".getBytes());
                    os.flush();
                } catch (IOException ignored) {}
            }
            int runExit = runProcess.waitFor();

            if (runExit == 0) {
                System.out.printf("\n[SUCCESS] %s finished successfully.%n", module.title);
                return true;
            } else {
                System.err.printf("\n[FAILURE] %s exited with status code %d.%n", module.title, runExit);
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("[EXCEPTION] Error executing module: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private static class ModuleInfo {
        final String title;
        final String directory;
        final String mainClass;
        final String[] sourceFiles;

        ModuleInfo(String title, String directory, String mainClass, String[] sourceFiles) {
            this.title = title;
            this.directory = directory;
            this.mainClass = mainClass;
            this.sourceFiles = sourceFiles;
        }
    }
}
