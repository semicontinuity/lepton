package neutrino.script;

import java.util.concurrent.Executor;

public class SimpleExecutor implements Executor {
    @Override
    public void execute(final Runnable command) {
        command.run();
    }
}
