//package Experience.Lib.Suppliers.Drops;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.zip.Inflater;

//import Experience.Data.ConnectionManager;
//import Experience.Data.DBServerDrivers;
//import Experience.Data.ExpDate;

public class RISICOSupplier {

	private static final String QUERY_GET_DATA = "{call SPStorm_getData (?, ?)}";
	
//	private HashMap<Integer, HashMap<String, float[]>> m_oDataMap = new HashMap<Integer, HashMap<String,float[]>>();

	private CallableStatement m_oStatement = null;

	private int m_iDimX;

	private int m_iDimY;
	
	private float[] m_image;
	
	
	public RISICOSupplier(String sServer) 
	{
		ConnectionManager oCnxManager = new ConnectionManager(sServer, "i2storm", "i2storm");
		try 
		{
			
			oCnxManager.OpenConnection();
			Connection oCnx = oCnxManager.GetConnection();
			m_oStatement = oCnx.prepareCall(QUERY_GET_DATA);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			m_oStatement = null;
		}
	}
	
	public boolean LoadVariable(String sDtRun, String sVar) {		
		if (m_oStatement == null) return false;
		try {
			//ExpDate oDtRun = new ExpDate(sDtRun);		
			m_oStatement.setString(1, sVar);
			m_oStatement.setString(2, sDtRun);		
			ResultSet oRs = m_oStatement.executeQuery();
			if (!oRs.next()) return false;
			m_iDimX = oRs.getInt("nX");
			m_iDimY = oRs.getInt("nY");
//			int iNumLevels = oRs.getInt("nZ"); 
//			int iTimelineLen = oRs.getInt("nT");

			String sDescr = oRs.getString("descr");
			String[] asTokens = sDescr.split("#");
//			if (asTokens.length == iNumLevels+1) {

			int iBufferDim = Integer.parseInt(asTokens[0]);

		
			//int iDT = oRs.getInt("dT");

			
			//leggo il blob
			byte[] abBlob = oRs.getBytes("blob");

			int iGlobalDim = m_iDimX*m_iDimY;//*iNumLevels//*iTimelineLen;

			ByteBuffer oBuff = ByteBuffer.allocate(iGlobalDim*4);
			oBuff.order(ByteOrder.LITTLE_ENDIAN);

			//Decomprimo
			Inflater decompresser = new Inflater();
			decompresser.setInput(abBlob, 0, iBufferDim);
			//int iResultLength = 
			decompresser.inflate(oBuff.array());
			decompresser.end();
			
			m_image = new float[iGlobalDim];
			
			for(int i=0; i<iGlobalDim; i++)
				m_image[i] = oBuff.getFloat();
			
			return true;

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;		
	}
	
	public float[] GetImage() {
		return m_image;
	}

	public int GetDimX() {
		return m_iDimX;
	}


	public int GetDimY() {
		return m_iDimY;
	}
	
	public String[] GetVariables() {
		return new String[] {
				"FIRE_FWI",
				"FIRE_FWIP",
				"FIRE_INT",
				"FIRE_IRP",
				"FIRE_TEM",
				"FIRE_UMB",
				"FIRE_VEL",
				"FIRE_VMA",
				"FIRE_WIN"

		};
	}

	public static void main(String argv[])
	{
	
		RISICOSupplier rscs = null;
		rscs = new RISICOSupplier("130.251.104.249");
		
		rscs.LoadVariable("200701010000", "FIRE_UMB");
		
		float[] var = rscs.GetImage();
		
		for(int i=0; i<var.length; i++)
			System.out.println(var[i]);
		
		return;
	}
}
