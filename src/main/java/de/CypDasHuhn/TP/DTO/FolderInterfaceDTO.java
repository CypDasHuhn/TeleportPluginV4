package de.CypDasHuhn.TP.DTO;

public class FolderInterfaceDTO {
    public String directory;
    public String parentName;
    public int page;
    public int slot;
    public boolean moving;

    public FolderInterfaceDTO(String directory, String parentName, int page, int slot, boolean moving) {
        this.directory = directory;
        this.parentName = parentName;
        this.page = page;
        this.slot = slot;
        this.moving = moving;
    }
}
