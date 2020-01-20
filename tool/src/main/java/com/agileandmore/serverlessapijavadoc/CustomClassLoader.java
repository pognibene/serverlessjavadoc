package com.agileandmore.serverlessapijavadoc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pascal //mFIXME ay have problem in case of inner classes??? will
 * inner classes be loaded as well as part of the main class, or not? maybe
 * should build an aggregate classpath to load everything the 'standard' way?
 */
public class CustomClassLoader extends ClassLoader {

    private String fullPath;
    
    CustomClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> findClass(String name) {
        
        //peut etre que ça marche seulement a partir d'un urlclassloader?

        System.out.println("trying to load class " + fullPath);
        //FIXME j'ai besoin du nom de la classe
        //mais aussi du chemin complet
        //egalement : comment charger les nested class si besoin? Automatique?
        //ou bien charger la classe avec ASM pour extraire son nom et son chemin?
        //(note : les fichiers java peuvent resulter en plusieurs fichiers class...)
        //si je charge avec ASM : je charge le binaire en mémoire
        //puis je trouve le nom de la classe
        //ensuite je devrais pouvoir charger la classe avec son nom
        //charger toutes les classes trouvées sans distinction??
        //byte[] b = loadClassFromFile(name);
        byte[] b = loadClassFromFile(fullPath);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String fileName) {

        try {

            return Files.readAllBytes(Paths.get(fileName));
        } catch (IOException ex) {
            Logger.getLogger(CustomClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

}
