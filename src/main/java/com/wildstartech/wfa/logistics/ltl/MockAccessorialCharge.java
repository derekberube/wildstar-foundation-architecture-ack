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
package com.wildstartech.wfa.logistics.ltl;

import java.math.BigDecimal;

import com.wildstartech.wfa.MockObject;
import com.wildstartech.wfa.finance.ChargeDescriptionTooLongException;

public class MockAccessorialCharge extends MockObject 
implements AccessorialCharge {
	private int quantity=0;
	private BigDecimal amount=new BigDecimal(0);
	private String description="";
	
	public MockAccessorialCharge() {
		super();
	}
	
	//***** description
	@Override
	public String getDescription() {
		return this.description;
	}
	@Override
	public void setDescription(String description) 
			throws ChargeDescriptionTooLongException {
		this.description=description;
	}

	//***** amount
	@Override
	public BigDecimal getAmount() {
		return this.amount;
	}
	@Override
	public void setAmount(BigDecimal amount) {
		this.amount=amount;
	}
	@Override
	public void setAmount(int amount) {
		this.amount=new BigDecimal(amount);
	}
	@Override
	public void setAmount(double amount) {
		this.amount=new BigDecimal(amount);
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	@Override
	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public BigDecimal getTotalAmount() {	
		return this.amount.multiply(new BigDecimal(this.quantity));
	}
}