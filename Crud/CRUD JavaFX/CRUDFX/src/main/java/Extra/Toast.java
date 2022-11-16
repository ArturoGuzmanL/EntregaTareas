package Extra;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Toast extends Label {
    private float displayTime = 0.90f;
    private float initialPositon =0;
    private float downBias = 10f;// the distance the label should sit below before appearing

    Interpolator interpolator = new Interpolator() {
        @Override
        protected double curve(double v) {
            //quadratic curve for upward motion
            return -4 * ( v - 0.5 ) * ( v - 0.5 ) +1;
        }
    };
    public Toast(String msg){
        super();
        this.setText(msg);
        this.setOpacity(0);// starting it with zero because we dont want it to show up upoun adding it to the root
        this.setBackground(new Background(new BackgroundFill(Color.WHEAT, CornerRadii.EMPTY, Insets.EMPTY)));// some background cosmetics
        this.setAlignment(Pos.CENTER);
    }
    public void appear(){
        this.setEffect(new DropShadow(20, Color.BLACK));// Shadow
        //creating animation in the Y axis
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(this.displayTime), new KeyValue(this.translateYProperty(),this.initialPositon-this.getMinHeight()-20, interpolator))
        );
        FadeTransition fd  = new FadeTransition(Duration.seconds(this.displayTime),this);
        fd.setCycleCount(1);
        fd.setFromValue(0);
        fd.setToValue(1.0);
        fd.setInterpolator(interpolator);
        fd.play();
        timeline.play();
    }
    public  void  setInitialPositon(float positon){
        this.initialPositon = positon+downBias;
        this.setTranslateY(positon+downBias);
    }
}