package com.example.virtualmusicpad;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.FileNameMap;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Soundpack {
    private final String name;
    private final String path;
    //public HashMap<Key, String> binding; - x
    public HashMap<String, String> binding;

    /**
     * Create a new soundpack
     * @param name name of the soundpack
     */
    public Soundpack(String name) {
        this.name = name;
        this.path = MusicApp.soundpacksFolder.getPath() + File.separator + name;
        this.binding = new HashMap<>();

        File bindingsFile = new File(path + File.separator + "bindings.xml");
        if (bindingsFile.exists())
            loadXML(bindingsFile);
        else
            createXML();

        // if there is no binding
        if (this.binding.isEmpty()) {
            List<String> availableSounds = getAvailableSounds();
            List<String> keys = Arrays.asList("E", "R", "T", "Y", "U", "I",
                    "O", "P", "OPEN_BRACKET", "CLOSE_BRACKET", "X", "C", "V", "B", "N", "M", "COMMA", "PERIOD", "Q", "W");

            for (int i = 0; i < availableSounds.size(); i++) {
                addBinding(keys.get(i), availableSounds.get(i));
            }
        }
    }

    /**
     * Add a binding to the soundpack
     * If the key value is already associated, it will be replaced
     * @param keyValue
     * @param soundName
     */
    public void addBinding(String keyValue, String soundName) {
        //File soundFile = new File(path + File.separator + soundName);
        //binding.put(new Key(keyValue), new Sound(soundFile));

        //binding.put(new Key(keyValue), soundName); - x
        binding.put(keyValue, soundName);

        updateXML(keyValue, soundName);
    }

    /**
     * Play the sound associated to the given key value
     * If no sound associated, no sound played
     * @param keyValue
     */
    public void play(String keyValue) {
        /*Sound sound = binding.get(new Key(keyValue));
        System.out.println(sound);

        if (sound != null)
            sound.play();*/
    }

    /**
     * Get all the sounds available in the soundpack
     * @return sounds name
     */
    public List<String> getAvailableSounds() {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory())
            throw new IllegalArgumentException(folder.getAbsolutePath() + " doesn't exist or not a directory");
        return Arrays.stream(folder.listFiles((d, n) -> n.endsWith(".wav") || n.endsWith(".mp3")))
                .map(File::getName).toList();
    }

    /**
     * Return the name of the Soundpack
     * @return Soundpack's name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the path of the Soundpack
     * @return Soundpack's path
     */
    public String getPath() { return path; }

    private void loadXML(File bindingsFile) {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(bindingsFile);
            NodeList bindings = document.getElementsByTagName("binding");

            for (int i = 0; i < bindings.getLength(); i++) {
                Element binding = (Element) bindings.item(i);

                String keyValue = binding.getAttribute("key");
                String soundPath = binding.getAttribute("sound");

                //this.binding.put(new Key(keyValue), new Sound(soundPath));
                //this.binding.put(new Key(keyValue), soundPath); - x

                this.binding.put(keyValue, soundPath);


            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateXML(String keyValue, String soundName) {
        File bindingsFile = new File(path + File.separator + "bindings.xml");

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(bindingsFile);

            Element root = (Element) document.getElementsByTagName("soundpack").item(0);

            // If it exists update the binding
            boolean updated = false;
            NodeList bindings = root.getElementsByTagName("binding");
            for (int i = 0; i < bindings.getLength(); i++) {
                Element binding = (Element) bindings.item(i);

                if (binding.getAttribute("key").equals(keyValue)) {
                    binding.setAttribute("sound", soundName);
                    updated = true;
                    break;
                }

            }

            // If not update add a new binding
            if (!updated) {
                Element newBinding = document.createElement("binding");

                newBinding.setAttribute("key", keyValue);
                newBinding.setAttribute("sound", soundName);

                root.appendChild(newBinding);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(bindingsFile);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private void createXML() {
        File bindingsFile = new File(path + File.separator + "bindings.xml");

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("soundpack");
            document.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(bindingsFile);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    /*private class Key {
        private final String keyValue;

        public Key(String keyValue) {
            this.keyValue = keyValue;
        }

        @Override
        public String toString() {
            return this.keyValue;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof  Key) &&
                    keyValue.equals(((Key) obj).keyValue);
        }

        @Override
        public int hashCode() {
            return keyValue.hashCode();
        }
    }*/
}
