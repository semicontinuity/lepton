package neutrino.script;

import neutrino.model.Evaluable;
import neutrino.model.ListLiteral;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DirectoryListLiteral<E> extends ListLiteral<E> {

    private final File directory;
    private transient boolean loaded;

    public DirectoryListLiteral(final File directory) {this.directory = directory;}

    @Override
    public List<? extends Evaluable<E>> getElements() {
        if (!loaded) {
            final HashMap<String, Evaluable> load = FilesystemPersistenceUtils.load(directory);
            final Collection<Evaluable> values = load.values();
            final List<Evaluable<E>> elements1 = (List<Evaluable<E>>) elements;
            for (Evaluable<E> value : values) {
                elements1.add(value);
            }

            loaded = true;
        }
        return super.getElements();
    }
}
