import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

// ------------------------------------------------------------------
// MyFirstBot
// ------------------------------------------------------------------
// A sample bot original made for Robocode by Mathew Nelson.
// Ported to Robocode Tank Royale by Flemming N. Larsen.
//
// Probably the first bot you will learn about.
// Moves in a seesaw motion, and spins the gun around at each end.
// ------------------------------------------------------------------
public class StormBraker extends Bot {

    // The main method starts our bot
    public static void main(String[] args) {
        new StormBraker().start();
    }

    // Constructor, which loads the bot config file
    StormBraker() {
        super(BotInfo.fromFile("StormBraker.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        setAdjustRadarForBodyTurn(true); // Keep the gun independent of the bot's turn
        setAdjustRadarForGunTurn(true); // Keep the radar independent of the gun's turn

        while (isRunning()) {
            // Move in a more unpredictable pattern
            forward(100);
            turnRight(45);
            back(100);
            turnLeft(45);
        }
    }

    @Override
    public void onScannedBot(ScannedBotEvent e) {
        double enemyX = e.getX();
        double enemyY = e.getY();
        double distance = calculateDistance(getX(), getY(), enemyX, enemyY);
        double firePower = Math.min(500 / distance, 3);
        // Adjust fire power based on distance// Lock radar on the target

        double angleToTarget = Math.toDegrees(Math.atan2(enemyX - getX(), enemyY - getY()));
        double gunTurn = normalizeBearing(angleToTarget - getDirection());
        turnGunRight(gunTurn);
        double radarTurn = normalizeBearing(angleToTarget - getDirection());
        turnRadarRight(radarTurn);

        fire(firePower);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn perpendicular to the bullet direction
        turnLeft(90 - bearing);

        // Move forward to dodge the bullet
        forward(50);
    }
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    private double normalizeBearing(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}
