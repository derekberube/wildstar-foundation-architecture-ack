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
package com.wildstartech.wfa.dao.logistics.ltl.receiver;

import java.util.Date;

import com.wildstartech.wfa.logistics.ltl.MockReceiverWorkOrderLineItem;
import com.wildstartech.wfa.logistics.ltl.receiver.ReceiverWorkOrderLineItem;

public class TestCaseReceiverWorkOrder1 extends TestCaseReceiverWorkOrderBase {
   public Date dateReceived=new Date();
   
   public TestCaseReceiverWorkOrder1() {
      ReceiverWorkOrderLineItem lineItem=null;
      
      this.setBillOfLadingNumber("BOL1234");
      this.setInboundCarrier("ACME Furniture Purveyors");
      this.setDateReceived(this.dateReceived);
      this.setPurchaseOrderNumber("PO#4321");
      this.setSalesOrderNumber("SO97531");
      this.setShortDescription("Short Description");
      this.setStatusState("New");
      this.setStatusReason("");
      this.setTitle("Receiver Work Order Test Case");
      
      //***** First LineItem
      lineItem=new MockReceiverWorkOrderLineItem();
      lineItem.setDescription("FIRST LINE ITEM");
      addLineItem(lineItem);
      
      //***** Second LineItem
      lineItem=new MockReceiverWorkOrderLineItem();
      lineItem.setDescription("SECOND LINE ITEM");
      addLineItem(lineItem);
      
      //***** Third LineItem
      lineItem=new MockReceiverWorkOrderLineItem();
      lineItem.setDescription("THIRD LINE ITEM");
      addLineItem(lineItem);
   }
}