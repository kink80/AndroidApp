package myapp.com.myapplication.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stecl on 01/01/15.
 */
public class InMemoryprovider implements DataProvider {

    private List<WordBean> words = new ArrayList<>();

    private Map<WordBean, Integer> displays = new HashMap<WordBean, Integer>();

    private Integer wordCounter = 0;

    private WordBean current;

    @Override
    public void rateUp(WordBean word) {
        int wordAt = words.indexOf(word);
        if (wordAt != -1) {
            WordBean w = words.get(wordAt);
            words.set(wordAt, new WordBean(w.getId(), w.getFrequency().greater(), w.getWord(), w.getTranslation()));
        }
    }

    @Override
    public void rateDown(WordBean word) {
        int wordAt = words.indexOf(word);
        if (wordAt != -1) {
            WordBean w = words.get(wordAt);
            words.set(wordAt, new WordBean(w.getId(), w.getFrequency().lower(), w.getWord(), w.getTranslation()));
        }
    }

    @Override
    public void load() {
        words.clear();

        words.add(new WordBean(1L, WordBean.Frequency.HIGH, "hi", "ahoj"));
        words.add(new WordBean(2L, WordBean.Frequency.HIGH, "word", "slovo"));
        words.add(new WordBean(3L, WordBean.Frequency.HIGH, "name", "jmeno"));
        words.add(new WordBean(4L, WordBean.Frequency.HIGH, "count", "pocet"));
        words.add(new WordBean(5L, WordBean.Frequency.HIGH, "sun", "slunce"));
        words.add(new WordBean(6L, WordBean.Frequency.HIGH, "moon", "mesic"));
        words.add(new WordBean(7L, WordBean.Frequency.HIGH, "month", "mesic"));
        words.add(new WordBean(8L, WordBean.Frequency.HIGH, "day", "den"));
        words.add(new WordBean(9L, WordBean.Frequency.HIGH, "year", "rok"));
        words.add(new WordBean(10L, WordBean.Frequency.HIGH, "minute", "minuta"));

        for (WordBean word: words) {
            displays.put(word, 0);
        }
    }

    @Override
    public WordBean getNext() {
        if (words.size() == 0) {
            return new EmptyWord();
        }

        wordCounter++;

        double pivot = 0.0;

        WordBean greatestDiff = null;
        for (WordBean word: displays.keySet()) {
            Integer actualDisplays = displays.get(word);

            double ratio = (double) actualDisplays / (double) wordCounter;
            double target = word.getFrequency().numeric();
            double diff = target - ratio;

            if (diff > pivot && !word.equals(current)) {
                greatestDiff = word;
                pivot = diff;
            }
        }

        Integer actualDisplays = displays.get(greatestDiff);
        displays.put(greatestDiff, actualDisplays + 1);

        current = greatestDiff;
        return current;
    }

    @Override
    public void rateCurrent(String rating) {
        WordBean.Frequency f = WordBean.Frequency.valueOf(rating);
        current.setFrequency(f);
    }

}
