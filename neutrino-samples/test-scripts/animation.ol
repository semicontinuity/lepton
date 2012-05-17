#()
<
    font:   font("Arial", BOLD, 64d)
>
<
    diode:  diode {stroke:STEELBLUE, translateX:275d, translateY:75d }
    hello:  text {content:"Hello", font:font, x:150d, y:100d, opacity:0d, fill:FORESTGREEN}
    world:  text {content:"World", font:font, x:310d, y:100d, opacity:0d, fill:SIENNA}
>
application {
    primaryStage:
        stage {
            title: "Animation"
            scene: scene( group { children: [ diode, hello, world] }, 550d, 150d)
        }
    onStart:
        playAnimation(
            sequentialTransition {
                children: [
                    fadeTransition      { node:diode, duration:ms(800d), fromValue:0d, toValue:1d }
                    rotateTransition    { node:diode, duration:ms(800d), byAngle:180d }
                    translateTransition { node:diode, duration:ms(800d), byX:-200d }
                    fadeTransition      { node:hello, duration:ms(800d), fromValue:0d, toValue:1d }
                    fadeTransition      { node:world, duration:ms(800d), fromValue:0d, toValue:1d }
                ]
            }
        )
}
