package at.spengergasse.sj2324seedproject.presentation.api.commands;

public record StorageObjectCommand(String serialNr,
                                   String mac,
                                   String remark,
                                   String projectDev,
                                   String storedAtCu){
}
