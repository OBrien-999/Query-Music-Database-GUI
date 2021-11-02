package SER322Final;

import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class Music {

    public static void main(String[] args) {

        Connection conn = null;
        String url = "";
        String user = "";
        String pwd = "";
        String driver = "";

        if(args.length == 4) {

            url = args[0];
            user = args[1];
            pwd = args[2];
            driver = args[3];

        }else {

            System.out.println("\nERROR: Incorrect usage of command line");
            System.out.println("USAGE: java Music <url> <user> <pwd> <driver>");
            System.exit(0);

        }

        try {

            System.out.println("\nEstablishing a connection, thank you for your patience.\n");

            // Loading the JDBC driver
            Class.forName(driver);

            // Establishing the connection
            conn = DriverManager.getConnection(url, user, pwd);

            System.out.println("Connection established!\n");

            boolean exit = false;
            int selection = 0;
            Scanner input = new Scanner(System.in);

            System.out.println("Hello and welcome! You now have direct administrative access to the music database. \n");
            System.out.println("WARNING: Editing the database will permanently alter the data. Proceed with caution!\n");

            // Print loop for the main menu
            while(!exit) {

                System.out.println("--== Music Database Editor: Main Menu ==--");
                System.out.println("1: Edit the database");
                System.out.println("2: Search the database");
                System.out.println("3: Exit");
                System.out.println("--======================================--\n");

                if(input.hasNextInt()){ // Error handling, checks if input is an int

                    selection = input.nextInt();
                    input.nextLine();

                    switch(selection){

                        case 1: // Calls method "edit"
                            edit(conn, input);
                            break;
                        case 2: // Calls method "search"
                            search(conn, input);
                            break;
                        case 3: // Exits the loop and closes the program
                            exit = true;
                            break;
                        default: // Error handling, option 1-3 only
                            System.out.println("Please enter 1 to edit the database, 2 to search the database or 3 to exit.");
                            break;


                    }

                }else { // Error handling, int only

                    input.nextLine();
                    System.out.println("Please enter 1 to edit the database, 2 to search the database or 3 to exit.");

                }

            }

        }catch (Exception exc) { // Error handling, connection failed

            System.out.println("The connection to the database could not be established. Please check that you have entered the information correctly.");
            exc.printStackTrace();

        }finally { // System exit

            try {

                System.out.println("\nClosing the connection and exiting... goodbye!");

                // Closing the connection to the database
                if (conn != null)
                    conn.close();

            }catch (SQLException se) {

                se.printStackTrace();

            }

        }

    }

    private static void edit(Connection conn, Scanner input) {

        boolean exit = false;
        boolean correct = false;
        int selection = 0;
        int intInput = 0;
        int songID = 0;
        int songLength = 0;
        int songYear = 0;
        String songName = "";
        String stringInput = "";

        while(!exit) {

            System.out.println("--== Music Database Editor: Edit Menu ==--");
            System.out.println("1: Add songs to the database");
            System.out.println("2: Delete songs from the database");
            System.out.println("3: Edit songs in the database");
            System.out.println("4: Add songs to an album");
            System.out.println("5: Delete song from an album");
            System.out.println("6: Edit an album name");
            System.out.println("7: Add a genre to a song");
            System.out.println("8: Delete a genre from a song");
            System.out.println("9: Edit a genre name");
            System.out.println("10: Add an artist to a song");
            System.out.println("11: Delete an artist from a song");
            System.out.println("12: Edit an artist name");
            System.out.println("13: Add an artist to an album");
            System.out.println("14: Delete an artist");
            System.out.println("15: Delete a genre");
            System.out.println("16: Delete an album");
            System.out.println("17: Go back to the previous menu");
            System.out.println("--======================================--\n");

            if(input.hasNextInt()){ // Error handling, checks if input is an int

                selection = input.nextInt();
                input.nextLine();

                if(selection == 1) { // Adding a song

                    System.out.println("Lets add a song!\n");
                    System.out.println("First, enter the song ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            songID = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an integer value.\n");

                        }

                    }

                    correct = false;
                    System.out.println("\nNext, enter the length of the song: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            songLength = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an integer value.\n");

                        }

                    }

                    correct = false;
                    System.out.println("\nNow, enter the year the song was released: ");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            songYear = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an integer value.\n");

                        }

                    }

                    correct = false;
                    System.out.println("\nLastly, enter the name of the song: \n");

                    while(!correct) {

                        if (input.hasNextLine()) {

                            songName = input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter a string value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Insert Into SONG(song_id, length, release_year, title) Values(?, ?, ?, ?)");
                        statement.setInt(1, songID);
                        statement.setInt(2, songLength);
                        statement.setInt(3, songYear);
                        statement.setString(4, songName);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe song has been added successfully!\n");

                        }else {

                            System.out.println("\nFailed to add the song. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 2) { // Deleting a song

                    correct = false;
                    System.out.println("\nLets delete a song!");
                    System.out.println("Enter the name of the song: \n");

                    while(!correct) {

                        if (input.hasNextLine()) {

                            stringInput = input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter a string value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Delete From SONG Where title = ?");
                        statement.setString(1, stringInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe song has been deleted successfully!\n");

                        }else {

                            System.out.println("\nFailed to delete the song. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 3) { // Editing a song

                    correct = false;
                    System.out.println("\nLets edit a song!\n");
                    System.out.println("What would you like to edit?");
                    System.out.println("1: The song ID");
                    System.out.println("2: The length of a song");
                    System.out.println("3: The year the song was released");
                    System.out.println("4: The name of the song");
                    System.out.println("5: Go back to the previous menu\n");

                    while(!correct) {

                        if(input.hasNextInt()) {

                            intInput = input.nextInt();
                            input.nextLine();

                            inputLoop: while(true) {

                                System.out.println("\nEnter the name of the song to change: \n");

                                innerInputLoop: while(true) {

                                    if(input.hasNextLine()) {

                                        stringInput = input.nextLine();
                                        break innerInputLoop;

                                    }else {

                                        input.nextLine();
                                        System.out.println("\nPlease enter a string value.\n");

                                    }

                                }

                                switch(intInput) {

                                    case 1:
                                        System.out.println("\nEnter the new song ID: \n");

                                        innerInputLoop: while(true) {

                                            if (input.hasNextInt()) {

                                                intInput = input.nextInt();
                                                input.nextLine();

                                                break innerInputLoop;

                                            } else {

                                                input.nextLine();
                                                System.out.println("\nPlease enter an integer value.\n");

                                            }

                                        }

                                        try {

                                            PreparedStatement statement = conn.prepareStatement("Update SONG Set song_id = ? Where title = ?");
                                            statement.setInt(1, intInput);
                                            statement.setString(2, stringInput);

                                            int result = statement.executeUpdate();

                                            if(result == 1){

                                                System.out.println("\nThe song ID has been changed successfully!\n");

                                            }else {

                                                System.out.println("\nFailed to change song ID. Please retry.\n");

                                            }

                                        }catch (Exception exc) {

                                            exc.printStackTrace();

                                        }

                                        break inputLoop;
                                    case 2:
                                        System.out.println("\nEnter the new length of the song: \n");

                                        innerInputLoop: while(true) {

                                            if (input.hasNextInt()) {

                                                intInput = input.nextInt();
                                                input.nextLine();

                                                break innerInputLoop;

                                            } else {

                                                input.nextLine();
                                                System.out.println("\nPlease enter an integer value.\n");

                                            }

                                        }

                                        try {

                                            PreparedStatement statement = conn.prepareStatement("Update SONG Set length = ? Where title = ?");
                                            statement.setInt(1, intInput);
                                            statement.setString(2, stringInput);

                                            int result = statement.executeUpdate();

                                            if(result == 1){

                                                System.out.println("\nThe song length has been changed successfully!\n");

                                            }else {

                                                System.out.println("\nFailed to change song length. Please retry.\n");

                                            }

                                        }catch (Exception exc) {

                                            exc.printStackTrace();

                                        }

                                        break inputLoop;
                                    case 3:
                                        System.out.println("\nEnter the new release year of the song: \n");

                                        innerInputLoop: while(true) {

                                            if (input.hasNextInt()) {

                                                intInput = input.nextInt();
                                                input.nextLine();

                                                break innerInputLoop;

                                            } else {

                                                input.nextLine();
                                                System.out.println("\nPlease enter an integer value.\n");

                                            }

                                        }

                                        try {

                                            PreparedStatement statement = conn.prepareStatement("Update SONG Set release_year = ? Where title = ?");
                                            statement.setInt(1, intInput);
                                            statement.setString(2, stringInput);

                                            int result = statement.executeUpdate();

                                            if(result == 1){

                                                System.out.println("\nThe song release year has been changed successfully!\n");

                                            }else {

                                                System.out.println("\nFailed to change song release year. Please retry.\n");

                                            }

                                        }catch (Exception exc) {

                                            exc.printStackTrace();

                                        }

                                        break inputLoop;
                                    case 4:
                                        System.out.println("\nEnter the new name of the song: \n");

                                        innerInputLoop: while(true) {

                                            if (input.hasNextLine()) {

                                                songName = input.nextLine();

                                                break innerInputLoop;

                                            } else {

                                                input.nextLine();
                                                System.out.println("\nPlease enter a string value.\n");

                                            }

                                        }

                                        try {

                                            PreparedStatement statement = conn.prepareStatement("Update SONG Set title = ? Where title = ?");
                                            statement.setString(1, songName);
                                            statement.setString(2, stringInput);

                                            int result = statement.executeUpdate();

                                            if(result == 1){

                                                System.out.println("\nThe song name has been changed successfully!\n");

                                            }else {

                                                System.out.println("\nFailed to change song name. Please retry.\n");

                                            }

                                        }catch (Exception exc) {

                                            exc.printStackTrace();

                                        }

                                        break inputLoop;
                                    case 5:
                                        System.out.println("\nReturning to the previous menu...\n");
                                        break inputLoop;
                                    default:
                                        System.out.println("\nPlease enter a valid value (1-4)\n");
                                        break;

                                }

                            }

                            correct = true;

                        }else {

                            input.nextLine();
                            System.out.println("\nPlease enter an integer value.\n");

                        }

                    }

                }else if(selection == 4) {

                    // Add songs to an album
                    correct = false;
                    int intInput2 = 0;
                    System.out.println("\nLets add a song to an album!");
                    System.out.println("\nBefore doing this, please select option 1 in the previous menu to add a song.");
                    System.out.println("Enter the song ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nNow enter the album ID of the album you would like to add the song to: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput2 = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nFinally, enter the album name: \n");

                    while(!correct) {

                        if (input.hasNextLine()) {

                            stringInput = input.nextLine();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter a string value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Insert Into ALBUM(album_id, album_name, song_id) Values(?, ?, ?)");
                        statement.setInt(1, intInput2);
                        statement.setString(2, stringInput);
                        statement.setInt(3, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe song has been added to the album successfully!\n");

                        }else {

                            System.out.println("\nFailed to add the song to the album. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 5) {

                    correct = false;
                    System.out.println("\nLets delete a song from an album!");
                    System.out.println("Enter the song ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Delete From ALBUM Where song_id = ?");
                        statement.setInt(1, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe song has been deleted from the album successfully!\n");

                        }else {

                            System.out.println("\nFailed to delete the song from the album. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 6) {

                    correct = false;
                    System.out.println("\nLets change an albums name!");
                    System.out.println("Enter the album ID you would like to change: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nNow enter the new name of the album: \n");

                    while(!correct) {

                        if (input.hasNextLine()) {

                            stringInput = input.nextLine();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter a string value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Update ALBUM Set album_name = ? Where album_id = ?");
                        statement.setString(1, stringInput);
                        statement.setInt(2, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe album has been renamed successfully!\n");

                        }else {

                            System.out.println("\nFailed to rename the album. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 7) {

                    correct = false;
                    int intInput2 = 0;
                    System.out.println("\nLets add a genre to a song!");
                    System.out.println("Enter the genre ID to add to a song: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nEnter the song ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput2 = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Insert Into GENRE_SONG(song_id, genre_id) Values(?, ?)");
                        statement.setInt(1, intInput2);
                        statement.setInt(2, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe genre has been added to the song successfully!\n");

                        }else {

                            System.out.println("\nFailed to add the genre to the song. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 8) {

                    correct = false;
                    int intInput2 = 0;
                    System.out.println("\nLets delete a genre from a song!");
                    System.out.println("Enter the song ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nEnter the genre ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput2 = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Delete From GENRE_SONG Where song_id = ? And genre_id = ?");
                        statement.setInt(1, intInput);
                        statement.setInt(2, intInput2);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe genre has been deleted from the song successfully!\n");

                        }else {

                            System.out.println("\nFailed to delete the genre from the song. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 9) {

                    correct = false;
                    System.out.println("\nLets change a genres name!");
                    System.out.println("Enter the genre ID you would like to change: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nNow enter the new name of the genre: \n");

                    while(!correct) {

                        if (input.hasNextLine()) {

                            stringInput = input.nextLine();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter a string value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Update GENRE Set genre_name = ? Where genre_id = ?");
                        statement.setString(1, stringInput);
                        statement.setInt(2, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe genre has been renamed successfully!\n");

                        }else {

                            System.out.println("\nFailed to rename the genre. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 10) {

                    correct = false;
                    int intInput2 = 0;
                    System.out.println("\nLets add an artist to a song!");
                    System.out.println("Enter the artist ID to add to a song: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nEnter the song ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput2 = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Insert Into SONG_ARTIST(song_id, artist_id) Values(?, ?)");
                        statement.setInt(1, intInput2);
                        statement.setInt(2, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe artist has been added to the song successfully!\n");

                        }else {

                            System.out.println("\nFailed to add the artist to the song. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 11) {

                    correct = false;
                    int intInput2 = 0;
                    System.out.println("\nLets delete an artist from a song!");
                    System.out.println("Enter the song ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nEnter the artist ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput2 = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Delete From SONG_ARTIST Where song_id = ? And artist_id = ?");
                        statement.setInt(1, intInput);
                        statement.setInt(2, intInput2);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe artist has been deleted from the song successfully!\n");

                        }else {

                            System.out.println("\nFailed to delete the artist from the song. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 12) {

                    correct = false;
                    String stringInput2 = "";
                    System.out.println("\nLets change a genres name!");
                    System.out.println("Enter the artist ID you would like to change: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nNow, enter the new first name of the artist: \n");

                    while(!correct) {

                        if (input.hasNextLine()) {

                            stringInput = input.nextLine();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter a string value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nFinally, enter the new last name: \n");

                    while(!correct) {

                        if (input.hasNextLine()) {

                            stringInput2 = input.nextLine();
                            input.nextLine();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter a string value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Update ARTIST Set first_name = ?, last_name = ? Where artist_id = ?");
                        statement.setString(1, stringInput);
                        statement.setString(2, stringInput2);
                        statement.setInt(3, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe artist has been renamed successfully!\n");

                        }else {

                            System.out.println("\nFailed to rename the artist. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 13) {

                    correct = false;
                    int intInput2 = 0;
                    System.out.println("\nLets add an artist to an album!");
                    System.out.println("Enter the artist ID to add to an album: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    correct = false;

                    System.out.println("\nEnter the album ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput2 = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an int value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Insert Into ARTIST_ALBUM(arist_id, album_id) Values(?, ?)");
                        statement.setInt(1, intInput);
                        statement.setInt(2, intInput2);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe artist has been added to the album successfully!\n");

                        }else {

                            System.out.println("\nFailed to add the artist to the album. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 14) {

                    correct = false;
                    System.out.println("\nLets delete an artist!");
                    System.out.println("Enter the artist ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an integer value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Delete From ARTIST Where artist_id = ?");
                        statement.setInt(1, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe artist has been deleted successfully!\n");

                        }else {

                            System.out.println("\nFailed to delete the artist. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 15) {

                    correct = false;
                    System.out.println("\nLets delete a genre!");
                    System.out.println("Enter the genre ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an integer value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Delete From GENRE Where genre_id = ?");
                        statement.setInt(1, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe genre has been deleted successfully!\n");

                        }else {

                            System.out.println("\nFailed to delete the genre. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 16) {

                    correct = false;
                    System.out.println("\nLets delete an album!");
                    System.out.println("Enter the album ID: \n");

                    while(!correct) {

                        if (input.hasNextInt()) {

                            intInput = input.nextInt();

                            correct = true;

                        } else {

                            input.nextLine();
                            System.out.println("\nPlease enter an integer value.\n");

                        }

                    }

                    try {

                        PreparedStatement statement = conn.prepareStatement("Delete From ALBUM Where album_id = ?");
                        statement.setInt(1, intInput);

                        int result = statement.executeUpdate();

                        if(result == 1){

                            System.out.println("\nThe album has been deleted successfully!\n");

                        }else {

                            System.out.println("\nFailed to delete the album. Please retry.\n");

                        }

                    }catch (Exception exc) {

                        exc.printStackTrace();

                    }

                }else if(selection == 17) { // Exiting to the previous menu

                    System.out.println("\nReturning to the main menu...\n");
                    exit = true;

                }else { // Error handling, 1-4 not entered

                    input.nextLine();
                    System.out.println("\nPlease enter a valid value (1-17).\n");

                }

            }else { // Error handling, int not entered

                input.nextLine();
                System.out.println("\nPlease enter a valid value (1-17).\n");

            }

        }

    }

    private static void search(Connection conn, Scanner input) {

        boolean exit = false;
        boolean correct = false;
        int selection = 0;
        int intInput = 0;
        int intInput2 = 0;
        String stringInput = "";

        while(!exit) {

            System.out.println("\n--== Music Database Editor: Search Menu ==--");
            System.out.println("1: Search for songs by artist");
            System.out.println("2: Search for songs by album");
            System.out.println("3: Search for songs by year");
            System.out.println("4: Search for songs by genre");
            System.out.println("5: Search for songs by length");
            System.out.println("6: Search for a song by artist & album");
            System.out.println("7: Search for a song by artist & year");
            System.out.println("8: Search for a song by artist & genre");
            System.out.println("9: Search for a song by artist & length");
            System.out.println("10: Go back to the main menu");
            System.out.println("--========================================--\n");

            if(input.hasNextInt()) {

                selection = input.nextInt();
                input.nextLine();

                switch(selection){

                    case 1:
                        inputLoop: while(true) {

                            System.out.println("\nEnter the artist ID: \n");

                            if(input.hasNextInt()){

                                intInput = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            }else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG Join SONG_ARTIST On SONG.song_id = SONG_ARTIST.song_id where artist_id = ?");
                            statement.setInt(1, intInput);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs Created By Artist ID " + intInput);

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 2:
                        inputLoop: while(true) {

                            System.out.println("\nEnter the album name: \n");

                            if(input.hasNextLine()){

                                stringInput = input.nextLine();

                                break inputLoop;

                            }else {

                                System.out.println("\nPlease enter a string value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG Join ALBUM On SONG.song_id = ALBUM.song_id where album_name = ?");
                            statement.setString(1, stringInput);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs Listed On " + "\"" + stringInput + "\"");

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 3:
                        inputLoop: while(true) {

                            System.out.println("\nEnter the year: \n");

                            if (input.hasNextInt()) {

                                intInput = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            } else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG where release_year = ?");
                            statement.setInt(1, intInput);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs Released In Year " + intInput);

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 4:
                        inputLoop: while(true) {

                            System.out.println("\nEnter the genre name: \n");

                            if(input.hasNextLine()){

                                stringInput = input.nextLine();

                                break inputLoop;

                            }else {

                                System.out.println("\nPlease enter a string value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG Join GENRE_SONG On SONG.song_id = GENRE_SONG.song_id Join GENRE On GENRE_SONG.genre_id = GENRE.genre_id where genre_name = ?");
                            statement.setString(1, stringInput);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs By Genre Name " + "\"" + stringInput + "\"");

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 5:
                        inputLoop: while(true) {

                            System.out.println("\nEnter the length: \n");

                            if (input.hasNextInt()) {

                                intInput = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            } else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG where length = ?");
                            statement.setInt(1, intInput);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs With Length " + intInput);

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 6:
                        System.out.println("\nEnter the artist ID: \n");

                        inputLoop: while(true) {

                            if (input.hasNextInt()) {

                                intInput = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            } else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        System.out.println("\nEnter the album name: \n");

                        inputLoop: while(true) {

                            if (input.hasNextLine()) {

                                stringInput = input.nextLine();

                                break inputLoop;

                            } else {

                                System.out.println("\nPlease enter a string value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG Join ALBUM On SONG.song_id = ALBUM.song_id Join ARTIST_ALBUM On ALBUM.album_id = ARTIST_ALBUM.album_id where artist_id = ? and album_name = ?");
                            statement.setInt(1, intInput);
                            statement.setString(2, stringInput);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs Created By Artist ID " + intInput + " On The Album " + "\"" + stringInput + "\"");

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 7:
                        System.out.println("\nEnter the artist ID: \n");

                        inputLoop: while(true) {

                            if (input.hasNextInt()) {

                                intInput = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            } else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        System.out.println("\nEnter the year: \n");

                        inputLoop: while(true) {

                            if (input.hasNextInt()) {

                                intInput2 = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            } else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG Join SONG_ARTIST On SONG.song_id = SONG_ARTIST.song_id where artist_id = ? and release_year = ?");
                            statement.setInt(1, intInput);
                            statement.setInt(2, intInput2);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs Created By Artist ID " + intInput + " In The Year " + intInput2);

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 8:
                        System.out.println("\nEnter the artist ID: \n");

                        inputLoop: while(true) {

                            if (input.hasNextInt()) {

                                intInput = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            } else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        System.out.println("\nEnter the genre name: \n");

                        inputLoop: while(true) {

                            if (input.hasNextLine()) {

                                stringInput = input.nextLine();

                                break inputLoop;

                            } else {

                                System.out.println("\nPlease enter a string value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG Join GENRE_SONG On SONG.song_id = GENRE_SONG.song_id Join GENRE On GENRE_SONG.genre_id = GENRE.genre_id where artist_id = ? and genre_name = ?");
                            statement.setInt(1, intInput);
                            statement.setString(2, stringInput);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs Created By Artist ID " + intInput + " By The Genre " + "\"" + stringInput + "\"");

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 9:
                        System.out.println("\nEnter the artist ID: \n");

                        inputLoop: while(true) {

                            if (input.hasNextInt()) {

                                intInput = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            } else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        System.out.println("\nEnter the length: \n");

                        inputLoop: while(true) {

                            if (input.hasNextInt()) {

                                intInput2 = input.nextInt();
                                input.nextLine();

                                break inputLoop;

                            } else {

                                input.nextLine();
                                System.out.println("\nPlease enter an integer value.\n");

                            }

                        }

                        try {

                            ResultSet result;
                            PreparedStatement statement = conn.prepareStatement("Select title From SONG Join SONG_ARTIST On SONG.song_id = SONG_ARTIST.song_id where artist_id = ? and length = ?");
                            statement.setInt(1, intInput);
                            statement.setInt(2, intInput2);

                            result = statement.executeQuery();

                            System.out.printf("%-22s\n", "\nSongs Created By Artist ID " + intInput + " With A Length Of " + intInput2);

                            while(result.next()) {

                                System.out.printf("%-22s\n", result.getString(1));

                            }

                        }catch (Exception exc) {

                            exc.printStackTrace();

                        }

                        break;
                    case 10:
                        System.out.println("\nReturning to the main menu...\n");
                        exit = true;
                        break;
                    default:
                        System.out.println("\nPlease enter a valid integer value (1-10)\n");
                        break;

                }

            }else {

                input.nextLine();
                System.out.println("\nPlease enter a valid integer value (1-10)\n");

            }

        }

    }

}
