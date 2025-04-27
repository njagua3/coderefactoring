                        /* CODE REFACTORING
  import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


                final int MONTHS_IN_YEAR = 12;
                final int PERCENTAGE = 100;

                Scanner scanner = new Scanner(System.in);// scanner object


                double principal; // global scope variable declaration

                while(true) {
                    System.out.print("Principal ($1K - $1M): ");//principal label
                    principal = scanner.nextDouble(); // read user input and store
                    if (principal >= 1000 && principal <= 1_000_000)
                        break;
                    System.out.println("Principal must be between 1000 and 1_000_000");

                }
                // Annual Interest

                double annualInterest; // variable declaration
                while(true) {
                    System.out.print("Annual Interest Rate (1 - 30): ");// A.I.R label
                    annualInterest = scanner.nextDouble();// read user input & store
                    if (annualInterest >= 1 && annualInterest <= 30)
                        break;
                    System.out.println("Annual Interest must be between 1 and 30");

                }
                 // YEARS

                 int years; // variable declaration
                 while (true) {
                     System.out.print("Period (Years): ");
                     years = scanner.nextInt();
                     if (years >= 1 && years <= 30)
                         break;
                 System.out.println("Period must be between 1 and 30 years");
                }

                 // calculate monthly interest
                 double monthlyInterest = annualInterest / PERCENTAGE / MONTHS_IN_YEAR;

                 //calculate no of payments
                 int numberOfPayments = years * MONTHS_IN_YEAR;

                 //calculate mortgage
                 double mortgage = principal *
                     (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments)) /
                         (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

                 // format mortgage value as a currency

                 String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
                 System.out.println("Mortgage: " + mortgageFormatted);

                 // close scanner
                  scanner.close();
    }
}
 - We made a calculator, and we're going to refactor it
 - we want to reduce the code we write
 - Currently everything is under one method, we want to split it up
 - When refactoring you should look for 2 things:
      1: concepts that always go together - e.g., when calculating the mortgage.
      What do we need to know to calculate the mortgage?
      - we need the principal, monthly interest & no of payments
      - Monthly interest is calculated from the annual interest rate.
      - lets put the concept of calculating mortgage into a separate method.

      2: Repetitive patterns in code
      - e.g., the while loops have been repeated 3x.
      - the only thing different from the loops is;
      A - the validation message,
      B - the labels,
      C - values in the validation
      WE can refactor all of these

What's happening here?
1. User Input (Instance)
You are asking the user to enter:

Principal amount (the loan amount).

Annual Interest Rate (as a percentage).

Years (loan period in years).

This is done through the readNumber method using a
Scanner.

2. Calculating Mortgage (Instance)

- calculate the mortgage using the calculateMortgage
static method.

3. Formatting the Result (Instance)

📣 Summary:
readNumber = Static method to get input safely.

calculateMortgage = Static method to calculate mortgage.

main = Static method where everything starts.

All these are static because we don't want to create
 objects — run the program directly.

Instance methods would need an object (like Employee
emp1 = new Employee()), but here, not necessary.

Type	          Keyword	            Called How?
Static method	static	       ClassName.methodName()
Instance method	(no static keyword)	objectName.methodName()
*/

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double principal = readNumber("Principal: ", 1000, 1_000_000);

        // Annual Interest

        double annualInterest = readNumber("Annual Interest Rate: ", 1, 30);

        // YEARS
        int years = (int) readNumber("Period (Years): ", 1, 30);


        double mortgage = calculateMortgage((int) principal, (float) annualInterest, years);
        // format mortgage value as a currency

        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println("MORTGAGE");
        System.out.println("--------");
        System.out.println("Monthly Payments: " + mortgageFormatted);
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("----------------");
        for (short month = 1; month <= years * 12; month++) {
            double balance = calculateBalance((int) principal, (float) annualInterest, years, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }



    }
    public static double readNumber(
            String prompt,
            double min,
            double max){
        Scanner scanner = new Scanner(System.in);
        double value;
        while(true) {
            System.out.print(prompt);
            value = scanner.nextDouble();// read user input & store
            if (value >= min && value <= max)
                break;
            System.out.println("Annual Interest must be between " + min + " and " + max);

        }
        return value;
    }
    public static double calculateMortgage(
            int principal,
            float annualInterest,
            float years) {
        final byte MONTHS_IN_YEAR = 12;
        final byte PERCENTAGE = 100;

        //calculate no of payments
        short numberOfPayments = (short)(years * MONTHS_IN_YEAR);
        //calculate monthly interest
        float monthlyInterest = annualInterest / PERCENTAGE / MONTHS_IN_YEAR;

        //calculate mortgage
        double mortgage = principal *
                (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments)) /
                (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);
        return mortgage;
    }
    public static double calculateBalance(
            int principal,
            float annualInterest,
            float years,
            short numberOfPaymentsMade){
        final byte MONTHS_IN_YEAR = 12;
        final byte PERCENTAGE = 100;

        float monthlyInterest = annualInterest / PERCENTAGE / MONTHS_IN_YEAR;
        short numberOfPayments = (short)(years * MONTHS_IN_YEAR);

        double balance = principal*(Math.pow(1 + monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);
        return balance;
    }
}