#(d e q qn)
<
    sn:bit()
    rn:bit()
>
[
    nand (in1:d,    in2:e,  out:sn)
    nand (in1:sn,   in2:e,  out:rn)
    nand (in1:sn,   in2:qn, out:q)
    nand (in1:q,    in2:rn, out:qn)
]