#()
<
    d:  bit()
    e:  bit()
    q:  bit()
    qn: bit()
>
[
    d_latch(d:d, e:e, q:q, qn:qn)
    frame {
        title: "D Latch"
        contentPane: panel {
            toggle(signal:d, text:"D")
            toggle(signal:e, text:"E")
            indicator(signal:q,  text:"Q")
            indicator(signal:qn, text:"Qn")
        }
    }
]
