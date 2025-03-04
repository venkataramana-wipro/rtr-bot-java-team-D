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
        setBodyColor(new Color(255, 0, 0)); // Metallic silver
        setGunColor(new Color(0, 255, 0)); // Darker metallic silver
        setRadarColor(new Color(169, 169, 169)); // Metallic silver
        setBulletColor(new Color(255, 215, 0)); // Gold for the lightning effect
        setScanColor(new Color(255, 255, 255));

        // Repeat while the bot is running
        while (isRunning()) {
            // Move in a more unpredictable pattern
            forward(100);
            turnGunRight(360);
            back(100);
            turnGunRight(360);
            turnRadarRight(360);
        }
    }

    @Override
    public void onScannedBot(ScannedBotEvent e) {
        double enemyX = e.getX();
        double enemyY = e.getY();
        double angleToTarget = Math.toDegrees(Math.atan2(enemyX - getX(), enemyY - getY()));
        double gunTurn = normalizeBearing(angleToTarget - getDirection());
        turnGunRight(gunTurn);
        // Lock radar on the target
        double radarTurn = normalizeBearing(angleToTarget - getDirection());
        turnRadarRight(radarTurn);
        fire(1);
    }

    private double normalizeBearing(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
        turnLeft(90 - bearing);
        // turnRight(90 + bearing);
        forward(50 + Math.random() * 50);
    }
}