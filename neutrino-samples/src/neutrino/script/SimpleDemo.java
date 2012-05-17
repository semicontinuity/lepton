package neutrino.script;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

public class SimpleDemo {

    public static final String script1 =
            "(\n" +
            "    frame: #() javax.swing.JFrame {defaultCloseOperation: javax.swing.JFrame.EXIT_ON_CLOSE, title: \"Swing demo\"}\n" +
            "    panel: javax.swing.JPanel\n" +
            "    red_button: #() javax.swing.JButton {preferredSize: java.awt.Dimension(250, 150) foreground: java.awt.Color.RED}\n" +
            "    green_button: #() javax.swing.JButton {preferredSize: java.awt.Dimension(250, 150) foreground: java.awt.Color.GREEN}\n" +
            "    vbox: javax.swing.Box.createVerticalBox\n" +
            ")";


    public static final String script2 =
            "frame {\n" +
            "    title: \"layout\"\n" +
            "    contentPane: vbox {\n" +
            "        panel {\n" +
            "            red_button {text: \"text1\"}\n" +
            "            red_button {text: \"text2\"}\n" +
            "            red_button {text: \"text3\"}\n" +
            "        }\n" +
            "        panel {\n" +
            "            green_button {text: \"text4\"}\n" +
            "            green_button {text: \"text5\"}\n" +
            "            green_button {text: \"text6\"}\n" +
            "        }\n" +
            "    }\n" +
            "}\n";


    public static void main(String[] args) throws ScriptException {
        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("OL");
        if (engine == null) throw new RuntimeException("Engine not found");

        final JavaBindings javaBindings = new JavaBindings();
        final Bindings contextBindings = (Bindings) engine.eval(script1, javaBindings);

        final DelegatingBindings bindings = new DelegatingBindings(contextBindings, javaBindings);

        final JFrame frame = (JFrame) engine.eval(script2, bindings);
        frame.setVisible(true);
        frame.pack();
    }
}
