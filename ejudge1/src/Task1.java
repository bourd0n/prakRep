import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Написать программу, которая получает со стандартного ввода целые числа от -2000000000 до 2000000000 через пробел до тех пор, пока не встретит 0, и выводит минимальное из них (не считая завершающего 0).
 *
 *   Считайте, что программа получает на вход как минимум одно ненулевое число, пока не встретит 0.
 */
public class Task1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        Integer min = 2000000000;
        Integer var = scanner.nextInt();

        while (var != null && var != 0){
            if (var < min)
                min = var;
            var = scanner.nextInt();
        }
	System.out.println("T");
        System.out.println(min);
    }
}
