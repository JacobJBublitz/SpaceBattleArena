package com.github.kaboomboom3.spacebattlearena;

public class PIDController {

	private double mP, mI, mD;
	private double mSetpoint = 0;

	private double mIntegral = 0;
	private long mLastTime = 0;

	private double mOutput = 0;
	private double mLastValue = 0;


	public PIDController(double p, double i, double d) {
		mP = p;
		mI = i;
		mD = d;
	}

	private void resetIntegralAccumulator() {
		mIntegral = 0;
	}

	private double error(double input) {
		return mSetpoint - input;
	}

	public void input(double value) {
		final long now = System.currentTimeMillis();
		final double deltaTime = (now - mLastTime) / 1000.0;
		final double derivative = (mLastValue - value) / (mLastTime - now);
		mIntegral += value * deltaTime;
		mLastTime = now;



		final double newOutput = mP * error(value) + mI * mIntegral + mD * derivative;
		if (Double.compare(mOutput, 0) != Double.compare(newOutput, 0)) {
			resetIntegralAccumulator();
		}

		mOutput = newOutput;
	}

	public double getOutput() {
		return mOutput;
	}

	public void setSetpoint(double setpoint) {
		mSetpoint = setpoint;
		resetIntegralAccumulator();
	}
}
