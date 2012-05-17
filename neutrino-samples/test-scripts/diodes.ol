#()
application {
    primaryStage: stage {
        title: "Diodes"
        scene: scene(
            group {
                children: [
                    diode {translateX:375d, translateY:75d, stroke:YELLOW }
                    diode {translateX:275d, translateY:75d, stroke:STEELBLUE }
                    diode {translateX:175d, translateY:75d, stroke:RED }
                    diode {translateX:75d, translateY:75d, stroke:GREEN }
                ]
            }
        )
    }
}
