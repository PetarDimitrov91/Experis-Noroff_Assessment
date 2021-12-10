import core.Engine;

import java.io.IOException;

public class Main {
    /*
    Hi!  if you want to start the program properly,
    maybe you must change the path matching the directory of the file on your computer.
    You will find the path variables in the Engine class.


   Quick User guide:

   When you run the main method, the terminal will open. You can type different commands, depending on what you want to see.
   If you want to see the “recent popular products” from the task description, just type "recent N" where N is the number of movies that you want to see.
   If you type only "recent" there will be shown 3 Movies
   If you want to see the recommended movie for a user, just type "personal". Then type the name of the user.
   For example, type "Olav", and the recommended movie will be shown. If you want to see the “recent popular products” again, you must "back".

   If you want to stop the application, type "end"

   The application works case-insensitive.

   Best Regards
   Petar
   */

    public static void main(String[] args) {
        Engine engine;

        try {
            engine = new Engine();
        } catch (IOException e) {
            System.out.println("Exception occurs when trying to read from file, please check the path of the source file!");
            return;
        }

        engine.run();
    }

}
