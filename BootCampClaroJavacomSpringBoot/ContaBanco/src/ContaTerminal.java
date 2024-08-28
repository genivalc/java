import java.util.Scanner;

public class ContaTerminal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int number = 0;
        while (true) {
            try {
                System.out.print("Por favor, digite o número da Agência: ");
                number = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
        System.out.print("Por favor, digite o nome da Agência: ");
        String agency = sc.nextLine();

        System.out.print("Por favor, digite o nome do Cliente: ");
        String clientName = sc.nextLine();

        float balance = 0.0f;
        while (true) {
            try {
                System.out.print("Por favor, digite seu saldo: ");
                balance = Float.parseFloat(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número decimal válido.");
            }
        }
        System.out.println("Sua agência é " + agency + " com o número " + number +
                ". Cliente: " + clientName + " com o saldo: R$ " + String.format("%.2f", balance));

        sc.close();
    }
}