import java.util.*;
import java.util.stream.Collectors;

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
        sortByCountDesc(inputList);

        return formatWordFrequency(inputList);
    }

    private static void sortByCountDesc(List<Input> inputList) {
        inputList.sort(Comparator.comparingInt(Input::getWordCount).reversed());
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

        return inputsByValue.entrySet().stream()
                .map(entry -> new Input(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static List<Input> convertToInputList(String[] arr) {
        return Arrays.stream(arr)
                .map(s -> new Input(s, WORD_COUNT))
                .toList();
    }

    private String[] splitByWhitespace(String inputStr) {
        return inputStr.split(REGEX);
    }

    private Map<String, List<Input>> groupInputsByValue(List<Input> inputList) {
        Map<String, List<Input>> map = new HashMap<>();
        inputList.forEach(input -> map.computeIfAbsent(input.getValue(), k -> new ArrayList<>()).add(input));
        return map;
    }
}