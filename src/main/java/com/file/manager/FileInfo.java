package com.file.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FileInfo {

    private String name;
    private fileType type;
    private long size;
    private LocalDateTime lastModified;


    public FileInfo(Path path){
        try {
            this.name = path.getFileName().toString();
            this.size = Files.size(path);
            if (Files.isDirectory(path)){
                this.type = fileType.DIRECTORY;
            }
            else{
                this.type=fileType.FILE;
            }

            if (type == fileType.DIRECTORY){
                this.size= -1L;
            }
            this.lastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(path)
                            .toInstant(), ZoneOffset.ofHours(4));





        } catch (IOException e) {
            throw  new RuntimeException("Unable to create file info from path");
        }


    }













    public enum fileType{
        FILE("F"), DIRECTORY("D");
        private String name;

        public String getName() {
            return name;
        }
        fileType(String name){
            this.name = name;
        }
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public fileType getType() {
        return type;
    }

    public void setType(fileType type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }









}
