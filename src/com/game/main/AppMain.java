package com.game.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class AppMain {

	private static Scanner sc = new Scanner(System.in);
	private static int level = 0;
	private static String inputChar;
	private static String loadedWordList = "";
	private static String originalSelectedWord = "";
	private static String currentWord = "";
	private static int lives = 5;
	private static Set<Character> guessedChars = new HashSet<Character>();
	
	public static void main(String[] args) {
		
		System.out.println("Hello! To start the game choose the difficulty level \n1 for EASY \n2 for MEDIUM \n3 for DIFFICULT");
		getLevel();
		
		loadWords();
		System.out.println(loadedWordList);
		
		selectRandomWord();
		
		while(lives > 0 && !currentWord.equals(originalSelectedWord)) {
			getInputChar();
		}
		if(lives == 0) {
			System.out.println("Sorry!! No more lives left.\nThe word was "+originalSelectedWord);
		}else {
			System.out.println("You have guessed the word correctly. The word was " + originalSelectedWord);
		}
	}
	
	//print the word with the * and chars that has been guessed correctly
	private static void buildCurrentWord(Character inChar) { 
		String temp = "";
		int index = 0;
		for(Character c: originalSelectedWord.toCharArray()) {
			if(c == inChar) {
				temp = temp + c.toString();
			}else {
				temp = temp + currentWord.charAt(index);
			}
			index++;
		}
		currentWord = temp;
		System.out.println("Correct : " + currentWord);
		
	}
	
	
	//Method to take input from the user
	private static void getInputChar() {
		inputChar = sc.next();
		char inChar = (char) inputChar.charAt(0);
		
		if(guessedChars.contains(inChar)) {
			System.out.println("You have already tried this letter");
			getInputChar();
		}else {
			guessedChars.add(inChar);
			if(originalSelectedWord.contains(inputChar)) {
				buildCurrentWord(inChar);
			}else {
				--lives;
				System.out.println(String.format("Incorrect. 1 life lost. You have %s lives remaining. The current word is %s", lives, currentWord));
			}
			
		}
		
	}

	//Method to take difficulty level from user
	private static void getLevel() {
		level= sc.nextInt();
	}
	
	//Load words from properties file based on level selected by user
	private static void loadWords() {
		try {
			Properties properties = new Properties();
			InputStream inputStream = AppMain.class.getResourceAsStream("/wordlist.properties");
			
			properties.load(inputStream);
			
			if(level == 1) {
				loadedWordList = properties.getProperty("easy");
			}
			else if(level == 2) {
				loadedWordList = properties.getProperty("medium");
			}
			else if(level == 3) {
				loadedWordList = properties.getProperty("difficult");
			}else {
				System.out.println("Invalid choice");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Select a random word from the choice of level
	private static void selectRandomWord() {
		String[] wordList = loadedWordList.split(",");
		originalSelectedWord = wordList[(int) (Math.random() * wordList.length)];
		currentWord = "*".repeat(originalSelectedWord.length());
	}
}