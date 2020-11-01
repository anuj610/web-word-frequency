package wordfrequency;

public class FrequencyCalculatorMain {
    private static final String DEFAULT_URL = "https://www.314e.com/";

    public static void main(String[] args) {

        String url = DEFAULT_URL;

        // check if argument is passed, use that as url, otherwise use default url
        if(args.length != 0) {
            url = args[0];
        }

        (new FrequencyCalculator()).parseAndCalculate(url);
    }
}
