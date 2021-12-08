import core.Engine;

import java.io.IOException;

public class Main {
    /*
    Hi!  if you want to start the program properly,
    maybe you must change the directory of the String
    path matching the directory of the file on your computer.
    You will find the path variables in the Engine class.
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
