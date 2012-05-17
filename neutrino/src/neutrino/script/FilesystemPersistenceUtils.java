package neutrino.script;

import neutrino.model.FunctionCallLiteral;
import neutrino.model.FunctionDeclarationLiteral;
import neutrino.model.Evaluable;

import java.io.*;
import java.util.HashMap;

public class FilesystemPersistenceUtils {
    static final String FILE_SUFFIX = ".ol";

    static boolean isRecognizedDirectoryName(final String fileName) {
        return fileName.indexOf('.') == -1;
    }

    static String stripExtension(final String name) {
        final int index = name.lastIndexOf('.');
        if (index == -1 || index == 0) throw new IllegalArgumentException();
        return name.substring(0, index);
    }

    public static Evaluable loadLiteralFromFile(final File file) {
        InputStreamReader inputStreamReader = null;
        try {
            final InputStream inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream);
            return ScriptEngineImpl.readLiteral(inputStreamReader);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e1) {
                    //
                }
            }
        }
    }


    static HashMap<String, Evaluable> load(final File directory) {
//        System.out.println("Scan and load *.ol files in " + directory);
        final HashMap<String, Evaluable> literals = new HashMap<String, Evaluable>();
        //noinspection ResultOfMethodCallIgnored
        final File[] directories = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathName) {
                final String fileName = pathName.getName();
                if (pathName.isFile() && fileName.endsWith(FILE_SUFFIX)) {
                    literals.put(
                        stripExtension(fileName),
                        loadLiteralFromFile(pathName));
                    return false;
                } else if (pathName.isDirectory() && isRecognizedDirectoryName(fileName)) {
//                    System.out.println("*** Will process folder " + pathName);
                    return true;
                }
                return false;
            }
        });

        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < directories.length; i++) {
            final File subFolder = directories[i];
            final String name = subFolder.getName();
            final Evaluable literal = literals.get(name);
            if (literal == null) {
                literals.put(name, new DirectoryBindingsLiteral(subFolder));
            } else {
                // add initializer to existing literal : must be func. declaration returning func. call
                if (literal instanceof FunctionDeclarationLiteral) {
                    final Evaluable<?> returnValue = ((FunctionDeclarationLiteral) literal).returnValue;
                    if (!(returnValue instanceof FunctionCallLiteral)) throw new IllegalArgumentException();
                    ((FunctionCallLiteral) returnValue).getInitializers().add(new DirectoryListLiteral(subFolder));
                } else {  // TODO
                    throw new IllegalArgumentException();
                }
            }
        }

//        System.out.println("*** Loaded: " + literals.keySet());
        return literals;
    }
}
