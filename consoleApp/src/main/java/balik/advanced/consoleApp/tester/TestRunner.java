package balik.advanced.consoleApp.tester;

import balik.advanced.consoleApp.heap.LeftistHeap;
import balik.advanced.consoleApp.parser.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

class TestRunner {
    private File inputFile;
    private File answerFile;

    private LeftistHeap testHeap;

    private ArrayList<String> answers;
    private ArrayList<String> inputResults;

    private boolean validateInput = true;

    TestRunner(File inputFile, File answerFile) throws IOException {
        this.inputFile = inputFile;
        this.answerFile = answerFile;
        answers = new ArrayList<>();
        inputResults = new ArrayList<>();
        testHeap = new LeftistHeap();
        readAnswer();
    }

    private void readAnswer() throws IOException {
        try (Reader reader = new FileReader(answerFile);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String currLine = bufferedReader.readLine();
            while (currLine != null) {
                answers.add(currLine);
                currLine = bufferedReader.readLine();
            }

        } catch (IOException e) {
            throw e;
        }
    }

    void runTest() throws IOException {
        try (Reader reader = new FileReader(inputFile);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String currLine = bufferedReader.readLine();
            while (currLine != null) {
                if (currLine.equals(Message.GET_MIN.getMessage())) {
                    inputResults.add(String.valueOf(testHeap.getMin()));
                }
                else if(currLine.equals(Message.DELETE_MIN.getMessage())){
                    testHeap.extractMin();
                }
                else {
                    Optional<Integer> newValue = getInput(currLine);
                    if (newValue.isPresent()) {
                        testHeap.insert(newValue.get());
                    } else {
                        validateInput = false;
                        break;
                    }

                }
                currLine = bufferedReader.readLine();
            }

        } catch (IOException e) {
            throw e;
        }
    }

    boolean checkTest() {
        if (!validateInput) {
            return false;
        }
        return Arrays.equals(answers.toArray(), inputResults.toArray());
    }

    boolean isValidateInput() {
        return validateInput;
    }

    private Optional<Integer> getInput(String input) {
        Optional<Integer> optional;
        if (input.matches("^(-?)\\d+")) {
            optional = Optional.of(Integer.parseInt(input));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }
}
