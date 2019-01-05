package balik.advanced.consoleApp;

import balik.advanced.consoleApp.parser.ConsoleParser;
import balik.advanced.consoleApp.parser.Message;
import picocli.CommandLine;

import static picocli.CommandLine.*;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 0) {
                ConsoleParser parser = new ConsoleParser();
                /**
                 * На этом моменте парсятся команды коммандной строки
                 */
                new CommandLine(parser).parse(args);
                /**
                 * Если встретилась команда с usageHelp = true, то выведется сообщение с помощью.
                 * Сообщение описывает каждую команду, помеченную аннотацией @Option
                 */
                if (parser.usageHelpRequested) {
                    CommandLine.usage(new ConsoleParser(), System.out);
                }
            } else {
                System.out.println(Message.MISSING_PARAMETER.getMessage());
            }
        }
        /**
         * В случае некорректного ввода аргументов - сообщение об ошибке
         */ catch (MissingParameterException e) {
            System.out.println(Message.MISSING_PARAMETER.getMessage());
        }
        /**
         * В случае некорректного ввода команды - сообщение об ошибке
         */ catch (UnmatchedArgumentException e) {
            System.out.println(Message.UNKNOWN_COMMAND.getMessage());
        }
    }
}

