import java.util.List;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
	   BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	 int player_age;
        Dice d1 = new Dice();
        Dice d2 = new Dice();
        Dice d3 = new Dice();

        Player player = new Player("Fred", 100);
        Game game = new Game(d1, d2, d3);
        List<DiceValue> cdv = game.getDiceValues();

        int totalWins = 0;
        int totalLosses = 0;
 
        while (true)
        {
	// Code should be added here asking the player to input his age
		System.out.print("Enter your age: ");
        	try{
        		player_age = Integer.parseInt(console.readLine());
        		if(player_age > 0 && player_age < 100)
        			break; // Valid age entered
        	}catch(Exception e){
        		// Invalid age
        		System.out.println("Invalid age! Please try again!");
        	}
        }
// Once age is read, it will be checked and the game will only continue if player's age greater than 18
        if(player_age < 18){
        	// Player is too young, game is not allowed to start
        	System.out.println("Player under 18 is not allowed to play the game. Terminate now!");
        	return;
        }
	 while (true)
        {
            int winCount = 0;
            int loseCount = 0;
            
            for (int i = 0; i < 100; i++)
            {
            	String name = "Fred";
            	int balance = 100;
            	int limit = 0;
                player = new Player(name, balance);
                player.setLimit(limit);
                int bet = 5;

                System.out.println(String.format("Start Game %d: ", i));
                System.out.println(String.format("%s starts with balance %d, limit %d", 
                		player.getName(), player.getBalance(), player.getLimit()));

                int turn = 0;
                while (player.balanceExceedsLimitBy(bet) && player.getBalance() < 200)
                {
                    turn++;                    
                	DiceValue pick = DiceValue.getRandom();
                   
                	System.out.printf("Turn %d: %s bet %d on %s\n",
                			turn, player.getName(), bet, pick); 
			// Testing incorrect balance increase on winning
                	System.out.println("----- Main.java: Starting calculation of winnings..." );
                	System.out.println("----- Main.java: Balance before play: " + player.getBalance());
                
                	 //Bug 3: DiceValues remain the same all the time
                	System.out.println("--- BUG 3 detector: old dice values: " + cdv.get(0) + "," + cdv.get(1) + "," + cdv.get(2));
                	int winnings = game.playRound(player, pick, bet);
                	System.out.println("----- Main.java: Winning amount: " + winnings);
                	System.out.println("----- Main.java: Balance after play: " + player.getBalance());
                	
                    cdv = game.getDiceValues();
                    //System.out.println("--- BUG 3 detector: new dice values: " + cdv.get(0) + "," + cdv.get(1) + "," + cdv.get(2));
                    
                	
                	
                    
                    System.out.printf("Rolled %s, %s, %s\n",
                    		cdv.get(0), cdv.get(1), cdv.get(2));
                    
                    if (winnings > 0) {
	                    System.out.printf("%s won %d, balance now %d\n\n",
	                    		player.getName(), winnings, player.getBalance());
	                	winCount++; 
                    }
                    else {
	                    System.out.printf("%s lost, balance now %d\n\n",
	                    		player.getName(), player.getBalance());
	                	loseCount++;
                    }
                    //code added to test the loop of it continues when balance still positive
                    //System.out.println("---- Debugging: Now balance: " + player.getBalance() + ". Bet: " + bet);
                    //System.out.println("---- Does balance exceeds limit by " + bet + "?: " + player.balanceExceedsLimitBy(bet));
                    //System.out.println("---- Is balance less than 200?: " + (player.getBalance() < 200));
                    //System.out.println("---- Will the loop continue?: " + (player.balanceExceedsLimitBy(bet) && player.getBalance()<200));
                  
                } //while

                System.out.print(String.format("%d turns later.\nEnd Game %d: ", turn, i));
                System.out.println(String.format("%s now has balance %d\n", player.getName(), player.getBalance()));
                
            } //for
            
            System.out.println(String.format("Win count = %d, Lose Count = %d, %.2f", winCount, loseCount, (float) winCount/(winCount+loseCount)));
            totalWins += winCount;
            totalLosses += loseCount;

            String ans = console.readLine();
            if (ans.equals("q")) break;

        } //while true
        
        System.out.println(String.format("Overall win rate = %.1f%%", (float)(totalWins * 100) / (totalWins + totalLosses)));
	}

}
