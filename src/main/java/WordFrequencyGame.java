import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class WordFrequencyGame {

    public static final String SPACE_ONE = " 1";
    public static final String CALCULATE_ERROR = "Calculate Error";
    public static final int WORD_COUNT = 1;
    public static final String SPACE = " ";
    public static final String LINE_BREAK = "\n";
    public static final String REGEX = "\\s+";

    public String getResult(String inputStr) {
        if (splitByWhitespace(inputStr).length == 1) {
            return inputStr + SPACE_ONE;
        } else {
            try {
                //split the input string with 1 to n pieces of spaces
                String[] arr = splitByWhitespace(inputStr);
                return sortAndFormatWords(arr);
            } catch (Exception e) {
                return CALCULATE_ERROR;
            }
        }
    }

    private String sortAndFormatWords(String[] arr) {
        List<Input> inputList = convertToInputList(arr);

        //get the map for the next step of sizing the same word
        inputList = groupAndCountInputs(inputList);
        inputList.sort((wordPre, wordCurr) -> wordCurr.getWordCount() - wordPre.getWordCount());

        return formatWordFrequency(inputList);
    }

    private static String formatWordFrequency(List<Input> inputList) {
        StringJoiner joiner = new StringJoiner(LINE_BREAK);
        for (Input input : inputList) {
            String word = input.getValue() + SPACE + input.getWordCount();
            joiner.add(word);
        }
        return joiner.toString();
    }

    private List<Input> groupAndCountInputs(List<Input> inputList) {
        Map<String, List<Input>> inputsByValue = groupInputsByValue(inputList);

        List<Input> groupedInputs = new ArrayList<>();
        for (Map.Entry<String, List<Input>> entry : inputsByValue.entrySet()) {
            Input input = new Input(entry.getKey(), entry.getValue().size());
            groupedInputs.add(input);
        }
        return groupedInputs;
    }

    private static List<Input> convertToInputList(String[] arr) {
        List<Input> inputList = new ArrayList<>();
        for (String s : arr) {
            Input input = new Input(s, WORD_COUNT);
            inputList.add(input);
        }
        return inputList;
    }

    private String[] splitByWhitespace(String inputStr) {
        return inputStr.split(REGEX);
    }

    private Map<String, List<Input>> groupInputsByValue(List<Input> inputList) {
        Map<String, List<Input>> map = new HashMap<>();
        for (Input input : inputList) {
//       map.computeIfAbsent(input.getValue(), k -> new ArrayList<>()).add(input);
            if (map.containsKey(input.getValue())) {
                map.get(input.getValue()).add(input);
            } else {
                ArrayList wordsArray = new ArrayList<>();
                wordsArray.add(input);
                map.put(input.getValue(), wordsArray);
            }
        }
        return map;
    }
}