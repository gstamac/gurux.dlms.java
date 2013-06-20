//
// --------------------------------------------------------------------------
//  Gurux Ltd
// 
//
//
// Filename:        $HeadURL$
//
// Version:         $Revision$,
//                  $Date$
//                  $Author$
//
// Copyright (c) Gurux Ltd
//
//---------------------------------------------------------------------------
//
//  DESCRIPTION
//
// This file is a part of Gurux Device Framework.
//
// Gurux Device Framework is Open Source software; you can redistribute it
// and/or modify it under the terms of the GNU General Public License 
// as published by the Free Software Foundation; version 2 of the License.
// Gurux Device Framework is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of 
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
// See the GNU General Public License for more details.
//
// More information of Gurux products: http://www.gurux.org
//
// This code is licensed under the GNU General Public License v2. 
// Full text may be retrieved at http://www.gnu.org/licenses/gpl-2.0.txt
//---------------------------------------------------------------------------

package gurux.dlms;

import gurux.dlms.internal.GXCommon;
import gurux.dlms.enums.ObjectType;
import gurux.dlms.enums.RequestTypes;
import gurux.dlms.enums.InterfaceType;
import gurux.dlms.enums.DataType;
import gurux.dlms.objects.*;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.EnumSet;

/** 
 GXDLMS implements methods to communicate with DLMS/COSEM metering devices.
*/
class GXDLMS
{
    private int privateValueOfQualityOfService;
    private int privateNumberOfUnusedBits;
    private int packetIndex;
    private boolean bIsLastMsgKeepAliveMsg;
    int expectedFrame, frameSequence;
    //Cached data.
    Object cacheData;
    //Cached data type.
    DataType cacheType;
    //Index where last item found.
    int cacheIndex;
    //Cache Size
    int cacheSize;
    int itemCount;
    int maxItemCount;
    private int privateMaxReceivePDUSize;
    private boolean privateUseCache;
    private Object privateServerID;
    private Object privateClientID;
    private boolean privateGenerateFrame;
    private byte privateDLMSVersion;
    private boolean privateUseLogicalNameReferencing;
    private String privatePassword;
    private InterfaceType privateInterfaceType;
    private boolean privateServer;
    private GXDLMSLimits privateLimits;
    private GXDLMSLNSettings privateLNSettings;
    private GXDLMSSNSettings privateSNSettings;

    static GXDLMSObject createObject(ObjectType type)
    {
        if (type == ObjectType.ACTION_SCHEDULE)
        {
            return new GXDLMSActionSchedule();
        }        
        if (type == ObjectType.ACTIVITY_CALENDAR)
        {
            return new GXDLMSActivityCalendar();
        }
        if (type == ObjectType.ASSOCIATION_LOGICAL_NAME)
        {
            return new GXDLMSAssociationLogicalName();
        }
        if (type == ObjectType.ASSOCIATION_SHORT_NAME)
        {
            return new GXDLMSAssociationShortName();
        }
        if (type == ObjectType.AUTO_ANSWER)
        {
            return new GXDLMSAutoAnswer();
        }
        if (type == ObjectType.AUTO_CONNECT)
        {
            return new GXDLMSAutoConnect();
        }
        if (type == ObjectType.CLOCK)
        {
            return new GXDLMSClock();
        }
        if (type == ObjectType.DATA)
        {
            return new GXDLMSData();
        }
        if (type == ObjectType.DEMAND_REGISTER)
        {
            return new GXDLMSDemandRegister();
        }
        if (type == ObjectType.MAC_ADDRESS_SETUP)
        {
            return new GXDLMSMacAddressSetup();
        }        
        if (type == ObjectType.EVENT)
        {
            return new GXDLMSObject();
        }
        if (type == ObjectType.EXTENDED_REGISTER)
        {
            return new GXDLMSExtendedRegister();
        }
        if (type == ObjectType.GPRS_SETUP)
        {
            return new GXDLMSGprsSetup();
        }
        if (type == ObjectType.IEC_HDLC_SETUP)
        {
            return new GXDLMSHdlcSetup();
        }
        if (type == ObjectType.IEC_LOCAL_PORT_SETUP)
        {
            return new GXDLMSIECOpticalPortSetup();
        }
        if (type == ObjectType.IEC_TWISTED_PAIR_SETUP)
        {
            return new GXDLMSObject();
        }
        if (type == ObjectType.IP4_SETUP)
        {
            return new GXDLMSIp4Setup();
        }
        if (type == ObjectType.MBUS_SLAVE_PORT_SETUP)
        {
            return new GXDLMSMBusSlavePortSetup();
        }
        if (type == ObjectType.IMAGE_TRANSFER)
        {
            return new GXDLMSImageTransfer();
        }
        if (type == ObjectType.DISCONNECT_CONTROL)
        {
            return new GXDLMSDisconnectControl();
        }
        if (type == ObjectType.MODEM_CONFIGURATION)
        {
            return new GXDLMSModemConfiguration();
        }
        if (type == ObjectType.PPP_SETUP)
        {
            return new GXDLMSPppSetup();
        }
        if (type == ObjectType.PROFILE_GENERIC)
        {
            return new GXDLMSProfileGeneric();
        }        
        if (type == ObjectType.REGISTER)
        {
            return new GXDLMSRegister();
        }
        if (type == ObjectType.REGISTER_ACTIVATION)
        {
            return new GXDLMSRegisterActivation();
        }
        if (type == ObjectType.REGISTER_MONITOR)
        {
            return new GXDLMSRegisterMonitor();
        }
        if (type == ObjectType.REGISTER_TABLE)
        {
            return new GXDLMSObject();
        }
        if (type == ObjectType.REMOTE_ANALOGUE_CONTROL)
        {
            return new GXDLMSObject();
        }
        if (type == ObjectType.REMOTE_DIGITAL_CONTROL)
        {
            return new GXDLMSObject();
        }
        if (type == ObjectType.SAP_ASSIGNMENT)
        {
            return new GXDLMSSapAssignment();
        }
        if (type == ObjectType.SCHEDULE)
        {
            return new GXDLMSSchedule();
        }
        if (type == ObjectType.SCRIPT_TABLE)
        {
            return new GXDLMSScriptTable();
        }
        if (type == ObjectType.SMTP_SETUP)
        {
            return new GXDLMSObject();
        }
        if (type == ObjectType.SPECIAL_DAYS_TABLE)
        {
            return new GXDLMSSpecialDaysTable();
        }
        if (type == ObjectType.STATUS_MAPPING)
        {
            return new GXDLMSObject();
        }
        if (type == ObjectType.TCP_UDP_SETUP)
        {
            return new GXDLMSTcpUdpSetup();
        }
        if (type == ObjectType.TUNNEL)
        {
            return new GXDLMSObject();
        }
        if (type == ObjectType.UTILITY_TABLES)
        {
            return new GXDLMSObject();
        }                
        return new GXDLMSObject();
    }

    /** 
     Constructor.
    */
    public GXDLMS(boolean server)
    {
        bIsLastMsgKeepAliveMsg = false;
        setServer(server);
        setUseCache(true);
        this.setInterfaceType(InterfaceType.GENERAL);
        setDLMSVersion((byte) 6);
        setMaxReceivePDUSize(0xFFFF);
        clearProgress();
        setGenerateFrame(true);
        setLimits(new GXDLMSLimits());
    }

    /** 
     Reserved for internal use.
    */
    protected final void clearProgress()
    {
        cacheIndex = cacheSize = itemCount = maxItemCount = 0;
        cacheData = null;
        cacheType = DataType.NONE;
    }

    /** 
     Client ID is the identification of the device that is used as a client.
     Client ID is aka HDLC Address.
    */
    public final Object getClientID()
    {
        return privateClientID;
    }
    public final void setClientID(Object value)
    {
        privateClientID = value;
    }

    /** 
     Server ID is the indentification of the device that is used as a server.
     Server ID is aka HDLC Address.
    */
    public final Object getServerID()
    {
        return privateServerID;
    }
    public final void setServerID(Object value)
    {
        privateServerID = value;
    }

    public final boolean getGenerateFrame()
    {
        return privateGenerateFrame;
    }
    public final void setGenerateFrame(boolean value)
    {
        privateGenerateFrame = value;
    }

    /** 
     Is cache used. Default value is True;
    */
    public final boolean getUseCache()
    {
        return privateUseCache;
    }
    public final void setUseCache(boolean value)
    {
        privateUseCache = value;
    }

    public final byte getDLMSVersion()
    {
        return privateDLMSVersion;
    }
    public final void setDLMSVersion(byte value)
    {
        privateDLMSVersion = value;
    }

    /** 
     Retrieves the maximum size of PDU receiver.

     PDU size tells maximum size of PDU packet.
     Value can be from 0 to 0xFFFF. By default the value is 0xFFFF.
    */
    public final int getMaxReceivePDUSize()
    {
        return privateMaxReceivePDUSize;
    }

    public final void setMaxReceivePDUSize(int value)
    {
        privateMaxReceivePDUSize = value;
    }

    /** 
     Determines, whether Logical, or Short name, referencing is used.     

     Referencing depends on the device to communicate with.
     Normally, a device supports only either Logical or Short name referencing.
     The referencing is defined by the device manufacurer.
     If the referencing is wrong, the SNMR message will fail.
    */
    public final boolean getUseLogicalNameReferencing()
    {
        return privateUseLogicalNameReferencing;
    }
    public final void setUseLogicalNameReferencing(boolean value)
    {
        privateUseLogicalNameReferencing = value;
    }

    /** 
     Reserved for internal use.
    */
    final byte[][] generateMessage(Object name, int parameterCount, byte[] data, ObjectType interfaceClass, int AttributeOrdinal, Command cmd)
    {
        if (getLimits().getMaxInfoRX() == null)
        {
            throw new GXDLMSException("Invalid arguement.");
        }
        java.nio.ByteBuffer buff;
        if (name instanceof byte[])
        {
            buff = java.nio.ByteBuffer.wrap((byte[])name);
        }
        else
        {
            if (name == null)
            {
                buff = java.nio.ByteBuffer.wrap(data);
            }
            else if (getUseLogicalNameReferencing())
            {
                int len = data == null ? 0 : data.length;
                buff = java.nio.ByteBuffer.allocate(20 + len);
                if (cmd == Command.GetRequest || cmd == Command.SetRequest || cmd == Command.MethodRequest)
                {
                    buff.putShort((short) interfaceClass.getValue());
                    //Add LN
                    String[] items = ((String)name).split("[.]", -1);
                    if (items.length != 6)
                    {
                        throw new GXDLMSException("Invalid Logical Name.");
                    }
                    for (String it : items)
                    {
                        buff.put((byte) (Short.parseShort(it) & 0xFF));
                    }
                    buff.put((byte) AttributeOrdinal);
                    buff.put((byte) 0x0); //Items count
                    }
                }
                else
                {
                    int len = data == null ? 0 : data.length;
                    buff = java.nio.ByteBuffer.allocate(11 + len);
                    //Add name count.
                    if (name.getClass().isArray())
                    {
                        for (int pos = 0; pos != Array.getLength(name); ++pos)
                        {
                            Object it = Array.get(name, pos);
                            buff.put((byte) 2);
                            int base_address = ((Number)it).shortValue() & 0xFFFF;
                            base_address += ((AttributeOrdinal - 1) * 8);
                            buff.putShort((short) base_address);
                        }
                    }
                        else
                        {
                            buff.put((byte) 1);
                        }
                        if (cmd == Command.ReadResponse || cmd == Command.WriteResponse)
                        {
                            buff.put((byte) 0x0);
                        }
                        else
                        {
                            buff.put((byte)parameterCount);
                            int base_address = GXCommon.intValue(name);
                            if (cmd == Command.MethodRequest)
                            {
                                base_address += AttributeOrdinal;
                            }
                            else
                            {
                                base_address += ((AttributeOrdinal - 1) * 8);
                            }
                            buff.putShort((short) base_address);
                        }
                }
                if (data != null && data.length != 0)
                {
                    buff.put(data);
                }
        }
        return splitToBlocks(buff, cmd);
    }

    /** 
     Is operated as server or client.
    */
    public final boolean getServer()
    {
        return privateServer;
    }
    public final void setServer(boolean value)
    {
        privateServer = value;
    }

    /** 
     Information from the connection size that server can handle.
    */
    public final GXDLMSLimits getLimits()
    {
        return privateLimits;
    }

    public final void setLimits(GXDLMSLimits value)
    {
        privateLimits = value;
    }

    /** 
     Removes the HDLC header from the packet, and returns COSEM data only.

     @param packet The received packet, from the device, as byte array.
     @param data The exported data.
     @return COSEM data.
    */
    public final java.util.Set<RequestTypes> getDataFromPacket(byte[] packet, 
            ByteArrayOutputStream data, byte[] frame, int[] command) throws Exception
    {
        if (packet == null || packet.length == 0)
        {
            throw new IllegalArgumentException("Packet is invalid.");
        }
        int[] error = new int[1];         
        boolean[] packetFull = new boolean[1], wrongCrc = new boolean[1];
        java.util.Set<RequestTypes> moreData = getDataFromFrame(java.nio.ByteBuffer.wrap(packet), data, frame, true, error, false, packetFull, wrongCrc, command);
        if (!packetFull[0])
        {
            throw new GXDLMSException("Not enought data to parse frame.");
        }
        if (wrongCrc[0])
        {
            throw new GXDLMSException("Wrong Checksum.");
        }
        if (command[0] == Command.REJECTED.getValue())
        {
            throw new GXDLMSException("Packet rejected.");
        }
        return moreData;
    }

    /** 
     Generates an acknowledgment message, with which the server is informed to 
     send next packets.

     @param type Frame type
     @return Acknowledgment message as byte array.
    */
    public final byte[] receiverReady(RequestTypes type)
    {
        if (!getUseLogicalNameReferencing() || type == RequestTypes.FRAME)
        {
            return addFrame(generateSupervisoryFrame((byte)0), false, null, 0, 0);
        }
        java.nio.ByteBuffer buff = java.nio.ByteBuffer.allocate(10);
        if (this.getInterfaceType() == InterfaceType.GENERAL)
        {
            if (getServer())
            {
                buff.put(GXCommon.LLCReplyBytes);
            }
            else
            {
                buff.put(GXCommon.LLCSendBytes);
            }
        }
        //Get request normal
        buff.put((byte) 0xC0);
        buff.put((byte) 0x02);
        //Invoke ID and priority.
        buff.put((byte)0x81);
        buff.putInt(packetIndex);
        return addFrame(generateIFrame(), false, buff, 0, buff.position());
    }

    /** 
     Determines, whether the DLMS packet is completed.

     @param data The data to be parsed, to complete the DLMS packet.
     @return True, when the DLMS packet is completed.
    */
    public final boolean isDLMSPacketComplete(byte[] data)
    {
        byte[] frame = new byte[1];
        int[] error = new int[1];
        try
        {
            boolean[] packetFull = new boolean[1], wrongCrc = new boolean[1];
            int[] command = new int[1];
            getDataFromFrame(java.nio.ByteBuffer.wrap(data), null, frame, true, 
                    error, false, packetFull, wrongCrc, command);
            return packetFull[0];
        }
        catch (java.lang.Exception ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /** 
     Reserved for internal use.
    */
    public final void parseReplyData(ActionType action, byte[] buff, 
            Object[] value, DataType[] type) throws Exception
    {
        type[0] = DataType.NONE;
        if (!getUseCache())
        {
            clearProgress();
        }
        int[] read = new int[1], total = new int[1], index = new int[1];
        int[] cache = new int[]{cacheIndex};
        value[0] = GXCommon.getData(buff, index, action.getValue(), total, read, type, cache);
        cacheIndex = cache[0];
        if (getUseCache())
        {
            cacheSize = buff.length;
            //If array.
            if (cacheData != null && cacheData.getClass().isArray())
            {
                if (value[0] != null)
                {
                    Object oldData = cacheData;
                    if (value.getClass().isArray())
                    {
                        Object newData = value[0];
                        Object[] data = new Object[Array.getLength(oldData) + Array.getLength(newData)];
                        System.arraycopy((Object[]) oldData, 0, data, 0, Array.getLength(oldData));
                        System.arraycopy((Object[]) newData, 0, data, Array.getLength(oldData), Array.getLength(newData));
                        cacheData = data;
                    }
                    else
                    {
                        Object[] data = new Object[Array.getLength(oldData) + 1];
                        System.arraycopy((Object[]) oldData, 0, data, 0, Array.getLength(oldData));
                        data[Array.getLength(oldData)] = value;
                        cacheData = data;
                    }
                }
            }
            else
            {
                cacheData = value[0];
                cacheType = type[0];
            }
        }
        itemCount += read[0];
        maxItemCount = total[0];
    }
    
    /*
     * Is cache used.
     */
    boolean useCache(byte[] data)
    {
        return getUseCache() && data.length == cacheSize;
    }

    /** 
     This method is used to solve current index of received DLMS packet, 
     by retrieving the current progress status.

     @param data DLMS data to parse.
     @return The current index of the packet.
    */
    public final int getCurrentProgressStatus(byte[] data)
    {
        try
        {
            //Return cached size.
            if (useCache(data))
            {
                return itemCount;
            }
            Object[] value = new Object[1];
            DataType type[] = new DataType[]{DataType.NONE};
            parseReplyData(getUseCache() ? ActionType.INDEX : ActionType.INDEX,
                    data, value, type);
            return itemCount;
        }        
        catch (RuntimeException ex)
        {
            System.out.println(ex.getMessage());
            return itemCount;
        }
        catch (java.lang.Exception ex)
        {
            System.out.println(ex.getMessage());
            return itemCount;
        }
    }

    /** 
     This method is used to solve the total amount of received items,
     by retrieving the maximum progress status.

     @param data DLMS data to parse.
     @return Total amount of received items.
    */
    public final int getMaxProgressStatus(byte[] data)
    {
        //Return cached size.
        if (useCache(data))
        {
            return maxItemCount;
        }
        if (!getUseCache())
        {
            clearProgress();
        }
        try
        {
            Object[] value = new Object[1];
            DataType type[] = new DataType[]{DataType.NONE};
            parseReplyData(getUseCache() ? ActionType.INDEX : ActionType.COUNT, data, value, type);
        }
        catch(RuntimeException ex)
        {
            System.out.println(ex.getMessage());
            return 1;
        }        
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return 1;
        }                
        return maxItemCount;
    }      

    /** 
     Checks, whether the received packet is a reply to the sent packet.

     @param sendData The sent data as a byte array. 
     @param receivedData The received data as a byte array.
     @return True, if the received packet is a reply to the sent packet. False, if not.
    */
    public final boolean isReplyPacket(byte[] sendData, byte[] receivedData) throws Exception
    {
        byte[] sendID = new byte[1];
        byte[] receiveID = new byte[1];
        int[] error = new int[1];
        boolean[] packetFull = new boolean[1];
        boolean[] wrongCrc = new boolean[1];
        int[] command = new int[1];
        getDataFromFrame(java.nio.ByteBuffer.wrap(sendData), null, sendID, 
                false, error, false, packetFull, wrongCrc, command);
        if (!packetFull[0])
        {
            throw new GXDLMSException("Not enought data to parse frame.");
        }
        if (wrongCrc[0])
        {
            throw new GXDLMSException("Wrong Checksum.");
        }
        getDataFromFrame(java.nio.ByteBuffer.wrap(receivedData), null, 
                receiveID, true, error, true, packetFull, wrongCrc, command);
        if (!packetFull[0])
        {
            throw new GXDLMSException("Not enought data to parse frame.");
        }
        if (wrongCrc[0])
        {
            throw new GXDLMSException("Wrong Checksum.");
        }
        if (command[0] == Command.REJECTED.getValue())
        {
            throw new GXDLMSException("Frame rejected.");
        }
        int sid = (sendID[0] & 0xFF);
        int rid = (receiveID[0] & 0xFF);
        boolean ret = rid == FrameType.Rejected.getValue() || 
                (sid == FrameType.Disconnect.getValue() && rid == FrameType.UA.getValue()) || 
                isExpectedFrame(sid, rid);
        return ret;
    }

    /** 
     Determines the type of the connection


     All DLMS meters do not support the IEC 62056-47 standard.  
     If the device does not support the standard, and the connection is made 
     using TCP/IP, set the type to InterfaceType.General.

    */
    public final InterfaceType getInterfaceType()
    {
        return privateInterfaceType;
    }
    public final void setInterfaceType(InterfaceType value)
    {
        privateInterfaceType = value;
    }

    /** 
     Retrieves the password that is used in communication.

     If authentication is set to none, password is not used.
    */
    public final String getPassword()
    {
        return privatePassword;
    }
    public final void setPassword(String value)
    {
        privatePassword = value;
    }

    private byte[][] splitToFrames(java.nio.ByteBuffer packet, int blockIndex, int[] index, int count, Command cmd)
    {
        java.nio.ByteBuffer tmp = java.nio.ByteBuffer.allocate(count + 13);
        if (this.getInterfaceType() == InterfaceType.GENERAL)
        {
            if (getServer())
            {
                tmp.put(GXCommon.LLCReplyBytes);
            }
            else
            {
                tmp.put(GXCommon.LLCSendBytes);
            }
        }
        if (cmd != Command.None && this.getUseLogicalNameReferencing())
        {
            boolean moreBlocks = packet.limit() > getMaxReceivePDUSize() && packet.limit() > index[0] + count;
            //Command, multiple blocks and Invoke ID and priority.
            tmp.put(new byte[] {(byte) cmd.getValue(), (byte)(moreBlocks ? 2 : 1), (byte) 0x81});
            if (getServer())
            {
                tmp.put((byte) 0x0); // Get-Data-Result choice data
            }
            if (moreBlocks)
            {
                tmp.putInt(blockIndex);
                tmp.put((byte) 0);
                GXCommon.setObjectCount(count, tmp);
            }
        }
        else if (cmd != Command.None && !this.getUseLogicalNameReferencing())
        {
            tmp.put((byte) cmd.getValue());
        }
        int dataSize;
        if (this.getInterfaceType() == InterfaceType.NET)
        {
            dataSize = getMaxReceivePDUSize();
        }
        else
        {
            if (cmd == Command.GetRequest || cmd == Command.MethodRequest || 
                cmd == Command.ReadRequest || cmd == Command.SetRequest || 
                cmd == Command.WriteRequest)
            {
                dataSize = GXCommon.intValue(getLimits().getMaxInfoTX());
            }
            else
            {
                dataSize = GXCommon.intValue(getLimits().getMaxInfoRX());
            }
        }
        if (count + index[0] > packet.limit())
        {
            count = packet.limit() - index[0];
        }
        packet.position(index[0]);
        byte[] tmp2 = new byte[count];
        packet.get(tmp2);
        tmp.put(tmp2);
        index[0] += count;
        count = tmp.position();
        if (count < dataSize)
        {
            dataSize = count;
        }
        int cnt = (int)(count / dataSize);
        if (count % dataSize != 0)
        {
            ++cnt;
        }
        int start = 0;
        byte[][] buff = new byte[cnt][];
        for (int pos = 0; pos < cnt; ++pos)
        {
            byte id;
            if (pos == 0)
            {
                id = generateIFrame();
            }
            else
            {
                id = generateNextFrame();
            }
            if (start + dataSize > count)
            {
                dataSize = count - start;
            }            
            buff[pos] = addFrame(id, cnt != 1 && pos < cnt - 1, tmp, start, dataSize);
            start += dataSize;
        }
        return buff;
    }

    /** 
     Split the send packet to a size that the device can handle.

     @param packet Packet to send.
     @return Array of byte arrays that are sent to device.
    */
    public final byte[][] splitToBlocks(java.nio.ByteBuffer packet, Command cmd)
    {
        int[] index = new int[1];
        int len = packet.position();
        packet.limit(len);
        packet.position(0);
        if (!getUseLogicalNameReferencing()) //SN
        {
            return splitToFrames(packet, 0, index, len, cmd);
        }
        //If LN
        //Split to Blocks.
        java.util.ArrayList<byte[]> buff = new java.util.ArrayList<byte[]>();
        int blockIndex = 0;
        do
        {
            byte[][] frames = splitToFrames(packet, ++blockIndex, index, getMaxReceivePDUSize(), cmd);
            for(byte[] it : frames)
            {
                buff.add(it);
            }
            if (frames.length != 1)
            {
                expectedFrame += 3;
            }
        }
        while (index[0] < len);
        byte[][] tmp = new byte[buff.size()][];
        int pos = -1;
        for(byte[] it : buff)
        {
            ++pos;
            tmp[pos] = new byte[it.length];
            System.arraycopy(it, 0, tmp[pos], 0, it.length);
        }
        return tmp;
    }

    /** 
     Checks, whether there are any errors on received packet.

     @param sendData Sent data. 
     @param receivedData Received data. 
     @return True, if there are any errors on received data.
    */
    public final Object[][] checkReplyErrors(byte[] sendData, byte[] receivedData) throws Exception
    {
        boolean ret = true;
        if (sendData != null)
        {
            ret = isReplyPacket(sendData, receivedData);
        }
        //If packet is not reply for send packet...
        if (!ret)
        {
            Object[][] list = new Object[1][2];
            list[0][0] = -1;
            list[0][1] = "Not a reply.";
            return list;
        }

        //If we are checking UA or AARE messages.
        if (getLNSettings() == null && getSNSettings() == null) //TODO:
        {
            return null;
        }

        int[] err = new int[1];
        byte[] frame = new byte[1];
        boolean[] packetFull = new boolean[1], wrongCrc = new boolean[1];
        int[] command = new int[1];
        if (sendData != null)
        {            
            getDataFromFrame(java.nio.ByteBuffer.wrap(sendData), null, frame, 
                    false, err, false, packetFull, wrongCrc, command);
            if (!packetFull[0])
            {
                throw new GXDLMSException("Not enought data to parse frame.");
            }
            if (wrongCrc[0])
            {
                throw new GXDLMSException("Wrong Checksum.");
            }
            if (isReceiverReadyRequest(frame[0]) || frame[0] == FrameType.Disconnect.getValue())
            {
                return null;
            }
        }
        command[0] = 0;
        getDataFromFrame(java.nio.ByteBuffer.wrap(receivedData), null, frame, 
                true, err, false, packetFull, wrongCrc, command);
        if (!packetFull[0])
        {
            throw new GXDLMSException("Not enought data to parse frame.");
        }
        if (wrongCrc[0])
        {
            throw new GXDLMSException("Wrong Checksum.");
        }
        if (command[0] == Command.REJECTED.getValue())
        {
            throw new GXDLMSException("Frame rejected.");
        }
        if (err[0] != 0x00)
        {
            String str;
            switch (err[0])
            {
                case 1:
                    str = "Access Error : Device reports a hardware fault.";
                    break;
                case 2:
                    str = "Access Error : Device reports a temporary failure.";
                    break;
                case 3:
                    str = "Access Error : Device reports Read-Write denied.";
                    break;
                case 4:
                    str = "Access Error : Device reports a undefined object.";
                    break;
                case 5:
                    str = "Access Error : Device reports a inconsistent Class or object.";
                    break;
                case 6:
                    str = "Access Error : Device reports a unavailable object.";
                    break;
                case 7:
                    str = "Access Error : Device reports a unmatched type.";
                    break;
                case 8:
                    str = "Access Error : Device reports scope of access violated.";
                    break;
                default:
                    str = "Unknown error: " + err[0];
                    break;
            }
            Object[][] list = new Object[1][2];
            list[0][0] = err[0];
            list[0][1] = str;
            return list;
        }
        return null;
    }

    /** 
     Gets Logical Name settings, read from the device. 
    */
    public final GXDLMSLNSettings getLNSettings()
    {
        return privateLNSettings;
    }
    public final void setLNSettings(GXDLMSLNSettings value)
    {
        privateLNSettings = value;
    }

    /** 
     Gets Short Name settings, read from the device.
    */
    public final GXDLMSSNSettings getSNSettings()
    {
        return privateSNSettings;
    }
    public final void setSNSettings(GXDLMSSNSettings value)
    {
        privateSNSettings = value;
    }

    /** 
     Quality Of Service is an analysis of nonfunctional aspects of the software properties.

     @return 
    */    
    final int getValueOfQualityOfService()
    {
        return privateValueOfQualityOfService;
    }
    void setValueOfQualityOfService(int value)
    {
        privateValueOfQualityOfService = value;
    }

    /** 
     Retrieves the amount of unused bits.

     @return 
    */
    final int getNumberOfUnusedBits()
    {
        return privateNumberOfUnusedBits;
    }
    void setNumberOfUnusedBits(int value)
    {
        privateNumberOfUnusedBits = value;
    }

    /** 
     Generate I-frame: Information frame. Reserved for internal use.
    */
    byte generateIFrame()
    {
        //Expected frame number is increased only when first keep alive msg is send...
        if (!bIsLastMsgKeepAliveMsg)
        {
            ++expectedFrame;
            expectedFrame = (expectedFrame & 0x7);
        }
        return generateNextFrame();
    }

    /** 
     Generate ACK message. Reserved for internal use.

     @return 
    */
    private byte generateNextFrame()
    {
        ++frameSequence;
        frameSequence = (frameSequence & 0x7);
        byte val = (byte)(((frameSequence & 0x7) << 1) & 0xF);
        val |= (byte)(((((expectedFrame & 0x7) << 1) | 0x1) & 0xF) << 4);
        bIsLastMsgKeepAliveMsg = false;
        return val;
    }

    /** 
     Generates Keep Alive frame for keep alive message. Reserved for internal use.

     @return 
    */
    byte generateAliveFrame()
    {
        //Expected frame number is increased only when first keep alive msg is send...
        if (!bIsLastMsgKeepAliveMsg && !getServer())
        {
            ++expectedFrame;
            expectedFrame = (expectedFrame & 0x7);
            bIsLastMsgKeepAliveMsg = true;
        }
        byte val = 1;
        val |= (byte)((((((expectedFrame) & 0x7) << 1) | 0x1) & 0xF) << 4);
        return val;
    }

    private boolean isKeepAlive(byte value)
    {
        if (((value >> 5) & 0x7) == expectedFrame)
        {
            return true;
        }
        return false;
    }

    /** 
     Return true if frame sequences are same. Reserved for internal use.

    */
    private boolean isExpectedFrame(int send, int received)
    {
        //In keep alive msg send ID might be same as receiver ID.
        boolean ret = send == received || ((send >> 5) & 0x7) == ((received >> 1) & 0x7) || ((send & 0x1) == 0x1 && (received & 0x1) == 1); //If echo.
        //If U-Frame...
        if (!ret)
        {
            return ret;
        }
        return ret;
    }

    /** 
     Generate I-frame: Information frame. Reserved for internal use. 

     0 Receive Ready (denoted RR(R)) is a positive acknowledge ACK of all frames
     up to and including frame number R-1.
     1 Reject (denoted RE.J(R)) is a negative acknowledge NAK
     of a Go-back-N mechanism. ie start retransmitting from frame number R.
     2 Receive Not Ready (denoted RNR(R)) is a positive acknowledge of all
     frames up to and including R-1 but the sender must pause until a
     Receive Ready arrives. This can be used to pause the sender because of
     temporary problems at the receiver.
     3 Selective Reject (denoted SREJ(R)) is a negative acknowledge in a
     Selective Repeat mechanism. ie resend only frame R. It is not
     supported in several implementations.

     @param type
     @return 
    */
    private byte generateSupervisoryFrame(byte type)
    {
        ++expectedFrame;
        expectedFrame = (expectedFrame & 0x7);
        byte val = (byte)((((type & 0x3) << 2) | 0x1) & 0xF);
        val |= (byte)(((((expectedFrame & 0x7) << 1) | 0x1) & 0xF) << 4);
        bIsLastMsgKeepAliveMsg = false;
        return val;
    }

    /** 
     Reserved for internal use.

     @param val
     @return 
    */
    private boolean isReceiverReadyRequest(byte val)
    {
        boolean b = (val & 0xF) == 1 && (val >>> 4) == (((expectedFrame & 0x7) << 1) | 0x1);
        return b;
    }


    /** 
     Reserved for internal use.

     @param val
     @return 
    */
    private boolean isRejectedFrame(byte val)
    {
        return (val & 0x07) == 0x07;
    }

    /** 
     Reserved for internal use.
    */
    public final void checkInit()
    {
        if (this.getClientID() == null)
        {
            throw new GXDLMSException("Invalid Client ID");
        }
        if (this.getServerID() == null)
        {
            throw new GXDLMSException("Invalid Server ID");
        }
    }

    /** 
     Reserved for internal use.

     @param address
     @return 
    */
    private byte[] getAddress(Object address)
    {
        if (this.getInterfaceType() == InterfaceType.NET)
        {
            java.nio.ByteBuffer b = java.nio.ByteBuffer.allocate(2);
            b.putShort(((Number)address).shortValue());
            return b.array();
        }
        if (address instanceof Byte)
        {
            return new byte[] {((Number)address).byteValue()};
        }   
        java.nio.ByteBuffer b;
        if (address instanceof Short)
        {
            b = java.nio.ByteBuffer.allocate(2);
            b.putShort(((Number)address).shortValue());
            return b.array();
        }
        if (address instanceof Integer)
        {
            b = java.nio.ByteBuffer.allocate(4);
            b.putInt(((Number)address).intValue());
            return b.array();
        }
        if (getServer())
        {
            return new byte[0];
        }
        throw new GXDLMSException("Invalid Server Address.");
    }

    /** 
     Reserved for internal use.
    */
    final byte[] addFrame(byte Type, boolean moreFrames, java.nio.ByteBuffer data, int index, int count)
    {
        count = (count & 0xFFFF);
        if (data != null)
        {
            data.position(index);
        }
        checkInit();
        byte[] ServerBuff = getAddress(this.getServerID());
        byte[] ClientBuff = getAddress(this.getClientID());
        //Set packet size. BOP + Data size + dest and source size + EOP.
        int len = 7 + ServerBuff.length + ClientBuff.length;
        //If data is added. CRC is count for HDLC frame.
        if ((count) > 0)
        {
            len += count + 2;
        }
        java.nio.ByteBuffer buff = java.nio.ByteBuffer.allocate(len);
        if (this.getInterfaceType() == InterfaceType.NET)
        {
            //Add version 0x0001
            buff.put((byte) 0x00);
            buff.put((byte) 0x01);
            if (getServer())
            {
                //Add Destination (Server)
                buff.put(ServerBuff);
                //Add Source (Client)
                buff.put(ClientBuff);
            }
            else
            {
                //Add Source (Client)
                buff.put(ClientBuff);
                //Add Destination (Server)
                buff.put(ServerBuff);
            }
            //Add data length. 2 bytes.
            buff.putShort((short)count);
            if (data != null)
            {
                buff.put(data.array(), 0, count);
            }
        }
        else
        {
            if (this.getGenerateFrame())
            {
                //HDLC frame opening flag.
                buff.put(GXCommon.HDLCFrameStartEnd);
            }
            //Frame type
            buff.put((byte)(moreFrames ? 0xA8 : 0xA0));
            //Length of msg.
            buff.put((byte)(len - 2));
            if (this.getServer())
            {
                //Client address
                buff.put(ClientBuff);
                //Server address
                buff.put(ServerBuff);
            }
            else
            {
                //Server address
                buff.put(ServerBuff);
                //Client address
                buff.put(ClientBuff);
            }
            //Add DLMS frame type
            buff.put(Type);
            //Count CRC for header.
            int crc;
            if (count > 0)
            {
                int start = 0;
                int cnt = buff.position();
                if (this.getGenerateFrame())
                {
                    --cnt;
                    start = 1;
                }
                crc = GXFCS16.countFCS16(buff, start, cnt);
                buff.putShort((short) crc);
                if (data != null)
                {
                    data.position(index);
                    data.limit(index + count);
                    buff.put(data);
                }
            }
            //If framework is not generating CRC and EOP.
            if (this.getGenerateFrame())
            {
                //Count CRC for HDLC frame.
                crc = GXFCS16.countFCS16(buff, 1, buff.position() - 1);
                buff.putShort((short) crc);
                //EOP
                buff.put(GXCommon.HDLCFrameStartEnd);
            }
        }
        len = buff.position();
        byte tmp[] = new byte[len];
        buff.position(0);
        buff.get(tmp, 0, len);
        return tmp;
    }

    /** 
     Check LLC bytes. Reserved for internal use.
    */
    private boolean checkLLCBytes(java.nio.ByteBuffer buff)
    {
        if (getInterfaceType() == InterfaceType.GENERAL)
        {
            if (getServer())
            {
                return GXCommon.compare(buff, GXCommon.LLCSendBytes);
            }
            return GXCommon.compare(buff, GXCommon.LLCReplyBytes);
        }
        return false;
    }

    /** 
     Reserved for internal use.
    */
    private long getAddress(java.nio.ByteBuffer buff, int[] index, int size)
    {
        if (size == 1)
        {
            return buff.get(index[0]) & 0xFF;
        }
        if (size == 2)
        {
            return buff.getShort(index[0]);
        }
        if (size == 4)
        {
            return buff.getInt(index[0]);
        }
        throw new GXDLMSException("Invalid address size.");
    }

    /** 
     Reserved for internal use.
    */
    public final java.util.Set<RequestTypes> getDataFromFrame(java.nio.ByteBuffer buff, 
            ByteArrayOutputStream data, byte[] frame, boolean bReply, 
            int[] pError, boolean skipLLC, boolean[] packetFull, 
            boolean[] wrongCrc, int[] command) throws Exception
    {
        command[0] = 0;
        wrongCrc[0] = false;
        packetFull[0] = true;
        frame[0] = 0;
        pError[0] = 0;
        int DataLen;
        java.util.Set<RequestTypes> MoreData = EnumSet.noneOf(RequestTypes.class);
        int PacketStartID = 0, len = buff.limit();
        int FrameLen = 0;
        //If DLMS frame is generated.
        if (getInterfaceType() != InterfaceType.NET)
        {
            if (len < 5)
            {
                packetFull[0] = false;
                return MoreData;
            }
            //Find start of HDLC frame.
            while(buff.position() < buff.limit())
            {
                if (buff.get() == GXCommon.HDLCFrameStartEnd)
                {
                    PacketStartID = buff.position() - 1;
                    break;
                }
            }
            if (buff.position() == len) //Not a HDLC frame.
            {
                throw new GXDLMSException("Invalid data format.");
            }
            frame[0] = buff.get();
            //Is there more data available.
            if (frame[0] == GXCommon.HDLCFrameTypeMoreData)
            {
                MoreData = EnumSet.of(RequestTypes.FRAME);
            }
            //If not enought data.
            FrameLen = buff.get();
            //if (len - index + 2 < FrameLen)
            if (len < FrameLen + buff.position() - 1)
            {
                packetFull[0] = false;
                MoreData.clear();
                return MoreData;
            }
            if ((frame[0] != GXCommon.HDLCFrameType && 
                    frame[0] != GXCommon.HDLCFrameTypeMoreData) || 
                    (MoreData.isEmpty() && 
                    buff.get(FrameLen + PacketStartID + 1) != GXCommon.HDLCFrameStartEnd)) 
                //Check EOP - Check BOP
            {
                throw new GXDLMSException("Invalid data format.");
            }
        }
        else
        {
            //Get version
            int ver = (buff.getShort() & 0xFFFF);
            if (ver != 1)
            {
                throw new GXDLMSException("Unknown version.");
            }
        }
        byte[] ServerBuff = getAddress(this.getServerID());
        byte[] ClientBuff = getAddress(this.getClientID());
        if (((getServer() || !bReply) && getInterfaceType() == InterfaceType.GENERAL) || 
            (!getServer() && bReply && (getInterfaceType() == InterfaceType.NET)))
        {
            byte[] pTmp = ServerBuff;
            ServerBuff = ClientBuff;
            ClientBuff = pTmp;
        }
        if (!GXCommon.compare(buff, ClientBuff))
        {
            //If echo.
            if (getInterfaceType() != InterfaceType.NET && FrameLen != 0)
            {
                if (GXCommon.compare(buff, ServerBuff) && 
                    GXCommon.compare(buff, ClientBuff))
                    //Check that server addresses match.
                {
                    buff.position(2 + FrameLen);
                    DataLen = buff.limit() - buff.position() - 1;
                    if (DataLen > 5)
                    {
                        return getDataFromFrame(buff, data, frame, bReply, 
                                pError, skipLLC, packetFull, wrongCrc, command);
                    }
                    packetFull[0] = false;
                    MoreData.clear();
                    return MoreData;                    
                }
            }            
            int tmp[] = new int[]{buff.position()};
            throw new GXDLMSException("Source addresses do not match. It is " + 
                    getAddress(buff, tmp, ClientBuff.length) + ". It should be " + this.getClientID() + ".");
        }
        if (!GXCommon.compare(buff, ServerBuff))
        {
            int tmp[] = new int[]{buff.position()};
            throw new GXDLMSException("Destination addresses do not match. It is " + 
                    getAddress(buff, tmp, ServerBuff.length) + ". It should be " + this.getServerID() + ".");
        }
        if (getInterfaceType() != InterfaceType.NET)
        {
            //Get frame type.
            frame[0] = buff.get();
            //If server has left.
            if (frame[0] == FrameType.DisconnectMode.getValue() || frame[0] == FrameType.Rejected.getValue())
            {
                MoreData = EnumSet.noneOf(RequestTypes.class);                
                command[0] = Command.REJECTED.getValue();
                return MoreData; 
            }
            if (frame[0] == FrameType.SNRM.getValue() || frame[0] == FrameType.Disconnect.getValue())
            {
                //Check that CRC match.
                int crcRead = buff.getShort();
                int crcCount = GXFCS16.countFCS16(buff, PacketStartID + 1, len - PacketStartID - 4);
                if (crcRead != crcCount)
                {
                    packetFull[0] = false;
                    wrongCrc[0] = true;
                    MoreData.clear();
                    return MoreData;                        
                }
                if (frame[0] == FrameType.SNRM.getValue())
                {
                    MoreData = EnumSet.noneOf(RequestTypes.class);                
                    command[0] = Command.Snrm.getValue();
                    return MoreData;
                }
                if (frame[0] == FrameType.Disconnect.getValue())
                {
                    MoreData = EnumSet.noneOf(RequestTypes.class);
                    command[0] = Command.DisconnectRequest.getValue();
                    return MoreData;
                }
                throw new RuntimeException("Invalid frame.");
            }
            else
            {
                //Check that header crc is corrent.
                int crcCount = GXFCS16.countFCS16(buff, PacketStartID + 1, buff.position() - PacketStartID - 1);
                int crcRead = buff.getShort() & 0xFFFF;
                if (crcRead != crcCount)
                {
                    //Do nothing because Actaris is counting wrong CRC to the header.
                    System.out.println("Wrong header CRC.");
                }
                //Check that CRC match.
                crcCount = GXFCS16.countFCS16(buff, PacketStartID + 1, len - PacketStartID - 4);
                crcRead = buff.getShort(len - 3) & 0xFFFF;
                if (crcRead != crcCount)
                {
                    System.out.println(GXCommon.toHex(buff.array()));
                    wrongCrc[0] = true;
                    MoreData.clear();
                    return MoreData;
                }
                //CheckLLCBytes returns false if LLC bytes are not used.
                if (!skipLLC && checkLLCBytes(buff))
                {
                    //TODO: LLC voi skipata SNRM ja Disconnect.
                    //Check response.
                    command[0] = buff.get(buff.position()) & 0xFF;
                    if (command[0] == 0x60 || command[0] == 0x62)
                    {
                        MoreData = EnumSet.noneOf(RequestTypes.class);
                    }
                    else if (bReply && command[0] != 0x61 && command[0] != 0x60)
                    {
                        //If LN is used, check is there more data available.
                        if (this.getUseLogicalNameReferencing())
                        {
                            if (!getLNData(buff, pError, MoreData, command[0]))
                            {
                                packetFull[0] = false;
                                MoreData.clear();
                                return MoreData;
                            }
                        }
                        else
                        {
                            getSNData(buff, pError, command);                                                  
                        }
                    }
                }
                //Skip data header and data CRC and EOP.
                if (buff.position() + 3 > buff.limit())
                {
                    if (getServer())
                    {
                        MoreData.add(RequestTypes.FRAME);
                    }                        
                }
                else if (data != null)
                {   
                    int index = buff.position();
                    data.write(buff.array(), index, buff.limit() - index - 3);                       
                }
            }
        }
        else
        {
            DataLen = buff.getShort();
            if (DataLen + buff.position() > len) //If frame is not read complete.
            {
                packetFull[0] = false;                
                MoreData.clear();
                return MoreData;
            }
            // IEC62056-53 Sections 8.3 and 8.6.1
            // If Get.Response.Normal.
            command[0] = buff.get(buff.position()) & 0xFF;
            if (command[0] == Command.Aarq.getValue() ||
                    command[0] == Command.DisconnectRequest.getValue() ||
                    command[0] == Command.DisconnectResponse.getValue())
            {
            }
            else if (bReply && command[0] != 0x61 && command[0] != 0x60)
            {
                //If LN is used, check is there more data available.
                if (this.getUseLogicalNameReferencing())
                {
                    getLNData(buff, pError, MoreData, command[0]);                    
                }
                else
                {
                    getSNData(buff, pError, command);
                }
            }
            if (data != null)
            {
                int index = buff.position();
                data.write(buff.array(), index, buff.limit() - index);
            }
        }
        return MoreData;
    }

    private static void getSNData(java.nio.ByteBuffer buff, int[] pError, int[] command)
    {
        int res = command[0];
        //Check that this is reply
        if (res != Command.ReadRequest.getValue() && 
            res != Command.WriteRequest.getValue() && 
            res != Command.SetRequest.getValue() && 
            res != Command.SetResponse.getValue() && 
            res != Command.ReadResponse.getValue() && 
            res != Command.WriteResponse.getValue() && 
            res != Command.GetRequest.getValue() && 
            res != Command.GetResponse.getValue() && 
            res != Command.MethodRequest.getValue() && 
            res != Command.MethodResponse.getValue())
        {
            throw new GXDLMSException("Invalid command");
        }        
        buff.get();
        if (res == Command.ReadResponse.getValue() || res == Command.WriteResponse.getValue())
        {
            //Add reply status.
            buff.get();
            boolean bIsError = (buff.get() != 0);
            if (bIsError)
            {
                pError[0] = buff.get();
            }
        }
    }   
    
    private boolean getLNData(java.nio.ByteBuffer buff, int[] pError, java.util.Set<RequestTypes> MoreData, int command)
    {
        int res = command;
        //Get count.
        buff.get();
        //If meter returns exception.
        if (res == 0xD8)
        {
            StateError stateError = StateError.forValue(buff.get());
            ServiceError serviceError = ServiceError.forValue(buff.get());
            throw new GXDLMSException(stateError.toString() + " " + serviceError.toString());
        }
        boolean server = this.getServer();
        if (res != 0x60 && res != 0x63 && 
                res != Command.GetResponse.getValue() && 
                res != Command.SetResponse.getValue() && 
                res != Command.SetRequest.getValue() && 
                res != Command.GetRequest.getValue() && 
                res != Command.MethodRequest.getValue() &&
                res != Command.MethodResponse.getValue())
        {
            throw new GXDLMSException("Invalid response");
        }
        byte AttributeID = buff.get();
        //Skip Invoke ID and priority.
        buff.get();
        if (server && AttributeID == 0x2)
        {
            MoreData.clear();
            MoreData.add(RequestTypes.DATABLOCK);
        }
        else if (res == Command.SetRequest.getValue() ||
                res == Command.MethodRequest.getValue())
        {
            MoreData.clear();
        }
        else
        {
            if (server && AttributeID == 0x01)
            {
                MoreData.clear();
            }
            else
            {
                byte Priority = buff.get();
                if (AttributeID == 0x01 && Priority != 0)
                {
                    pError[0] = buff.get();
                }
                else
                {
                    if (AttributeID == 0x02)
                    {
                        if (Priority == 0)
                        {
                            MoreData.add(RequestTypes.DATABLOCK);
                        }
                        packetIndex = buff.getInt();
                        buff.get();
                        //Get items count.
                        int[] index = new int[]{buff.position()};
                        GXCommon.getObjectCount(buff, index);
                        buff.position(index[0]);
                    }
                }
            }
        }
        return true;
    }
    
        static void getActionInfo(ObjectType objectType, int[] value, int[] count) throws RuntimeException
    {        
        switch (objectType)
        {
            case DATA:
            case ACTION_SCHEDULE:
            case ALL:
            case AUTO_ANSWER:
            case AUTO_CONNECT:
            case MAC_ADDRESS_SETUP:
            case EVENT:
            case GPRS_SETUP:
            case IEC_HDLC_SETUP:
            case IEC_LOCAL_PORT_SETUP:
            case IEC_TWISTED_PAIR_SETUP:
            case MODEM_CONFIGURATION:
            case PPP_SETUP:
            case REGISTER_MONITOR:
            case REMOTE_ANALOGUE_CONTROL:
            case REMOTE_DIGITAL_CONTROL:
            case SCHEDULE:
            case SMTP_SETUP:
            case STATUS_MAPPING:
            case TCP_UDP_SETUP:
            case TUNNEL:
            case UTILITY_TABLES:
                throw new RuntimeException("Target do not support Action.");
            case ACTIVITY_CALENDAR:
                value[0] = 0x50;
                count[0] = 1;
                break;
            case ASSOCIATION_LOGICAL_NAME:
                value[0] = 0x60;
                count[0] = 4;
                break;
            case ASSOCIATION_SHORT_NAME:
                value[0] = 0x20;
                count[0] = 8;
                break;
            case CLOCK:
                value[0] = 0x60;
                count[0] = 6;
                break;
            case DEMAND_REGISTER:
                value[0] = 0x48;
                count[0] = 2;
                break;
            case EXTENDED_REGISTER:
                value[0] = 0x38;
                count[0] = 1;
                break;
            case IP4_SETUP:
                value[0] = 0x60;
                count[0] = 3;
                break;
            case MBUS_SLAVE_PORT_SETUP:
                value[0] = 0x60;
                count[0] = 8;
                break;
            case PROFILE_GENERIC:
                value[0] = 0x58;
                count[0] = 4;
                break;
            case REGISTER:
                value[0] = 0x28;
                count[0] = 1;
                break;
            case REGISTER_ACTIVATION:
                value[0] = 0x30;
                count[0] = 3;
                break;
            case REGISTER_TABLE:
                value[0] = 0x28;
                count[0] = 2;
                break;
            case SAP_ASSIGNMENT:
            case SCRIPT_TABLE:
                value[0] = 0x20;
                count[0] = 1;
                break;
            case SPECIAL_DAYS_TABLE:
                value[0] = 0x10;
                count[0] = 2;
                break;
            default:     
                count[0] = value[0] = 0;
                break;
        }
    }
}