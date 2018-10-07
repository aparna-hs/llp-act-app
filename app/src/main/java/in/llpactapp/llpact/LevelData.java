package in.llpactapp.llpact;

import java.util.List;

public class LevelData {
    private List<String> levelConstituents;
    private  List<String> levelNames;
    private boolean isFolder;

    public LevelData(List<String> levelConstituents, List<String> levelNames, boolean isFolder) {
        this.levelConstituents = levelConstituents;
        this.levelNames = levelNames;
        this.isFolder = isFolder;
    }

    public List<String> getLevelConstituents() {
        return levelConstituents;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public List<String> getLevelNames() {
        return levelNames;
    }
}
