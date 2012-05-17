#()
<
    in1: bit()
    in2: bit()
    out: bit()
>
[
    nand (in1:in1, in2:in2, out:out)
    frame {
        title: "NAND"
        contentPane: panel {
            toggle(signal:in1, text:"In 1")
            toggle(signal:in2, text:"In 2")
            indicator(signal:out, text:"Out")
        }
    }
]
