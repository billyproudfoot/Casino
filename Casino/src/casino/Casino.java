
package casino;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;

 class Casino {

    static Random rand = new Random();
    static Scanner input = new Scanner(System.in);
    static int usercoins;
    static int multiplier = 0;
    static String result = new String();
    static String folderdirectory = System.getProperty("user.dir") + "\\casinodata.txt";
    static ArrayList<String> textItems = new ArrayList<>();
    static String username;
    
    public static void main(String[] args) {
        System.out.println("Welcome to the casino\n ");
        loginmenu();
    }
    
    public static void loginmenu(){
        System.out.println("Would you like to:\n1. Create a new account\n2. Login to an existing account\n0. Quit");
        String accountmenu = input.next();
        switch (accountmenu){
            case "1":
                createaccount();
                break;
            case "2":
                login();
            case "0":
                quit();
                break;
            default:
                System.out.println("\nThat is an incorrect input, please type the number of the option you wish to select.");
                loginmenu();
                break;
        }
    }
    
    public static void createaccount(){
        System.out.println("Enter Username");
        username = input.next();
        System.out.println("Enter Password");
        String password = input.next();
        readfile();
        String[] details;
        int loop = textItems.size();
            for (int i = 0; i < loop; i++) {
                details = textItems.get(i).split(",");
                if(details[0].equals(username)){
                    System.out.println("The username, " + username + ", is already used. You will be returned to the menu.");
                    loginmenu();
                }
            }
            textItems.add(username + "," + password + "," + "10000");
            writetofile();
            System.out.println("\nWelcome, " + username + ", you have 10000 coins in your wallet.\n");
            usercoins = 10000;
            menu();
    }
    
    public static void login(){
        System.out.println("Enter Username");
        username = input.next();
        System.out.println("Enter Password");
        String password = input.next();
        textItems.clear();
        readfile();
        String[] details;
        int loop = textItems.size();
        boolean check = true;
            for (int i = 0; i < loop; i++) {
                details = textItems.get(i).split(",");
                if((details[0].equals(username))&(details[1].equals(password))){
                    check = false;
                    loop = i;
                    usercoins = Integer.parseInt(details[2]);
                    if(usercoins==0){
                        System.out.println("\nYou have no coins, so we will give you 10000 as a gift.");
                        usercoins=10000;
                    }
                    System.out.println("\nWelcome, " + username + ", you have " + usercoins + " coins in your wallet.\n");
                    menu();
                }
            }            
               if(check==true){
                   System.out.println("Incorrect log in details, Select \"m\" to go to menu or \"r\" to re-enter login.");
                String menuorlogin = input.next();
                switch(menuorlogin){
                    case "r": 
                        login();
                        break;
                    case "m":
                        loginmenu();
                        break;
                        
                }
        }
    }
    
    public static void writetofile(){
        try{
        FileOutputStream fout = new FileOutputStream(folderdirectory);
        String myString = "";
            for (int i = 0; i < textItems.size(); i++) {
                myString = myString  + textItems.get(i) + "\n";
            }
        
        byte b[] = myString.getBytes();
        fout.write(b);
        
        fout.close();
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
    
    public static void readfile(){
            String inputLine;
            try{
                BufferedReader read = new BufferedReader(new FileReader(folderdirectory));
                while ((inputLine = read.readLine()) != null){
                    textItems.add(inputLine);
                }
                read.close();
            }catch (Exception e){
            }
        }
    
    public static void menu(){
        System.out.println("Select which game to play.");
        System.out.println("1. Slot Machine \n2. Poker \n3. Higher Or Lower \n0. Quit");
        int MenuChoice = input.nextInt();
        switch (MenuChoice) {
            case 1:
        System.out.println("\nHow to play:\nThe machine will roll three different wheels. If two of the three match you win 2x your bet. If all three match you win 5x your bet.");
               slots();
                break;
            case 2:
                System.out.println("\nHow to play:\nYou will get 5 cards. If you win your bet will be multiplied as follows:\nPair = 2x\nThree of a kind = 3x\nStraight = 4x\nFlush = 5x\nFull House = 7x\nFour of a kind = 10x\nStraight Flush = 20x\nRoyal Flush = 100x\n");
                poker();
                break;
            case 3:    
                System.out.println("\nHow to play:\nYou will be given a number between 1-13, you have to guess whether the next number will be higher or lower. If the number is the same, it will redraw. You have to predict 5 numbers in a row to win 2x your bet.");
                higherorlower();
            case 0:
                quit();
                break;
            default:
                System.out.println("\nThat is an incorrect input, please type the number of the option you wish to select.");
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
        System.out.println("\n - "+ "\u001B[31m" + wheel1+ "\u001B[0m" +" - "+ "\u001B[31m" +wheel2+ "\u001B[0m" +" - "+ "\u001B[31m" +wheel3+ "\u001B[0m" +" - ");
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
        returntomenu();
        slots();
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
        System.out.println("\nYour hand is: ");
        
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
        returntomenu();
        poker();
        } catch (Exception e) {
            String grab = input.next();
            System.out.println("There was an error, you will be returned to the menu. Error: " + e);
            menu();
        }         
    }
    
    public static void higherorlower(){
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
            boolean flag = true;
            int count = 0;       
            int number = rand.nextInt(13)+1;
            String guess;
            do {
           
            System.out.println("\nYour number is " + number + ". H/L?");
            guess = input.next();
            for (int i = 0; i < 1; i++) {
             
                if(!(guess.equalsIgnoreCase("h")|guess.equalsIgnoreCase("l"))){
                    System.out.println("Please enter a correct input, either \"H\" or \"L\" ");
                    guess = input.next();
                    i--;
                }
            }
            int nextnum = rand.nextInt(13)+1;
            for (int i = 0; i < 1; i++) {
                    if(number==nextnum){
                    nextnum = rand.nextInt(13)+1;
                    i--;
                    }
            }
            System.out.println("The new number is " + nextnum);
            if(((number<nextnum)&(guess.equalsIgnoreCase("h")))|((number>nextnum)&(guess.equalsIgnoreCase("l")))){
                System.out.println("You guessed correctly.\n");
                count++;
            }else{
                System.out.println("You guessed incorrectly and lost " + betvalue);
                flag=false;
            }
            number = nextnum;
        }while((count<5)&(flag == true));
            if(flag==true){
                System.out.println("You got 5 in a row and won " + betvalue*2 + "\n");
                usercoins = usercoins + betvalue*2;
            }
            returntomenu();
            higherorlower();
        }catch(Exception e){
                    System.out.println("There was an error, you will be returned to the menu.");
                    menu();
        }
    }
    
    public static void returntomenu(){
        savecoins();
        if(usercoins==0){
            System.out.println("\nYou are bankrupt.\nYour account has been restored to 10000.\n");
            usercoins=10000;
            menu();
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
                break;
            default:
                System.out.println("That is an incorrect input, you will be returned to the menu.");
                menu();
                break;
    }
    }
    
     public static void savecoins(){
        try{
        textItems.clear();
        readfile();
        String[] details;
        int loop = textItems.size();
            for (int i = 0; i < loop; i++) {
                details = textItems.get(i).split(",");
                if(details[0].equals(username)){
                    textItems.set(i, details[0] + "," + details[1] + "," + Integer.toString(usercoins));
                }
            }
        String myString = "";
        FileOutputStream fout = new FileOutputStream(folderdirectory);
        for (int i = 0; i < textItems.size(); i++) {
                myString = myString  + textItems.get(i) + "\n";
            }
        byte b[] = myString.getBytes();
        
        fout.write(b);
        fout.close();
        }catch(Exception e){
            System.out.println("Error: " + e);
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
        System.out.println("\nYour wallet has been updated to " + usercoins + " coins.\nThanks for visiting the casino!");
        System.exit(0);
    }
}
