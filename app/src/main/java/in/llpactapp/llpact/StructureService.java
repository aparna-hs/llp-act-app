package in.llpactapp.llpact;

import android.content.Context;

import java.io.File;
import java.util.List;

public class StructureService {

   private boolean isFolder;
   private List<String> contentsPath;
   private List<String> contentsName;
    // gives list of things at any level. And gives if that level is a folder level or a files level

    public LevelData getCurrentLevelData(String sourcePath, Context context) {
        String path = Constants.SOURCE_DIRECTORY + File.separator + sourcePath;
        File docsFolder = new File(context.getFilesDir(), path);
        listFiles(docsFolder.getAbsolutePath());

        LevelData levelData = new LevelData(contentsPath, contentsName, isFolder);
        return levelData;

    }

    public void listFiles(String directoryName){
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();

        if (fList[0].isFile()) {
            this.isFolder = false;
        } else if (fList[0].isDirectory()) {
            this.isFolder = true;
        }

        for (File file: fList) {
            System.out.println(file.getName());
            this.contentsPath.add(file.getAbsolutePath());
            this.contentsName.add(file.getName());
        }
    }
}
