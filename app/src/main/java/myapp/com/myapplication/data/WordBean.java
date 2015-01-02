package myapp.com.myapplication.data;

/**
 * Created by stecl on 01/01/15.
 */
public class WordBean {

    public enum Frequency {
        HIGH(.5), MEDIUM(.32), LOW(.12), LEARNED(.03);

        private double freq;

        private Frequency(double freq) {
            this.freq = freq;
        }

        public double numeric() {
            return freq;
        }

        public Frequency greater() {
            switch (this) {
                case LOW: return MEDIUM;
                case LEARNED: return LOW;
            }

            return HIGH;
        }

        public Frequency lower() {
            switch (this) {
                case HIGH: return MEDIUM;
                case MEDIUM: return LOW;
            }

            return LEARNED;
        }

    }

    private Long id;

    private Frequency frequency;

    private String word;

    private String translation;

    public WordBean(Long id, Frequency frequency, String word, String translation) {
        this.id = id;
        this.frequency = frequency;
        this.word = word;
        this.translation = translation;
    }

    protected WordBean() {}

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordBean wordBean = (WordBean) o;

        if (id != null ? !id.equals(wordBean.id) : wordBean.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public boolean isValid() {
        return true;
    }
}
