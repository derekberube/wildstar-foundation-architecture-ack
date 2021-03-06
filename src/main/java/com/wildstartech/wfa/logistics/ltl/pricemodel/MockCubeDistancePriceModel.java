/*
 * Copyright (c) 2001 - 2016 Wildstar Technologies, LLC.
 *
 * This file is part of the Wildstar Foundation Architecture ACK.
 *
 * Wildstar Foundation Architecture Application Compatibility Kit (WFA-ACK) 
 * is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free  
 * Software Foundation, either version 3 of the License, or (at your  
 * option) any later version.
 *
 * WFA-ACK is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License 
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * WFA-ACK.  If not, see  <http://www.gnu.org/licenses/>.
 * 
 * Linking this library statically or dynamically with other modules is making a
 * combined work based on this library. Thus, the terms and conditions of the 
 * GNU General Public License cover the whole combination.
 * 
 * As a special exception, the copyright holders of this library give you 
 * permission to link this library with independent modules to produce an 
 * executable, regardless of the license terms of these independent modules, 
 * and to copy and distribute the resulting executable under terms of your 
 * choice, provided that you also meet, for each linked independent module, the
 * terms and conditions of the license of that module. An independent module is
 * a module which is not derived from or based on this library. If you modify 
 * this library, you may extend this exception to your version of the library, 
 * but you are not obliged to do so. If you do not wish to do so, delete this 
 * exception statement from your version.
 * 
 * If you need additional information or have any questions, please contact:
 *
 *      Wildstar Technologies, LLC.
 *      63 The Greenway Loop
 *      Inlet Beach, FL 32461
 *      USA
 *
 *      derek.berube@wildstartech.com
 *      www.wildstartech.com
 */
package com.wildstartech.wfa.logistics.ltl.pricemodel;

import java.util.logging.Logger;

import com.wildstartech.wfa.logistics.ltl.pricemodels.CubeDistancePriceModel;
import com.wildstartech.wfa.logistics.ltl.quote.QuickQuote;
import com.wildstartech.wfa.logistics.ltl.quote.Quote;
import com.wildstartech.wfa.logistics.ltl.quote.SimpleQuote;
import com.wildstartech.wfa.logistics.ltl.workorder.WorkOrder;

public class MockCubeDistancePriceModel extends MockPriceModel implements CubeDistancePriceModel {
	/** Used in object serialization. */
	private static final String _CLASS = MockCubeDistancePriceModel.class.getName();
	private static final Logger logger = Logger.getLogger(_CLASS);

	private int mileageInterval = 0;
	private int mileageMax = 0;
	private int minCube = 0;
	private double baseCharge = 0;
	private double cubeDiscount = 0;
	private double mileageStep = 1;
	private double minCubeCharge = 0;
	private String label = "";

	/**
	 * Default, no-argument constructor.
	 */
	public MockCubeDistancePriceModel() {
		logger.entering(_CLASS, "CubeDistancePriceModelImpl()");
		logger.exiting(_CLASS, "CubeDistancePriceModelImpl()");
	}

	// ***** label
	@Override
	public String getLabel() {
		logger.entering(_CLASS, "getLabel()");
		logger.exiting(_CLASS, "getLabel()", this.label);
		return this.label;
	}
	@Override
	public void setLabel(String label) {
		logger.entering(_CLASS, "setLabel(String)", label);
		if (label != null) {
			this.label = label;
		} else {
			this.label = "";
		} // END if (label != null)
		logger.exiting(_CLASS, "setLabel(String)");
	}

	// ***** mileageInterval
	public int getMileageInterval() {
		logger.entering(_CLASS, "getMileageInterval()");
		logger.exiting(_CLASS, "getMileageInterval()", this.mileageInterval);
		return this.mileageInterval;
	}
	@Override
	public void setMileageInterval(int mileageInterval) {
		logger.entering(_CLASS, "setMileageInterval(int)", mileageInterval);
		this.mileageInterval = mileageInterval;
		logger.exiting(_CLASS, "setMileageInterval(int)");
	}

	// ***** minCube
	@Override
	public int getMinCube() {
		logger.entering(_CLASS, "getMinCube()");
		logger.exiting(_CLASS, "getMinCube()", this.minCube);
		return this.minCube;
	}
	@Override
	public void setMinCube(int minCube) {
		logger.entering(_CLASS, "setMinCube(int)", minCube);
		this.minCube = minCube;
		logger.exiting(_CLASS, "setMinCube(int)");
	}

	// ***** baseCharge
	@Override
	public double getBaseCharge() {
		logger.entering(_CLASS, "getBaseCharge()");
		logger.exiting(_CLASS, "getBaseCharge()", this.baseCharge);
		return this.baseCharge;
	}

	/**
	 * Returns the base charge
	 * 
	 * @param cube
	 * @param mileage
	 * @return
	 */
	@Override
	public double getBaseCharge(int cube, double mileage) {
		logger.entering(_CLASS, "getBaseCharge(int)", cube);
		double computedBaseCharge = 0;
		double mileageMultiplier = 0;
		double mileageStep = 0;
		double minimumBaseCharge = 0;
		int roundedMileage = 0;
		int mileageExponent = 0;
		int mileageInterval = 0;

		computedBaseCharge = getBaseCubeCharge(cube);
		// Normalize the mileage
		roundedMileage = new Long(Math.round(mileage)).intValue();
		// Get the mileage Interval
		mileageInterval = getMileageInterval();
		// Get the mileageStep
		mileageStep = getMileageStep();
		// Determine mileage exponent
		if (roundedMileage < mileageInterval) {
			mileageExponent = 0;
		} else if ((roundedMileage % mileageInterval) == 0) {
			mileageExponent = (roundedMileage / mileageInterval) - 1;
		} else {
			mileageExponent = (roundedMileage / mileageInterval);
		} // END if (roundedMileage < mileageInterval)
		mileageMultiplier = Math.pow(mileageStep, mileageExponent);
		// Apply mileageMultiplier
		computedBaseCharge = computedBaseCharge * mileageMultiplier;
		// Check computed computedBaseCharge against minimum base charge.
		if (computedBaseCharge < minimumBaseCharge) {
			computedBaseCharge = minimumBaseCharge;
		} // END if (computedBaseCharge < minimumBaseCharge)
		logger.exiting(_CLASS, "getBaseCharge(int)", computedBaseCharge);
		return computedBaseCharge;
	}

	/**
	 * Returns the base charge for the specified number of cubes.
	 * 
	 * @param cube
	 * @return
	 */
	@Override
	public double getBaseCubeCharge(int cube) {
		logger.entering(_CLASS, "getBaseCubeCharge(int)", cube);
		double baseCharge;
		double cubeDiscount = 0;
		double computedBaseCharge = 0;
		double computedCubeDiscount = 0;
		int minCube = 0;

		// Obtain the base Charge
		baseCharge = getBaseCharge();
		// Normalize the cube
		minCube = getMinCube();
		if (cube < minCube) {
			cube = minCube;
		} // END if (cube < this.model.getMinCube())
			// Determine the cube discount rate.
		cubeDiscount = getCubeDiscount();
		computedCubeDiscount = 1 - cubeDiscount;
		if (cube == minCube) {
			computedCubeDiscount = 1;
		} else {
			computedCubeDiscount = Math.pow(computedCubeDiscount, (cube - minCube));
		} // END if (cube == minCube)
			// Calculate base charge.
		computedBaseCharge = baseCharge * computedCubeDiscount;
		logger.exiting(_CLASS, "getBaseCubeCharge(int)", baseCharge);
		return computedBaseCharge;
	}
	@Override
	public void setBaseCharge(double baseCharge) {
		logger.entering(_CLASS, "setBaseCharge(double)", baseCharge);
		this.baseCharge = baseCharge;
		logger.exiting(_CLASS, "setBaseCharge(double)");
	}
	@Override
	// ***** cubeDiscount
	public double getCubeDiscount() {
		logger.entering(_CLASS, "getCubeDiscount()");
		logger.exiting(_CLASS, "getCubeDiscount()", this.cubeDiscount);
		return this.cubeDiscount;
	}
	@Override
	public void setCubeDiscount(double cubeDiscount) {
		logger.entering(_CLASS, "setCubeDiscount(double)", cubeDiscount);
		this.cubeDiscount = cubeDiscount;
		logger.exiting(_CLASS, "setCubeDiscount(double)");
	}

	// ***** mileageMax
	@Override
	public int getMileageMax() {
		logger.entering(_CLASS, "getMileageMax()");
		logger.exiting(_CLASS, "getMileageMax()", this.mileageMax);
		return this.mileageMax;
	}
	@Override
	public void setMileageMax(int mileageMax) {
		logger.entering(_CLASS, "setMileageMax(int)", mileageMax);
		if (mileageMax < 0) {
			this.mileageMax = 0;
		} else {
			this.mileageMax = mileageMax;
		} // END if (mileageMax < 0)
		logger.exiting(_CLASS, "setMileageMax(int)");
	}

	// ***** mileageStep
	@Override
	public double getMileageStep() {
		logger.entering(_CLASS, "getMileageStep()");
		logger.exiting(_CLASS, "getMileageStep()", this.mileageStep);
		return this.mileageStep;
	}
	@Override
	public void setMileageStep(double mileageStep) {
		logger.entering(_CLASS, "setMileageStep(double)", mileageStep);
		this.mileageStep = mileageStep;
		logger.exiting(_CLASS, "setMileageStep(double)");
	}
	@Override
	// ***** minCubeCharge
	public double getMinCubeCharge() {
		logger.entering(_CLASS, "getMinCubeCharge()");
		logger.exiting(_CLASS, "getMinCubeCharge()", this.minCubeCharge);
		return this.minCubeCharge;
	}
	@Override
	public void setMinCubeCharge(double minCubeCharge) {
		logger.entering(_CLASS, "setMinCubeCharge(double)", minCubeCharge);
		this.minCubeCharge = minCubeCharge;
		logger.exiting(_CLASS, "setMinCubeCharge(double)");
	}

	// ***** calculateCharge
	/**
	 * Return an estimated rate for the specified cube and mileage.
	 * 
	 * @param cube
	 * @param mileage
	 * @return
	 */
	@Override
	public double calculateCharge(int cube, double mileage) {
		logger.entering(_CLASS, "calculateCharge(int,int)", new Object[] { cube, mileage });
		int minCube = 0;
		double charge = 0;
		double baseCharge = 0;
		// Calculate the base charge.
		baseCharge = getBaseCharge(cube, mileage);
		/*
		 * Determine if the specified cube meets the minimum. If it does not, then
		 * adjust the cube to relfect the minimum.
		 */
		minCube = getMinCube();
		if (cube < minCube)
			cube = minCube;
		charge = baseCharge * cube;
		logger.exiting(_CLASS, "calculateCharge(int,int)", charge);
		return charge;
	}

	@Override
	public double calculateTotalCharges(QuickQuote quote) {
		logger.entering(_CLASS, "calculateCharge(Quote)", quote);
		double charges = 0;
		double distance = 0;
		int cubes = 0;
		if (quote != null) {
			distance = quote.getDistance();
			cubes = quote.getTotalCubes();
		} else {
			logger.severe("The specified quote parameter is null.");
		} // END if (quote != null)
		charges = calculateCharge(cubes, distance);
		logger.exiting(_CLASS, "calculateCharge(Quote)", charges);
		return charges;
	}

	@Override
	public double calculateTotalCharges(SimpleQuote quote) {
		logger.entering(_CLASS, "calculateCharge(SimpleQuote)", quote);
		double charges = calculateTotalCharges((QuickQuote) quote);
		logger.exiting(_CLASS, "calculateCharge(SimpleQuote)", charges);
		return charges;
	}

	@Override
	public double calculateTotalCharges(Quote quote) {
		logger.entering(_CLASS, "calculateCharge(SimpleQuote)", quote);
		double charges = calculateTotalCharges((QuickQuote) quote);
		logger.exiting(_CLASS, "calculateCharge(SimpleQuote)", charges);
		return charges;
	}

	@Override
	public double calculateTotalCharges(WorkOrder workOrder) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		logger.entering(_CLASS, "getType()");
		logger.entering(_CLASS, "getType()", CubeDistancePriceModel.TYPE);
		return CubeDistancePriceModel.TYPE;
	}
}