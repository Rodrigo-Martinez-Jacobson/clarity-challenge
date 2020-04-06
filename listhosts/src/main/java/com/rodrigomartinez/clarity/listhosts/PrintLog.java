package com.rodrigomartinez.clarity.listhosts;

public class PrintLog {
    public static void main(String[] args) {
        if (args.length < 4) {
            printUsage();
            System.exit(1);
        }

        final ListConnectedHosts listConnectedHosts = new ListConnectedHosts(args[0], args[1], args[2], args[3]);
        System.out.println("List of hosts connected to "+args[3]);
        System.out.println("=====================================");
        listConnectedHosts
                .processLog()
                .forEach(System.out::println);

    }
    private static void printUsage() {
        System.out.println("List connected hosts");
        System.out.println("Usage:");
        System.out.println("\tListConnected <log_file> <init_date> <end_date> <hostname>");
        System.out.println("Example: ");
        System.out.println();
    }
}
