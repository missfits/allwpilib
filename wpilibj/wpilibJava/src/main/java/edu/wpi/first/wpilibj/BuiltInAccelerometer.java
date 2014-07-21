/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2014. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in $(WIND_BASE)/WPILib.  */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj;
import edu.wpi.first.wpilibj.hal.AccelerometerJNI;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.parsing.ISensor;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * Built-in accelerometer.
 *
 * This class allows access to the RoboRIO's internal accelerometer.
 */
public class BuiltInAccelerometer implements ISensor, LiveWindowSendable
{
	public enum Range { k2G, k4G, k8G }

	/**
	 * Constructor.
	 * @param range The range the accelerometer will measure
	 */
	public BuiltInAccelerometer(Range range) {
		AccelerometerJNI.setAccelerometerActive(false);

		switch(range) {
		case k2G:
			AccelerometerJNI.setAccelerometerRange(0);
			break;
		case k4G:
			AccelerometerJNI.setAccelerometerRange(1);
			break;
		case k8G:
			AccelerometerJNI.setAccelerometerRange(2);
			break;
		}

		AccelerometerJNI.setAccelerometerActive(true);

		UsageReporting.report(tResourceType.kResourceType_Accelerometer, 0, 0, "Built-in accelerometer");
	}

	/**
	 * Constructor.
	 * The accelerometer will measure +/-8 g-forces
	 */
	public BuiltInAccelerometer() {
		this(Range.k8G);
	}

	/**
	 * @return The acceleration of the RoboRIO along the X axis in g-forces
	 */
	public double getX() {
		return AccelerometerJNI.getAccelerometerX();
	}

	/**
	 * @return The acceleration of the RoboRIO along the Y axis in g-forces
	 */
	public double getY() {
		return AccelerometerJNI.getAccelerometerY();
	}

	/**
	 * @return The acceleration of the RoboRIO along the Z axis in g-forces
	 */
	public double getZ() {
		return AccelerometerJNI.getAccelerometerZ();
	}

	public String getSmartDashboardType(){
		return "Accelerometer";
	}

	private ITable m_table;

	/** {@inheritDoc} */
	public void initTable(ITable subtable) {
		m_table = subtable;
		updateTable();
	}

	/** {@inheritDoc} */
	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("X", getX());
			m_table.putNumber("Y", getY());
			m_table.putNumber("Z", getZ());
		}
	}

	/** {@inheritDoc} */
	public ITable getTable(){
		return m_table;
	}

	public void startLiveWindowMode() {}
	public void stopLiveWindowMode() {}
};
