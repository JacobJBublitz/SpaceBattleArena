package com.github.kaboomboom3.spacebattlearena;

/**
 * Created by 246673 on 5/15/2017.
 */
public class LinearScaling {
    public static double scale(double minTarget, double maxTarget, double current, double target,
                               double minSpeed, double maxSpeed, double scaleDivisor, double bufferSize) {
        final double error = target - current;

        final double totalRange = maxTarget - minTarget;
        final double linearScaleRange = totalRange / scaleDivisor;

        if (error > linearScaleRange) {
            return maxSpeed;
        } else if (error < -linearScaleRange) {
            return -maxSpeed;
        } else if (Math.abs(error) > bufferSize) {
            final double percentage = Math.abs(error) / linearScaleRange;

            final double speed = (maxSpeed - minSpeed) * percentage + minSpeed;

            if (error > 0) {
                return speed;
            } else {
                return -speed;
            }
        } else {
            return 0;
        }
    }
}
