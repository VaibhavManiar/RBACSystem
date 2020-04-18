package org.resource.file.impl;

import org.auth.Resource;
import org.auth.model.IdGenerator;
import org.resource.file.Creatable;
import org.resource.file.Deletable;
import org.resource.file.Readable;
import org.resource.file.Writable;
import org.resource.file.exception.FileNotFoundException;
import org.resource.file.exception.InvalidURLException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class FileResource implements Resource, Readable, Writable, Deletable, Creatable {

    private final String id;

    private final URL url;

    public FileResource(URL url) {
        String resourceType = "FILE";
        this.url = url;
        this.id = IdGenerator.generateResourceId(resourceType, getURL().toString());
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public URL getURL() {
        return this.url;
    }

    @Override
    public boolean create() {
        try {
            Path filePath = getPath();
            Path dir = getDirectoryPath(filePath);
            if(Files.notExists(dir)) {
                Files.createDirectories(dir);
            }
            if(Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
            return Files.exists(filePath);
        } catch (URISyntaxException e) {
            throw new InvalidURLException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage(), e);
        }
    }

    private Path getPath() throws URISyntaxException {
        return Paths.get("/"+url.toURI().getHost()  + url.getPath());
    }

    private Path getDirectoryPath(Path filePath) {
        String[] dirs = filePath.toString().split("/");
        StringBuilder dir = new StringBuilder("/");
        for(int index=0;index<dirs.length-1;index++) {
            dir.append(dirs[index]+"/");
        }
        return Paths.get(dir.toString());
    }

    @Override
    public boolean delete() {
        try {
            Files.deleteIfExists(getPath());
            return Boolean.TRUE;
        } catch (URISyntaxException e) {
            throw new InvalidURLException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public boolean write(String data) {
        try {
            Files.write(getPath(), data.getBytes());
            return Boolean.TRUE;
        } catch (URISyntaxException e) {
            throw new InvalidURLException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] read() {
        try {
            return Files.readAllBytes(getPath());
        } catch (URISyntaxException e) {
            throw new InvalidURLException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage(), e);
        }
    }
}
