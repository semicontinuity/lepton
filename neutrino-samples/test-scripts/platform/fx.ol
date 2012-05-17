<
    javafx.scene.paint.Color
    javafx.scene.text.FontWeight
>
(
    application:    gearbox.javafx.adapter.FxApplication
    stage:          javafx.stage.Stage
    scene:          javafx.scene.Scene
    group:          javafx.scene.Group

    text:   javafx.scene.text.Text
    font:   javafx.scene.text.Font.font

    path: javafx.scene.shape.Path
    m:javafx.scene.shape.MoveTo
    l:javafx.scene.shape.LineTo

    playAnimation:  gearbox.javafx.adapter.FxAnimationPlayer

    ms:                     javafx.util.Duration.valueOf
    fadeTransition:         javafx.animation.FadeTransition
    sequentialTransition:   javafx.animation.SequentialTransition
    rotateTransition:       javafx.animation.RotateTransition
    translateTransition:    javafx.animation.TranslateTransition
)