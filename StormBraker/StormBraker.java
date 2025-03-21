import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

import java.awt.*;

public class StormBraker extends Bot {

    public static void main(String[] args) {
        new StormBraker().start();
    }

    StormBraker() {
        super(BotInfo.fromFile("StormBraker.json"));
    }

    @Override
    public void run() {
        // Set colors to match Thor's Stormbreaker hammer
        setBodyColor(new Color(255, 0, 0));
        setGunColor(new Color(0, 255, 0));
        setRadarColor(new Color(169, 169, 169));
        setBulletColor(new Color(255, 215, 0));
        setScanColor(new Color(255, 255, 255));

        // Repeat while the bot is running
        while (isRunning()) {

            forward(50);

            turnGunRight(360);

            //fire(1);

            back(100);

            turnGunRight(360);

            turnGunRight(360);

            fire(1);

        }

    }

    // We saw another bot -> fire!

    @Override

    public void onScannedBot(ScannedBotEvent e) {

        fire(3);

        //fire(3);

    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

            setTurnRight(90 - bearing); // Move perpendicular to dodge

        // Turn 90 degrees to the bullet direction based on the bearing
        turnLeft(90 - bearing);
        back(150);
    }

//    } private double calculateDistance(double x1, double y1, double x2, double y2) {
//        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
//    }


}