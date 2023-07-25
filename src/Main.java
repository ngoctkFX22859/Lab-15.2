import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        try {
            List<Integer> bobDices = readDices("src/bob.txt");
            System.out.println(bobDices);

            List<Integer> aliceDices = readDices("src/alice.txt");
            System.out.println(aliceDices);

            String winner = getWinner(bobDices, aliceDices);
            if (winner == null) {
                System.out.println("Two people have equal points");
            } else {
                System.out.println("The winner is " + winner);
            }

        } catch (InvalidDiceException | NumberRollDiceNotEqualException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("The file does not exist: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("The file contains non numeric data");
        }
    }

    public static List<Integer> readDices(String fileName) throws FileNotFoundException, InputMismatchException {
        System.out.println("Read file: " + fileName);
        List<Integer> dices = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String dice;
            while ((dice = br.readLine()) != null) {
                int diceInt = Integer.parseInt(dice);
                if (diceInt <= 0 ) {
                    throw new InvalidDiceException("Dice value must greater than 0 ");
                } else if (diceInt > 6) {
                    throw new InvalidDiceException("Dice value must less than or equal to 6");
                } else {
                    dices.add(diceInt);
                }
            }
        } catch (IOException e) {
            System.err.println("Đã xảy ra lỗi khi đọc file: " + e.getMessage());
        }

        return dices;
    }

    public static String getWinner(List<Integer> bobDices, List<Integer> aliceDices){
        if (bobDices.size() != aliceDices.size()){
            throw new NumberRollDiceNotEqualException("Number of dice rolls not equal");
        }
        Integer subBob = sumOfDice(bobDices);
        Integer sumAlice = sumOfDice(aliceDices);
        if (subBob == sumAlice) {
            return null;
        } else if (subBob > sumAlice){
            return "bob";
        } else {
            return "alice";
        }
    }

    public static Integer sumOfDice(List<Integer> list) {
        int sum = 0;
        for (Integer num : list) {
            sum += num;
        }
        return sum;
    }
}
