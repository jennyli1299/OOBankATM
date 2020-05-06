package src;
import java.time.format.DateTimeFormatter;

public class Fee extends Transaction {

        /* Static Bank Variables */
        private static float openingCharge = 10;
        private static float closingCharge = 20;
        private static float depositFee = 5;
        private static float withdrawalFee = 3;
        private static float transferFee = 1;
        private static float SavingsAccountInterest = (float) 0.015;
        private static float LoanInterestRate = (float)0.015;

        public Fee(Account account, float amount) {
            super(account, -amount);
        }
    
}