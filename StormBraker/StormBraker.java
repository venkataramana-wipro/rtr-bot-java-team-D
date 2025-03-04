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


    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        fire(1);
    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
        turnLeft(90 - bearing);
    }


}