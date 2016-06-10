/**
 * Created by elnaz on 08.06.16.
 */

import java.io.IOException;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        try
        {
            String path = "";
            Indexer indexer = new Indexer(path);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
