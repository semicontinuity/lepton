package neutrino.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.concurrent.Executor;

public class Interpreter {

    public static void main(final String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        if (args.length != 3) {
            System.err.println("Arguments: <executor> <script path> <expression>");
            return;
        }

        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("OL");
        if (engine == null) throw new RuntimeException("Engine not found");


        final Executor executor = (Executor) Class.forName(args[0]).newInstance();

        final String[] path = args[1].split(System.getProperty("path.separator"));

        final PathBindings pathBindings = new PathBindings(path, new JavaBindings());

        final Runnable runnable = new Runnable() {
            public void run() {
                try {
                    String arg = args[2];
                    final Object value = engine.eval(arg, pathBindings);
                    ((Invocable) engine).invokeMethod(value, "start");
                } catch (ScriptException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        executor.execute(runnable);
    }
}
