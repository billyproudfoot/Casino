
package casino;

import java.util.*;

 class Casino {

    static Random rand = new Random();
    static Scanner input = new Scanner(System.in);
    static int usercoins = 10000;
    static int multiplier = 0;
    static String result = new String();
    
    public static void main(String[] args) {
        menu();
    }
    public static void menu(){
        System.out.println("Select which game to play.");
        System.out.println("1. Slot Machine");
        System.out.println("2. Poker");
        System.out.println("0. Quit");
        int MenuChoice = input.nextInt();
        switch (MenuChoice) {
            case 1:
                System.out.println("How to play:");
        System.out.println("The machine will roll three different wheels. If two of the three match you win 2x your bet. If all three match you win 5x your bet.");
               slots();
                break;
            case 2:
                System.out.println("How to play:");
                System.out.println("You will get 5 cards. If you get a pair you win 2x, a three of a kind 3x, a straight 4x, a flush 5x, a full house 7x, four of a kind 10x, a straight flush 20x and a royal flush 100x your bet! ");
                poker();
                break;
            case 0:
                quit();
                break;
            default:
                System.out.println("That is an incorrect input, please type the number of the option you wish to select.");
                menu();
                break;
    }
}

    public static void slots(){
        System.out.println("You have " + usercoins + " coins.");
        System.out.println("How much would you like to bet?");
        try {
            int betvalue = input.nextInt();
            if(betvalue<1){
                System.out.println("You must bet more than 0.");
                slots();
            }else if (betvalue>usercoins){
            System.out.println("Because the value of your bet is more than your total coins, your bet is set to the coins you have left.");
            betvalue = usercoins;
            usercoins = 0;
        }else{
            usercoins = usercoins - betvalue;
        }
        int wheel1 = rand.nextInt(7)+1;
        int wheel2 = rand.nextInt(7)+1;
        int wheel3 = rand.nextInt(7)+1;
        System.out.println(" - "+ "\u001B[31m" + wheel1+ "\u001B[0m" +" - "+ "\u001B[31m" +wheel2+ "\u001B[0m" +" - "+ "\u001B[31m" +wheel3+ "\u001B[0m" +" - ");
        if((wheel1==wheel2)&(wheel1==wheel3)&(wheel1==7)){
            System.out.println("JACKPOT. You just won " + betvalue*20 + " coins!!!");
            usercoins = usercoins + betvalue*20;
        }else if ((wheel1==wheel2)&(wheel1==wheel3)){
            System.out.println("WOW! You just won " + betvalue*5 + " coins!!");
            usercoins = usercoins + betvalue*5;
        }else if ((wheel1==wheel2)|(wheel1==wheel3)|(wheel2==wheel3)){
            System.out.println("Nice. You just won " + betvalue*2 +" coins!");
            usercoins = usercoins + betvalue*2;
        }else{
            System.out.println("Sorry, you didn't win this time.");
        }
        if(usercoins==0){
            System.out.println("Sorry, you have lost all your coins.");
            quit();
        }
        System.out.println("Would you like to play again? y/n");
        String goToMenu = input.next();
        switch (goToMenu) {
            case "n":
                menu();
                break;
            case "y":
                slots();
                break;
            default:
                System.out.println("That is an incorrect input, you will be returned to the menu.");
                menu();
                break;
        }
        } catch (Exception e) {
            String grab = input.next();
            System.out.println("There was an error, you will be returned to the menu. Error: " + e);
            menu();
        } 
    }
    
    public static ArrayList<String> hand = new ArrayList<>();
    
    public static void poker(){
        System.out.println("You have " + usercoins + " coins.");
        System.out.println("How much would you like to bet?");
        try {
            int betvalue = input.nextInt();
            if(betvalue<1){
                System.out.println("You must bet more than 0.");
                poker();
            }else if (betvalue>usercoins){
            System.out.println("Because the value of your bet is more than your total coins, your bet is set to the coins you have left.");
            betvalue = usercoins;
            usercoins = 0;
        }else{
            usercoins = usercoins - betvalue;
        }
        String[] suits = {"\u2666", "\u2665", "\u2660", "\u2663"}; // Diamond, Heart, Spade, Club
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        for (int i = 0; i < 5; i++) {
            int randomRank = rand.nextInt(13);
            int randomSuit = rand.nextInt(4);
            hand.add(ranks[randomRank] + suits[randomSuit]); 
            for (int j = 0; j < i; j++) {
                if(hand.get(i).equals(hand.get(j))){
                    hand.remove(i);
                    i--;
                    break;
                }
            }
        }
        System.out.println("Your hand is: ");
        
        for (int i = 0; i < 5; i++) {
            if((hand.get(i).contains("\u2666"))|(hand.get(i).contains("\u2665"))){
            System.out.println("\u001B[31m" + hand.get(i) + " \u001B[0m");
            }else{
                System.out.println(hand.get(i) + " ");
            }
        }
         result = "";
         pair();
         threeofakind();
//         straight();
//         flush();
         fullhouse();
         fourofakind();
         straightflush();
         royalflush();
         if(!result.equals("")){
         System.out.println("You got a " + result + ", you won " + betvalue*multiplier + " coins.");
         usercoins = usercoins + betvalue*multiplier;
         }else{
             System.out.println("Sorry, you didn't win.");
         }
        if(usercoins==0){
            System.out.println("Sorry, you have lost all your coins.");
            quit();
        }
        System.out.println("Would you like to play again? y/n");
        String goToMenu = input.next();
        System.out.println("");
        switch (goToMenu) {
            case "n":
                menu();
                break;
            case "y":
                hand.clear();
                poker();
                break;
            default:
                System.out.println("That is an incorrect input, you will be returned to the menu.");
                menu();
                break;
        }
        } catch (Exception e) {
            String grab = input.next();
            System.out.println("There was an error, you will be returned to the menu. Error: " + e);
            menu();
        }         
    }
    
    
    public static void royalflush(){
        if(hand.contains("A\u2660") & hand.contains("K\u2660") & hand.contains("Q\u2660") & hand.contains("J\u2660") & hand.contains("10\u2660")){
        }else if(hand.contains("A\u2665") & hand.contains("K\u2665") & hand.contains("Q\u2665") & hand.contains("J\u2665") & hand.contains("10\u2665")){
        }else if(hand.contains("A\u2663") & hand.contains("K\u2663") & hand.contains("Q\u2663") & hand.contains("J\u2663") & hand.contains("10\u2663")){
        }else if(hand.contains("A\u2666") & hand.contains("K\u2666") & hand.contains("Q\u2666") & hand.contains("J\u2666") & hand.contains("10\u2666")){
        }else{
           return; 
        }
        multiplier = 100;
        result = "royal flush";
    }
    
    public static void straightflush(){
        if((straight())&(flush())){
            result = "straight flush";
            multiplier = 20;
        }
    }
    
    public static boolean straight(){
        if((((hand.toString().contains("A")))|(hand.toString().contains("6")))&(hand.toString().contains("2"))&(hand.toString().contains("3"))&(hand.toString().contains("4"))&(hand.toString().contains("5"))){
        }else if((((hand.toString().contains("3")))|(hand.toString().contains("8")))&(hand.toString().contains("4"))&(hand.toString().contains("5"))&(hand.toString().contains("6"))&(hand.toString().contains("7"))){
        }else if((((hand.toString().contains("5")))|(hand.toString().contains("10")))&(hand.toString().contains("6"))&(hand.toString().contains("7"))&(hand.toString().contains("8"))&(hand.toString().contains("9"))){
        }else if((((hand.toString().contains("7")))|(hand.toString().contains("Q")))&(hand.toString().contains("8"))&(hand.toString().contains("9"))&(hand.toString().contains("10"))&(hand.toString().contains("J"))){
        }else if((((hand.toString().contains("9")))|(hand.toString().contains("A")))&(hand.toString().contains("10"))&(hand.toString().contains("J"))&(hand.toString().contains("Q"))&(hand.toString().contains("K"))){
        }else{
        return false;
        }
        multiplier = 4;
        result = "straight";
        return true;
    }
    
    public static boolean flush(){
        int Spade = 0;
        int Club = 0;
        int Heart = 0;
        int Diamond = 0;
        for (int i = 0; i < 5; i++) {
          if(hand.get(i).contains("\u2660")){
              Spade++;
          } else if(hand.get(i).contains("Diamond")){
              Diamond++;
          } else if(hand.get(i).contains("\u2665")){
              Heart++;
          } else if(hand.get(i).contains("\u2663")){
              Club++;
          }
        }
        if((Club==5)|(Spade==5)|(Heart==5)|(Diamond==5)){
            multiplier = 5;
            result = "flush";
            return true;
        }else{
            return false;
        }
    }
    
    public static void fourofakind(){
        int matching = 0;
    for (int i = 0; i < 5; i++) {
        matching = 0;
        for (int j = 0; j < 5; j++) {
            if (hand.get(j).charAt(0) == hand.get(i).charAt(0)) {
                matching++;
            }
        }
        if (matching == 4) {
            multiplier = 10;
            result = "four of a kind";
            return;
        }
    }
    }
    
    public static void threeofakind(){
        int matching = 0;
    for (int i = 0; i < 5; i++) {
        matching = 0;
        for (int j = 0; j < 5; j++) {
            if (hand.get(j).charAt(0) == hand.get(i).charAt(0)) {
                matching++;
            }
        }
        if (matching == 3) {
            multiplier = 3;
            result = "three of a kind";
            return;
        }
    }
    }
    
    public static void pair(){
        int matching = 0;
    for (int i = 0; i < 5; i++) {
        matching = 0;
        for (int j = 0; j < 5; j++) {
            if (hand.get(j).charAt(0) == hand.get(i).charAt(0)) {
                matching++;
            }
        }
        if (matching == 2) {
            multiplier = 2;
            result = "pair";
            return;
        }
    }
    }

    public static void fullhouse(){
        char check = 0;
        char temp = 0;
        boolean pair = false;
        int matching = 0;
    for (int i = 0; i < 5; i++) {
        matching = 0;
        for (int j = 0; j < 5; j++) {
            pair = false;
            if (hand.get(j).charAt(0) == hand.get(i).charAt(0)) {
                matching++;
            }else{
               check = hand.get(j).charAt(0);
               if(check==temp){
               pair = true;
               }else{
                   temp = check;
               }
            }
        }
        
        if ((matching == 3)& (pair==true)) {
            multiplier = 7;
            result = "full house";
            return;
        }
    }
    }

    public static void quit(){
        System.out.println("Thanks for coming to the casino!");
        System.exit(0);
    }
}
