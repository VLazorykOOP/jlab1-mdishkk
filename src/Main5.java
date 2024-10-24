import java.util.ArrayList;
import java.util.HashSet;

public class Main5 {
    public static void main(String[] args) {
        String text = "Зима найхолодніша пора року, час хуртовин і сильних морозів. Але для багатьох людей вона є улюбленою. Зима час свят і веселощів. Взимку люди зустрічають Різдво, Новий рік, день Святого Валентина. Зима для багатьох людей приносить радість. А як чекають зиму діти. Можна і покататися на санках, на лижах, на ковзанах, пограти в сніжки, зліпити снігову бабу.";

        String[] words = text.split("\\P{L}+");

        ArrayList<String> uniqueWords = new ArrayList<>();

        for (String word : words) {
            if (!hasRepeatedLetters(word)) {
                uniqueWords.add(word);
            }
        }

        String result = String.join(" ", uniqueWords);

        System.out.println("Текст після видалення слів з повторюваними літерами:");
        System.out.println(result);
    }

    public static boolean hasRepeatedLetters(String word) {
        word = word.toLowerCase();

        HashSet<Character> seen = new HashSet<>();

        for (char c : word.toCharArray()) {
            if (seen.contains(c)) {
                return true;
            }
            seen.add(c);
        }

        return false;
    }
}

