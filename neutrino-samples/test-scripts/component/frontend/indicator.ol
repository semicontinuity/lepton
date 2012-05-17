#(signal text)
javax.swing.JToggleButton {
    model: gearbox.bindings.adapter.swing.ToggleButtonAdapter(signal)
    enabled: false
    preferredSize: java.awt.Dimension(100, 100)
    text: text
}