package main.java.com.ramesh.dictionarysearch;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/*
 * CopyRight: © 2017 Permission granted for acoadamic purpuse only, any type of reproduction is prohibited
 *
 * Author: Ramesh Manchiknati
 *
 * Company: Personal Development
 *
 *Created by Ramesh on 7/11/15..
 *
 */
public class DictionaryLookup {
    private static String[] args;
    private static  Logger log;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        DictionaryLookup.args = args;
        CommandLineParser parser = new GnuParser();

        // create the Options
        Options options = new Options();
//        String header = "DictionaryLookup Usage is as follows\n\n";

        // create the Options
        options.addOption("m", "matrix", true, "Matrix file location");
        options.addOption("d", "dictionary", true, "Words dictionary file location");
        options.addOption("l", "logfile", true, "Log file location");
        options.addOption("r", "delimeter", true, "Matrix file delemeter default blank");
        options.addOption("h", "help", false, "Help about Dictionary");
        OptionBuilder.withLongOpt("version");
        OptionBuilder.withDescription("Print the version of the application");
        options.addOption(OptionBuilder.create('v'));
        OptionBuilder.withLongOpt("help");
        options.addOption(OptionBuilder.create('h'));
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //noinspection ConstantConditions
        if (!(cmd.hasOption("m") && cmd.hasOption("d"))) {
            HelpFormatter formatter = new HelpFormatter();
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            StackTraceElement main = stack[stack.length - 1];
            formatter.printHelp(main.getClassName(), "", options, "", true);
            return;
        }
        String mtrFile = cmd.getOptionValue("m");
        String dctFile = cmd.getOptionValue("d");
        String strDelMtr = (cmd.hasOption("r") ? cmd.getOptionValue("r") : " ");
        String logFile = (cmd.hasOption("l") ? cmd.getOptionValue("d") : "logDictionaryLookup.log");
        System.setProperty("logback.configurationFile", "logback1.xml");
        System.setProperty("Lighthouse_log_file", logFile);
        log = LoggerFactory.getLogger(DictionaryLookup.class);
        log.info("Building the lookup table");
        try {
            BuildTable btMTable = new BuildTable(mtrFile, strDelMtr);
            List<String> validWords = btMTable.getTransitionsTable().getWords(dctFile);
            if (validWords.size() > 0) {
                System.out.println(validWords.size() + " Words found ");
                validWords.forEach(System.out::println);
            } else {
                System.out.println("*****   Unfortunately we did not find any words with the given matrix file ****");
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

