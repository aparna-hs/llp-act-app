package in.llpactapp.llpact;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StructureService {

   private boolean isFolder;
   private List<String> contentsPath = new ArrayList<>();

   private List<String> contentsName = new ArrayList<>();
    // gives list of things at any level. And gives if that level is a folder level or a files level

    public LevelData getCurrentLevelData(String sourcePath, Context context) {


        listFiles(sourcePath);

        LevelData levelData = new LevelData(contentsPath, contentsName, isFolder);
        return levelData;

    }

    public void listFiles(String directoryName){

        File directory = new File(directoryName);
        //get all the files from a directory
        Log.d("HEREEE",directory.getAbsolutePath());
        File[] fList = directory.listFiles();

        if(fList==null)
            Log.d("HEREE","i m null");

        Log.d("HEREEE",fList[0].getName());

        int check_pos = 0;
        if(fList[0].getName().equals(".gitignore"))
            check_pos = 1;

        if (fList[check_pos].isFile()) {
            Log.d("HEREEE FIL",fList[check_pos].getName());
            this.isFolder = false;
        } else if (fList[check_pos].isDirectory()) {
            Log.d("HEREEE DIR",fList[check_pos].getName());
            this.isFolder = true;
        }

        if(!isFolder)
        {
            //files

            for (File file: fList) {

                if(file.getName().equals(".keep") )
                    continue;
                this.contentsPath.add(file.getAbsolutePath());
                String file_name = file.getName();
                file_name = file_name.substring(0,(file_name.length()-3));
                this.contentsName.add(file_name);

                }

        }
        else
        {
            // folders
            for (File file: fList) {

                if(file.getName().equals(".gitignore"))
                    continue;
                this.contentsPath.add(file.getAbsolutePath());
                this.contentsName.add(file.getName());

            }
        }


    }
}
