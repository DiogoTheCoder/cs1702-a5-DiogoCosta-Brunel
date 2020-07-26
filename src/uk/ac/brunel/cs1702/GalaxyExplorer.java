package uk.ac.brunel.cs1702;

import java.util.ArrayList;

public class GalaxyExplorer {
	String posFacing = "N";
	int posX = 0;
	int posY = 0;
	int galaxySizeX;
	int galaxySizeY;
	
	String enemyShipPosText;
	int[][] enemyShipPos;
	ArrayList<String> enemyShipFoundPos = new ArrayList<String>();
	
	public GalaxyExplorer(int x, int y, String enemyShips){
		
		galaxySizeX = x;
		galaxySizeY = y;
		
		//GalaxyExplorer enterprise = new GalaxyExplorer(100,100,"(5,5)(7,8)");
		
		/*	x and y represent the size of the galaxy grid.
		 *  enemyShips is a String formatted as follows: "(es1_x,es1_y)(es2_x,es2_y)...(esN_x,eN_y)" with no white spaces, 
		 *  representing the location of enemy ships. 
		 *  
			Example use:
			GalaxyExplorer enterprise = new GalaxyExplorer(100,100,"(5,5)(7,8)")  
			//A 100x100 galaxy grid with two enemy ships at coordinates (5,5) and (7,8) 
		 */		
		
		displayArrivalPos();
		enemyShipPosText = enemyShips;
		enemyShipsToArray();
	}
	
	public String executeCommand(String command){
		
		for (int i = 0; i < command.length(); i++) {
			String commandChar = Character.toString(command.charAt(i));
			
			switch (commandChar) {
				case "f":
					moveForward();
					break;
				case "b":
					moveBackward();
					break;
				case "l":
					turnLeft();
					break;
				case "r":
					turnRight();
					break;
			}
		}
		
		return getNewPosText();
		
		/* The command string is composed of any combination of "f" (forward), "b" (backward), "l" (left) and "r" (right)
		 * Starting from (0,0,N) - quadrant (0,0) facing North - the starship executes these commands.
		 * Return value is a string representation of the final location and facing of the starship along with a list of enemy ship locations, if encountered any.
		 * If there is an enemy ship in a quadrant, your starship should not move there, but keep executing remaining commands.
		 * 
		 * Example: 
		 * The starship is on a 100x100 galaxy initially at location (0,0) and facing NORTH. The rover is given the commands "ffrff" and should end up at (2, 2) facing East.
		 
		 * The return string is in the following format: "(x;y;facing)(es1_x;es1_y)(es2_x;es2_y)...(esN_x;esN_y)"  No white spaces.
		 * Where x and y are the final coordinates, facing is the final direction the startship is pointing to, i.e. (N,S,W,E).
		 * The return string should also contain a list of coordinates of the encountered enemy ships, which can be an arbitrary number.
		 * Please note the format of the result, which uses a semi-column rather than a comma, as a delimiter. 
		 */
		
	}
	
	public void moveForward() {
		// TODO: break up the if statements into functions	
		
		int newPosX;
		int newPosY;
		
		if (posFacing.equals("N")) {
			if (posY == (galaxySizeY - 1)) {
				newPosY = 0;
			} else {
				newPosY = posY + 1;
			}
			
			checkForEnemies(posX, newPosY);
		} else if (posFacing.equals("E")) {
			if (posX == (galaxySizeX - 1)) {
				newPosX = 0;
			} else {
				newPosX = posX + 1;
			}
			
			checkForEnemies(newPosX, posY);
		} else if (posFacing.equals("S")) {
			if (posY == 0) {
				newPosY = galaxySizeY - 1;
			} else {
				newPosY = posY - 1;
			}
			
			checkForEnemies(posX, newPosY);
		} else if (posFacing.equals("W")) {
			if (posX == 0) {
				newPosX = galaxySizeX - 1;
			} else {
				newPosX = posX - 1;
			}
			
			checkForEnemies(newPosX, posY);
		}
	}
	
	public void moveBackward() {
		// TODO: break up the if statements into functions
		// GalaxySizeX / GalaxySizeY is used in case the size of the galaxy changes
		
		int newPosX;
		int newPosY;
		
		if (posFacing.equals("N")) {
			// If the ship is at 0 in the Y, go back to 99
			if (posY == 0) {
				newPosY = galaxySizeY - 1;
			} else {
				newPosY = posY - 1;
			}
			checkForEnemies(posX, newPosY);
		} else if (posFacing.equals("E")) {
			// If the ship is at 0 in the X, go back to 99
			if (posX == 0) {
				newPosX = galaxySizeX - 1;
			} else {
				newPosX = posX - 1;
			}
			
			checkForEnemies(newPosX, posY);
		} else if (posFacing.equals("S")) {
			// If the ship is at 99 in the Y, go back to 0
			if (posY == (galaxySizeY - 1)) {
				newPosY = 0;
			} else {
				newPosY = posY + 1;
			}
			
			checkForEnemies(posX, newPosY);
		} else if (posFacing.equals("W")) {
			// If the ship is at 99 in the X, go back to 0
			if (posX == (galaxySizeX - 1)) {
				newPosX = 0;
			} else {
				newPosX = posX + 1;
			}
			
			checkForEnemies(newPosX, posY);
		}
	}
	
	public void turnLeft() {
		if (posFacing.equals("N")) {
			changeFacingPos("W");
		} else if (posFacing.equals("E")) {
			changeFacingPos("N");
		} else if (posFacing.equals("S")) {
			changeFacingPos("E");
		} else if (posFacing.equals("W")) {
			changeFacingPos("S");
		}
		
	}
	
	public void turnRight() {
		if (posFacing.equals("N")) {
			changeFacingPos("E");
		} else if (posFacing.equals("E")) {
			changeFacingPos("S");
		} else if (posFacing.equals("S")) {
			changeFacingPos("W");
		} else if (posFacing.equals("W")) {
			changeFacingPos("N");
		}
		
	}
	
	public void checkForEnemies(int x, int y) {
		boolean enemyFound = false;
		int enemyFoundPosX = 0;
		int enemyFoundPosY = 0;
		
		for (int i = 0; i < enemyShipPos.length; i++) {
			if (enemyShipPos[i][0] == x && enemyShipPos[i][1] == y) {
				enemyFound = true;
				enemyFoundPosX = x;
				enemyFoundPosY = y;
				break;
			}
		}
		
		if (enemyFound) {
			String enemyPosReport = "(" +  String.valueOf(enemyFoundPosX) + ";" + String.valueOf(enemyFoundPosY) + ")";
			
			if (!enemyShipFoundPos.contains(enemyPosReport)) {
				enemyShipFoundPos.add(enemyPosReport);
			}
		} else {
			posX = x;
			posY = y;
		}
	}
	
	public void displayArrivalPos() {
		getNewPosText();
	}
	
	public String getNewPosText() {
		String newPosText = "(" + posX + ";" + posY + ";" + posFacing + ")";
		
		if (enemyShipFoundPos.size() > 0) {
			newPosText += enemyShipFoundPos.get(0);
			for (int i = 1; i < enemyShipFoundPos.size(); i++) {
				// If the POS is the same as the previous, don't print it
				if (!enemyShipFoundPos.get(i).equals(enemyShipFoundPos.get(i - 1))) {
					newPosText += enemyShipFoundPos.get(i);
				}
			}
		}
		
		return newPosText;
	}
	
	public void enemyShipsToArray() {
		// The amount of commas, is equal to the amount of POS of enemy ships
		int commaCount = 0;
		for (int i = 0; i < enemyShipPosText.length(); i++) {
			if (Character.toString(enemyShipPosText.charAt(i)).equals(",")) {
				commaCount += 1;
			}
		}
		
		// 2 for the last dimension, since there is only X and Y
		enemyShipPos = new int[commaCount][2];
		
		for (int i = 0; i < enemyShipPos.length; i++) {
			int[] enemyPos = getEnemyPos();
			enemyShipPos[i][0] = enemyPos[0];
			enemyShipPos[i][1] = enemyPos[1];
		}
	}
	
	public int[] getEnemyPos() {
		// Remove the brackets out of each POS and the comma, get the raw values
		String enemyPosValues = enemyShipPosText.substring(enemyShipPosText.indexOf("(") + 1, enemyShipPosText.indexOf(")"));
		String enemyShipPosX = enemyPosValues.split(",")[0].trim();
		String enemyShipPosY = enemyPosValues.split(",")[1].trim();
		
		// The POS has been processed, now remove it so the next can be processed
		enemyShipPosText = enemyShipPosText.replace("(" + enemyPosValues + ")", "");
		
		
		int[] enemyPos = { Integer.parseInt(enemyShipPosX), Integer.parseInt(enemyShipPosY) };
		
		return enemyPos;
	}
	
	public void changeFacingPos(String newFacingPos) {
		posFacing = newFacingPos;
	}
}
