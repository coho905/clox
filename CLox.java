package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


// define overall architecture
public class CLox {

    static boolean hadError = false;  

    public static void main(String[] args) throws IOException {
    // too many args
    if (args.length > 1) {
      System.out.println("Usage: jlox [script]");
      System.exit(64); 
    //execute a file
    } else if (args.length == 1) {
      runFile(args[0]);
    //execute interpreter
    } else {
      runPrompt();
    }
  }
  
  // run interpreter for source code
  public static void runFile(String path) throws IOException {
    //read in from file
    byte [] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));
    if (hadError){
      System.exit(65);
    }
  }
  
  // run interactive shell interpreter
  public static void runPrompt() throws IOException {
    //read in user input
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);
    //do so until EOF
    for(;;){
      System.out.println("> ");
      String line = reader.readLine();
      if (line == null){
        break;
      }
      run(line);
      hadError = false;
    }
  }

  //actual interpreter
  public static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();
    //placeholder
    for (Token token: tokens){
      System.out.println(token);
    }
  }
  
  // TODO make an error reporting interface
  static void error(int line, String message) {
    report(line, " ", message);
  }

  private static void report(int line, String where, String message){
    System.err.println("[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }


}


