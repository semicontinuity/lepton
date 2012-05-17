Lepton - the language for object literals in Java
================================

Lepton is the laguage, in which you can describe object literals in Java.

Quick example:

To create a window in Swing, it is necessary to write a lot of java code.

Would it be easier to write something like

	<
	    frame: #() javax.swing.JFrame {defaultCloseOperation: javax.swing.JFrame.EXIT_ON_CLOSE, title: \"Swing demo\"}
	    panel: javax.swing.JPanel
	    important_button: #() javax.swing.JButton {preferredSize: java.awt.Dimension(250, 150) foreground: java.awt.Color.RED}
	    regular_button: #() javax.swing.JButton {preferredSize: java.awt.Dimension(250, 150) foreground: java.awt.Color.GREEN}
	    vbox: javax.swing.Box.createVerticalBox
	>
	frame {
	    title: "layout"
	    contentPane: vbox {
	        vbox {
	            important_button {text: "text1"}
	            important_button {text: "text2"}
	            important_button {text: "text3"}
	        }
	        hbox {
	            regular_button {text: "text4"}
	            regular_button {text: "text5"}
	            regular_button {text: "text6"}
	        }
	    }
	}


This is one big object literal, describing the whole JFrame.
And it is not necessary to repeat youself. To cut down lines of code, you can define a macro first, like "red_button", and use it immediately.


Lepton is a good fit many cases, where xml and json are used nowadays.
For instance, for configuration files or for network message payload.
In the example above, the header with internal stuff can be kept inside the program, leaving the clean resource file

	frame {
	    title: "layout"
	    contentPane: vbox {
	        vbox {
	            important_button {text: "text1"}
	            important_button {text: "text2"}
	            important_button {text: "text3"}
	        }
	        hbox {
	            regular_button {text: "text4"}
	            regular_button {text: "text5"}
	            regular_button {text: "text6"}
	        }
	    }
	}



Why use Lepton, not XML or JSON?
XML looks ugly. JSON is untyped, what Java object to create? And you have to repeat yourself.


From Java, Lepton literals can be transformed to Java objects with Scripting API, see [an example](https://github.com/semicontinuity/lepton/blob/master/neutrino-samples/src/neutrino/script/SimpleDemo.java)
