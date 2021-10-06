package fr.romain.pingpong;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import static java.lang.Math.*;

public class PongUtils {

    private static final double UP_LIMIT = 15;
    private static final double BOTTOM_LIMIT = 735;
    private static final double INITIAL_VX = 5.0;
    private static final double INITIAL_VY = 1.0;
    private static final double SPEED_LIMIT = 30;
    private static final double ACCELERATION = 1.05;

    private static double angle = atan2(INITIAL_VY, INITIAL_VX);
    private static double magnitude = sqrt(26); // racine carrée de (INI_VX au carré + INI_VY)

    private static double deltaX = magnitude * cos(angle);
    private static double deltaY = magnitude * sin(angle);

    private static SimpleIntegerProperty score = new SimpleIntegerProperty(0);

    public static void handlePlayer(Rectangle player, double velY) {
        // Permet de mettre à jour la position du joueur ET de ne pas sortir du board
        // (CONDITION ? TRUE : FALSE)
        player.setY(player.getY() >= 0 && player.getY() <= 600 ? player.getY() + velY : player.getY());
    }

    public static void updateGame(Rectangle player, Rectangle computer, Circle ball) {
        //ball.setCenterX(ball.getCenterX() + 4); ----> Pour essayer
        ball.setCenterX(ball.getCenterX() + deltaX);
        ball.setCenterY(ball.getCenterY() + deltaY);
        // Comme la raquette fait 150px, la balle tapera automatiquement en son centre (-75)
        computer.setY(ball.getCenterY() - 75);

        // 4 CAS DE FIGURE A GERER
        // Gestion de la collision de la balle sur la raquette du joueur
        if (player.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            // On augmente la vitesse de la balle
            magnitude *= (magnitude < SPEED_LIMIT) ? ACCELERATION : 1;
            // Plus la balle tape au centre de la raquette, moins l'angle sera prononcé
            angle = (PI / 4) * abs((player.getY() + 75 - ball.getCenterY() - 15) / 75);
            deltaX = abs(magnitude * cos(angle));
            // Valeur dépendante de l'endroit où frappe la balle : moitié haute ou moitié basse
            deltaY = (ball.getCenterY() <= player.getY() + 75 ? -abs(magnitude * sin(angle)) :
                    abs(magnitude * sin(angle)));

            // On incrémente le score à chaque fois que la balle touche la raquette du joueur
            score.set(score.get() + 1);

        // Gestion de la collision avec la raquette de l'ordi
        } else if (computer.getBoundsInParent().intersects(ball.getBoundsInParent())) {
            // On augmente la vitesse de la balle
            magnitude *= (magnitude < SPEED_LIMIT) ? ACCELERATION : 1;
            deltaX = -deltaX;
        // Gestion de la collision avec le bas du board
        } else if (ball.getCenterY() > BOTTOM_LIMIT) {
            deltaY = -deltaY;
        // Gestion de la collision avec le haut du board
        } else if (ball.getCenterY() < UP_LIMIT) {
            deltaY = -deltaY;
        }
    }

    public static void resetGame() {
        // On reset aussi le score
        score.set(0);

        angle = atan2(INITIAL_VY, INITIAL_VX);
        magnitude = sqrt(26);
        deltaX = magnitude * cos(angle);
        deltaY = magnitude * sin(angle);
    }

    // On génère un getter (qu'on récupère dans le controller) pour le score et l'affichage
    public static SimpleIntegerProperty scoreProperty() {
        return score;
    }
}
