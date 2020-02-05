package services;

import model.Word;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyFile {

    private String path;
    private String contents;
    private List<String> allWords;
    private List<Word> words;

    public MyFile(String path) {
        this.path = path;
        allWords = new ArrayList<>();
        words = new ArrayList<>();
        setText();
        splitWords(removeSpecialchar(contents.toLowerCase()));
        countSpecificWord();
    }

    private void setText(){
        try {
            contents = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Odstraní znaky, které se neberou jako písmeno
     * @param text výchozí text
     * @return text bez speciálních znaků
     */
    private String removeSpecialchar(String text){
        String tempText = text.replaceAll("[,.“„?!()+:]", "").replaceAll("[\\-]", " ");
        return tempText;
    }

    /**
     * Uloží slovo do listu po-té co je rozdělí podle mezery
     * @param text upravený text bez speciaĺních znaku
     */
    private void splitWords(String text){
        allWords = Arrays.asList(text.split(" "));
    }

    public int countAllWords(){
        return allWords.size();
    }

    public int countNoDublicateWords(){
        return words.size();
    }

    public List<String> getAllWords() {
        return allWords;
    }

    private void sortWords(List<Word> list){
        Collections.sort(list, Comparator.comparing(Word::getWord));
    }

    public List<Word> getNoDuplicatedWords(){
        sortWords(words);
        return Collections.unmodifiableList(words);
    }

    public int getSentenceCount(){
        String[] array = contents.split("\\.", -1);
        return array.length;
    }

    private void addWords(Map<String, Integer> frequencyMap){
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            Word word = new Word();
            word.setWord(entry.getKey());
            word.setWordCount(entry.getValue());
            System.out.println(entry.getValue());
            System.out.println(countAllWords());
            word.setPercentage((double) entry.getValue() / (double)countAllWords());
            words.add(word);
        }
    }

    private void countSpecificWord(){

        Map<String, Integer> frequencyMap = allWords.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(val -> 1)));

        addWords(frequencyMap);
    }

}
